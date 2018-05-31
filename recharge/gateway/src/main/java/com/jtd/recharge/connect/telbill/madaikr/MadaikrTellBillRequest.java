package com.jtd.recharge.connect.telbill.madaikr;

import com.alibaba.fastjson.JSONObject;
import com.jtd.recharge.base.util.HttpTookit;
import com.jtd.recharge.bean.ChargeRequest;
import com.jtd.recharge.bean.ChargeSubmitResponse;
import com.jtd.recharge.connect.base.ConnectReqest;
import com.jtd.recharge.connect.video.SupplyConfig;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URLEncoder;
import java.util.Map;

/**
 * 麻袋氪话费充值通道
 */

public class MadaikrTellBillRequest implements ConnectReqest {

    private final Logger logger = LoggerFactory.getLogger(MadaikrTellBillRequest.class);

    @Override
    public ChargeSubmitResponse chargeRequest(ChargeRequest chargeRequest) throws Exception {

        Map supplyMap = (Map) SupplyConfig.getConfig().get(chargeRequest.getSupplyName());
        long start =System.currentTimeMillis();

        // 请求参数
        JSONObject busiBodyParam = new JSONObject();
        busiBodyParam.put("action", "CZ");  //交易指令码
        busiBodyParam.put("orderId", chargeRequest.getChannelNum());    // 定单号
        busiBodyParam.put("chargeAcct", chargeRequest.getMobile());     // 充值账号
        busiBodyParam.put("chargeCash", chargeRequest.getAmount());     // 充值金额
        busiBodyParam.put("chargeType", "0");                           // 充值类型 0:手机充值
        busiBodyParam.put("ispName", 1 == chargeRequest.getOperator() ?
                "移动" : 2 == chargeRequest.getOperator() ? "联通" : "电信");      // 充值类型 0:手机充值
        busiBodyParam.put("retUrl", URLEncoder.encode(String.valueOf(supplyMap.get("callback_url")), "GBK")); // 充值类型 0:手机充值

        JSONObject param = new JSONObject();
        param.put("sign", DigestUtils.md5Hex(busiBodyParam.toJSONString() + String.valueOf(supplyMap.get("key"))));
        param.put("agentAccount", String.valueOf(supplyMap.get("user_id")));
        param.put("busiBody", busiBodyParam);

        ChargeSubmitResponse response = new ChargeSubmitResponse();
        String resultContent;

        // 充值请求
        try {
            logger.info("麻袋氪话费充值请求参数：{}", param);
            resultContent = HttpTookit.doPost(String.valueOf(supplyMap.get("host")), param.toJSONString(), "GBK");
            logger.info("麻袋氪话费充值请求成功，响应数据：{} 耗时: {}", resultContent, (System.currentTimeMillis()-start));
        } catch (Exception e) {
            logger.error("发送异常：发送供应商---麻袋氪话费充值---充值号码{}--, 订单号:{}-失败原因{}",
                    chargeRequest.getMobile(), chargeRequest.getChannelNum(), e.getLocalizedMessage());

            response.setStatus(ChargeSubmitResponse.Status.FAIL);
            response.setStatusMsg(e.getLocalizedMessage()+"请咨询供应商！");
            return response;
        }

        JSONObject resultJson = JSONObject.parseObject(resultContent);

        String status = resultJson.getString("errorCode");
        if ("1".equals(status)) {
            response.setStatusCode(status);
            response.setStatus(ChargeSubmitResponse.Status.SUCCESS);
            logger.info("4、发送流程：发送供应商---麻袋氪话费充值----mobile ={} orderNum={} 提交到麻袋氪话费成功！",
                    chargeRequest.getMobile(),chargeRequest.getChannelNum());
        } else {
            String desc = resultJson.getString("errorDesc");
            response.setStatus(ChargeSubmitResponse.Status.FAIL);
            response.setStatusCode(status);
            response.setStatusMsg(desc + "请咨询供应商！");
            logger.info("5、发送流程：发送供应商---麻袋氪话费充值----mobile ={} orderNum={} 提交到麻袋氪话费充值失败！原因：{}请咨询供应商！",
                    chargeRequest.getMobile(), chargeRequest.getChannelNum(), desc);
        }

        return response;
    }
}
