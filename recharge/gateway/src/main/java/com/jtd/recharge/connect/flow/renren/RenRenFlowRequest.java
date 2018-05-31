package com.jtd.recharge.connect.flow.renren;

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

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @autor lyp
 * 仁仁 流量 充值
 */
@Service
public class RenRenFlowRequest implements ConnectReqest{

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
        log.info("8、发送流程--发送供应商---仁仁流量--mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum());

        String key = (String) supplyMap.get("apiKey");
        String host = (String) supplyMap.get("host");

        String otherParam = chargeRequest.getChannelNum();
        String account = (String) supplyMap.get("account");
        String mobile = chargeRequest.getMobile();
        String productCode = chargeRequest.getPositionCode();
        //时间加密格式
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
        String nonce =encode(account+","+df.format(new Date()));
        //签名
        String signature = DigestUtils.sha1Hex(account+df.format(new Date())+key);
        String data =  "?mobile=" + mobile +"&productCode=" + productCode +
                "&nonce="+nonce+"&signature="+signature+"&otherParam="+otherParam;


        log.info("8、发送流程---发送供应商--仁仁流量--mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+" 封装应用参数json报文请求参数---" +data);

        ChargeSubmitResponse response = new ChargeSubmitResponse();
        String resultContent = "";
        try {
            resultContent = getHttpFromUrl(host  + data);
        } catch (Exception e) {
            log.error("8、发送异常：发送供应商---仁仁流量----mobile="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+" 提交到仁仁流量！原因："+ e.getLocalizedMessage());
            response.setStatus(ChargeSubmitResponse.Status.SUCCESS);
            response.setStatusMsg(e.getLocalizedMessage()+"请咨询供应商！");
            return response;
        }
        log.info("8、发送流程----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+"  发送供应商---仁仁流量---" +data+"   ------返回数据：" + resultContent+"*******发送请求耗时："+
                (System.currentTimeMillis()-start));


        JSONObject object=JSON.parseObject(resultContent);
        String errorCode=object.getString("status");
        if(errorCode.equals("00000")){
            String orderID=object.getString("orderNo");
            response.setStatus(ChargeSubmitResponse.Status.SUCCESS);
            response.setChannelNum(orderID);
            log.info("8、发送流程：发送供应商---仁仁流量----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+" 提交到仁仁流量成功！");

        }else{
            response.setStatus(ChargeSubmitResponse.Status.FAIL);
            response.setStatusCode(errorCode);
            response.setStatusMsg(errorCode+"请咨询供应商！");
            log.info("8、发送流程：发送供应商---仁仁流量----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+" 提交到仁仁流量失败！原因："+errorCode+"请咨询供应商！");

        }

        return response;
    }





    public static String getHttpFromUrl(String myurl) {

        String[] return2String = { "success", "" };
        StringBuffer sb = new StringBuffer();

        URL url = null;
        InputStream in = null;
        BufferedReader breader = null;
        HttpURLConnection connection = null;

        try {

            url = new URL(myurl);
            connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(30000);
            connection.setReadTimeout(30000);

            if (connection.getResponseCode() == 200) {

                in = connection.getInputStream();
                breader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
                String str = breader.readLine();
                while (str != null) {
                    sb.append(str);
                    str = breader.readLine();
                }
                return2String[1]=sb.toString();
            }

        } catch (Exception ex) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            ex.printStackTrace(pw);

            sw = null;
            pw = null;

            ex.printStackTrace();
            return2String[0] = "failed";
            return2String[1] = ex.toString();
            return return2String[1];
        } finally {

            try {
                breader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                connection.disconnect();
            } catch (Exception e) {
                e.printStackTrace();
            }

            breader = null;
            in = null;
            url = null;
            connection = null;
        }
        return return2String[1];
    }



    static private char[] alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=".toCharArray();
    /**
     * base64编码
     * @param param
     * @return
     */
    static public String encode(String param)  {
        byte[] data=param.getBytes();
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

        String outStirng = new String(out);

        return outStirng;
    }
}
