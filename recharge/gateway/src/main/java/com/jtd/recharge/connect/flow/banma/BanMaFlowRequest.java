package com.jtd.recharge.connect.flow.banma;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jtd.recharge.base.util.GroovyConfigUtil;
import com.jtd.recharge.base.util.HttpTookit;
import com.jtd.recharge.bean.ChargeRequest;
import com.jtd.recharge.bean.ChargeSubmitResponse;
import com.jtd.recharge.connect.base.ConnectReqest;
import com.jtd.recharge.dao.bean.util.SHAUtil;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import sun.misc.BASE64Encoder;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by lhm on 2017/3/28.
 * 斑马省网流量 充值
 */
@Service
public class BanMaFlowRequest implements ConnectReqest {

    private Log log = LogFactory.getLog(this.getClass());
    private static Properties properties = null;

    private static Map config = new HashMap();

    static {
        config = GroovyConfigUtil.init("config/supply.groovy");
    }

    @Override
    public ChargeSubmitResponse chargeRequest(ChargeRequest chargeRequest) {

        log.info("BanMaRequest request");

        String supplyName = chargeRequest.getSupplyName();
        Map supplyMap = (Map) config.get(supplyName);

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//时间戳格式
        String timestamp = formatter.format(new java.util.Date());//时间戳
        String host = (String) supplyMap.get("host");//充值地址

        String accessToken = (String) supplyMap.get("accessToken");//appKey
        String appKey = (String) supplyMap.get("appKey");//appKey
        String appSecret = (String) supplyMap.get("appSecret");//appsecret
        String method = (String) supplyMap.get("method");
        String format = (String) supplyMap.get("format");//json
        String v = (String) supplyMap.get("v");//1.1

        String callbackURL = (String) supplyMap.get("callbackURL");//回调地址
        String mobileNo = chargeRequest.getMobile();//充值手机号
        String itemId = chargeRequest.getPositionCode();//充值流量代码
        String outerTid = chargeRequest.getChannelNum();//订单号

        HashMap<String, String> map = new HashMap<String, String>();

        map.put("timestamp", timestamp);
        map.put("method", method);
        map.put("access_token", accessToken);
        map.put("format", format);
        map.put("appKey", appKey);
        map.put("v", v);
        map.put("itemId", itemId);
        map.put("callbackURL", callbackURL);
        map.put("mobileNo", mobileNo);
        map.put("outerTid", outerTid);
        try {
            map.put("sign", sign(map,appSecret));
        } catch (IOException e) {
            e.printStackTrace();
        }

        String jsonMap=JSON.toJSONString(map);

        log.info("banMa flow request---" + JSON.toJSONString(map));
        ChargeSubmitResponse response = new ChargeSubmitResponse();
        String resultContent = "";
        try {
            resultContent = HttpTookit.doPost(host,jsonMap);
        } catch (Exception e) {
            log.error("banMa flow exception",e);
            response.setStatus(ChargeSubmitResponse.Status.SUCCESS);
            return response;
        }
        log.info("banMa flow result---" + resultContent);
        JSONObject object=JSON.parseObject(resultContent);
        String code=object.getString("code");
        if(code.equals("000")){
            String orderID="1";
            response.setStatus(ChargeSubmitResponse.Status.SUCCESS);
            response.setChannelNum(orderID);
        }else{
            response.setStatus(ChargeSubmitResponse.Status.FAIL);
            response.setStatusCode(code);
        }
        return response;
    }




    public static String sha1(String str) throws IOException {
        return byte2hex(getSHA1Digest(str));
    }

    private static byte[] getSHA1Digest(String data) throws IOException {
        byte[] bytes;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            bytes = md.digest(data.getBytes("utf-8"));
        } catch (GeneralSecurityException gse) {
            throw new IOException(gse);
        }
        return bytes;
    }

    private static String byte2hex(byte[] bytes) {
        StringBuilder sign = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(bytes[i] & 0xFF);
            if (hex.length() == 1) {
                sign.append("0");
            }
            sign.append(hex.toUpperCase());
        }
        return sign.toString();
    }

    public static String sign(Map<String, String> param, String secret) throws IOException {
        if (param == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        List<String> paramNames = new ArrayList<>(param.size());
        paramNames.addAll(param.keySet());
        Collections.sort(paramNames);
        sb.append(secret);
        for (String paramName : paramNames) {
            sb.append(paramName).append(param.get(paramName));
        }
        sb.append(secret);
        return sha1(sb.toString());
    }
}