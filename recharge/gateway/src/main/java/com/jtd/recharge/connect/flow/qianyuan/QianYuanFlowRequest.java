package com.jtd.recharge.connect.flow.qianyuan;

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
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by lyp on 2017-02-06.
 * 千源流量接口对接
 */
public class QianYuanFlowRequest implements ConnectReqest {

    private Log log = LogFactory.getLog(this.getClass());

    private static Map config = new HashMap();

    static {
        config = GroovyConfigUtil.init("config/supply.groovy");
    }

    @Override
    public ChargeSubmitResponse chargeRequest(ChargeRequest chargeRequest) {
        ChargeSubmitResponse response = new ChargeSubmitResponse();
        response.setStatus(ChargeSubmitResponse.Status.FAIL);
        long start =System.currentTimeMillis();
        log.info("8、发送流程--发送供应商---千源流量--mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum());

        String supplyName = chargeRequest.getSupplyName();
        Map supplyMap = (Map) config.get(supplyName);

        String host = (String)supplyMap.get("host");
        String custId = (String)supplyMap.get("custId");
        String secretkey = (String)supplyMap.get("secretkey");
        String requestNo = chargeRequest.getChannelNum();
        String shopProductId = chargeRequest.getPositionCode();
        String telPhone = chargeRequest.getMobile();
        String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        Map<String, String> paramsMap = new HashMap<String, String>();
        paramsMap.put("custId", custId);
        paramsMap.put("shopProductId", shopProductId);
        paramsMap.put("telPhone", telPhone);
        paramsMap.put("requestNo", requestNo);
        String json = JSON.toJSONString(paramsMap);
        String param = aesEncrypt(json, secretkey);

        log.info("8、发送流程---发送供应商---千源平台--mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+" 封装应用参数json报文请求参数---" +JSON.toJSONString(param));

        String result = "";

        try {
            result = sendPostFlow(host,param,custId,timestamp);
        } catch (Exception e) {
            log.error("8.千源 flow exception；订单参数："+ JSON.toJSONString(param),e);
            response.setStatusMsg("提交订单时候，http访问出错"+e.getMessage()+"；订单参数："+json);
            return response;
        }
        log.info("8、发送流程----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+"  发送供应商---千源流量---" +JSON.toJSONString(param)+"   ------返回数据：" + result+"*******发送请求耗时："+
                (System.currentTimeMillis()-start));

        JSONObject object=JSON.parseObject(result);
        String statusCode = object.getString("errorcode");
        if(statusCode.equals("200")){
            response.setStatus(ChargeSubmitResponse.Status.SUCCESS);
            response.setChannelNum(requestNo);
            log.info("8、发送流程：发送供应商---千源流量----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+" 提交到千源流量成功！");
        }else{
            response.setStatus(ChargeSubmitResponse.Status.FAIL);
            response.setStatusCode(statusCode);
            response.setStatusMsg(statusCode+"请咨询供应商！");
            log.info("8、发送流程：发送供应商---千源流量----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+" 提交到千源流量失败！原因："+result+"请咨询供应商！");
        }

        return response;
    }

    /**
     * post请求方法
     * @param url
     * @param param
     * @param custId
     * @param timestamp
     * @return
     */
    public static String sendPostFlow(String url, Object param,String custId,String timestamp) {
        String[] strArr={"200",""};
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) realUrl.openConnection();
            conn.setRequestProperty("Content-Type",
                    "text/plain");
            conn.setRequestProperty("datatype","json");
            conn.setRequestProperty("custId",custId);
            conn.setRequestProperty("timestamp",timestamp);

            conn.setDoOutput(true);
            conn.setDoInput(true);

            out = new PrintWriter(conn.getOutputStream());

            out.print(param);

            out.flush();
            String resultCode=conn.getResponseCode()+"";
            System.out.println(conn.getResponseCode());
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

        return result;
    }

    /**
     * aes 加密代码
     */
    private static final String ALGO = "AES";


    public static String aesDecrypt(String ciphertext, String password) throws NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException,
            IOException {
        byte[] enCodeFormat =password.getBytes();
        SecretKeySpec key = new SecretKeySpec(enCodeFormat, ALGO);
        Cipher cipher = Cipher.getInstance(ALGO);// 创建密码器
        cipher.init(Cipher.DECRYPT_MODE, key);// 初始化
        byte[] result = cipher.doFinal(new BASE64Decoder().decodeBuffer(ciphertext));
        return new String(result, "UTF-8"); //
    }

    public static String aesEncrypt(String content, String password) {
        byte[] result = null;
        try{

            byte[] enCodeFormat = password.getBytes();
            SecretKeySpec key = new SecretKeySpec(enCodeFormat, ALGO);
            Cipher cipher = Cipher.getInstance(ALGO);// 创建密码器
            byte[] byteContent = content.getBytes("UTF-8");
            cipher.init(Cipher.ENCRYPT_MODE, key);// 初始化
            result = cipher.doFinal(byteContent);
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return new BASE64Encoder().encode(result); // 加密

    }

}
