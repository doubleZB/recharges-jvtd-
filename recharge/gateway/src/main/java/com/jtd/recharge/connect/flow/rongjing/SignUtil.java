package com.jtd.recharge.connect.flow.rongjing;

import java.security.MessageDigest;

/**
 * CryptTool 封装了一些加密工具方法, 包括 3DES, MD5 等.
 */
public class SignUtil {

	public SignUtil() {
	}

	private final static String[] hexDigits = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F" };

	/**
	 * 转换字节数组为16进制字串 
	 * @param b 字节数组
	 * @return 16进制字串
	 */

	public static String byteArrayToHexString(byte[] b) {
		StringBuffer resultSb = new StringBuffer();
		for (int i = 0,bl = b.length; i < bl; i++) {
			resultSb.append(byteToHexString(b[i]));
		}
		return resultSb.toString();
	}

	private static String byteToHexString(byte b) {
		int n = b;
		if (n < 0)
			n = 256 + n;
		int d1 = n / 16;
		int d2 = n % 16;
		return hexDigits[d1] + hexDigits[d2];
	}

	/**
	 * MD5 摘要计算(byte[]). 
	 * @param src byte[]
	 * @throws Exception
	 * @return byte[] 16 bit digest
	 */
	public static byte[] md5Digest(byte[] src) throws Exception {
		MessageDigest alg = MessageDigest.getInstance("MD5"); 
		// MD5 is 16 bit message digest
		return alg.digest(src);
	}

	/**
	 * MD5 摘要计算(String). 
	 * @param src String
	 * @throws Exception
	 * @return String
	 */
	public static String md5Digest(String src) {
		try {
			return byteArrayToHexString(md5Digest(src.getBytes("UTF-8")));
		} catch (Exception e) {
			return "";
		}
	}

	/**
	 * MD5 摘要计算(String)，此函数会在源字符串后面添加"&KEY=serectKey". 
	 * @param src String
	 * @param serectKey 签名秘钥
	 * @throws Exception
	 * @return String
	 */
	public static String md5Digest(String src, String serectKey)
			throws Exception {
		return byteArrayToHexString(md5Digest((src + "&KEY=" + serectKey)
				.getBytes()));
	}



}
