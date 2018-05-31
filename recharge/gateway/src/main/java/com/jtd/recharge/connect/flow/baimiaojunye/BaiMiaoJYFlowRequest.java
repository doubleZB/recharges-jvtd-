package com.jtd.recharge.connect.flow.baimiaojunye;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jtd.recharge.base.util.Ascii;
import com.jtd.recharge.base.util.GroovyConfigUtil;
import com.jtd.recharge.bean.ChargeRequest;
import com.jtd.recharge.bean.ChargeSubmitResponse;
import com.jtd.recharge.connect.base.ConnectReqest;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by liyabin on 2017/9/27.
 */
public class BaiMiaoJYFlowRequest implements ConnectReqest {

    private Log log = LogFactory.getLog(this.getClass());

    private static Map config = new HashMap();

    static {
        config = GroovyConfigUtil.init("config/supply.groovy");
    }

    @Override
    public ChargeSubmitResponse chargeRequest(ChargeRequest chargeRequest) {

        String supplyName = chargeRequest.getSupplyName();
        Map supplyMap = (Map) config.get(supplyName);
        ChargeSubmitResponse response = new ChargeSubmitResponse();

        long timestamp =System.currentTimeMillis();
        String host = (String) supplyMap.get("host");//充值地址
        String platformid = (String) supplyMap.get("platformid");//appKey
        String security = (String) supplyMap.get("security");//appKey
        String callbackURL = (String) supplyMap.get("callback_url");//回调地址
        String mobile = chargeRequest.getMobile();//充值手机号
        String productid = chargeRequest.getPositionCode();//充值流量代码
        String outerTid = chargeRequest.getChannelNum();//订单号

        HashMap<String, String> map = new HashMap<String, String>();
        HashMap<String, String> SingGmap = new HashMap<String, String>();
        try {
            map.put("mobile", DesUtil.encrypt(mobile, security));
            map.put("effecttype", "0");
            map.put("userorderno", platformid + outerTid);
            map.put("timestamp", String.valueOf(timestamp));
            map.put("productid", DesUtil.encrypt(productid, security));
            map.put("cburl", URLEncoder.encode(callbackURL));

            SingGmap.put("mobile", mobile);
            SingGmap.put("userorderno", platformid + outerTid);
            SingGmap.put("timestamp", String.valueOf(timestamp));
            SingGmap.put("productid", productid);
            SingGmap.put("platformid", platformid);
            SingGmap.put("security", security);
            String sign = Ascii.formatUrlMap(SingGmap, false);
            log.info("8、发送流程---发送供应商--百妙骏业流量"+sign);
            map.put("sign",DigestUtils.md5Hex(sign));
        } catch (Exception e) {
            e.printStackTrace();
        }
        String jsonMap = JSON.toJSONString(map);

        log.info("8、发送流程---发送供应商--百妙骏业流量--mobile =" + chargeRequest.getMobile() + " orderNum=" + chargeRequest.getChannelNum() + " 封装应用参数json报文请求参数---" + jsonMap);

        /**
         * 提交消息
         */
        String resultha;
        try {
            resultha =doPost(host, platformid, jsonMap);
        } catch (Exception e) {
            log.error("8、发送异常：发送供应商---百妙骏业流量----mobile=" + chargeRequest.getMobile() + " orderNum=" + chargeRequest.getChannelNum() + " 提交到百妙骏业流量！原因：" + e.getLocalizedMessage());
            response.setStatus(ChargeSubmitResponse.Status.SUCCESS);
            response.setStatusMsg(e.getLocalizedMessage() + "请咨询供应商！");
            return response;
        }
        log.info("8、发送流程----mobile =" + chargeRequest.getMobile() + " orderNum=" + chargeRequest.getChannelNum() + "  发送供应商---百妙骏业流量---" + jsonMap + "   ------返回数据：" + JSON.toJSONString(resultha));
        JSONObject object=JSON.parseObject(resultha);
        String respCode=object.getString("resultcode");
        String respMsg=object.getString("resultdescription");
        if(respCode.equals("00000")){
            String orderId=object.getString("userorderno").replaceAll(platformid,"");
            response.setStatus(ChargeSubmitResponse.Status.SUCCESS);
            response.setChannelNum(orderId);
            log.info("8、发送流程：发送供应商---百妙骏业----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+" 提交到百妙骏业成功！");
        }else{
            response.setStatus(ChargeSubmitResponse.Status.FAIL);
            response.setStatusCode(respCode);
            response.setStatusMsg(respMsg+"请咨询供应商！");
            log.info("8、发送流程：发送供应商---百妙骏业----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+" 提交到百妙骏业失败！原因："+respMsg+"请咨询供应商！");
        }


        return response;
    }

    /**
     * post无参数内容到指定url
     *
     * @return
     */
    public  String doPost(String urls, String strchannel, String send) throws Exception {
        StringBuffer stringBuffer = new StringBuffer();
        try {
            URL url = new URL(urls);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            String tmpplatformid = "platformid=" + "\"" + strchannel + "\"";
            connection.setRequestProperty("Authorization", tmpplatformid);
            connection.setRequestProperty("Accept", "application/json;charset=UTF-8");
            connection.setRequestProperty("Content-Type", "application/json");

            // Post 请求不能使用缓存
            connection.setUseCaches(false);
            OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
            out.write(send);
            out.flush();
            out.close();

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
            String responseLine = "";

            while ((responseLine = reader.readLine()) != null) {
                stringBuffer.append(new String(responseLine.getBytes()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuffer.toString();
    }
}
