package com.jtd.recharge.connect.flow.xinmu;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONObject;

public class ToolsUtil {

	private static JSONObject para = new JSONObject();

	/**
	 * 获得签名串sign SIGN 加密
	 * 
	 * @param params
	 *            消息体字符串
	 * @param busiId
	 *            业务ID
	 * @param method
	 *            业务方法
	 * @param timestamp
	 *            时间戳
	 * @param sign
	 *            密钥SK
	 * @return
	 */
	// 将相关的参数转为签名串
	public static String mapToRessignVerify(Map<String, String> parameterMap,
			Map<String, String> signatureMap) {
		String parameterString = JSONObject.fromObject(parameterMap).toString();
		return stringToRessignVerify(parameterString, signatureMap);
	}
	
	// 将相关的参数转为签名串
	public static String stringToRessignVerify(String paramStr,
			Map<String, String> signatureMap) {
		String secretKey = signatureMap.get("SecretKey");
		signatureMap.remove("SecretKey");
		para = JSONObject.fromObject(paramStr);
		List<Map.Entry<String, String>> list = SortUtil
				.mapKeySort(signatureMap);
		String param = list.toString().replace("[", "").replace("]", "")
				.replaceAll("=", "").replaceAll(",", "").replaceAll(" ", "");
		return SignUtil.md5Digest(secretKey + param + para + secretKey);
	}
	
	// 将相关的参数转为签名串
	public static String getRessignVerify(String paramStr,
			Map<String, String> signatureMap) {
		signatureMap.put("sign_type", "md5"); // 固定值
		String secretKey = signatureMap.get("SecretKey");
		signatureMap.remove("SecretKey");
		para = JSONObject.fromObject(paramStr);
		List<Map.Entry<String, String>> list = SortUtil
				.mapKeySort(signatureMap);
		String param = list.toString().replace("[", "").replace("]", "")
				.replaceAll("=", "").replaceAll(",", "").replaceAll(" ", "");
		return SignUtil.md5Digest(secretKey + param + para + secretKey);
	}

	/* 对map的key值进行排序 */

	/* 获得参数的字符串 */
	public static String getUrlParameter(Map<String, String> map) {
		String parameter = "";
		map.remove("SecretKey");
		for (Map.Entry<String, String> entity : map.entrySet()) {
			if (entity.getKey().equals("sign")) {
				parameter = parameter + "&" + entity.getKey() + "="
						+ entity.getValue();
			} else {
				parameter = entity.getKey() + "=" + entity.getValue() + "&"
						+ parameter;
			}
		}
		return parameter.replace("&&", "&");
	}
	
	/* 对外接口使用获得返回的json字符串 */
	public static String getUrl(Map<String, String> parameterMap,
			Map<String, String> signatureMap, String visitUrl) {
		// 获得签名字符串
		String sign = mapToRessignVerify(parameterMap, signatureMap);
		signatureMap.put("sign", sign);
		String parameter = getUrlParameter(signatureMap);// 获得参数的字符串
		return visitUrl + parameter;// 获得总的访问路径
	}
	
	public static void main(String[] args) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("business_id", "qweqw");
		map.put("timestamp", "qweqw");
		map.put("method", "soshare.xl.open.data_flow.order");
		map.put("SecretKey", "qweqw");

		Set<String> keys = map.keySet();
		String[] texts = new String[keys.size()];// (Object[]) keys.toArray();
		for (int i = 0,keyNum = keys.size(); i < keyNum; i++) {
			// texts[i] = keys.;
		}

	}
}
