package com.jtd.recharge.connect.flow.rongman;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.GsonBuilder;
import com.jtd.recharge.base.util.GroovyConfigUtil;
import com.jtd.recharge.bean.ChargeRequest;
import com.jtd.recharge.bean.ChargeSubmitResponse;
import com.jtd.recharge.connect.base.ConnectReqest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by liyabin on 2017/9/28.
 * 融曼流量
 */
@Service
public class RongManFlowRequest implements ConnectReqest {

    private Log log = LogFactory.getLog(this.getClass());
    private static Properties properties = null;

    private static Map config = new HashMap();

    static {
        config = GroovyConfigUtil.init("config/supply.groovy");
    }

    @Override
    public ChargeSubmitResponse chargeRequest(ChargeRequest chargeRequest) throws Exception {

        String supplyName = chargeRequest.getSupplyName();
        Map supplyMap = (Map) config.get(supplyName);
        long start = System.currentTimeMillis();
        log.info("8、发送流程--发送供应商---融曼流量流量--mobile =" + chargeRequest.getMobile() + " orderNum=" + chargeRequest.getChannelNum());

        ChargeSubmitResponse response = new ChargeSubmitResponse();
        String host = (String) supplyMap.get("host");
        String adaptorId = (String) supplyMap.get("adapterid");
        String key = (String) supplyMap.get("key");
        String iv = (String) supplyMap.get("iv");
        String callbackUrl = (String) supplyMap.get("callback_url");//回调地址
        String phoneNum = chargeRequest.getMobile();
        String sn = chargeRequest.getPositionCode();
        String orderNum = chargeRequest.getChannelNum();

        String operator = "";
        if (("M").equals(sn.substring(0,1))) {
            operator = "MOBILE";
        } else if (("U").equals(sn.substring(0,1))) {
            operator = "UNICOM";
        } else if (("T").equals(sn.substring(0,1))) {
            operator = "TELECOM";
        }

        Map<String, Object> headerMap = new HashMap();
        headerMap.put("adaptorId", adaptorId);
        Map<String, Object> bodyMap = new HashMap();
        bodyMap.put("orderId", orderNum);
        bodyMap.put("mobile", phoneNum);
        bodyMap.put("productId", sn);
        bodyMap.put("callbackUrl", callbackUrl);
        bodyMap.put("province", "CHINA");
        bodyMap.put("operator", operator);
        bodyMap.put("proxOrder", 0);
        bodyMap.put("timestamp", System.currentTimeMillis());


        log.info("加密前的body----"+JSON.toJSONString(bodyMap));
        String toJsons = encoding(bodyMap, key, iv);
        log.info("8、发送流程---发送供应商--融曼流量流量--mobile =" + chargeRequest.getMobile() + " orderNum=" + chargeRequest.getChannelNum() + "body加密后---" + toJsons);

        JSONObject objectOne = JSON.parseObject(toJsons);
        String body = objectOne.getString("body");
        log.info("body------"+body);

        Map<String, Object> headerBody = new HashMap();
        headerBody.put("header", headerMap);
        headerBody.put("body", body);

        log.info("8、发送供应商--融曼流量流量--mobile =" + chargeRequest.getMobile() + " orderNum=" + chargeRequest.getChannelNum() + "-header+body----" + JSON.toJSONString(headerBody));

        /**
         * 提交消息
         */
        String result;
        try {
            result = Utils.post(host,headerBody);
        } catch (Exception e) {
            log.error("8、发送异常：发送供应商---融曼流量流量----mobile=" + chargeRequest.getMobile() + " orderNum=" + chargeRequest.getChannelNum() + " 提交到融曼流量流量！原因：" + e.getLocalizedMessage());
            response.setStatus(ChargeSubmitResponse.Status.SUCCESS);
            response.setStatusMsg(e.getLocalizedMessage() + "请咨询供应商！");
            return response;
        }
        log.info("8、发送流程----mobile =" + chargeRequest.getMobile() + " orderNum=" + chargeRequest.getChannelNum() +"结果：----"+result + "  发送供应商---融曼流量流量---"+ JSON.toJSONString(headerBody)+"*******发送请求耗时：" +
                (System.currentTimeMillis() - start));

        JSONObject object = JSON.parseObject(result);
        String respCode = object.getString("respCode");
        String rspDesc = object.getString("rspDesc");
        if (("0000").equals(respCode)) {
            response.setStatus(ChargeSubmitResponse.Status.SUCCESS);
            response.setChannelNum(orderNum);
            log.info("8、发送流程：发送供应商---融曼流量流量----mobile =" + chargeRequest.getMobile() + " orderNum=" + chargeRequest.getChannelNum() + " 提交融曼流量流量成功！");

        } else {
            response.setStatus(ChargeSubmitResponse.Status.FAIL);
            response.setStatusCode(respCode);
            response.setStatusMsg(rspDesc + "请咨询供应商！");
            log.info("8、发送流程：发送供应商---融曼流量流量----mobile =" + chargeRequest.getMobile() + " orderNum=" + chargeRequest.getChannelNum() + " 提交融曼流量流量失败！原因：" + respCode + "请咨询供应商！");

        }
        return response;
    }
    private static String encoding( Map<String, Object> bodyMap, String key, String iv)
            throws Exception {
        Map<String, Object> m = new LinkedHashMap<String, Object>();
        String json = new GsonBuilder().create().toJson(bodyMap);
        m.put("body", Aes128CBCUtil.encrypt(json, key, iv));
        json =  new GsonBuilder().create().toJson(m);
        return json;
    }
}