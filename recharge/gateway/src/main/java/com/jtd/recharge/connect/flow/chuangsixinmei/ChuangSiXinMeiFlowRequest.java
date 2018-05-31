package com.jtd.recharge.connect.flow.chuangsixinmei;

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

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by lhm on 2017/5/8.
 * 创思新媒
 */
@Service
public class ChuangSiXinMeiFlowRequest implements ConnectReqest {
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
        log.info("8、发送流程：发送供应商---创思新媒----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum());

        String host = (String) supplyMap.get("host");
        String key = (String) supplyMap.get("key");
        String type = (String) supplyMap.get("type");
        String userCode = (String) supplyMap.get("userCode");
        String phone = chargeRequest.getMobile();
        String cardCode = chargeRequest.getPositionCode();
        String orderId = chargeRequest.getChannelNum();

        String baseStr= orderId+userCode+cardCode+phone;
        log.info("8、发送流程：发送供应商---创思新媒----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+"加密前的参数"+baseStr);

        String sign = ChuangSiXinMeiFlowRequest.getSha1(baseStr);
        log.info("8、发送流程：发送供应商---创思新媒----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+"SHA1加密后的参数"+sign);
        String encrypt = null;
        try {
            encrypt= ChuangSiXinMeiFlowRequest.encrypt(sign, key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.info("8、发送流程：发送供应商---创思新媒----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+"AES加密后的参数"+encrypt);

        Map<String, String> param = new HashMap<String, String>();
        param.put("type", type);
        param.put("userCode", userCode);
        param.put("phone", phone);
        param.put("cardCode", cardCode);
        param.put("orderId", orderId);
        param.put("sign", encrypt);

        log.info("8、发送流程：发送供应商---创思新媒----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+"封装应用参数json报文请求参数:"+ JSON.toJSONString(param));

        ChargeSubmitResponse response = new ChargeSubmitResponse();
        String resultContent = "";
        try {
            resultContent = HttpTookit.doPost(host,JSON.toJSONString(param));
        } catch (Exception e) {
            log.error("8、发送异常：发送供应商---创思新媒----mobile="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+" 提交到创思新媒！原因："+ e.getLocalizedMessage());
            response.setStatus(ChargeSubmitResponse.Status.SUCCESS);
            response.setStatusMsg(e.getLocalizedMessage()+"请咨询供应商！");
            return response;
        }
        log.info("8、发送流程：发送供应商---创思新媒----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+"返回数据：" + resultContent+"***************发送请求耗时："+
                (System.currentTimeMillis()-start));

        JSONObject object=JSON.parseObject(resultContent);
        String ret_code=object.getString("code");
        String msssage=object.getString("msssage");
        String orderID=object.getString("orderId");
        if(ret_code.equals("1000")){
            response.setStatus(ChargeSubmitResponse.Status.SUCCESS);
            response.setChannelNum(orderID);
            log.info("8、发送流程：发送供应商---创思新媒----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+" 提交到创思新媒成功！");

        }else{
            response.setStatus(ChargeSubmitResponse.Status.FAIL);
            response.setStatusCode(ret_code);
            response.setStatusMsg(msssage+"请咨询供应商！");
            log.info("8、发送流程：发送供应商---创思新媒----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+" 提交到创思新媒失败！原因："+msssage+"请咨询供应商！");
        }
        return response;
    }

    public static String getSha1(String str){
        if(str==null||str.length()==0){
            return null;
        }
        char hexDigits[] = {'0','1','2','3','4','5','6','7','8','9',
                'a','b','c','d','e','f'};
        try {
            MessageDigest mdTemp = MessageDigest.getInstance("SHA1");
            mdTemp.update(str.getBytes("UTF-8"));

            byte[] md = mdTemp.digest();
            int j = md.length;
            char buf[] = new char[j*2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                buf[k++] = hexDigits[byte0 >>> 4 & 0xf];
                buf[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(buf);
        } catch (Exception e) {
            // TODO: handle exception
            return null;
        }
    }

    private static String INIT_VECTOR = "aescbcinitvector";

    /**
     * 采用java的aes加密算法，AES/CBC/PKCS5Padding
     *
     * @param content 加密的内容，转utf8格式进行加密
     * @return 加密后的十六进制文本
     * @throws Exception
     */
    public static String encrypt(String content, String apiKey) throws Exception {
        if(apiKey == null) {
            throw new IllegalArgumentException("Key cannot be null!");
        }
        else if(apiKey.length() != 32 && apiKey.length() != 24 && apiKey.length() != 16) {
            throw new IllegalArgumentException("Key length must be 128/192/256 bits!");
        }

        byte[] keyBytes = apiKey.getBytes();
        SecretKeySpec skeySpec = new SecretKeySpec(keyBytes, "AES");// 创建密码器 /DES or Triple DES
        IvParameterSpec iv = new IvParameterSpec(INIT_VECTOR.getBytes());// 使用CBC模式，需要一个向量iv，可增加加密算法的强度
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");// cbc模式
        // 在线文档：http://tool.oschina.net/apidocs/apidoc?api=jdk_7u4
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
        byte[] encrypted = cipher.doFinal(content.getBytes("UTF-8"));
        return parseByte2HexStr(encrypted);// 二进制转十六进制返回（直接string这个加密byte二进制数组报错）或使用BASE64做转码。
    }

    /**
     * 将二进制转换成16进制 　　
     * @param buf 　　
     * @return 　　
     */
    public static String parseByte2HexStr(byte buf[]) {
        StringBuffer sb = new StringBuffer();
        for(int i = 0; i < buf.length; i++) {
            String hex = Integer.toHexString(buf[i] & 0xFF);
            if(hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex.toUpperCase());
        }
        return sb.toString();
    }


}
