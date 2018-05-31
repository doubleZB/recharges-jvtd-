package com.jtd.recharge.connect.flow.chunyao;

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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @autor lyp
 * 春摇 流量 充值
 */
@Service
public class ChunYaoFlowRequest implements ConnectReqest{

    private Log log = LogFactory.getLog(this.getClass());
    private static Properties properties = null;

    private static Map config = new HashMap();

    static {
        config = GroovyConfigUtil.init("config/supply.groovy");
    }

    @Override
    public ChargeSubmitResponse chargeRequest(ChargeRequest chargeRequest) {
        long start =System.currentTimeMillis();
        String supplyName = chargeRequest.getSupplyName();
        Map supplyMap = (Map) config.get(supplyName);
        log.info("8、发送流程：发送供应商---春摇流量----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum());

        String apiKey = (String) supplyMap.get("apiKey");
        String host = (String) supplyMap.get("host");
        String outTradeNo = chargeRequest.getChannelNum();
        String account = (String) supplyMap.get("account");
        String mobile = chargeRequest.getMobile();
        String packageSize = chargeRequest.getPositionCode();

        String sign=DigestUtils.md5Hex("account="+account+"&apiKey="+apiKey+
                "&mobile="+mobile+"&productNo="+packageSize+"&orderId="+outTradeNo);


        HashMap<String, String> map=new HashMap<String, String>();
        map.put("account", account);
        map.put("mobile", mobile);
        map.put("productNo", packageSize);
        map.put("orderId", outTradeNo);
        map.put("sign", sign);
        String jsonMap=JSON.toJSONString(map);
        log.info("8、发送流程：发送供应商---春摇流量----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+"封装应用参数json报文请求参数:"+ JSON.toJSONString(map));
        ChargeSubmitResponse response = new ChargeSubmitResponse();
        String resultContent = "";
        try {
            resultContent = HttpTookit.doPost(host,jsonMap);
        } catch (Exception e) {
            log.error("8、发送异常：发送供应商---春摇流量----mobile="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+" 提交到春摇流量！原因："+ e.getLocalizedMessage());
            response.setStatus(ChargeSubmitResponse.Status.SUCCESS);
            response.setStatusMsg(e.getLocalizedMessage()+"请咨询供应商！");
            return response;
        }
        log.info("8、发送流程：发送供应商---春摇流量----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+"返回数据：" + resultContent+"***************发送请求耗时："+
                (System.currentTimeMillis()-start));


        JSONObject object=JSON.parseObject(resultContent);
        String errorCode=object.getString("success");
        if(errorCode.equals("true")){
            String orderID=object.getString("tradeNo");
            response.setStatus(ChargeSubmitResponse.Status.SUCCESS);
            response.setChannelNum(orderID);
            log.info("8、发送流程：发送供应商---春摇流量----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+" 提交到春摇流量成功！");

        }else{
            response.setStatus(ChargeSubmitResponse.Status.FAIL);
            response.setStatusCode(errorCode);
            response.setStatusMsg(object.getString("message"));
            log.info("8、发送流程：发送供应商---春摇流量----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+" 提交到云媒流量失败！原因："+object.getString("message"));
        }

        return response;
    }
}
