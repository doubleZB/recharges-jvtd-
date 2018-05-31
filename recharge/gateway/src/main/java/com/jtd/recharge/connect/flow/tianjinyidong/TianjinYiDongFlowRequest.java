package com.jtd.recharge.connect.flow.tianjinyidong;

import com.jtd.recharge.base.util.GroovyConfigUtil;
import com.jtd.recharge.bean.ChargeRequest;
import com.jtd.recharge.bean.ChargeSubmitResponse;
import com.jtd.recharge.connect.base.ConnectReqest;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import org.dom4j.Element;
import org.dom4j.DocumentHelper;
import org.dom4j.Document;

import javax.net.ssl.*;

/**
 * @autor lyb
 * 天津 流量 充值
 */
@Service
public class TianjinYiDongFlowRequest implements ConnectReqest{

    private Log log = LogFactory.getLog(this.getClass());
    private static Properties properties = null;

    private static Map config = new HashMap();

    static {
        config = GroovyConfigUtil.init("config/supply.groovy");
    }

    @Override
    public ChargeSubmitResponse chargeRequest(ChargeRequest chargeRequest)throws  Exception {
        ChargeSubmitResponse response = new ChargeSubmitResponse();
        String supplyName = chargeRequest.getSupplyName();
        Map supplyMap = (Map) config.get(supplyName);
        long start =System.currentTimeMillis();
        log.info("8、发送流程：发送供应商---天津 流量----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum());

        String host = (String) supplyMap.get("host");
        String sendHost=(String) supplyMap.get("sendHost");
        String apiKey = (String) supplyMap.get("AppKey");
        String appSecret = (String) supplyMap.get("AppSecret");


        String Mobile = chargeRequest.getMobile();
        String ProductId = chargeRequest.getPositionCode();

        String timeStamp = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX").format(new Date());
        String SerialNum = chargeRequest.getChannelNum();
        String xml="<?xml version=\"1.0\" encoding=\"utf-8\" ?>" +
                "<Request>" +
                    "<Datetime>"+timeStamp+"</Datetime>" +
                    "<ChargeData>" +
                        "<Mobile>"+Mobile+"</Mobile>" +
                        "<ProductId>"+ProductId+"</ProductId>" +
                        "<SerialNum>"+SerialNum+"</SerialNum>" +
                    "</ChargeData>" +
                "</Request>";
        String token = getToken(apiKey,appSecret,host);
        if(token.equals("false")){
            response.setStatus(ChargeSubmitResponse.Status.FAIL);
            return response;
        }
        String sign=DigestUtils.sha256Hex(xml+appSecret);


        log.info("8、发送流程：发送供应商---天津流量----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+"封装应用参数json报文请求参数:"+ xml);


        String[] resultContent = new String [2];
        try {
            resultContent = sendPostFlow(sendHost,xml,token,sign);
        } catch (Exception e) {
            log.error("8、发送异常：发送供应商---天津流量----mobile="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+" 提交到天津流量！原因："+ e.getLocalizedMessage());
            response.setStatus(ChargeSubmitResponse.Status.SUCCESS);
            response.setStatusMsg(e.getLocalizedMessage()+"请咨询供应商！");
            return response;
        }
        log.info("8、发送流程：发送供应商---天津流量----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+"返回数据：" + resultContent[0]+"第二个参数："+resultContent[1]+"***************发送请求耗时："+
                (System.currentTimeMillis()-start));


        if(resultContent[0].equals("200")){
            String orderID ="";
            try {
                Document document = DocumentHelper.parseText(resultContent[1]);
                Element root = document.getRootElement();
                orderID= root.element("ChargeData").elementText("SerialNum");
            }catch (Exception e){
                log.error("天津流量提交异常",e);
            }

            response.setStatus(ChargeSubmitResponse.Status.SUCCESS);
            response.setChannelNum(orderID);
            log.info("8、发送流程：发送供应商---天津流量----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+" 提交到天津流量成功！");

        }else{
            response.setStatus(ChargeSubmitResponse.Status.FAIL);
            response.setStatusCode(resultContent[0]);
            response.setStatusMsg(resultContent[0]+"请咨询供应商！");
            log.info("8、发送流程：发送供应商---天津流量----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+" 提交到天津流量失败！原因："+resultContent[0]+"请咨询供应商！");

        }

        return response;
    }


    /**
     * 获取token
     * @param appKey
     * @param appSecret
     * @param host
     * @return
     */
    public String getToken(String appKey,String appSecret,String host ) throws  Exception{

        String timestamp=new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX").format(new Date());
        String sign=DigestUtils.sha256Hex(appKey+timestamp+appSecret);
        String xml="<?xml version=\"1.0\" encoding=\"utf-8\" ?>" +
                "<Request>" +
                "<Datetime>"+timestamp+"</Datetime>" +
                "<Authorization>" +
                "<AppKey>"+appKey+"</AppKey>" +
                "<Sign>"+sign+"</Sign>" +
                "</Authorization>" +
                "</Request>";

        String heard="application/xml";
//        String[] res= postHttps(host,xml);
//
//            Document document = DocumentHelper.parseText(res[1]);
//            Element root = document.getRootElement();
            String Token = "";

        try {
            String[] res= postHttps(host,xml);
            log.info("tianjinyidong flow getToken result---" + res[1]);
            Document document = DocumentHelper.parseText(res[1]);
            Element root = document.getRootElement();
            Token = root.element("Authorization").elementText("Token");
        } catch (Exception e) {
            log.error("tianjinyidong flowgetToken exception",e);
            String bool = "false";
            return bool;
        }
            log.info("tianjinyidong flow getToken---" + Token);
        return Token;
    }


    /**
     *  获取token发送post请求
     */
    public static String[] postHttps(String myurl, String data) {
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
            connection.setRequestProperty("Content-Type", "application/xml;charset=UTF-8");

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

    /**
     * 提交流量充值
     * @param url
     * @param param
     * @param token
     * @param SIGNATURE
     * @return
     */
    public static String[] sendPostFlow(String url, Object param,String token,String SIGNATURE) {
        String[] strArr={"200",""};
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);

            HttpURLConnection conn = (HttpURLConnection) realUrl.openConnection();
            conn.setRequestProperty("Content-Type",
                    "application/xml");
            conn.setRequestProperty("4GGOGO-Auth-Token",token);
            conn.setRequestProperty("HTTP-X-4GGOGO-Signature",SIGNATURE);

            conn.setDoOutput(true);
            conn.setDoInput(true);

            out = new PrintWriter(conn.getOutputStream());

            out.print(param);

            out.flush();

            String resultCode=conn.getResponseCode()+"";
            strArr[0]=resultCode;

            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("充值错误信息："+e);
            e.printStackTrace();
        }
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        strArr[1]=result;

        return strArr;
    }

}
