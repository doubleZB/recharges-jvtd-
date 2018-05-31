package com.jtd.recharge.connect.flow.beiweitongxin;

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

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by lhm on 2017/10/12.
 * 北纬通信
 */
@Service
public class BeiWeiFlowRequest implements ConnectReqest {

    private Log log = LogFactory.getLog(this.getClass());
    private static Properties properties = null;

    private static Map config = new HashMap();

    static {
        config = GroovyConfigUtil.init("config/supply.groovy");
    }

    @Override
    public ChargeSubmitResponse chargeRequest(ChargeRequest chargeRequest) throws Exception {

        log.info("8、发送流程--北纬通信--mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum());
        long start =System.currentTimeMillis();

        String supplyName = chargeRequest.getSupplyName();
        Map supplyMap = (Map) config.get(supplyName);

        String host = (String) supplyMap.get("host");
        String partner = (String) supplyMap.get("partner");
        String apiKey = (String) supplyMap.get("key");
        String scope = (String) supplyMap.get("scope");
        String vi = (String) supplyMap.get("vi");
        String phone = chargeRequest.getMobile();
        String flowsize = chargeRequest.getPositionCode();

        String orderid = chargeRequest.getChannelNum();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String timestamp=formatter.format(new java.util.Date());

        Map param = new HashMap();
        param.put("flowsize",flowsize);
        param.put("orderid",orderid);
        param.put("phone",phone);
        param.put("scope",scope);

        log.info("8、发送流程----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+"  发送供应商---北纬通信data---" +JSON.toJSONString(param));

        String data = BeiWeiFlowRequest.encrypt(vi, JSON.toJSONString(param), apiKey);

        Map paramTwo = new HashMap();
        paramTwo.put("partner",partner);
        paramTwo.put("data",data);
        paramTwo.put("timestamp",timestamp);

        log.info("8、发送流程----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+"  发送供应商---北纬通信； 封装应用参数json报文请求参数---" +JSON.toJSONString(paramTwo));

        ChargeSubmitResponse response = new ChargeSubmitResponse();
        String resultContent = "";
        try {
            resultContent = HttpTookit.doPost(host,JSON.toJSONString(paramTwo));
        } catch (Exception e) {
            log.error("8、发送异常：发送供应商---北纬通信----mobile="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+" 提交到北纬通信！原因："+ e.getLocalizedMessage());
            response.setStatusMsg(e.getLocalizedMessage()+"请咨询供应商！");
            response.setStatus(ChargeSubmitResponse.Status.SUCCESS);
            return response;
        }
        log.info("8、发送流程：发送供应商---北纬通信----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+"返回数据：" + resultContent+"***************发送请求耗时："+
                (System.currentTimeMillis()-start));

        JSONObject object=JSON.parseObject(resultContent);
        String respCode=object.getString("result");
        String msg=object.getString("remarks");
        if(respCode.equals("0000")){
            response.setStatus(ChargeSubmitResponse.Status.SUCCESS);
            response.setChannelNum(orderid);
            log.info("8、发送流程----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+" 提交到北纬通信！");
        }else{
            response.setStatus(ChargeSubmitResponse.Status.FAIL);
            response.setStatusCode(respCode);
            response.setStatusMsg(msg+"请咨询供应商！");
            log.info("8、发送流程----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+" 提交到北纬通信！原因："+msg+"请咨询供应商！");
        }
        return response;
    }

    /**
     * 采用java的aes加密算法，AES/CBC/PKCS5Padding
     *
     * @param content 加密的内容，转utf8格式进行加密
     * @return 加密后的十六进制文本
     * @throws Exception
     */
    public static String encrypt(String INIT_VECTOR,String content, String apiKey) throws Exception {
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
