package com.jtd.recharge.iot.gateway.action;

import java.util.Map;
import java.util.TreeMap;

import org.springframework.data.redis.core.script.DigestUtils;

import com.jtd.recharge.base.util.HttpTookit;

public class IotQueryTest {
	public static void main(String[] args) {
		//String host = "http://localhost:8080/iot-gateway/iotQuery/usages";
		String host = "http://manager.iot.yunpaas.cn:9008/iotQuery/usages";
		try {
			
			Map<String, String> params = new TreeMap<>();
			String token = "716c4e3d1c624f4fa0e6dc933120a1ce";
			params.put("token", token);
			params.put("iccid", "89860616010049046952");
			params.put("msisdn", "861064619018767");
			StringBuilder buffer = new StringBuilder(token);
			for (Map.Entry<String, String> entry : params.entrySet()) {
				buffer.append(entry.getKey()).append(entry.getValue());
			}
			buffer.append(token);
			String sign = DigestUtils.sha1DigestAsHex(buffer.toString());
			params.put("sign", sign);
			String result = HttpTookit.doGet(host, params);
			System.out.println(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
