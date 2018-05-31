package com.jtd.recharge.connect.flow.huaxin;

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
 * 华信 流量 充值
 */
@Service
public class HuaXinFlowRequest implements ConnectReqest{

    private Log log = LogFactory.getLog(this.getClass());
    private static Properties properties = null;

    private static Map config = new HashMap();

    static {
        config = GroovyConfigUtil.init("config/supply.groovy");
    }

    @Override
    public ChargeSubmitResponse chargeRequest(ChargeRequest chargeRequest) {
        String supplyName = chargeRequest.getSupplyName();
        Map supplyMap = (Map) config.get(supplyName);
        long start =System.currentTimeMillis();
        log.info("8、发送流程---发送供应商---华信流量--mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum());

        String host = (String) supplyMap.get("host");
        String appid = (String) supplyMap.get("appid");
        String sign_method = (String) supplyMap.get("sign_method");
        String return_format = (String) supplyMap.get("return_format");
        String productId = (String) supplyMap.get("productId");//产品id
        String callbackUrl = (String) supplyMap.get("callback_url");
        String phone = chargeRequest.getMobile();
        String standardfeeid = chargeRequest.getPositionCode();//流量包资费代码

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String timestamp=formatter.format(new java.util.Date());
        String reqId = chargeRequest.getChannelNum();//订单号

        String sign=DigestUtils.md5Hex("ffbab3dc979948e69339100dc432218e"+"appid"+appid+"callbackUrl"+callbackUrl+"phone"+phone+"productId"+productId+
                "reqId"+reqId+"return_format"+return_format+"sign_method"+sign_method+"standardfeeid"+standardfeeid+"timestamp"+timestamp+"ffbab3dc979948e69339100dc432218e");
        String sings=sign.toUpperCase();

        Map map = new HashMap<String,String>();
        map.put("appid",appid);//应用id
        map.put("phone",phone);//手机号码
        map.put("productId", productId);//产品id
        map.put("standardfeeid",standardfeeid);//流量包资费代码
        map.put("reqId",reqId);//上游订单号
        map.put("callbackUrl", callbackUrl);
        map.put("timestamp", timestamp);
        map.put("sign_method", sign_method);
        map.put("sign", sings);
        map.put("return_format", return_format);
        String jsonMap=JSON.toJSONString(map);

        log.info("8、发送流程---发送供应商---华信流量--mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+" 封装应用参数json报文请求参数---" +JSON.toJSONString(map));

        ChargeSubmitResponse response = new ChargeSubmitResponse();
        String resultContent = "";
        try {
            resultContent = HttpTookit.doPost(host,jsonMap);
        } catch (Exception e) {
            log.error("8、发送异常：发送供应商---华信流量----mobile="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+" 提交到华信流量！原因："+ e.getLocalizedMessage());
            response.setStatus(ChargeSubmitResponse.Status.SUCCESS);
            response.setStatusMsg(e.getLocalizedMessage()+"请咨询供应商！");
            return response;
        }
        log.info("8、发送流程----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+"  发送供应商---华信流量封装应用参数json报文请求参数---" +JSON.toJSONString(map)+"   ------返回数据：" + resultContent+"*******发送请求耗时："+
                (System.currentTimeMillis()-start));


        JSONObject object=JSON.parseObject(resultContent);
        String errorCode=object.getString("errorCode");
        if(errorCode.equals("0")){
            String orderID=object.getString("reqId");

            response.setStatus(ChargeSubmitResponse.Status.SUCCESS);
            response.setChannelNum(orderID);
            log.info("8、发送流程：发送供应商---华信流量----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+" 提交到华信流量成功！");

        }else{
            response.setStatus(ChargeSubmitResponse.Status.FAIL);
            response.setStatusCode(errorCode);
            response.setStatusMsg(errorCode+"请咨询供应商！");
            log.info("8、发送流程：发送供应商---华信流量----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+" 提交到华信流量失败！原因："+errorCode+"请咨询供应商！");

        }

        return response;
    }
}
