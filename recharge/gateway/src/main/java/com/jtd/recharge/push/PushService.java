package com.jtd.recharge.push;

import com.alibaba.fastjson.JSON;
import com.jtd.recharge.base.util.CommonUtil;
import com.jtd.recharge.base.util.HttpTookit;
import com.jtd.recharge.dao.bean.CallbackReport;
import com.jtd.recharge.dao.mapper.ChargeOrderMapper;
import com.jtd.recharge.dao.po.ChargeOrder;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @autor liyabin
 * 推送状态
 */
@Service("pushService")
public class PushService {

    private Log log = LogFactory.getLog(this.getClass());

    @Resource
    ChargeOrderMapper chargeOrderMapper;


    /**
     * 推送消息
     */
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public String pushCallback(CallbackReport callbackReport) {
        String callbackUrl = callbackReport.getCallbackUrl();
        String content = "";
        try {
            Map paramMap = new HashMap();
            paramMap.put("mobile", callbackReport.getMobile());
            paramMap.put("status", callbackReport.getStatus());
            paramMap.put("customId", callbackReport.getCustomId());
            paramMap.put("sign", DigestUtils.md5Hex(callbackReport.getToken()
                    + callbackReport.getMobile() + callbackReport.getStatus() + callbackReport.getCustomId()));
            paramMap.put("orderNum",callbackReport.getOrderNum());

            Integer pushSum = callbackReport.getPushSum();


            /**
             * 推送失败则多次推送
             */
            long startPushTime=System.currentTimeMillis();
            long endPushTime;
            for(int i=0;i<pushSum;i++) {
                try {
                    content = HttpTookit.doPost(callbackUrl, paramMap);
                    log.info("订单号OrderNum---"+callbackReport.getOrderNum()+" 手机--"+callbackReport.getMobile() +"   推送推送状态报告--第" + i + "次  向地址："+callbackUrl+"   推送内容--" + JSON.toJSONString(paramMap)+ callbackReport.getOrderNum() + "----响应结果---" + content);
                    endPushTime=System.currentTimeMillis();
                    log.info("订单号OrderNum---"+callbackReport.getOrderNum()+" 手机--"+callbackReport.getMobile() +"   推送推送状态报告--第" + i + "次  向地址："+callbackUrl+"   推送内容--" + JSON.toJSONString(paramMap)+ callbackReport.getOrderNum() + "----响应时长---" + (endPushTime-startPushTime));
                }catch (Exception e){
                    log.error("订单号OrderNum---"+callbackReport.getOrderNum()+" 手机--"+callbackReport.getMobile()+"  推送状态报告url异常  callbackUrl=" + callbackUrl +"  原因 --"+e.getLocalizedMessage()+e.getMessage(),e);
                    e.printStackTrace();
                    continue;
                }

                if(CallbackReport.CALLBACK_SUCCESS.equals(content)) {
                    break;
                }else {
                    continue;
                }
            }
            endPushTime=System.currentTimeMillis();
            log.info("订单号OrderNum---"+callbackReport.getOrderNum()+" 手机--"+callbackReport.getMobile() +"队列推送URL执行时长:"+(endPushTime-startPushTime));


            if (CallbackReport.CALLBACK_SUCCESS.equals(content)) {
                log.info("推送成功 customId---" + callbackReport.getCustomId());
                ChargeOrder order = new ChargeOrder();
                order.setPushStatus(ChargeOrder.PushState.PUSH_SUCCESS);//推送成功
                order.setOrderNum(callbackReport.getOrderNum());
                order.setTable(CommonUtil.getOrderTableName(callbackReport.getOrderNum()));
                chargeOrderMapper.updateOrderByOrderNum(order);
            } else {
                log.info("推送失败 customId---" + callbackReport.getCustomId());
                ChargeOrder order = new ChargeOrder();
                order.setPushStatus(ChargeOrder.PushState.PUSH_FAIL);//推送失败
                order.setOrderNum(callbackReport.getOrderNum());
                order.setTable(CommonUtil.getOrderTableName(callbackReport.getOrderNum()));
                chargeOrderMapper.updateOrderByOrderNum(order);
            }

        } catch (Exception e) {
            log.error("推送失败", e);

            log.info("推送失败 channelNum---" + callbackReport.getCallbackUrl());
            ChargeOrder order = new ChargeOrder();
            order.setPushStatus(ChargeOrder.PushState.PUSH_FAIL);//推送失败
            order.setOrderNum(callbackReport.getOrderNum());
            order.setTable(CommonUtil.getOrderTableName(callbackReport.getOrderNum()));
            chargeOrderMapper.updateOrderByOrderNum(order);
            content = "调用异常--"+e;
        }
        return content;
    }
}
