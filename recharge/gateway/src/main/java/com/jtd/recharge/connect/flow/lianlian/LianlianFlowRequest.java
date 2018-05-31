package com.jtd.recharge.connect.flow.lianlian;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jtd.recharge.base.util.GroovyConfigUtil;
import com.jtd.recharge.base.util.HttpTookit;
import com.jtd.recharge.bean.ChargeRequest;
import com.jtd.recharge.bean.ChargeSubmitResponse;
import com.jtd.recharge.connect.base.ConnectReqest;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @autor lyp
 * 连连 流量 充值
 */
@Service
public class LianlianFlowRequest implements ConnectReqest{

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

        long start =System.currentTimeMillis();
        log.info("8、发送流程--发送供应商---连连流量--mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum());

        //准备供应商参数
        String host = (String) supplyMap.get("host");

        String partner_id = (String)supplyMap.get("partner_id");
        String orgcode = (String)supplyMap.get("orgcode");
        String password = (String)supplyMap.get("password");
        String sign_type = (String)supplyMap.get("sign_type");
        String key = (String)supplyMap.get("key");

        String data_type = (String)supplyMap.get("data_type");
        String notify_url = (String)supplyMap.get("callback_url");
        String timesta = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String order_no =chargeRequest.getChannelNum();
        String account_no = chargeRequest.getMobile();
        String data = chargeRequest.getPositionCode();

        String sign = DigestUtils.md5Hex("account_no="+account_no
                +"&data="+data+"&data_type="+data_type+"&notify_url="+notify_url+"&order_no="
                +order_no+"&orgcode="+orgcode+"&partner_id="
                +partner_id+"&password="+password
                +"&sign_type="+sign_type+"&timestamp="
                +timesta+key);

        //封装参数
        HashMap<String, String> jsonMapHeader = new HashMap<String, String>();
        jsonMapHeader.put("partner_id", partner_id);
        jsonMapHeader.put("orgcode", orgcode);
        jsonMapHeader.put("timestamp", timesta);
        jsonMapHeader.put("sign_type", sign_type);
        jsonMapHeader.put("sign", sign);

        HashMap<String, String> jsonMapBody = new HashMap<String, String>();
        jsonMapBody.put("password", password);
        jsonMapBody.put("order_no", order_no);
        jsonMapBody.put("account_no", account_no);
        jsonMapBody.put("data", data);
        jsonMapBody.put("data_type", data_type);
        jsonMapBody.put("notify_url", notify_url);

        HashMap<String, Object> jsonString = new HashMap<String, Object>();
        jsonString.put("header", jsonMapHeader);
        jsonString.put("body", jsonMapBody);
        String param=JSON.toJSONString(jsonString);

        log.info("8、发送流程---发送供应商---连连流量--mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+" 封装应用参数json报文请求参数---" +JSON.toJSONString(param));

        ChargeSubmitResponse response = new ChargeSubmitResponse();
        String resultContent = "";
        try {
            resultContent = HttpTookit.doPost(host,param);
        } catch (Exception e) {
            log.error("8、发送异常：发送供应商---连连流量----mobile="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+" 提交到连连流量！原因："+ e.getLocalizedMessage());
            response.setStatus(ChargeSubmitResponse.Status.SUCCESS);
            response.setStatusMsg(e.getLocalizedMessage()+"请咨询供应商！");
            return response;
        }
        log.info("8、发送流程----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+"  发送供应商---连连流量---" +JSON.toJSONString(param)+"   ------返回数据：" + resultContent+"*******发送请求耗时："+
                (System.currentTimeMillis()-start));


        JSONObject object=JSON.parseObject(resultContent);
        String result=object.getString("result");
        JSONObject objectResult=JSON.parseObject(result);
        String retCode=objectResult.getString("ret_code");
        String ret_msg=objectResult.getString("ret_msg");
        if(retCode.equals("10000000")){
            String body=object.getString("body");
            JSONObject objectBodey=JSON.parseObject(body);
            String orderNo=objectBodey.getString("order_no");
            response.setStatus(ChargeSubmitResponse.Status.SUCCESS);
            response.setChannelNum(orderNo);
            log.info("8、发送流程：发送供应商---连连流量----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+" 提交到连连流量成功！");

        }else{
            response.setStatus(ChargeSubmitResponse.Status.FAIL);
            response.setStatusCode(retCode);
            response.setStatusMsg(ret_msg+"请咨询供应商！");
            log.info("8、发送流程：发送供应商---连连流量----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+" 提交到连连流量失败！原因："+ret_msg+"请咨询供应商！");

        }
        return response;
    }
}
