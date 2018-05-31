package com.jtd.recharge.connect.flow.jiajia;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.MessageDigest;

/**
 * Created by liyabin on 2017/11/16.
 */
public class AES {
    // 加密
    public static String encrypt(String encData, String Key, String vector)
            throws Exception {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        byte[] raw = Key.getBytes();
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        IvParameterSpec iv = new IvParameterSpec(vector.getBytes());// 使用CBC模式，需要一个向量iv，可增加加密算法的强度
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
        byte[] encrypted = cipher.doFinal(encData.getBytes("utf-8"));
        return parseByte2HexStr(encrypted);
    }

     public static void main(String [] args) throws Exception {
         encrypt("phone=13812345678&flow=100&type=1&notify=http://unify.jjliuliang.com/xxx/xxx/hello","307@#abx7jtdk314","AES");
     }

    // 解密
    public static String decrypt(String encData, String Key, String vector) {
        try {
            byte[] raw = Key.getBytes("ASCII");
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            IvParameterSpec iv = new IvParameterSpec(vector.getBytes());
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
            byte[] encrypted1 = parseHexStr2Byte(encData);
            byte[] original = cipher.doFinal(encrypted1);
            String originalString = new String(original, "utf-8");
            return originalString;
        } catch (Exception ex) {
            return null;
        }
    }

    public static String parseByte2HexStr(byte buf[]) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < buf.length; i++) {
            String hex = Integer.toHexString(buf[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex.toUpperCase());
        }
        return sb.toString();
    }

    public static byte[] parseHexStr2Byte(String hexStr) {
        if (hexStr.length() < 1)
            return null;
        byte[] result = new byte[hexStr.length() / 2];
        for (int i = 0; i < hexStr.length() / 2; i++) {
            int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
            int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
            result[i] = (byte) (high * 16 + low);
        }
        return result;
    }


    public static String MD5(String sign, String format) {

        byte[] bytes = null;

        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            bytes = md5.digest(sign.toString().getBytes(format));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        // 将MD5输出的二进制结果转换为小写的十六进制
        StringBuilder sign_s = new StringBuilder();

        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(bytes[i] & 0xFF);

            if (hex.length() == 1) {
                sign_s.append("0");
            }

            sign_s.append(hex);

        }

        return sign_s.toString();
    }
}