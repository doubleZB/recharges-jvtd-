package com.jtd.recharge.iot.connect.cmccgd;


import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;

import org.apache.commons.codec.binary.Base64;


/**
 * @author zhangsy
 * @create 2016-11-15 14:18
 */
public class DESUtils {

    // 加密
    public static String encrypt(String src, String key) {
        try {
            DESedeKeySpec dks = new DESedeKeySpec(key.getBytes("UTF-8"));
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DESede");
            SecretKey securekey = keyFactory.generateSecret(dks);

            Cipher cipher = Cipher.getInstance("DESede");
            cipher.init(Cipher.ENCRYPT_MODE, securekey);
            byte[] b = cipher.doFinal(src.getBytes("UTF-8"));
            return Base64.encodeBase64String(b);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // 解密
    public static String decrypt(String src, String key)  {
        try {
            // --通过base64,将字符串转成byte数组
            byte[] bytesrc = Base64.decodeBase64(src);

            // --解密的key
            DESedeKeySpec dks = new DESedeKeySpec(key.getBytes("UTF-8"));
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DESede");
            SecretKey securekey = keyFactory.generateSecret(dks);

            // --Chipher对象解密
            Cipher cipher = Cipher.getInstance("DESede");
            cipher.init(Cipher.DECRYPT_MODE, securekey);
            byte[] retByte = cipher.doFinal(bytesrc);

            return new String(retByte, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }
}

