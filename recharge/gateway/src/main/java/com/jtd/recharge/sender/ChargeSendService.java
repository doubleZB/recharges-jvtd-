package com.jtd.recharge.sender;

import com.alibaba.fastjson.JSON;
import com.aliyun.mns.client.CloudQueue;
import com.aliyun.mns.client.MNSClient;
import com.aliyun.mns.model.Message;
import com.github.pagehelper.StringUtil;
import com.jtd.recharge.base.cache.redis.RedisTemplate;
import com.jtd.recharge.base.constant.SysConstants;
import com.jtd.recharge.base.util.ApplicationContextUtil;
import com.jtd.recharge.base.util.CommonUtil;
import com.jtd.recharge.base.util.MessageClient;
import com.jtd.recharge.dao.bean.CallbackReport;
import com.jtd.recharge.bean.ChargeMessage;
import com.jtd.recharge.bean.ChargeRequest;
import com.jtd.recharge.bean.ChargeSubmitResponse;
import com.jtd.recharge.connect.base.ChargeAdapter;
import com.jtd.recharge.dao.mapper.ChargeOrderDetailMapper;
import com.jtd.recharge.dao.mapper.ChargeOrderMapper;
import com.jtd.recharge.dao.po.ChargeOrder;
import com.jtd.recharge.dao.po.ChargeOrderDetail;
import com.jtd.recharge.dao.po.User;
import com.jtd.recharge.service.charge.order.PushCallbackService;
import com.jtd.recharge.service.user.BalanceService;
import com.jtd.recharge.service.user.UserService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @autor jipengkun
 */
@Service("chargeSendService")
public class ChargeSendService {

    private Log log = LogFactory.getLog(this.getClass());

    @Resource
    ChargeOrderMapper chargeOrderMapper;

    @Resource
    ChargeOrderDetailMapper chargeOrderDetailMapper;

    @Resource
    ChargeAdapter chargeAdapter;

    @Resource
    BalanceService balanceService;

    @Resource
    PushCallbackService pushCallbackService;

    @Resource
    UserService userService;
    @Resource(name="commRedisTemplate")
    RedisTemplate redisTemplate;


    public void chargeBase(ChargeMessage chargeMessage){
        long startReportHandletime=System.currentTimeMillis();//程序开始时长
        List<ChargeRequest> supplyList = chargeMessage.getSupplyList();
        ChargeRequest chargeRequests =  supplyList.get(0);
        String timelenghtsBase=redisTemplate.get(chargeRequests.getSupplyName()+SysConstants.TIME_SUPPLY_SUBMIT);
        log.info("供应商"+supplyList.get(0).getSupplyName()+"推送時長---" +timelenghtsBase);
        if(timelenghtsBase!=null){
            //如果超过限定时长，则将该商家的回调放到队列中执行
            Long times=Long.valueOf(timelenghtsBase);
            if(times>SysConstants.TIMELONGHT) {
                MNSClient clients = MessageClient.getClient();
                CloudQueue queues = clients.getQueueRef(SysConstants.Queue.SUBMIT_SLOW_QUEUE);
                Message message = new Message();
                message.setMessageBody(JSON.toJSONString(chargeMessage));
                Message putMsg = queues.putMessage(message);
                log.info("Send message id is: " + putMsg.getMessageId());
                return;
            }
        }
        chargeSend(chargeMessage);
        long endReportHandletime=System.currentTimeMillis();
        long taking=endReportHandletime-startReportHandletime;
        log.info("订单编号"+chargeRequests.getChannelNum()+",手机号:"+chargeRequests.getMobile()+"订单发送执行时长:"+taking);
        if(timelenghtsBase==null) {
            redisTemplate.setExpire(chargeRequests.getSupplyName() + SysConstants.TIME_SUPPLY_SUBMIT,String.valueOf(taking),3600*24);
        }
    }

    public void chargeSend(ChargeMessage chargeMessage) {
        try {
            List<ChargeRequest> supplyList = chargeMessage.getSupplyList();
            ChargeRequest chargeRequests =  supplyList.get(0);
            String orderNum = chargeMessage.getOrderNum();
            int businessType = chargeMessage.getBusinessType();
            log.info("7、订单进入发送流程----mobile ="+chargeRequests.getMobile()+" orderNum=" + orderNum);

            //只有一个供应商
            if(supplyList.size() == 1) {
                String channelNum = CommonUtil.getChannelNum();//流水
                ChargeRequest chargeRequest =  supplyList.get(0);

                log.info("7、订单进入发送流程----mobile ="+chargeRequests.getMobile()+" orderNum=" + orderNum+"=========供应商SupplyName:"+chargeRequest.getSupplyName());


                chargeRequest.setChannelNum(channelNum);//设置流水

                ChargeSubmitResponse chargeSubmitResponse = chargeAdapter.chargeRequest(chargeRequest);

                //添加订单明细
                addOrderDetail(businessType,orderNum,chargeRequest,chargeSubmitResponse);

                //更新订单
                if(chargeSubmitResponse.getStatus().equals(ChargeSubmitResponse.Status.UNKNOWN)) {
                    //如果提交成功,更新订单状态为充值中
                    chargeOrderMapper.updateStatusByOrderNum(orderNum,ChargeOrder.OrderStatus.CHARGEING_UNKNOWN,chargeRequest.getSupplyId());
                    log.info("9、订单进入更新数据阶段----mobile ="+chargeRequests.getMobile()+" orderNum=" + orderNum+"  提交数据：异常！更新数据：成功");
                }
                if(chargeSubmitResponse.getStatus().equals(ChargeSubmitResponse.Status.SUCCESS)) {
                    //如果提交成功,更新订单状态为充值中
                    chargeOrderMapper.updateStatusByOrderNum(orderNum,ChargeOrder.OrderStatus.CHARGEING,chargeRequest.getSupplyId());
                    log.info("9、订单进入更新数据阶段----mobile ="+chargeRequests.getMobile()+" orderNum=" + orderNum+"  提交数据：成功！更新数据：成功");
                }
                if(chargeSubmitResponse.getStatus().equals(ChargeSubmitResponse.Status.FAIL)) {
                    log.info("9、订单进入更新数据阶段----mobile ="+chargeRequests.getMobile()+" orderNum=" + orderNum+"  提交数据：失败！原因："+chargeSubmitResponse.getStatusMsg());
                    //退款
                    ChargeOrder chargeOrder = chargeOrderMapper.selectOrderByOrderNum(CommonUtil.getOrderTableName(orderNum),orderNum);
                    log.info("10、订单进入更新数据阶段----mobile ="+chargeRequests.getMobile()+" orderNum=" + orderNum+"  提交数据：失败！即将退款---");

                    int refundStatus = ChargeOrder.RefundState.Refund_SUCCESS;//退款成功
                    try {
                        balanceService.refund(chargeOrder.getUserId(), chargeOrder.getAmount(), chargeOrder.getBusinessType(), orderNum);
                        log.info("11、订单进入更新数据阶段----mobile ="+chargeRequests.getMobile()+" orderNum=" + orderNum+"  提交数据：失败！退款：成功！");
                    }catch (Exception e) {
                        log.info("11、订单进入更新数据阶段----mobile ="+chargeRequests.getMobile()+" orderNum=" + orderNum+"  提交数据：失败！退款：失败！原因："+e.getMessage());
                        refundStatus = ChargeOrder.RefundState.Refund_FAIL;//退款失败
                        log.error("11、订单进入更新数据阶段----mobile ="+chargeRequests.getMobile()+" orderNum=" + orderNum+"  提交数据：失败！退款：失败！原因："+e.getMessage(),e);
                    }

                    //更新订单状态失败
                    ChargeOrder order = new ChargeOrder();
                    order.setOrderNum(orderNum);
                    order.setStatus(ChargeOrder.OrderStatus.CHARGE_FAIL);
                    order.setSupplyId(chargeRequest.getSupplyId());
                    order.setRefundStatus(refundStatus);
                    order.setTable(CommonUtil.getOrderTableName(orderNum));//设置查询的表
                    order.setOrderReturnTime(new Date());
                    chargeOrderMapper.updateReturnStatusByOrderNum(order);
                    log.info("12、订单进入更新数据阶段----mobile ="+chargeRequests.getMobile()+" orderNum=" + orderNum+"  更新订单数据：成功！即将推送状态报告---");

                    //连接消息队列服务
                    MNSClient client = MessageClient.getClient();
                    CloudQueue queue = client.getQueueRef(SysConstants.Queue.PUSH_QUEUE);

                    //推送回调状态
                    User user = userService.findUserByUserId(chargeOrder.getUserId());
                    String token = user.getToken();
                    CallbackReport callbackReport = new CallbackReport();
                    callbackReport.setStatus(new Integer(ChargeOrder.OrderStatus.CHARGE_FAIL).toString());
                    callbackReport.setMobile(chargeRequest.getMobile());
                    callbackReport.setCustomId(chargeOrder.getCustomid());
                    callbackReport.setToken(token);
                    callbackReport.setOrderNum(chargeOrder.getOrderNum());
                    callbackReport.setCallbackUrl(chargeOrder.getCallbackUrl());
                    callbackReport.setPushSum(3);

                    //从redis中获取商家执行时长记录，将执行慢的放入队列中执行
                    String timelenghts=redisTemplate.get(String.valueOf(chargeOrder.getUserId())+SysConstants.TIME_USER_SUBMIT);
                    log.info("商家"+chargeOrder.getUserId()+"推送時長---" +timelenghts);

                    if(timelenghts!=null){
                        //如果超过限定时长，则将该商家的回调放到队列中执行
                        Long time=Long.valueOf(timelenghts);
                        if(time>SysConstants.TIMELONGHT){
                            //将要推送的内容放到队列中
                            Message message = new Message();
                            message.setMessageBody(JSON.toJSONString(callbackReport));
                            Message putMsg = queue.putMessage(message);
                            log.info("submit-商家"+chargeOrder.getUserId()+"订单号:"+orderNum+"进队列Push回调---");
                        }else{
                            pushCallbackService.pushCallback(callbackReport);
                        }
                    }else{
                        long startPushTime=System.currentTimeMillis();//开始时间
                        pushCallbackService.pushCallback(callbackReport);
                        long endPushTime=System.currentTimeMillis();//结束时间
                        long timelenght=endPushTime-startPushTime;//执行时长
                        redisTemplate.setExpire(String.valueOf(chargeOrder.getUserId())+SysConstants.TIME_USER_SUBMIT,String.valueOf(timelenght),3600*24);
                        log.info("商家"+chargeOrder.getUserId()+"订单编号"+orderNum+",手机号:"+chargeRequests.getMobile()+"推送状态执行时长:"+timelenght);
                    }
                    log.info("13、订单进入更新数据阶段----mobile ="+chargeRequests.getMobile()+" orderNum=" + orderNum+"  更新订单数据：成功！状态报告推送完毕！");
                }
                return;
            }


            //多于一个供应商,进行分流,提交失败后轮询提交
            if(supplyList.size() > 1) {
                //先分流
                ChargeRequest chargeRequest = LoadBalance.loadRoute(supplyList);
                String routeSupplyName = chargeRequest.getSupplyName();//分发到的供应商名称
                log.info("分流到的供应商---" + routeSupplyName);

                String channelNum = CommonUtil.getChannelNum();
                chargeRequest.setChannelNum(channelNum);//提交流水
                //提交接口
                ChargeSubmitResponse flowSubmitResponse = chargeAdapter.chargeRequest(chargeRequest);
                log.info("supplyName" + routeSupplyName);

                //提交成功返回
                if(ChargeSubmitResponse.Status.SUCCESS.equals(flowSubmitResponse.getStatus())) {
                    chargeOrderMapper.updateStatusByOrderNum(orderNum, ChargeOrder.OrderStatus.CHARGEING, chargeRequest.getSupplyId());
                    addOrderDetail(businessType, orderNum, chargeRequest, flowSubmitResponse);
                    return;
                }

                //分流第一次提交失败,更新明细
                if(ChargeSubmitResponse.Status.FAIL.equals(flowSubmitResponse.getStatus())) {
                    addOrderDetail(businessType,orderNum,chargeRequest,flowSubmitResponse);
                }

                //提交失败后,轮询其他供应商
                for(int i=0;i<supplyList.size();i++) {
                    ChargeRequest chargeRequestRoute = supplyList.get(i);
                    //如果是第一次提交的供应商则跳过
                    if(chargeRequestRoute.getSupplyName().equals(routeSupplyName)) {
                        continue;
                    }
                    String channelNumRoute = CommonUtil.getChannelNum();
                    chargeRequestRoute.setChannelNum(channelNumRoute);//提交流水

                    //提交请求
                    ChargeSubmitResponse submitResponseRoute = chargeAdapter.chargeRequest(chargeRequestRoute);
                    log.info("supplyName" + chargeRequestRoute.getSupplyName());

                    //更新定单明细
                    addOrderDetail(businessType,orderNum,chargeRequestRoute,submitResponseRoute);

                    if(submitResponseRoute.getStatus().equals(ChargeSubmitResponse.Status.SUCCESS)) {
                        //如果提交成功,更新订单状态为充值中
                        chargeOrderMapper.updateStatusByOrderNum(orderNum,ChargeOrder.OrderStatus.CHARGEING,chargeRequestRoute.getSupplyId());
                        return;
                    }

                    //最后一次还是提交失败
                    if(submitResponseRoute.getStatus().equals(ChargeSubmitResponse.Status.FAIL)) {
                        if(i == (supplyList.size() - 1)) {
                            //退款
                            ChargeOrder chargeOrder = chargeOrderMapper.selectOrderByOrderNum(CommonUtil.getOrderTableName(orderNum),orderNum);
                            int refundStatus = ChargeOrder.RefundState.Refund_SUCCESS;//退款成功
                            try {
                                balanceService.refund(chargeOrder.getUserId(), chargeOrder.getAmount(), chargeOrder.getBusinessType(), orderNum);
                            }catch (Exception e) {
                                log.error("退款失败",e);
                                refundStatus = ChargeOrder.RefundState.Refund_FAIL;//退款失败
                            }
                            //如果最后一次还是提交失败则,更新订单状态为失败
                            ChargeOrder order = new ChargeOrder();
                            order.setOrderNum(orderNum);
                            order.setStatus(ChargeOrder.OrderStatus.CHARGE_FAIL);
                            order.setSupplyId(chargeRequestRoute.getSupplyId());
                            order.setRefundStatus(refundStatus);
                            order.setTable(CommonUtil.getOrderTableName(orderNum));//设置查询的表
                            chargeOrderMapper.updateReturnStatusByOrderNum(order);

                            //连接消息队列服务
                            MNSClient client = MessageClient.getClient();
                            CloudQueue queue = client.getQueueRef(SysConstants.Queue.PUSH_QUEUE);

                            //推送回调状态
                            User user = userService.findUserByUserId(chargeOrder.getUserId());
                            String token = user.getToken();
                            CallbackReport callbackReport = new CallbackReport();
                            callbackReport.setStatus(new Integer(ChargeOrder.OrderStatus.CHARGE_FAIL).toString());
                            callbackReport.setMobile(chargeRequest.getMobile());
                            callbackReport.setCustomId(chargeOrder.getCustomid());
                            callbackReport.setToken(token);
                            callbackReport.setOrderNum(chargeOrder.getOrderNum());
                            callbackReport.setCallbackUrl(chargeOrder.getCallbackUrl());
                            callbackReport.setPushSum(3);


                            //从redis中获取商家执行时长记录，将执行慢的放入队列中执行
                            String timelenghts=redisTemplate.get(String.valueOf(chargeOrder.getUserId())+SysConstants.TIME_USER_SUBMIT);
                            log.info("商家"+chargeOrder.getUserId()+"推送時長---" +timelenghts);

                            if(timelenghts!=null){
                                //如果超过限定时长，则将该商家的回调放到队列中执行
                                Long time=Long.valueOf(timelenghts);
                                if(time>SysConstants.TIMELONGHT){
                                    //将要推送的内容放到队列中
                                    Message message = new Message();
                                    message.setMessageBody(JSON.toJSONString(callbackReport));
                                    Message putMsg = queue.putMessage(message);
                                    log.info("submit-商家"+chargeOrder.getUserId()+"订单号:"+orderNum+"进队列Push回调---");
                                }else{
                                   pushCallbackService.pushCallback(callbackReport);
                                }
                            }else{
                                long startPushTime=System.currentTimeMillis();//开始时间
                                pushCallbackService.pushCallback(callbackReport);
                                long endPushTime=System.currentTimeMillis();//结束时间
                                long timelenght=endPushTime-startPushTime;//执行时长
                                redisTemplate.setExpire(String.valueOf(chargeOrder.getUserId())+SysConstants.TIME_USER_SUBMIT,String.valueOf(timelenght),3600*24);
                                log.info("商家"+chargeOrder.getUserId()+"订单编号"+orderNum+",手机号:"+chargeRequests.getMobile()+"推送状态执行时长:"+timelenght);
                            }
                        }
                        continue;
                    }
                }
            }
        }catch (Exception e) {
            log.error("处理消息异常"+e.getMessage(),e);
            //消费失败
        }
        return;
    }



    /**
     * 更新订单明细
     */
    public void addOrderDetail(int businessType,String orderNum,
                                  ChargeRequest chargeRequest,ChargeSubmitResponse flowSubmitResponse) {
        //更新定单明细
        ChargeOrderDetail orderDetail = new ChargeOrderDetail();
        orderDetail.setBusinessType(businessType);
        orderDetail.setChannelNum(chargeRequest.getChannelNum());//渠道流水
        orderDetail.setSupplyChannelNum(flowSubmitResponse.getSupplyChannelNum());//通道流水
        orderDetail.setOrderNum(orderNum);
        orderDetail.setMobile(chargeRequest.getMobile());
        orderDetail.setSupplyId(chargeRequest.getSupplyId());
        orderDetail.setStatus(new Integer(flowSubmitResponse.getStatus()));
        orderDetail.setSubmitRspcode(flowSubmitResponse.getStatusCode());
        orderDetail.setSubmitTime(new Date());
        orderDetail.setReturnRspcode(flowSubmitResponse.getStatusMsg());
       /* if(flowSubmitResponse.getSupplyOrderNum()!=null) {
            redisTemplate.set(flowSubmitResponse.getSupplyChannelNum(), flowSubmitResponse.getSupplyOrderNum());
        }*/
        if(ChargeSubmitResponse.Status.FAIL.equals(flowSubmitResponse.getStatus())) {
            orderDetail.setReturnTime(new Date());
        }
        chargeOrderDetailMapper.insertSelective(orderDetail);
    }
}
