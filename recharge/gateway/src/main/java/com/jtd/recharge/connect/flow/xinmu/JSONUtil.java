package com.jtd.recharge.connect.flow.xinmu;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

public class JSONUtil {

	
	public static Map<String, String> jsonToMapString(String json) {
		ObjectMapper objectMapper = new ObjectMapper();
		Map<String, String> map = new HashMap<String, String>();
		try {
			// 将json字符串转成map结合解析出来，并打印(这里以解析成map为例)
			map = objectMapper.readValue(json, Map.class);
			// Set<String> key = map.keySet();
			// Iterator<String> iter = key.iterator();
			// while (iter.hasNext()) {
			// String field = iter.next();
			// }
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return map;
	}
	
	public static String mapToJson(Map map) {
		JSONObject jsObj = JSONObject.fromObject(map);
		return jsObj.toString();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
	}

}
