package com.jtd.recharge.connect.flow.dahan;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jtd.recharge.base.util.GroovyConfigUtil;
import com.jtd.recharge.base.util.HttpTookit;
import com.jtd.recharge.base.util.XMLUtil;
import com.jtd.recharge.bean.ChargeRequest;
import com.jtd.recharge.bean.ChargeSubmitResponse;
import com.jtd.recharge.connect.base.ConnectReqest;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.springframework.stereotype.Service;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by liyabin on 2017/8/3.
 * 大汉三通流量
 */
@Service
public class DaHanFlowRequest   implements ConnectReqest {

    private Log log = LogFactory.getLog(this.getClass());
    private static Properties properties = null;

    private static Map config = new HashMap();

    static {
        config = GroovyConfigUtil.init("config/supply.groovy");
    }

    @Override
    public ChargeSubmitResponse chargeRequest(ChargeRequest chargeRequest) throws Exception {
        long start =System.currentTimeMillis();
        String supplyName = chargeRequest.getSupplyName();
        Map supplyMap = (Map) config.get(supplyName);
        log.info("8、发送流程：发送供应商---大汉流量----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum());

        String host = (String) supplyMap.get("host");//充值地址
        String account = (String) supplyMap.get("account");//用户账号
        String password = (String) supplyMap.get("password");//用户密码
        String mobiles = chargeRequest.getMobile();//手机号码
        String packageSize = chargeRequest.getPositionCode();//流量大小
        String clientOrderId = chargeRequest.getChannelNum();
//        String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        long timestamp =System.currentTimeMillis();


        String pwd= DigestUtils.md5Hex(password);//加密串
        log.info("8、发送流程：发送供应商---大汉流量-md5pwd加密："+pwd+"---mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum());

        String key = pwd.substring(0, 16);
        String subStringEnd = pwd.substring(16, 32);

        log.info("8、发送流程：发送供应商---大汉流量-key："+key+"向量值："+subStringEnd+"---mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum());

        String sign= DigestUtils.md5Hex(account+pwd+timestamp+mobiles+packageSize+clientOrderId);//加密串

        String encrypt = DaHanFlowRequest.encrypt(mobiles, key,subStringEnd);
        log.info("8、发送流程：发送供应商---大汉流量-Base64加密："+encrypt+"---mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum());

        HashMap<String, String> map=new HashMap<String, String>();
        map.put("host", host);
        map.put("timestamp", String.valueOf(timestamp));
        map.put("sign", sign);
        map.put("mobiles", encrypt);
        map.put("account", account);
        map.put("clientOrderId", clientOrderId);
        map.put("packageSize", packageSize);

        /**
         * 提交消息
         */
        log.info("8、发送流程：发送供应商---大汉流量----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+"封装应用参数json报文请求参数:"+ JSON.toJSONString(map));
        ChargeSubmitResponse response = new ChargeSubmitResponse();
        String resultContent = "";
        try {
            resultContent = HttpTookit.doPost(host,JSON.toJSONString(map));
        } catch (Exception e) {
            log.error("8、发送异常：发送供应商---大汉流量----mobile="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+" 提交到大汉流量！原因："+ e.getLocalizedMessage());
            response.setStatus(ChargeSubmitResponse.Status.SUCCESS);
            response.setStatusMsg(e.getLocalizedMessage()+"请咨询供应商！");
            return response;
        }
        log.info("8、发送流程：发送供应商---大汉流量----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+"返回数据：" + resultContent+"***************发送请求耗时："+
                (System.currentTimeMillis()-start));

        JSONObject object=JSON.parseObject(resultContent);
        String resultCode = object.getString("resultCode");
        String resultMsg = object.getString("resultMsg");
        try {
            if(resultCode.equals("00")){
                response.setStatus(ChargeSubmitResponse.Status.SUCCESS);
                response.setChannelNum(clientOrderId);
                log.info("8、发送流程：发送供应商---大汉流量----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+" 提交到大汉流量成功！");
            }else{
                response.setStatus(ChargeSubmitResponse.Status.FAIL);
                response.setStatusCode(resultCode);
                response.setStatusMsg(resultMsg+"请咨询供应商！");
                log.info("8、发送流程：发送供应商---大汉流量----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+" 提交到大汉流量失败!原因："+resultMsg+"请咨询供应商！");
            }
        }catch (Exception e){
            log.error("8、发送异常：发送供应商---大汉流量----mobile="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+"原因："+ e.getLocalizedMessage());
        }
        return response;
    }

//    private static String INIT_VECTOR = "3a6e9f89618277ad"

    /**
     * 采用java的aes加密算法，AES/CBC/PKCS5Padding
     *
     * @param content 加密的内容，转utf8格式进行加密
     * @return 加密后的十六进制文本
     * @throws Exception
     */
    public static String encrypt(String content, String apiKey,String INIT_VECTOR) throws Exception {
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
        return encode(encrypted);// 二进制转十六进制返回（直接string这个加密byte二进制数组报错）或使用BASE64做转码。
    }

    static public String encode(byte[] data) {
        char[] out = new char[((data.length + 2) / 3) * 4];
        for (int i = 0, index = 0; i < data.length; i += 3, index += 4) {
            boolean quad = false;
            boolean trip = false;
            int val = (0xFF & (int) data[i]);
            val <<= 8;
            if ((i + 1) < data.length) {
                val |= (0xFF & (int) data[i + 1]);
                trip = true;
            }
            val <<= 8;
            if ((i + 2) < data.length) {
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
        return String.valueOf(out);
    }

    static private char[] alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/="
            .toCharArray();
}
