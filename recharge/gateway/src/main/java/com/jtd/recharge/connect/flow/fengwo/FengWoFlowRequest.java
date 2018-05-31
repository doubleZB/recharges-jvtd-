package com.jtd.recharge.connect.flow.fengwo;

import com.alibaba.fastjson.JSONObject;
import com.jtd.recharge.base.util.DateUtil;
import com.jtd.recharge.base.util.HttpTookit;
import com.jtd.recharge.bean.ChargeRequest;
import com.jtd.recharge.bean.ChargeSubmitResponse;
import com.jtd.recharge.connect.base.ConnectReqest;
import com.jtd.recharge.connect.video.SupplyConfig;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.Map;

/**
 * 蜂窝科技 全国流量充值
 */
public class FengWoFlowRequest implements ConnectReqest {

    private final Logger logger = LoggerFactory.getLogger(FengWoFlowRequest.class);

    @Override
    public ChargeSubmitResponse chargeRequest(ChargeRequest chargeRequest) throws Exception {
        logger.info("蜂窝科技全国流量充值请求");

        Map supplyMap = (Map) SupplyConfig.getConfig().get(chargeRequest.getSupplyName());
        long start =System.currentTimeMillis();

        // 系统参数
        JSONObject sysParam = new JSONObject();
        sysParam.put("VERSION", "V1.1");        // 协议版本号
        sysParam.put("TIMESTAMP", DateUtil.Date2String(new Date(), "yyyyMMddHHmmssSSS"));
        sysParam.put("SEQNO", chargeRequest.getChannelNum());       // 流水号
        sysParam.put("APPID", String.valueOf(supplyMap.get("app_id")));       //应用标识号

        sysParam.put("SECERTKEY", DigestUtils.md5Hex(sysParam.getString("TIMESTAMP")
                + sysParam.getString("SEQNO") + sysParam.getString("APPID")
                + String.valueOf(supplyMap.get("key"))));       // MD5签名
        logger.info("蜂窝流量充值:系统参数: {}", sysParam);

        // 请求参数
        JSONObject contentParam = new JSONObject();
        contentParam.put("USER", chargeRequest.getMobile());       // 分发对象标识（手机号码/UserID）
        contentParam.put("PACKAGEID", chargeRequest.getPositionCode());     // 流量包ID
        contentParam.put("ORDERTYPE", "1"); //  1流量 3话费
        contentParam.put("EXTORDER", chargeRequest.getChannelNum());   // 为确保与cp一致，该项调整为必填

        contentParam.put("SIGN", DigestUtils.md5Hex(String.valueOf(supplyMap.get("key"))
                + contentParam.getString("USER") + contentParam.getString("PACKAGEID")
                + contentParam.getString("ORDERTYPE") + contentParam.getString("EXTORDER")));       // 签名值
        
        JSONObject msgbody = new JSONObject();
        msgbody.put("CONTENT", contentParam);
        logger.info("蜂窝流量充值:请求参数: {}", contentParam);

        JSONObject param = new JSONObject();
        param.put("HEADER", sysParam);
        param.put("MSGBODY", msgbody);
        logger.info("蜂窝流量充值:最终的请求参数: {}", param);

        ChargeSubmitResponse response = new ChargeSubmitResponse();
        String resultContent;

        // 充值请求
        try {
            logger.info("蜂窝流量充值请求参数：{}", param);
            resultContent = HttpTookit.doPost(String.valueOf(supplyMap.get("host")), param.toJSONString());
            logger.info("蜂窝流量充值请求成功，响应数据：{} 耗时:", resultContent, (System.currentTimeMillis()-start));
        } catch (Exception e) {
            logger.error("发送异常：发送供应商---蜂窝流量充值---充值号码{}--, 订单号:{}-失败原因{}",
                    chargeRequest.getMobile(), chargeRequest.getChannelNum(), e.getLocalizedMessage());

            response.setStatus(ChargeSubmitResponse.Status.FAIL);
            response.setStatusMsg(e.getLocalizedMessage()+"请咨询供应商！");
            return response;
        }

        JSONObject resultJson = JSONObject.parseObject(resultContent);
        JSONObject resp = resultJson.getJSONObject("MSGBODY").getJSONObject("RESP");

        String status = resp.getString("RCODE");
        if ("00".equals(status)) {
            response.setStatusCode(status);
            response.setStatus(ChargeSubmitResponse.Status.SUCCESS);
            logger.info("4、发送流程：发送供应商---蜂窝流量充值----mobile ={} orderNum={} 提交到蜂窝流量成功！",
                    chargeRequest.getMobile(),chargeRequest.getChannelNum());
        } else {
            String desc = resp.getString("RMSG");
            response.setStatus(ChargeSubmitResponse.Status.FAIL);
            response.setStatusCode(status);
            response.setStatusMsg(desc + "请咨询供应商！");
            logger.info("5、发送流程：发送供应商---蜂窝流量充值----mobile ={} orderNum={} 提交到蜂窝流量充值失败！原因：{}请咨询供应商！",
                    chargeRequest.getMobile(), chargeRequest.getChannelNum(), desc);
        }

        return response;
    }
}
