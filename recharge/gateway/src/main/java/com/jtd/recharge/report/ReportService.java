package com.jtd.recharge.report;

import com.alibaba.fastjson.JSON;
import com.aliyun.mns.client.CloudQueue;
import com.aliyun.mns.client.MNSClient;
import com.aliyun.mns.model.Message;
import com.jtd.recharge.base.cache.redis.RedisTemplate;
import com.jtd.recharge.base.constant.SysConstants;
import com.jtd.recharge.base.util.CommonUtil;
import com.jtd.recharge.base.util.MessageClient;
import com.jtd.recharge.dao.bean.CallbackReport;
import com.jtd.recharge.bean.ChargeReport;
import com.jtd.recharge.dao.mapper.*;
import com.jtd.recharge.dao.po.*;
import com.jtd.recharge.service.charge.order.PushCallbackService;
import com.jtd.recharge.service.user.BalanceService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @autor jipengkun
 */
@Service("reportService")
//@Transactional(readOnly = true)
public class ReportService {


    private Log log = LogFactory.getLog(this.getClass());

    @Resource
    ChargeOrderMapper chargeOrderMapper;

    @Resource
    ChargeOrderDetailMapper chargeOrderDetailMapper;

    @Resource
    public BalanceService balanceService;

    @Resource
    UserBalanceMapper userBalanceMapper;

    @Resource
    UserBalanceDetailMapper userBalanceDetailMapper;

    @Resource
    UserMapper userMapper;

    @Resource
    PushCallbackService pushCallbackService;

    @Resource(name="commRedisTemplate")
    RedisTemplate redisTemplate;



    //    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public void reportHandle(ChargeReport chargeReport) {
        String result=null;
        log.info("即将处理状态报告："+ JSON.toJSONString(chargeReport));

        long startReportHandletime=System.currentTimeMillis();//程序开始时长
        /**
         * 判断重复回调
         */
        ChargeOrderDetail orderDetail = chargeOrderDetailMapper.selectOrderNumByChannelNum(
                CommonUtil.getOrderDetailTableName(chargeReport.getChannelNum()),chargeReport.getChannelNum());

        if (orderDetail==null){
            log.info("没有找到该笔订单---" + JSON.toJSONString(chargeReport));
            return;
        }
        if(orderDetail.getReturnTime() != null) {
            log.info("channelNum 已经回调,不重复处理---" + orderDetail.getChannelNum());
            return;
        }


        try {
            /**
             * 回执成功
             */
            if (ChargeReport.ChargeReportStatus.SUCCESS.equals(chargeReport.getStatus())) {
                log.info("订单充值成功---" + JSON.toJSONString(chargeReport));
                /**
                 *更新流水状态成功
                 */
                long startUpdateChargeTime=System.currentTimeMillis();
                ChargeOrderDetail chargeOrderDetail = new ChargeOrderDetail();
                chargeOrderDetail.setChannelNum(chargeReport.getChannelNum());
                chargeOrderDetail.setStatus(new Integer(chargeReport.getStatus()));
                chargeOrderDetail.setReturnTime(new Date());
                chargeOrderDetail.setTableName(CommonUtil.getOrderDetailTableName(chargeReport.getChannelNum()));//设置查询的表
                chargeOrderDetailMapper.updateByChannelNum(chargeOrderDetail);

                long endUpdateChargeTime=System.currentTimeMillis();
                log.info("订单编号"+orderDetail.getOrderNum()+",手机号:"+orderDetail.getMobile()+"成功-更新流水执行时长:"+(endUpdateChargeTime-startUpdateChargeTime));
                /**
                 * 更新订单状态成功
                 */
                long startUpdateOrderTime=System.currentTimeMillis();

                ChargeOrder chargeOrderParam = new ChargeOrder();
                chargeOrderParam.setOrderNum(orderDetail.getOrderNum());
                chargeOrderParam.setStatus(ChargeOrder.OrderStatus.CHARGE_SUCCESS);
                chargeOrderParam.setSupplyId(orderDetail.getSupplyId());
                chargeOrderParam.setTable(CommonUtil.getOrderTableName(orderDetail.getOrderNum()));//设置查询的表
                chargeOrderParam.setOrderReturnTime(new Date());
                chargeOrderMapper.updateReturnStatusByOrderNum(chargeOrderParam);

                long endUpdateOrderTime=System.currentTimeMillis();
                log.info("订单编号"+orderDetail.getOrderNum()+",手机号:"+orderDetail.getMobile()+"成功-更新订单执行时长:"+(endUpdateOrderTime-startUpdateOrderTime));

                /**
                 * 根据订单号获取订单信息
                 */
                long startOrderInfoTime=System.currentTimeMillis();

                ChargeOrder chargeOrder = chargeOrderMapper.selectOrderByOrderNum(CommonUtil.getOrderTableName(orderDetail.getOrderNum()),orderDetail.getOrderNum());
                int userId = chargeOrder.getUserId();
                User user = getUserByUserId(userId);//商户token
                String token = user.getToken();
                Integer pushSum = user.getPushSum();

                long endOrderInfoTime=System.currentTimeMillis();
                log.info("订单编号"+orderDetail.getOrderNum()+",手机号:"+orderDetail.getMobile()+"成功-查询订单信息执行时长:"+(endOrderInfoTime-startOrderInfoTime));

                /**
                 * 推送状态
                 */
                log.info("成功-即将推送状态---" +JSON.toJSONString(chargeReport));
                //连接消息队列服务
                MNSClient client = MessageClient.getClient();
                CloudQueue queue = client.getQueueRef(SysConstants.Queue.PUSH_QUEUE);

                String customId = chargeOrder.getCustomid();
                String mobile = chargeOrder.getRechargeMobile();
                CallbackReport callbackReport = new CallbackReport();
                callbackReport.setStatus(new Integer(ChargeOrder.OrderStatus.CHARGE_SUCCESS).toString());
                callbackReport.setMobile(mobile);
                callbackReport.setCustomId(customId);
                callbackReport.setToken(token);
                callbackReport.setOrderNum(chargeOrder.getOrderNum());
                callbackReport.setCallbackUrl(chargeOrder.getCallbackUrl());
                callbackReport.setPushSum(pushSum);

                //从redis中获取商家执行时长记录，将执行慢的放入队列中执行
                String timelenghts=redisTemplate.get(String.valueOf(chargeOrder.getUserId())+SysConstants.TIME_USER);
                log.info("商家"+chargeOrder.getUserId()+"推送時長---" +timelenghts);

                if(timelenghts!=null){
                    //如果超过限定时长，则将该商家的回调放到队列中执行
                    Long time=Long.valueOf(timelenghts);
                    if(time>SysConstants.TIMELONGHT){
                        //将要推送的内容放到队列中
                        Message message = new Message();
                        message.setMessageBody(JSON.toJSONString(callbackReport));
                        Message putMsg = queue.putMessage(message);
                        log.info("report-商家"+chargeOrder.getUserId()+"订单号:"+orderDetail.getOrderNum()+"进队列Push回调---");
                    }else{
                        result=pushCallbackService.pushCallback(callbackReport);
                    }
                }else{
                    long startPushTime=System.currentTimeMillis();//开始时间
                    result=pushCallbackService.pushCallback(callbackReport);
                    log.info("推送状态---" +JSON.toJSONString(chargeReport)+"  结果--"+result);
                    long endPushTime=System.currentTimeMillis();//结束时间
                    long timelenght=endPushTime-startPushTime;//执行时长
                    redisTemplate.setExpire(String.valueOf(chargeOrder.getUserId())+SysConstants.TIME_USER,String.valueOf(timelenght),3600*24);
                    log.info("商家"+chargeOrder.getUserId()+"订单编号"+orderDetail.getOrderNum()+",手机号:"+orderDetail.getMobile()+"成功-推送状态执行时长:"+timelenght);
                }
            }
            /**
             * 回执失败
             */
            if (ChargeReport.ChargeReportStatus.FAIL.equals(chargeReport.getStatus())) {
                log.info("订单充值失败---" + JSON.toJSONString(chargeReport));
                /**
                 *更新流水状态失败
                 */
                long startUpdateChargeTime=System.currentTimeMillis();

                ChargeOrderDetail chargeOrderDetail = new ChargeOrderDetail();
                chargeOrderDetail.setChannelNum(chargeReport.getChannelNum());
                chargeOrderDetail.setStatus(new Integer(chargeReport.getStatus()));
                chargeOrderDetail.setReturnTime(new Date());
                chargeOrderDetail.setReturnRspcode(chargeReport.getMessage());
                chargeOrderDetail.setTableName(CommonUtil.getOrderDetailTableName(chargeReport.getChannelNum()));//设置查询的表
                chargeOrderDetailMapper.updateByChannelNum(chargeOrderDetail);

                long endUpdateChargeTime=System.currentTimeMillis();
                log.info("订单编号"+orderDetail.getOrderNum()+",手机号:"+orderDetail.getMobile()+"失败-更新流水执行时长:"+(endUpdateChargeTime-startUpdateChargeTime));


                long startOrderInfoTime=System.currentTimeMillis();

                ChargeOrder chargeOrder = chargeOrderMapper.selectOrderByOrderNum(CommonUtil.getOrderTableName(orderDetail.getOrderNum()),orderDetail.getOrderNum());
                int userId = chargeOrder.getUserId();

                User user = getUserByUserId(userId);//商户token
                String token = user.getToken();
                Integer pushSum = user.getPushSum();

                long endOrderInfoTime=System.currentTimeMillis();
                log.info("订单编号"+orderDetail.getOrderNum()+",手机号:"+orderDetail.getMobile()+"失败-查询订单信息执行时长:"+(endOrderInfoTime-startOrderInfoTime));

                /**
                 * 退款
                 */
                long startRefundTime=System.currentTimeMillis();

                int refundStatus = ChargeOrder.RefundState.Refund_SUCCESS;//退款成功
                BigDecimal amount = chargeOrder.getAmount();//用户消费金额,退款金额
                try {
                    balanceService.refund(userId, amount, chargeOrder.getBusinessType(), chargeOrder.getOrderNum());
                    log.info("充值失败退款成功---" + JSON.toJSONString(chargeReport));
                }catch (Exception e) {
                    log.error("退款异常",e);
                    log.info("充值失败退款失败---" + JSON.toJSONString(chargeReport)+e.getLocalizedMessage()+e.getMessage());
                    refundStatus = ChargeOrder.RefundState.Refund_FAIL;//退款失败
                }

                long endRefundTime=System.currentTimeMillis();
                log.info("订单编号"+orderDetail.getOrderNum()+",手机号:"+orderDetail.getMobile()+"失败-退款执行时长:"+(endRefundTime-startRefundTime));

                /**
                 * 更新订单状态失败
                 */
                long startUpdateOrderTime=System.currentTimeMillis();

                ChargeOrder order = new ChargeOrder();
                order.setOrderNum(chargeOrder.getOrderNum());
                order.setStatus(ChargeOrder.OrderStatus.CHARGE_FAIL);
                order.setSupplyId(orderDetail.getSupplyId());
                order.setRefundStatus(refundStatus);
                order.setTable(CommonUtil.getOrderTableName(orderDetail.getOrderNum()));//设置查询的表
                order.setOrderReturnTime(new Date());
                chargeOrderMapper.updateReturnStatusByOrderNum(order);

                long endUpdateOrderTime=System.currentTimeMillis();
                log.info("订单编号"+orderDetail.getOrderNum()+",手机号:"+orderDetail.getMobile()+"失败-更新订单执行时长:"+(endUpdateOrderTime-startUpdateOrderTime));

                /**
                 * 推送状态
                 */
                //连接消息队列服务
                MNSClient client = MessageClient.getClient();
                CloudQueue queue = client.getQueueRef(SysConstants.Queue.PUSH_QUEUE);

                String customId = chargeOrder.getCustomid();
                String mobile = chargeOrder.getRechargeMobile();

                CallbackReport callbackReport = new CallbackReport();
                callbackReport.setStatus(new Integer(ChargeOrder.OrderStatus.CHARGE_FAIL).toString());
                callbackReport.setMobile(mobile);
                callbackReport.setCustomId(customId);
                callbackReport.setToken(token);
                callbackReport.setOrderNum(chargeOrder.getOrderNum());
                callbackReport.setCallbackUrl(chargeOrder.getCallbackUrl());
                callbackReport.setPushSum(pushSum);

                //从redis中获取商家执行时长记录，将执行慢的放入队列中执行
                String timelenghts=redisTemplate.get(String.valueOf(chargeOrder.getUserId())+SysConstants.TIME_USER);
                log.info("商家"+chargeOrder.getUserId()+"推送時長---" +timelenghts);

                if(timelenghts!=null){
                    //如果超过限定时长，则将该商家的回调放到队列中执行
                    Long time=Long.valueOf(timelenghts);
                    if(time>SysConstants.TIMELONGHT){
                        //将要推送的内容放到队列中
                        Message message = new Message();
                        message.setMessageBody(JSON.toJSONString(callbackReport));
                        Message putMsg = queue.putMessage(message);
                        log.info("report-商家"+chargeOrder.getUserId()+"订单号:"+orderDetail.getOrderNum()+"进队列Push回调---");
                    }else{
                        result=pushCallbackService.pushCallback(callbackReport);
                    }
                }else{
                    long startPushTime=System.currentTimeMillis();//开始时间
                    result=pushCallbackService.pushCallback(callbackReport);
                    log.info("推送状态---" +JSON.toJSONString(chargeReport)+"  结果--"+result);
                    long endPushTime=System.currentTimeMillis();//结束时间
                    long timelenght=endPushTime-startPushTime;//执行时长
                    redisTemplate.setExpire(String.valueOf(chargeOrder.getUserId())+SysConstants.TIME_USER,String.valueOf(timelenght),3600*24);
                    log.info("商家"+chargeOrder.getUserId()+"订单编号"+orderDetail.getOrderNum()+",手机号:"+orderDetail.getMobile()+"失败-推送状态执行时长:"+timelenght);
                }
            }
            long endReportHandletime=System.currentTimeMillis();
            log.info("订单编号"+orderDetail.getOrderNum()+",手机号:"+orderDetail.getMobile()+"回执执行时长:"+(endReportHandletime-startReportHandletime));
        } catch (Exception e) {
            log.error("处理回执异常"+JSON.toJSONString(chargeReport)+e.getLocalizedMessage()+e.getMessage(), e);
        }
    }


    /**
     * 根据用户id获取User
     * @param userId
     * @return
     */
    private User getUserByUserId(int userId) {
        User user = userMapper.selectUserByUserId(userId);
        return user;
    }

    public static void main(String[] args) {

    }
}
