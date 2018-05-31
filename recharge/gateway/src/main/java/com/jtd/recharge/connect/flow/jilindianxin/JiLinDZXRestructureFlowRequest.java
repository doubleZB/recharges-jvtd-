package com.jtd.recharge.connect.flow.jilindianxin;

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
public class JiLinDZXRestructureFlowRequest {
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
        log.info("8、发送流程---发送供应商---吉林电信--mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum());

        String host = (String) supplyMap.get("host");
        String partner_no = (String) supplyMap.get("partner_no");
        String contract_id = (String) supplyMap.get("contract_id");
        String activity_id = (String) supplyMap.get("activity_id");
        String order_type = (String) supplyMap.get("order_type");
        String effect_type = (String) supplyMap.get("effect_type");
        String secreKey = (String) supplyMap.get("secreKey");
        String vector = (String) supplyMap.get("vector");
        String service_code = (String) supplyMap.get("service_code");

        String plat_offer_id = chargeRequest.getPositionCode();
        String request_no =  chargeRequest.getChannelNum();

        JSONObject reqJson=new JSONObject();
        reqJson.put("partner_no",partner_no);
        Map code=new HashMap();
        code.put("request_no",request_no);
        code.put("service_code",service_code);
        code.put("contract_id",contract_id);
        code.put("activity_id",activity_id);
        code.put("phone_id",chargeRequest.getMobile());
        code.put("order_type",order_type);
        code.put("plat_offer_id",plat_offer_id);
        code.put("effect_type",effect_type);
        log.info("code加密之前"+JSONObject.toJSONString(code));
        String sgin="";
        try {
             sgin=DSA.Encrypt(JSONObject.toJSONString(code),secreKey,vector);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }
        reqJson.put("code",sgin);


        log.info("8、发送流程---发送供应商---吉林电信--mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+" 封装应用参数json报文请求参数---" +reqJson.toString());

        ChargeSubmitResponse response = new ChargeSubmitResponse();
        String resultContent = "";
        try {
            resultContent = HttpTookit.doPost(host,reqJson.toString());
        } catch (Exception e) {
            log.error("8、发送异常：发送供应商---吉林电信----mobile="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+" 提交到吉林电信！原因："+ e.getLocalizedMessage());
            response.setStatus(ChargeSubmitResponse.Status.SUCCESS);
            return response;
        }
        log.info("8、发送流程--发送供应商---吉林电信--mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+"  封装应用参数json报文请求参数---" +reqJson.toString()+"   ------返回数据：" + resultContent+"*******发送请求耗时："+
                (System.currentTimeMillis()-start));


        JSONObject object= JSON.parseObject(resultContent);
        String respCode=object.getString("result_code");
        if("00000".equals(respCode)){
            response.setStatus(ChargeSubmitResponse.Status.SUCCESS);
            response.setChannelNum(chargeRequest.getChannelNum());
        }else{
            log.info("8.吉林电信提交失败直接回调失败请咨询供应商！");
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
