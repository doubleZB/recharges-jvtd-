package com.jtd.recharge.connect.flow.haihang;

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

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
/**
 * Created by lhm on 2017/6/26.一次修改2017/10/12
 * 海航流量
 */
public class HaiHangFlowRequest implements ConnectReqest {
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

        String host = (String) supplyMap.get("host");
        String appKey = (String) supplyMap.get("appKey");
        String hpno = (String) supplyMap.get("hpno");
        String pin = (String) supplyMap.get("pin");
        String channelCode = (String) supplyMap.get("channelCode");
        String requestAmount = (String) supplyMap.get("requestAmount");
        String payMone = (String) supplyMap.get("payMone");
        String destAttr = (String) supplyMap.get("destAttr");
        String operationCode = (String) supplyMap.get("operationCode");
        String callbackUrl = (String) supplyMap.get("callback_url");

        String rcgMobile = chargeRequest.getMobile();
        String ofrId = chargeRequest.getPositionCode();//流量包资费代码

        SimpleDateFormat formatter = new SimpleDateFormat("YYYYMMDDhhmmss");
        String timeStamp=formatter.format(new java.util.Date());
        String orderNumber = chargeRequest.getChannelNum();//订单号

        String signOne =appKey+"hpno="+hpno+"&ofrId="+ofrId+"&orderNumber="+orderNumber+"&pin="+pin+"&rcgMobile="+rcgMobile+"&timeStamp="+timeStamp+appKey;
        log.info("加密之前的参数-------"+signOne);
        String sign = DigestUtils.md5Hex(signOne);

        Map<String, Object> bodyMap = new HashMap();
        bodyMap.put("hpno", hpno);
        bodyMap.put("pin", pin);
        bodyMap.put("ofrId", ofrId);
        bodyMap.put("rcgMobile", rcgMobile);
        bodyMap.put("orderNumber", orderNumber);
        bodyMap.put("destAttr",destAttr);
        bodyMap.put("channelCode", channelCode);
        bodyMap.put("requestAmount",requestAmount);
        bodyMap.put("payMone", payMone);//支付方式（1：支付宝  2：微信 3：银联 4：话费宝 5：其他）
        bodyMap.put("operationCode", operationCode);

        log.info("8、发送流程----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+"海航流量===body内容---" +JSON.toJSONString(bodyMap)+"  ------发送之前");

        Map<String, String> headerMap = new HashMap();
        headerMap.put("appKey", appKey);
        headerMap.put("sign", sign);
        headerMap.put("timeStamp", timeStamp);
        headerMap.put("body", JSON.toJSONString(bodyMap));

        log.info("8、发送流程----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+"海航流量===json内容---" +JSON.toJSONString(headerMap)+"  ------发送之前");

        ChargeSubmitResponse response = new ChargeSubmitResponse();
        String resultContent = "";
        try {
            resultContent = HttpTookit.doGet(host,headerMap);
        }catch (Exception e) {
            log.error("8、发送流程----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+" 提交到海航失败！原因："+e.getMessage()+"请咨询供应商！");
            response.setStatus(ChargeSubmitResponse.Status.SUCCESS);
            response.setStatusCode(e.getMessage());
            response.setStatusMsg(e.getMessage()+"请咨询供应商！");
            return response;
        }

        log.info("8、发送流程----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+"  发送供应商---海航流量； 封装应用参数json报文请求参数---" +JSON.toJSONString(headerMap)+"返回数据：" + resultContent+"****发送请求耗时："+
                (System.currentTimeMillis()-start));
        JSONObject responseInfo=JSON.parseObject(resultContent);
        String Code = responseInfo.getString("Code");
        String errorMSG = responseInfo.getString("Data");
        String ResultCode = responseInfo.getJSONObject("Data").getString("ResultCode");

        if("200".equals(Code) && "0".equals(ResultCode)){
            response.setStatus(ChargeSubmitResponse.Status.SUCCESS);
            response.setChannelNum(orderNumber);
            log.info("8、发送流程----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+" 提交到海航成功！");
        }else{
            log.info("8、发送流程----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+" 提交到海航失败！原因："+ errorMSG +"请咨询供应商！");
            response.setStatus(ChargeSubmitResponse.Status.FAIL);
            response.setStatusCode(Code);
            response.setStatusMsg(errorMSG+"请咨询供应商！");
        }
        return response;
    }

}
