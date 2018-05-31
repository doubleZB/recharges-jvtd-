package com.jtd.recharge.cache;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.aliyun.mns.client.CloudQueue;
import com.aliyun.mns.client.MNSClient;
import com.aliyun.mns.model.Message;
import com.jtd.recharge.base.constant.SysConstants;
import com.jtd.recharge.base.util.MessageClient;
import com.jtd.recharge.bean.ChargeMessage;
import com.jtd.recharge.bean.ChargeRequest;
import com.jtd.recharge.dao.mapper.CacheOrderMapper;
import com.jtd.recharge.dao.mapper.ChargeOrderMapper;
import com.jtd.recharge.dao.mapper.ChargeSupplyMapper;
import com.jtd.recharge.dao.po.*;
import com.jtd.recharge.gateway.service.GateWayService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by liyabin on 2017/8/23.
 */
@Service("cacheService")
public class CacheService {
    private Log log = LogFactory.getLog(this.getClass());

    @Resource
    CacheOrderMapper cacheOrderMapper;

    @Autowired
    public GateWayService gateWayService;

    @Autowired
    public ChargeSupplyMapper chargeSupplyMapper;

    @Autowired
    public ChargeOrderMapper chargeOrderMapper;

    public void cacheOrderSubmit(CacheOrder cacheOrders){
            long startChargeTime = System.currentTimeMillis();

            ChargeOrder order = chargeOrderMapper.selectOrderByOrderNumAndCustomId(cacheOrders.getOrderNum(), cacheOrders.getCustomid(), cacheOrders.getMobile());
            try{
                /**
                 * 扣款
                 */
                try {
                    BigDecimal feeAmount=null;
                    if(cacheOrders.getNewDiscount()==null){
                        feeAmount=cacheOrders.getPayCount();
                    }else{
                        feeAmount= cacheOrders.getOriginalPrice().multiply(cacheOrders.getNewDiscount());
                    }
                    String res = gateWayService.paymentAmount(cacheOrders.getUserId(),feeAmount, cacheOrders.getBusinessType(), cacheOrders.getOrderNum());
                    if (res != null && "1010".equals(res)) {
                        chargeOrderMapper.updateStatusByOrderNums(order.getOrderNum(),ChargeOrder.OrderStatus.NO_BALANCE);
                        log.info("3、接口收到充值数据 mobile---" + cacheOrders.getMobile() + " 订单号orderNum =" + cacheOrders.getOrderNum() + " 余额不足 订单到此结束！");
                    }
                } catch (Exception e) {
                    chargeOrderMapper.updateStatusByOrderNums(order.getOrderNum(),ChargeOrder.OrderStatus.PAY_ERROR);
                    log.info("3、接口收到充值数据 mobile---" + cacheOrders.getMobile() + " 订单号orderNum =" + cacheOrders.getOrderNum() + " 扣费失败 订单到此结束！" + e.getMessage());
                }
                List<ChargeRequest> supplyList = new ArrayList();
                ChargeRequest chargeRequest = new ChargeRequest();
                ChargeSupply chargeSupply=new ChargeSupply();
                if(cacheOrders.getNewSupplier()!=null&&!"".equals(cacheOrders.getNewSupplier())) {
                    chargeSupply.setSupplyName(cacheOrders.getNewSupplier());
                }else{
                    chargeSupply.setSupplyName(cacheOrders.getSupplier());
                }
                log.info("chargeSupply："+ JSONObject.toJSON(chargeSupply));
                List<ChargeSupply>  chargeSupplyList= chargeSupplyMapper.selectByParam(chargeSupply);
                log.info("chargeSupplyList："+ JSONObject.toJSON(chargeSupplyList));
                if(chargeSupplyList!=null&&chargeSupplyList.size()>0){
                    chargeRequest.setSupplyId(chargeSupplyList.get(0).getId());
                }
                chargeRequest.setSupplyName(cacheOrders.getSupplier());
                chargeRequest.setPositionCode(cacheOrders.getPositionCode());
                chargeRequest.setPackageSize(order.getPackageSize());
                chargeRequest.setMobile(cacheOrders.getMobile());
                supplyList.add(chargeRequest);
                /**
                 * 12 修改订单状态
                 */
                chargeOrderMapper.updateStatusByOrderNums(order.getOrderNum(),ChargeOrder.OrderStatus.CREATE_ORDER);
                log.info("3、接口收到充值数据 mobile---" + cacheOrders.getMobile() + " 订单号orderNum =" + cacheOrders.getOrderNum() + " 保存订单成功！");
                /**
                 * 13 添加发送队列
                 */
                ChargeMessage chargeMessage = new ChargeMessage();
                chargeMessage.setOrderNum(cacheOrders.getOrderNum());
                chargeMessage.setBusinessType(cacheOrders.getBusinessType());

                chargeMessage.setSupplyList(supplyList);
                log.info("4、接口收到充值数据 mobile---" + cacheOrders.getMobile() + " 订单号orderNum =" + cacheOrders.getOrderNum() + "添加流量消息队列" + JSON.toJSONString(chargeMessage));
                MNSClient client = MessageClient.getClient();
                chargeMessage.setSupplyList(supplyList);
                String queueName=SysConstants.Queue.SUBMIT_QUEUE;
                //判断是否走单通道-应对只能绑定一个IP的供应商
                for(int i=0;i<supplyList.size();i++){
                    if(SysConstants.alone_mao.get(supplyList.get(i).getSupplyName())!=null){
                        queueName=SysConstants.Queue.SUBMIT_QUERE_ALONE;
                    }
                }
                CloudQueue queue = client.getQueueRef(queueName);
                Message message = new Message();
                message.setMessageBody(JSON.toJSONString(chargeMessage));
                Message putMsg = queue.putMessage(message);
                log.info("5、接口收到充值数据 mobile---" + cacheOrders.getMobile() + " 订单号orderNum =" + cacheOrders.getOrderNum() + "添加流量消息队列  ----成功  Send message id is: " + putMsg.getMessageId()+"，消息体："+JSON.toJSONString(chargeMessage));

                long endChangeTime = System.currentTimeMillis();
                log.info("订单编号" + cacheOrders.getOrderNum() + ",手机号:" + cacheOrders.getMobile() + "客户订单号:" + cacheOrders.getCustomid() + "用户缓存订单接口执行时长:" + (endChangeTime - startChargeTime));
                String[] orderNum={cacheOrders.getOrderNum()};
                cacheOrderMapper.updateChargeStatus(orderNum,CacheOrder.statuss.COMPLETED);
            } catch (Exception e) {
        log.info("2、接口收到充值数据  mobile---" + cacheOrders.getMobile()+"  customId---" + cacheOrders.getCustomid()+"  系统异常："+e.getMessage());
    }
    }
}
