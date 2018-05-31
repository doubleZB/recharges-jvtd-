package com.jtd.recharge.connect.flow.weiyu;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.aliyun.mns.client.CloudQueue;
import com.aliyun.mns.client.MNSClient;
import com.aliyun.mns.model.Message;
import com.jtd.recharge.base.constant.SysConstants;
import com.jtd.recharge.base.util.GroovyConfigUtil;
import com.jtd.recharge.base.util.HttpTookit;
import com.jtd.recharge.base.util.MessageClient;
import com.jtd.recharge.bean.ChargeReport;
import com.jtd.recharge.bean.ChargeRequest;
import com.jtd.recharge.bean.ChargeSubmitResponse;
import com.jtd.recharge.connect.flow.jilindianxin.DSA;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by liyabin on 2017/12/22.
 */
public class WeiYuFlowRequest {
    private Log log = LogFactory.getLog(this.getClass());
    private static Properties properties = null;

    private static Map config = new HashMap();

    static {
        config = GroovyConfigUtil.init("config/supply.groovy");
    }

    public ChargeSubmitResponse chargeRequest(ChargeRequest chargeRequest) {
        String supplyName = chargeRequest.getSupplyName();
        Map supplyMap = (Map) config.get(supplyName);
        long start =System.currentTimeMillis();
        log.info("8、发送流程---发送供应商---威宇--mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum());

        String host = (String) supplyMap.get("host");
        String APPSecret = (String) supplyMap.get("APPSecret");
        String ordertype = (String) supplyMap.get("ordertype");
        String callbackUrl = (String) supplyMap.get("callback_url");
        String version = (String) supplyMap.get("version");
        String appId = (String) supplyMap.get("appId");
        String plat_offer_id = chargeRequest.getPositionCode();
        String request_no =  chargeRequest.getChannelNum();
        String timeStamp = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
        JSONObject head=new JSONObject();
        String sign= DigestUtils.md5Hex(timeStamp+request_no+appId+APPSecret);
        head.put("VERSION",version);
        head.put("TIMESTAMP",timeStamp);
        head.put("SEQNO",request_no);
        head.put("APPID",appId);
        head.put("SECERTKEY",sign);


        JSONObject map=new JSONObject();
        String signs= DigestUtils.md5Hex(APPSecret+chargeRequest.getMobile()+plat_offer_id+ordertype+request_no);
        map.put("SIGN",signs);
        map.put("USER",chargeRequest.getMobile());
        map.put("PACKAGEID",plat_offer_id);
        map.put("ORDERTYPE",ordertype);
        map.put("EXTORDER",request_no);
        map.put("CALLBACKURL",callbackUrl);
        map.put("EXTORDER",request_no);
        JSONObject content=new JSONObject();
        content.put("CONTENT",map)
        ;
        JSONObject body=new JSONObject();
        body.put("HEADER",head);
        body.put("MSGBODY",content);

        log.info("8、发送流程---发送供应商---威宇--mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+" 封装应用参数json报文请求参数---" +body.toString());

        ChargeSubmitResponse response = new ChargeSubmitResponse();
        String resultContent = "";
        try {
            resultContent = HttpTookit.doPost(host,body.toString());
        } catch (Exception e) {
            log.error("8、发送异常：发送供应商---威宇----mobile="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+" 提交到威宇！原因："+ e.getLocalizedMessage());
            response.setStatus(ChargeSubmitResponse.Status.SUCCESS);
            return response;
        }
        log.info("8、发送流程--发送供应商---威宇--mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+"  封装应用参数json报文请求参数---" +body.toString()+"   ------返回数据：" + resultContent+"*******发送请求耗时："+
                (System.currentTimeMillis()-start));

        JSONObject object= JSON.parseObject(resultContent);
        JSONObject msgbody1=object.getJSONObject("MSGBODY").getJSONObject("RESP");
        String respCode=msgbody1.getString("RCODE");
        if("00".equals(respCode)){
            response.setStatus(ChargeSubmitResponse.Status.SUCCESS);
            response.setChannelNum(chargeRequest.getChannelNum());
        }else{
            log.info("8.威宇提交失败直接回调失败请咨询供应商！");
            response.setStatus(ChargeSubmitResponse.Status.FAIL);
            response.setStatusCode(respCode);
        }

        return response;
    }


    /**
     * 生成定单号
     * @return
     */
    public static String getOrderNum() {
        String orderNum = "";
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String datestr =formatter.format(new Date());
        String random  = RandomStringUtils.randomNumeric(10);
        orderNum = datestr + random;
        return orderNum;
    }
}
