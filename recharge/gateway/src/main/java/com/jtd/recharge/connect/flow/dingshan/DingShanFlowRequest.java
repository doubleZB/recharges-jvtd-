package com.jtd.recharge.connect.flow.dingshan;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jtd.recharge.base.util.GroovyConfigUtil;
import com.jtd.recharge.base.util.HttpTookit;
import com.jtd.recharge.bean.ChargeRequest;
import com.jtd.recharge.bean.ChargeSubmitResponse;
import com.jtd.recharge.connect.base.ConnectReqest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.*;

/**
 * Created by lhm on 2017/5/8.
 * 鼎山科技通道
 */
@Service
public class DingShanFlowRequest  implements ConnectReqest {
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
        log.info("8、发送流程：发送供应商---鼎山科技----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum());

        String host = (String) supplyMap.get("host");
        String cmd_flag = (String) supplyMap.get("cmd_flag");
        String flx_type = (String) supplyMap.get("flx_type");
        String platid = (String) supplyMap.get("platid");
        String token = (String) supplyMap.get("token");
        String mobile = chargeRequest.getMobile();
        String productcode = chargeRequest.getPositionCode();
        String orderid = chargeRequest.getChannelNum();
        String timestamp=System.currentTimeMillis()+"";

        String baseStr= "cmd_flag="+cmd_flag+"&flx_type="+flx_type +"&mobile="+mobile+"&orderid="+orderid
                +"&platid="+platid+"&productcode="+productcode+"&timestamp="+timestamp;

        SecretKeySpec key = null;
        Mac mac = null;
        String signature =null;
        try {
            key = new SecretKeySpec(token.getBytes("UTF-8"),
                    "HmacSHA1");
            mac = Mac.getInstance("HmacSHA1");
            mac.init(key);
            byte[] bytes = mac.doFinal(baseStr.getBytes("UTF-8"));
            org.apache.commons.codec.binary.Base64 base64 = new org.apache.commons.codec.binary.Base64();
            log.info("转译后的加密sign"+new String(base64.encode(bytes)));
            signature= new String(base64.encode(bytes));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }

        String data = "{"+"\"cmd_flag\":\""+cmd_flag+"\",\"flx_type\":\""+flx_type+"\"," +
        "\"mobile\":\""+mobile+"\",\"orderid\":\""+orderid+"\"," +
        "\"platid\":\""+platid+"\",\"productcode\":\""+productcode+"\"," +
        "\"timestamp\":\""+timestamp+"\",\"signature\":\""+signature+"\""+"}";

        log.info("1.鼎山科技手动转议后的json---" + data);

        Map<String, String> param = new HashMap<String, String>();
        param.put("cmd_flag", cmd_flag);
        param.put("flx_type", flx_type);
        param.put("mobile", mobile);
        param.put("orderid", orderid);
        param.put("platid", platid);
        param.put("productcode", productcode);
        param.put("timestamp", timestamp);
        param.put("signature", signature);

        log.info("8、发送流程：发送供应商---鼎山科技----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+"封装应用参数json报文请求参数:"+ JSON.toJSONString(param));

        ChargeSubmitResponse response = new ChargeSubmitResponse();
        String resultContent = "";
        try {
            resultContent = HttpTookit.doPost(host,data);
        } catch (Exception e) {
            log.error("8、发送异常：发送供应商---鼎山科技----mobile="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+" 提交到鼎山科技！原因："+ e.getLocalizedMessage());
            response.setStatus(ChargeSubmitResponse.Status.SUCCESS);
            response.setStatusMsg(e.getLocalizedMessage()+"请咨询供应商！");
            return response;
        }
        log.info("8、发送流程：发送供应商---鼎山科技----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+"返回数据：" + resultContent+"***************发送请求耗时："+
                (System.currentTimeMillis()-start));


        JSONObject object=JSON.parseObject(resultContent);
        String ret_code=object.getString("ret_code");
        if(ret_code.equals("0000")){
            String orderID=object.getString("clientorderid");
            response.setStatus(ChargeSubmitResponse.Status.SUCCESS);
            response.setChannelNum(orderID);
            log.info("8、发送流程：发送供应商---鼎山科技----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+" 提交到鼎山科技成功！");

        }else{
            response.setStatus(ChargeSubmitResponse.Status.FAIL);
            response.setStatusCode(ret_code);
            response.setStatusMsg(ret_code+"请咨询供应商！");
            log.info("8、发送流程：发送供应商---鼎山科技----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+" 提交到鼎山科技失败！原因："+ret_code+"请咨询供应商！");
        }
        return response;
    }

}
