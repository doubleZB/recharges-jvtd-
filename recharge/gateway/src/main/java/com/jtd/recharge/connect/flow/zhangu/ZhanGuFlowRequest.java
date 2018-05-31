package com.jtd.recharge.connect.flow.zhangu;

import com.alibaba.fastjson.JSONObject;
import com.jtd.recharge.base.util.GroovyConfigUtil;
import com.jtd.recharge.base.util.HttpTookit;
import com.jtd.recharge.bean.ChargeRequest;
import com.jtd.recharge.bean.ChargeSubmitResponse;
import com.jtd.recharge.connect.base.ConnectReqest;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * 战鼓通信
 */
@Service
public class ZhanGuFlowRequest implements ConnectReqest {

    private final Logger logger = LoggerFactory.getLogger(ZhanGuFlowRequest.class);
    private static Map config = new HashMap();

    static {
        config = GroovyConfigUtil.init("config/supply.groovy");
    }

    @Override
    public ChargeSubmitResponse chargeRequest(ChargeRequest chargeRequest) throws Exception {

        String supplyName = chargeRequest.getSupplyName();
        Map supplyMap = (Map) config.get(supplyName);
        long start =System.currentTimeMillis();
        logger.info("1、发送流程--发送供应商---战鼓--mobile ={} orderNum={}",
                chargeRequest.getMobile(), chargeRequest.getChannelNum());

        String host = (String) supplyMap.get("host");
        String apiKey = (String) supplyMap.get("apiKey");
        String callbackUrl = (String) supplyMap.get("callback_url");
        String userId = (String) supplyMap.get("userId");

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("action", "CZ");
        jsonObject.put("orderId", chargeRequest.getChannelNum());
        jsonObject.put("chargeAcct", chargeRequest.getMobile());
        // jsonObject.put("chargeCash", "1");
        jsonObject.put("chargeType", "1");
        jsonObject.put("flowPackageType", "0");
        jsonObject.put("flowPackageSize", chargeRequest.getPackageSize());
        jsonObject.put("chargeAmount", "1");
        jsonObject.put("ispName", "");
        jsonObject.put("retUrl", URLEncoder.encode(callbackUrl,"UTF-8"));

        JSONObject param = new JSONObject();
        param.put("sign", DigestUtils.md5Hex(jsonObject.toJSONString() + apiKey));
        param.put("agentAccount", userId.toLowerCase());
        param.put("busiBody", jsonObject);

        ChargeSubmitResponse response = new ChargeSubmitResponse();
        String resultContent;
        try {
            logger.info("2战鼓话费==请求参数: {}", param.toJSONString());
            resultContent = HttpTookit.doPost(host, param.toJSONString(), "GBK");
        } catch (Exception e) {
            logger.error("3、发送异常：发送供应商---战鼓话费----mobile={} orderNum={} 提交到战鼓！原因：",
                    chargeRequest.getMobile(), chargeRequest.getChannelNum(), e.getLocalizedMessage());
            response.setStatus(ChargeSubmitResponse.Status.FAIL);
            response.setStatusMsg(e.getLocalizedMessage()+"请咨询供应商！");

            return response;
        }

        logger.info("3、发送流程：发送供应商---战鼓话费----mobile ={} orderNum={} 返回数据：{}*******发送请求耗时：{}"
                ,chargeRequest.getMobile(),chargeRequest.getChannelNum(),resultContent,(System.currentTimeMillis()-start));

        JSONObject resultObj = JSONObject.parseObject(resultContent);
        if ("1".equals(resultObj.getString("errorCode"))) {
            response.setStatusCode(resultObj.getString("errorCode"));
            response.setStatus(ChargeSubmitResponse.Status.SUCCESS);
            logger.info("4、发送流程：发送供应商---战鼓话费----mobile ={} orderNum={} 提交到战鼓话费成功！",
                    chargeRequest.getMobile(),chargeRequest.getChannelNum());
        } else {
            response.setStatus(ChargeSubmitResponse.Status.FAIL);
            response.setStatusCode(resultObj.getString("errorCode"));
            response.setStatusMsg(resultObj.getString("errorDesc") + "请咨询供应商！");
            logger.info("5、发送流程：发送供应商---战鼓话费----mobile ={} orderNum={} 提交到战鼓话费失败！原因：{}请咨询供应商！",
                    chargeRequest.getMobile(), chargeRequest.getChannelNum(), resultObj.getString("errorDesc"));
        }

        return response;
    }
}
