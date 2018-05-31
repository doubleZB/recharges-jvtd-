package com.jtd.recharge.connect.flow.rongjing;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.sf.json.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.*;

public class ToolsUtil {
	private Log log = LogFactory.getLog(this.getClass());
	private static JSONObject para = new JSONObject();

	/**
	 * 获得签名串sign SIGN 加密
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
		List<Map.Entry<String, String>> list = ToolsUtil.mapKeySort(signatureMap);
		String param = list.toString().replace("[", "").replace("]", "")
				.replaceAll("=", "").replaceAll(",", "").replaceAll(" ", "");
		System.out.print("加密前的sign--------"+secretKey + param + para + secretKey);
		return SignUtil.md5Digest(secretKey + param + para + secretKey);
	}

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


	public static Map<String, String> jsonToMapString(String json) {
		ObjectMapper objectMapper = new ObjectMapper();
		Map<String, String> map = new HashMap<String, String>();
		try {
			// 将json字符串转成map结合解析出来，并打印(这里以解析成map为例)
			map = objectMapper.readValue(json, Map.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}

	public static List<Map.Entry<String,String>> mapKeySort(Map<String, String> map) {
		// 通过ArrayList构造函数把map.entrySet()转换成list
		List<Map.Entry<String,String>> list = new ArrayList<Map.Entry<String,String>>(map.entrySet());
		// 通过比较器实现比较排序
		Collections.sort(list, new Comparator<Map.Entry<String, String>>() {
			public int compare(Map.Entry<String, String> o1,
							   Map.Entry<String, String> o2) {
				return o1.getKey().compareTo (o2.getKey()); //升序
//				return o2.getKey().compareTo(o1.getKey()); // 倒序
			}
		});
		return list;
	}

}
