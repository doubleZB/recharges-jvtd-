package com.jtd.recharge.dao.bean.util;
import org.apache.commons.codec.digest.DigestUtils;

import java.security.MessageDigest;

/**
 * 采用SHAA加密
 * Created by SHA加密 on 2016/12/12.
 */
public class SHAUtil {
    /***
     * SHA加密 生成40位SHA码
     * @param待加密字符串
     * @return 返回40位SHA码
     */
    public static String shaEncode(String inStr) throws Exception {
        MessageDigest sha = null;
        try {
            sha = MessageDigest.getInstance("SHA");
        } catch (Exception e) {
            System.out.println(e.toString());
            e.printStackTrace();
            return "";
        }

        byte[] byteArray = inStr.getBytes("UTF-8");
        byte[] md5Bytes = sha.digest(byteArray);
        StringBuffer hexValue = new StringBuffer();
        for (int i = 0; i < md5Bytes.length; i++) {
            int val = ((int) md5Bytes[i]) & 0xff;
            if (val < 16) {
                hexValue.append("0");
            }
            hexValue.append(Integer.toHexString(val));
        }

        return hexValue.toString();
    }

    public static void main(String[] args) throws Exception {
        System.out.println(SHAUtil.shaEncode("publicadmin"));
    }
}
