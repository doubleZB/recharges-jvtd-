package com.jtd.recharge.base.util;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Properties;

/**
 * Created by anna on 2016-12-26.
 */
public class SmsUtil {
    private static final Log log = LogFactory.getLog(SmsUtil.class);
    private static Properties prop = PropertiesUtils.loadProperties("config.properties");



    /**
     * 发送短信
     * @param mobiles 手机号，多个手机号以英文半角逗号隔开
     * @param content 短信内容
     */
    public static String SendSms(String mobiles,String content){
        String res = null;
        try{
            StringBuffer sendData = new StringBuffer("");
            String smsUrl = prop.getProperty("smsUrl");
            String uid = prop.getProperty("uid");
            String pwd = prop.getProperty("pwd");
            pwd =getMD5(pwd);

            sendData.append("uid=").append(uid);	// 用户名
            sendData.append("&password=").append(pwd);	// 密码
            sendData.append("&encode=").append("utf8");	// encode=GBK或者encode=utf8
            String contentBase64 = Base64.encodeBase64String(content.getBytes("utf8"));// 先用encode中定义的格式编码，再用base64加密内容
            sendData.append("&encodeType=base64");	// 固定
            sendData.append("&content=").append(contentBase64);	// base64加密后的内容
            sendData.append("&mobile=").append(mobiles);	// 手机号
            res = HttpTookit.doGet(smsUrl+"?"+sendData.toString(),null);
        }catch (Exception e){
            log.error("发送短信出错！",e);
        }
        return res;
    }

    /**
     * 自动生成一个随机数
     * @param min
     * @param max
     * @return
     */
    public static int getRandNum(int min, int max) {
        int randNum = min + (int)(Math.random() * ((max - min) + 1));
        return randNum;
    }

    public static String getMD5(String sourceStr){
        String resultStr = "";
        try {
            byte[] temp = sourceStr.getBytes();
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(temp);
            byte[] b = md5.digest();
            for (int i = 0; i < b.length; i++) {
                char[] digit = { '0', '1', '2', '3', '4', '5', '6', '7', '8',
                        '9', 'A', 'B', 'C', 'D', 'E', 'F' };
                char[] ob = new char[2];
                ob[0] = digit[(b[i] >>> 4) & 0X0F];
                ob[1] = digit[b[i] & 0X0F];
                resultStr += new String(ob);
            }
            return resultStr;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] a){
        SmsUtil.SendSms("13716205360","【聚通达】验证码：1266");
    }
}
