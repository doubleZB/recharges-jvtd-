package com.jtd.recharge.connect.telbill.juhe;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jtd.recharge.base.util.GroovyConfigUtil;
import com.jtd.recharge.base.util.HttpTookit;
import com.jtd.recharge.bean.ChargeRequest;
import com.jtd.recharge.bean.ChargeSubmitResponse;
import com.jtd.recharge.connect.base.ConnectReqest;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * @autor jipengkun
 */
public class JuHeTelBillRequest implements ConnectReqest{

    private Log log = LogFactory.getLog(this.getClass());

    private static Map config = new HashMap();

    static {
        config = GroovyConfigUtil.init("config/supply.groovy");
    }

    @Override
    public ChargeSubmitResponse chargeRequest(ChargeRequest chargeRequest) {

        long start =System.currentTimeMillis();
        log.info("8、发送流程：发送供应商---聚合----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum());


        String supplyName = chargeRequest.getSupplyName();
        Map supplyMap = (Map) config.get(supplyName);
        String openId = (String) supplyMap.get("openId");
        String key = (String) supplyMap.get("key");
        String host = (String) supplyMap.get("host");
        String phoneno = chargeRequest.getMobile();
        String cardnum = chargeRequest.getPositionCode();
        String orderid = chargeRequest.getChannelNum();
        String sign = DigestUtils.md5Hex(openId + key + phoneno + cardnum + orderid);

        String resultContent = "";

        Map param = new HashMap();
        param.put("phoneno",phoneno);
        param.put("cardnum",cardnum);
        param.put("orderid",orderid);
        param.put("key",key);
        param.put("sign",sign);
        log.info("8、发送流程：发送供应商---聚合话费----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+"封装应用参数json报文请求参数:"+ JSON.toJSONString(param));

        ChargeSubmitResponse response = new ChargeSubmitResponse();
        try {
            resultContent = HttpTookit.doPost(host,param);
        } catch (Exception e) {
            log.error("8、发送异常：发送供应商---聚合话费----mobile="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+" 提交到聚合话费！原因："+ e.getLocalizedMessage());
            response.setStatus(ChargeSubmitResponse.Status.FAIL);
            response.setStatusMsg(e.getLocalizedMessage()+"请咨询供应商！");
            return response;
        }
        log.info("8、发送流程：发送供应商---聚合话费----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+"返回数据：" + resultContent+"***************发送请求耗时："+
                (System.currentTimeMillis()-start));

        JSONObject jsonObject= JSON.parseObject(resultContent);
        int error_code = jsonObject.getInteger("error_code");
        if (error_code == 0) {
            String result = jsonObject.getString("result");
            JSONObject resultObject = JSONObject.parseObject(result);
            response.setStatusCode(resultObject.getString("game_state"));
            response.setStatus(ChargeSubmitResponse.Status.SUCCESS);
            log.info("8、发送流程：发送供应商---聚合----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+" 提交到聚合话费成功！");

        } else {
            String result = jsonObject.getString("result");
            JSONObject resultObject = JSONObject.parseObject(result);
            response.setStatusCode(resultObject.getString("game_state"));
            response.setStatus(ChargeSubmitResponse.Status.FAIL);
            response.setStatusMsg(error_code+"请咨询供应商！");
            log.info("8、发送流程：发送供应商---聚合----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+" 提交到聚合话费失败！原因："+error_code+"请咨询供应商！");
        }

        return response;
    }
}
