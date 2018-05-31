package com.jtd.recharge.connect.flow.lingdian;

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
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @autor jipengkun
 * 零点 流量 充值
 */
@Service
public class LingDianFlowRequest implements ConnectReqest{

    private Log log = LogFactory.getLog(this.getClass());
    private static Properties properties = null;

    private static Map config = new HashMap();

    static {
        config = GroovyConfigUtil.init("config/supply.groovy");
    }

    @Override
    public ChargeSubmitResponse chargeRequest(ChargeRequest chargeRequest) {
        /*String supplyName = chargeRequest.getSupplyName();
        Map supplyMap = (Map) config.get(supplyName);

        String host = (String) supplyMap.get("host");
        String username = (String) supplyMap.get("username");


        LingDianRequestDTO dto = new LingDianRequestDTO();
        dto.setUsername(username);
        dto.setTradeNo(chargeRequest.getChannelNum()); //本地订单号或者交易号
        dto.setMobiles(chargeRequest.getMobile()); //单个号码
        dto.setSpec(chargeRequest.getPositionCode());
        dto.setAreaType("c");
        dto.setEffectiveType("tm");
        dto.generateSignature(key);

        JSONObject o = ClientUtils.getJson(method, dto);
        boolean ok = o.getBoolean("ok");
        int code = o.getIntValue("code");
        JSONArray array = o.getJSONArray("object");
        if (!ok) {
            String message = o.getString("message"); //错误原因,配合code一起看
            return;
        }

        String sign= DigestUtils.md5Hex(apiKey+"account="+account+"&action="+action+"&phone="+phone+"&range="+range+"&size="+size+"&timeStamp="+timeStamp+apiKey);


        Map param = new HashMap();
        param.put("account",account);
        param.put("action",action);
        param.put("phone",phone);
        param.put("size",size);
        param.put("range",range);
        param.put("timeStamp",timeStamp);
        param.put("orderNo",orderNo);
        param.put("sign",sign);


        log.info("shangtong flow request---" + JSON.toJSONString(param));
        ChargeSubmitResponse response = new ChargeSubmitResponse();
        String resultContent = "";
        try {
            resultContent = HttpTookit.doPost(host,param);
        } catch (Exception e) {
            log.error("shangtong flow exception",e);

            response.setStatus(ChargeSubmitResponse.Status.FAIL);
            return response;
        }
        log.info("shangtong flow result---" + resultContent);


        JSONObject object=JSON.parseObject(resultContent);
        String respCode=object.getString("respCode");
        if(respCode.equals("0000")){
            String orderID=object.getString("orderID");

            response.setStatus(ChargeSubmitResponse.Status.SUCCESS);
            response.setChannelNum(orderID);
        }else{
            response.setStatus(ChargeSubmitResponse.Status.FAIL);
            response.setStatusCode(respCode);
        }*/

        return null;
    }
}
