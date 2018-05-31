package com.jtd.recharge.connect.flow.ronglian;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jtd.recharge.base.util.GroovyConfigUtil;
import com.jtd.recharge.bean.ChargeRequest;
import com.jtd.recharge.bean.ChargeSubmitResponse;
import com.jtd.recharge.connect.base.ConnectReqest;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import javax.net.ssl.*;
import java.io.*;
import java.net.URL;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

/**
 * @autor jipengkun
 * 容联 流量 充值
 */
@Service
public class RongLiangFlowRequest implements ConnectReqest{

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
        log.info("8、发送流程--发送供应商---容连流量--mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum());

        ChargeSubmitResponse response = new ChargeSubmitResponse();
        String[] result = {"success",""};
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddhhmmss");
        String user= (String) supplyMap.get("user");
        String token=(String) supplyMap.get("token");

        String datetime=formatter.format(new java.util.Date());
        String sig= DigestUtils.md5Hex(user+token+datetime);

        String host= supplyMap.get("host") + "?sig="+sig;
        String appId=(String) supplyMap.get("appId");
        String phoneNum=chargeRequest.getMobile();

        String sn=chargeRequest.getPositionCode();
        String packet=chargeRequest.getPackageSize();
        String reason="lyptest";
        String callbackUrl = (String) supplyMap.get("callback_url");//回调地址

        HashMap<String, String> map=new HashMap<String, String>();
        map.put("appId", appId);
        map.put("phoneNum", phoneNum);
        map.put("customId", chargeRequest.getChannelNum());
        map.put("sn", sn);
        map.put("packet", packet);
        map.put("reason", reason);
        map.put("callbackUrl", callbackUrl);
        String toJson= JSON.toJSONString(map);

        log.info("8、发送流程---发送供应商--容连流量--mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+" 封装应用参数json报文请求参数---" +toJson);

        /**
         * 提交消息
         */
        String[] resultha;
        try{
            resultha=postHttps(host,toJson,datetime);
        }catch (Exception e){
            log.error("8、发送异常：发送供应商---容连流量----mobile="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+" 提交到容连流量！原因："+ e.getLocalizedMessage());
            response.setStatus(ChargeSubmitResponse.Status.SUCCESS);
            response.setStatusMsg(e.getLocalizedMessage()+"请咨询供应商！");
            return response;
        }
        log.info("8、发送流程----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+"  发送供应商---容连流量---" +toJson+"   ------返回数据：" + JSON.toJSONString(resultha)+"*******发送请求耗时："+
                (System.currentTimeMillis()-start));

        if(!resultha[0].equals("failed")) {
            String resltstr=resultha[1];
            JSONObject object = JSON.parseObject(resltstr);

            String statusCode = object.getString("statusCode");
            String statusMsg = object.getString("statusMsg");
            String status = object.getString("status");//
            String supplyOrderNum = object.getString("rechargeId");//容联订单号
            String orderNum = object.getString("customId");//第三方交易id


            //流量充值的结果  status  1,充值中 4,失败（测试档位返回3.成功）
            if (resultha[0].equals("success")) {
                if (statusCode.equals("000000")) {
                    response.setStatus(ChargeSubmitResponse.Status.SUCCESS);
                    log.info("8、发送流程：发送供应商---容连流量----mobile =" + chargeRequest.getMobile() + " orderNum=" + chargeRequest.getChannelNum() + " 提交容连流量成功！");

                } else {
                    response.setStatus(ChargeSubmitResponse.Status.FAIL);
                    response.setStatusCode(statusCode);
                    response.setStatusMsg(statusCode + "请咨询供应商！");
                    log.info("8、发送流程：发送供应商---容连流量----mobile =" + chargeRequest.getMobile() + " orderNum=" + chargeRequest.getChannelNum() + " 提交容连流量失败！原因：" + statusCode + "请咨询供应商！");
                }
                response.setStatusCode(statusCode);
                response.setStatusMsg(statusMsg);
                response.setChannelNum(orderNum);
                response.setSupplyChannelNum(supplyOrderNum);
            } else {
                result[0] = "failed";
                result[1] = statusMsg + ":" + statusCode;

                response.setStatus(ChargeSubmitResponse.Status.FAIL);
                response.setStatusMsg("网络异常");
                response.setChannelNum(orderNum);
                response.setSupplyChannelNum(supplyOrderNum);
                log.info("8、发送流程：发送供应商---容连流量----mobile =" + chargeRequest.getMobile() + " orderNum=" + chargeRequest.getChannelNum() + " 提交容连流量失败！原因：网络异常！");
            }
        }else{
            response.setStatus(ChargeSubmitResponse.Status.FAIL);
            response.setStatusMsg("网络异常");
            response.setChannelNum(chargeRequest.getChannelNum());
            log.info("8、发送流程：发送供应商---容连流量----mobile =" + chargeRequest.getMobile() + " orderNum=" + chargeRequest.getChannelNum() + " 提交容连流量失败！原因：网络异常！");

        }
        return response;
    }


    public static String[] postHttps(String myurl, String data,String datetime) {
        class TrustAnyTrustManager implements X509TrustManager {

            public void checkClientTrusted(X509Certificate[] chain, String authType)
                    throws CertificateException {
            }

            public void checkServerTrusted(X509Certificate[] chain, String authType)
                    throws CertificateException {
            }

            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[] {};
            }

        }
        class TrustAnyHostnameVerifier implements HostnameVerifier {


            public boolean verify(String hostname, SSLSession session) {
                // TODO Auto-generated method stub
                return true;
            }
        }
        String[] return2String = { "success", "" };
        OutputStreamWriter outputStreamWriter = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader bufferedReader = null;
        try {
            long myStart = System.currentTimeMillis();
            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, new TrustManager[] { new TrustAnyTrustManager() },
                    new SecureRandom());
            URL url = new URL(myurl);
            HttpsURLConnection connection = (HttpsURLConnection) url
                    .openConnection();
            connection.setSSLSocketFactory(sc.getSocketFactory());
            connection.setHostnameVerifier(new TrustAnyHostnameVerifier());
            connection.setDoOutput(true);
            connection.setUseCaches(false);
            connection.setConnectTimeout(30000);
            connection.setReadTimeout(30000);
            connection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
            connection.setRequestProperty("Accept", "application/json;charset=UTF-8");
            connection.setRequestProperty("Content-Length", "256");
            String str="f0fca399552539130155283bfa920020:"+datetime;
            char[] charbyte=encode(str.getBytes());
            String authorization = new String(charbyte);
            connection.setRequestProperty("Authorization",authorization);


            outputStreamWriter = new OutputStreamWriter(connection.getOutputStream(),"UTF-8");

            // write parameters
            outputStreamWriter.write(data);
            outputStreamWriter.flush();

            // Get the response
            StringBuffer answer = new StringBuffer();
            inputStreamReader = new InputStreamReader(
                    connection.getInputStream(), "utf-8");

            bufferedReader = new BufferedReader(inputStreamReader);

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                answer.append(line);
            }
            outputStreamWriter.close();
            bufferedReader.close();

            connection.disconnect();
            url = null;

            System.out.println("--debug get myurl used:"
                    + (System.currentTimeMillis() - myStart));
            return2String[1]=answer.toString();
            //System.out.println("return2String是:"+return2String[1].toString());
            return return2String;

        } catch (Exception ex) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            ex.printStackTrace(pw);

            sw = null;
            pw = null;

            ex.printStackTrace();
            return2String[0] = "failed";
            return2String[1] = ex.toString();
            return return2String;
        }finally {
            if (inputStreamReader != null) {
                try {
                    inputStreamReader.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (outputStreamWriter != null) {
                try {
                    outputStreamWriter.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }


    static private char[] alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=".toCharArray();
    /**
     * base64编码
     * @param data
     * @return
     */
    static public char[] encode(byte[] data)
    {
        char[] out = new char[((data.length + 2) / 3) * 4];

        for (int i = 0, index = 0; i < data.length; i += 3, index += 4)
        {
            boolean quad = false;
            boolean trip = false;
            int val = (0xFF & (int) data[i]);
            val <<= 8;
            if ((i + 1) < data.length)
            {
                val |= (0xFF & (int) data[i + 1]);
                trip = true;
            }
            val <<= 8;
            if ((i + 2) < data.length)
            {
                val |= (0xFF & (int) data[i + 2]);
                quad = true;
            }
            out[index + 3] = alphabet[(quad ? (val & 0x3F) : 64)];
            val >>= 6;
            out[index + 2] = alphabet[(trip ? (val & 0x3F) : 64)];
            val >>= 6;
            out[index + 1] = alphabet[val & 0x3F];
            val >>= 6;
            out[index + 0] = alphabet[val & 0x3F];
        }
        return out;
    }
}
