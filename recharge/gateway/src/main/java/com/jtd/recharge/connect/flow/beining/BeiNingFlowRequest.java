package com.jtd.recharge.connect.flow.beining;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jtd.recharge.base.util.GroovyConfigUtil;
import com.jtd.recharge.base.util.HttpClients;
import com.jtd.recharge.bean.ChargeRequest;
import com.jtd.recharge.bean.ChargeSubmitResponse;
import com.jtd.recharge.connect.base.ConnectReqest;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by lyb on 2017/8/30.
 * 贝宁流量
 */
@Service
public class BeiNingFlowRequest  implements ConnectReqest {
    private Log log = LogFactory.getLog(this.getClass());
    private static Properties properties = null;

    private static Map config = new HashMap();

    static {
        config = GroovyConfigUtil.init("config/supply.groovy");
    }


    @Override
    public ChargeSubmitResponse chargeRequest(ChargeRequest chargeRequest) {
        long start = System.currentTimeMillis();
        String supplyName = chargeRequest.getSupplyName();
        Map supplyMap = (Map) config.get(supplyName);
        log.info("8、发送流程：发送供应商---贝宁流量----mobile =" + chargeRequest.getMobile() + " orderNum=" + chargeRequest.getChannelNum());

        String host = (String) supplyMap.get("host");//充值地址
        String username = (String) supplyMap.get("username");//用户账号
        String psw = (String) supplyMap.get("password");
        String password = DigestUtils.md5Hex(psw).toUpperCase();
        String action = (String) supplyMap.get("action");
        String callbackurl = (String) supplyMap.get("callback_url");
        String publickey=(String) supplyMap.get("publickey");

        String mobile = chargeRequest.getMobile();//手机号码
        String productcode = chargeRequest.getPositionCode();//流量大小
        String orderid = chargeRequest.getChannelNum();

        HashMap map = new HashMap();
        HashMap mapKey = new HashMap();
        HashMap order = new HashMap();
        map.put("action", action);
        map.put("username", username);

        map.put("action", action);
        map.put("username", username);
        String  key  = "{\"password\":\""+password+"\",\"orderlist\":[{\"productcode\":\""+productcode+"\",\"orderid\":\""+orderid+"\",\"mobile\":\""+mobile+"\"}],\"callbackurl\":\""+callbackurl+"\"}";
        log.info("key:"+key);
        String data = null;
        try {
            data = RSA.encrypt(key.getBytes(), publickey);
        } catch (Exception e) {
            e.printStackTrace();
        }
        map.put("data", data);
        String postString =JSONObject.toJSONString(map);

        /**
         * 提交消息
         */
        log.info("8、发送流程：发送供应商---贝宁流量----mobile =" + chargeRequest.getMobile() + " orderNum=" + chargeRequest.getChannelNum() + "封装应用参数json报文请求参数:" + JSON.toJSONString(map));
        ChargeSubmitResponse response = new ChargeSubmitResponse();
        String resultContent = "";
        try {
            resultContent = HttpClients.doPost(host, postString, "application/x-www-form-urlencoded");
        } catch (Exception e) {
            log.error("8、发送异常：发送供应商---贝宁流量----mobile=" + chargeRequest.getMobile() + " orderNum=" + chargeRequest.getChannelNum() + " 提交到贝宁流量！原因：" + e.getLocalizedMessage());
            response.setStatus(ChargeSubmitResponse.Status.SUCCESS);
            response.setStatusMsg(e.getLocalizedMessage() + "请咨询供应商！");
            return response;
        }
        log.info("8、发送流程：发送供应商---贝宁流量----mobile =" + chargeRequest.getMobile() + " orderNum=" + chargeRequest.getChannelNum() + "返回数据：" + resultContent + "***************发送请求耗时：" +
                (System.currentTimeMillis() - start));
        try {
            JSONObject object = JSON.parseObject(resultContent);
            String status=object.getString("status");
            String message=object.getString("message");
            if ("0".equals(status)) {
                JSONArray jsonArray  =  JSONArray.parseArray(object.getString("results"));
                JSONObject results = jsonArray.getJSONObject(0);
                String errorCode = results.getString("orderstatus");
                String respMsg = results.getString("ordermsg");
                if ("0".equals(errorCode)) {
                    response.setStatus(ChargeSubmitResponse.Status.SUCCESS);
                    response.setChannelNum(orderid);
                    log.info("8、发送流程：发送供应商---贝宁流量----mobile =" + chargeRequest.getMobile() + " orderNum=" + chargeRequest.getChannelNum() + " 提交到贝宁流量成功！");
                } else {
                    response.setStatus(ChargeSubmitResponse.Status.FAIL);
                    response.setStatusCode(errorCode);
                    response.setStatusMsg(respMsg + "请咨询供应商！");
                    log.info("8、发送流程：发送供应商---贝宁流量----mobile =" + chargeRequest.getMobile() + " orderNum=" + chargeRequest.getChannelNum() + " 提交到贝宁流量失败!原因：" + respMsg + "请咨询供应商！");
                }
            }else{
                response.setStatus(ChargeSubmitResponse.Status.FAIL);
                response.setStatusCode(status);
                response.setStatusMsg(message + "请咨询供应商！");
                log.info("8、发送流程：发送供应商---贝宁流量----mobile =" + chargeRequest.getMobile() + " orderNum=" + chargeRequest.getChannelNum() + " 提交到贝宁流量失败!原因：" + message + "请咨询供应商！");
            }
        } catch (Exception e) {
            log.error("8、发送异常：发送供应商---贝宁流量----mobile=" + chargeRequest.getMobile() + " orderNum=" + chargeRequest.getChannelNum() + "原因：" + e.getLocalizedMessage());
        }
        return response;
    }

}
