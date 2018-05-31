package com.jtd.recharge.connect.flow.saiduosai;

import java.security.MessageDigest;


public class Md5Digest {
	/**
	 * @throws Exception
	 */
	public static String getKeyedDigest(String strSrc) throws Exception {
		MessageDigest md5 = MessageDigest.getInstance("MD5");
		System.out.println(strSrc);
		md5.update(strSrc.getBytes("GB2312"));
		String result = "";
		byte[] temp;
		temp = md5.digest();
		for (int i = 0; i < temp.length; i++) {
			result += Integer.toHexString((0x000000ff & temp[i]) | 0xffffff00).substring(6);
		}
		return result.toUpperCase().substring(0, 16);
	}
	
}
