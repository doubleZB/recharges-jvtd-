package com.jtd.recharge.connect.flow.rongman;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;

import com.google.gson.GsonBuilder;

public class Utils {


	public static String post(String orderUrl,String json) throws Exception {
		URL url = new URL(orderUrl);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Content-Type", "application/json");
		conn.setDoOutput(true);
		conn.setDoInput(true);
		OutputStream out = conn.getOutputStream();
		out.write(json.getBytes("UTF-8"));
		out.flush();
		out.close();
		InputStream in = conn.getInputStream();
		ByteArrayOutputStream response = new ByteArrayOutputStream();
		int i = -1;
		while ((i = in.read()) != -1) {
			response.write(i);
		}
		response.flush();
		return new String(new String(response.toByteArray(), "UTF-8"));
	}
	
	public static String post(String orderUrl,  Map<String, Object> bodyMap) throws Exception {
		URL url = new URL(orderUrl);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Content-Type", "application/json");
		conn.setDoOutput(true);
		conn.setDoInput(true);
		OutputStream out = conn.getOutputStream();
		String json = new GsonBuilder().create().toJson(bodyMap);
		System.out.println(json);
		out.write(json.getBytes("UTF-8"));
		out.flush();
		out.close();
		InputStream in = conn.getInputStream();
		ByteArrayOutputStream response = new ByteArrayOutputStream();
		int i = -1;
		while ((i = in.read()) != -1) {
			response.write(i);
		}
		response.flush();
		return new String(new String(response.toByteArray(), "UTF-8"));
	}
}
