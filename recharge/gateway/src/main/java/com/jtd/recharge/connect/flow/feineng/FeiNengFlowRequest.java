package com.jtd.recharge.connect.flow.feineng;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ibm.icu.text.SimpleDateFormat;
import com.jtd.recharge.base.util.GroovyConfigUtil;
import com.jtd.recharge.base.util.HttpTookit;
import com.jtd.recharge.bean.ChargeRequest;
import com.jtd.recharge.bean.ChargeSubmitResponse;
import com.jtd.recharge.connect.base.ConnectReqest;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by lhm on 2017/9/11.
 * 飞能流量
 */
@Service
public class FeiNengFlowRequest implements ConnectReqest {
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
        log.info("8、发送流程：发送供应商---飞能流量----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum());

        String host = (String) supplyMap.get("host");
        String appId = (String) supplyMap.get("appId");
        String key = (String) supplyMap.get("key");
        String appName = (String) supplyMap.get("appName");
        String merchantId = (String) supplyMap.get("merchantId");
        String notifyAddress = (String) supplyMap.get("notifyAddress");
        String chargePointNum = (String) supplyMap.get("chargePointNum");
        String chargePointDesc = (String) supplyMap.get("chargePointDesc");
        String productName = (String) supplyMap.get("productName");
        String channelId = (String) supplyMap.get("channelId");

        String phone = chargeRequest.getMobile();
        String chargeFlow = chargeRequest.getPositionCode();
        String channelNum = chargeRequest.getChannelNum();

        String date  = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
        int randNum = (int)((Math.random()*9+1)*1000);
        String orderId = date+randNum;//订单号


        String signOne= "appId="+appId+"&appName="+appName +"&channelId="+channelId+"&chargeFlow="+chargeFlow
                +"&chargePointDesc="+chargePointDesc+"&chargePointNum="+chargePointNum+"&merchantId="+merchantId
                +"&notifyAddress="+notifyAddress +"&orderId="+orderId+"&phone="+phone+"&productName="+productName+"&key="+key;

        log.info("8、发送流程：发送供应商---飞能流量----signOne "+signOne);

        String sign = DigestUtils.md5Hex(signOne).toUpperCase();//转大写

        Map<String, String> param = new HashMap<String, String>();
        param.put("orderId", orderId);
        param.put("phone", phone);
        param.put("merchantId", merchantId);
        param.put("appId", appId);
        param.put("appName", appName);
        param.put("chargeFlow", chargeFlow);
        param.put("chargePointNum", chargePointNum);
        param.put("chargePointDesc", chargePointDesc);
        param.put("productName", productName);
        param.put("channelId", channelId);
        param.put("notifyAddress", notifyAddress);
        param.put("sign", sign);

        log.info("8、发送流程：发送供应商---飞能流量----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+"封装应用参数json报文请求参数:"+ JSON.toJSONString(param));

        ChargeSubmitResponse response = new ChargeSubmitResponse();
        String resultContent = "";
        try {
            resultContent = HttpTookit.doPost(host,JSON.toJSONString(param));
        } catch (Exception e) {
            log.error("8、发送异常：发送供应商---飞能流量----mobile="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+" 提交到飞能流量！原因："+ e.getLocalizedMessage());
            response.setStatus(ChargeSubmitResponse.Status.SUCCESS);
            response.setStatusMsg(e.getLocalizedMessage()+"请咨询供应商！");
            return response;
        }
        log.info("8、发送流程：发送供应商---飞能流量----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+"返回数据：" + resultContent+"***************发送请求耗时："+
                (System.currentTimeMillis()-start));


        JSONObject object=JSON.parseObject(resultContent);
        String ret_code=object.getString("status");
        String errMsg=object.getString("errMsg");
        if(ret_code.equals("2")){
            response.setStatus(ChargeSubmitResponse.Status.SUCCESS);
            response.setChannelNum(channelNum);
            response.setSupplyChannelNum(orderId);
            log.info("8、发送流程：发送供应商---飞能流量----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+" 提交到飞能流量成功！");

        }else{
            response.setStatus(ChargeSubmitResponse.Status.FAIL);
            response.setStatusCode(ret_code);
            response.setStatusMsg(errMsg+"请咨询供应商！");
            log.info("8、发送流程：发送供应商---飞能流量----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+" 提交到飞能流量失败！原因："+errMsg+"请咨询供应商！");
        }
        return response;
    }

}
