package com.jtd.recharge.connect.telbill.daliankuaijie;

import com.alibaba.fastjson.JSONObject;
import com.jtd.recharge.base.util.HttpTookit;
import com.jtd.recharge.bean.ChargeRequest;
import com.jtd.recharge.bean.ChargeSubmitResponse;
import com.jtd.recharge.connect.base.ConnectReqest;
import com.jtd.recharge.connect.video.SupplyConfig;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * 大连快捷话费充值请求
 */
public class DaLianKuaiJieTelBillRequest implements ConnectReqest{

    private Logger logger = LoggerFactory.getLogger(DaLianKuaiJieTelBillRequest.class);

    @Override
    public ChargeSubmitResponse chargeRequest(ChargeRequest chargeRequest) throws Exception {

        logger.info("大连快捷话费充值。。。");
        Map supplyMap = (Map) SupplyConfig.getConfig().get(chargeRequest.getSupplyName());
        long start =System.currentTimeMillis();

        Map<String, String> param = new HashMap<>();
        param.put("OrderId", chargeRequest.getChannelNum());
        param.put("Account", chargeRequest.getMobile());
        param.put("ProductId", chargeRequest.getPositionCode());
        param.put("ShopId", String.valueOf(supplyMap.get("ShopId")));     // shopId
        param.put("UserId", String.valueOf(supplyMap.get("user_id")));
        param.put("Num", "1");
        param.put("Timestamp", String.valueOf(System.currentTimeMillis()));
        param.put("NotifyUrl", String.valueOf(supplyMap.get("callback_url")));
        // param.put("Province", String.valueOf(supplyMap.get("callback_url")));
        param.put("Amount", String.valueOf(chargeRequest.getAmount()));
        param.put("ProductType", "1"); // 1 话费 2流量

        // 签名
        StringBuffer signStr = new StringBuffer();
        signStr.append(param.get("ShopId")).append(param.get("UserId"))
                .append(param.get("ProductType")).append(param.get("OrderId"))
                .append(param.get("Account")).append(param.get("Amount"))
                .append(param.get("Num")).append(param.get("Timestamp"))
                .append(String.valueOf(supplyMap.get("key")));
        param.put("sign", DigestUtils.md5Hex(signStr.toString()));

        ChargeSubmitResponse response = new ChargeSubmitResponse();
        String resultContent;

        // 充值请求
        try {
            logger.info("大连快捷充值请求参数：{}", param);
            resultContent = HttpTookit.doGet(String.valueOf(supplyMap.get("host")), param);
            logger.info("大连快捷充值请求成功，响应数据：{} 耗时: {}", resultContent, (System.currentTimeMillis()-start));
        } catch (Exception e) {
            logger.error("发送异常：发送供应商---大连快捷充值---充值号码{}--, 订单号:{}-失败原因{}",
                    chargeRequest.getMobile(), chargeRequest.getChannelNum(), e.getLocalizedMessage());

            response.setStatus(ChargeSubmitResponse.Status.FAIL);
            response.setStatusMsg(e.getLocalizedMessage()+"请咨询供应商！");
            return response;
        }

        JSONObject resultJson = JSONObject.parseObject(resultContent);
        String code = resultJson.getString("Code");
        if ("1012".equals(code)) {
            response.setStatusCode(code);
            response.setStatus(ChargeSubmitResponse.Status.SUCCESS);
            logger.info("4、发送流程：发送供应商---大连快捷话费充值----mobile ={} orderNum={} 提交到大连快捷话费成功！",
                    chargeRequest.getMobile(),chargeRequest.getChannelNum());
        } else {
            String desc = resultJson.getString("Message");
            response.setStatus(ChargeSubmitResponse.Status.FAIL);
            response.setStatusCode(code);
            response.setStatusMsg(desc + "请咨询供应商！");
            logger.info("5、发送流程：发送供应商---大连快捷话费充值----mobile ={} orderNum={} 提交到大连快捷话费充值失败！原因：{}请咨询供应商！",
                    chargeRequest.getMobile(), chargeRequest.getChannelNum(), desc);
        }
        return response;
    }
}
