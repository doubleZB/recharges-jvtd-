package com.jtd.recharge.connect.flow.dahan;

import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;

/**
 * Created by Administrator on 2017/8/7.
 */
public class Test {
    public static void main(String[] args) throws Exception {
        // TODO Auto-generated method stub
        //String timestamp=new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX").format(new Date());

        String mobilesBase64 = encrypt("18621764382", "a906449d5769fa73", "61d7ecc6aa3f6d28");
        System.out.println("mobilesBase64:===="+mobilesBase64);
    }
    //    private static String INIT_VECTOR = "3a6e9f89618277ad"

    /**
     * 采用java的aes加密算法，AES/CBC/PKCS5Padding
     *
     * @param content 加密的内容，转utf8格式进行加密
     * @return 加密后的十六进制文本
     * @throws Exception
     */
    public static String encrypt(String content, String apiKey, String INIT_VECTOR) throws Exception {
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

