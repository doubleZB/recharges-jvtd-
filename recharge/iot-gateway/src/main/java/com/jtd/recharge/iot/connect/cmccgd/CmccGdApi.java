package com.jtd.recharge.iot.connect.cmccgd;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.data.redis.core.script.DigestUtils;

import com.alibaba.fastjson.JSON;
import com.jtd.recharge.base.iot.connect.IotApi;
import com.jtd.recharge.base.iot.connect.StatusCode;
import com.jtd.recharge.base.iot.connect.SupplierParam;
import com.jtd.recharge.base.iot.connect.protocol.DateActivatedResp;
import com.jtd.recharge.base.iot.connect.protocol.DeviceResp;
import com.jtd.recharge.base.iot.connect.protocol.UsagesResp;
import com.jtd.recharge.base.util.HttpTookit;
import com.jtd.recharge.define.CardStatus;
import com.jtd.recharge.iot.connect.cmccgd.protocol.CmccGdContextResp;
import com.jtd.recharge.iot.connect.cmccgd.protocol.member.ActitimeSingleQueryResp;
import com.jtd.recharge.iot.connect.cmccgd.protocol.member.Apn;
import com.jtd.recharge.iot.connect.cmccgd.protocol.member.DailyflowRealtimeQuery;
import com.jtd.recharge.iot.connect.cmccgd.protocol.member.LifecycleSingleQueryResp;

/**
 * 广东移动
 * 
 * @author ninghui
 *
 */
public class CmccGdApi implements IotApi {
	private Log logger = LogFactory.getLog(this.getClass());
	private String SUCCESS_CODE = "0";

	@Override
	public UsagesResp usages(SupplierParam sp, String iccid) {
		UsagesResp resp = new UsagesResp();
		String host = sp.getHost();
		String key = sp.getKey();
		Map<String, String> paramMap = new TreeMap<String, String>();
		try {
			putCommonParam(sp, key, paramMap);
			paramMap.put("iccid", iccid);
			paramMap.put("method", "triopi.member.dailyflow.realtime.query");
			paramMap.put("sign", createSign(paramMap, sp.getSecret()));
			String resultContent = HttpTookit.doGet(host, paramMap);
			resultContent = DESUtils.decrypt(resultContent, sp.getSecret());
			logger.info(resultContent);
			CmccGdContextResp baseResp = JSON.parseObject(resultContent, CmccGdContextResp.class);
			if(!baseResp.getCode().equals(SUCCESS_CODE)) {
				resp.setStatusCode(StatusCode.FAIL.getValue());
				resp.setStatusMsg(baseResp.getError());
				return resp;
			}
			DailyflowRealtimeQuery dfQuery = JSON.parseObject(baseResp.getData(), DailyflowRealtimeQuery.class);
			Apn apn = dfQuery.getApnList().get(0);
			resp.setRestFlow(apn.getRestFlow());
			resp.setUsedFlow(apn.getUsedFlow());
			resp.setTotalFlow(apn.getTotalFlow());
			resp.setStatusCode(StatusCode.SUCCESS.getValue());
			resp.setStatusMsg(StatusCode.SUCCESS.name());
			logger.info(resp);
			return resp;
		} catch (Exception e) {
			logger.error("host:" + host + " param:" + paramMap, e);
			resp.setStatusCode(StatusCode.FAIL.getValue());
			resp.setStatusMsg(e.getMessage());
			return resp;
		}

	}

	@Override
	public DateActivatedResp dateActivated(SupplierParam sp, String iccid) {
		DateActivatedResp resp = new DateActivatedResp();
		String host = sp.getHost();
		String key = sp.getKey();
		Map<String, String> paramMap = new TreeMap<String, String>();
		try {
			putCommonParam(sp, key, paramMap);
			paramMap.put("iccid", iccid);
			paramMap.put("method", "triopi.member.actitime.single.query");
			paramMap.put("sign", createSign(paramMap, sp.getSecret()));
			String resultContent = HttpTookit.doGet(host, paramMap);
			resultContent = DESUtils.decrypt(resultContent, sp.getSecret());
			logger.info(resultContent);
			CmccGdContextResp baseResp = JSON.parseObject(resultContent, CmccGdContextResp.class);
			if(!baseResp.getCode().equals(SUCCESS_CODE)) {
				resp.setStatusCode(StatusCode.FAIL.getValue());
				resp.setStatusMsg(baseResp.getError());
				return resp;
			}
			ActitimeSingleQueryResp actResp = JSON.parseObject(baseResp.getData(), ActitimeSingleQueryResp.class);
			SimpleDateFormat sdfT = new SimpleDateFormat("yyyyMMddHHmmss");
			Date times = sdfT.parse(actResp.getTime());
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			resp.setDateActivated(sdf.format(times));
			resp.setStatusCode(StatusCode.SUCCESS.getValue());
			resp.setStatusMsg(StatusCode.SUCCESS.name());
			logger.info(resp);
			return resp;
		} catch (Exception e) {
			logger.error("host:" + host + " param:" + paramMap, e);
			resp.setStatusCode(StatusCode.FAIL.getValue());
			resp.setStatusMsg(e.getMessage());
			return resp;
		}
	}

	@Override
	public DeviceResp device(SupplierParam sp, String iccid) {
		DeviceResp resp = new DeviceResp();
		String host = sp.getHost();
		String key = sp.getKey();
		Map<String, String> paramMap = new TreeMap<>();
		try {
			putCommonParam(sp, key, paramMap);
			paramMap.put("iccid", iccid);
			paramMap.put("method", "triopi.member.lifecycle.single.query");
			paramMap.put("sign", createSign(paramMap, sp.getSecret()));
			String resultContent = HttpTookit.doGet(host, paramMap);
			resultContent = DESUtils.decrypt(resultContent, sp.getSecret());
			logger.info(resultContent);
			CmccGdContextResp baseResp = JSON.parseObject(resultContent, CmccGdContextResp.class);
			if(!baseResp.getCode().equals(SUCCESS_CODE)) {
				resp.setStatusCode(StatusCode.FAIL.getValue());
				resp.setStatusMsg(baseResp.getError());
				return resp;
			}
			LifecycleSingleQueryResp singleQueryResp = JSON.parseObject(baseResp.getData(), LifecycleSingleQueryResp.class);
			String status = singleQueryResp.getStatus();
			CardStatus jtdStatus = CardStatus.未知;
			if (status.equalsIgnoreCase("test")) {
				jtdStatus = CardStatus.测试;
			} else if (status.equalsIgnoreCase("silent")) {
				jtdStatus = CardStatus.未激活;
			} else if (status.equalsIgnoreCase("normal")) {
				jtdStatus = CardStatus.正常;
			} else if (status.equalsIgnoreCase("stop")) {
				jtdStatus = CardStatus.停用;
			} else if (status.equalsIgnoreCase("preclose") || status.equalsIgnoreCase("bespeakClose")) {
				jtdStatus = CardStatus.注销;
			}
			resp.setCardStatus(jtdStatus.name());
			resp.setStatusCode(StatusCode.SUCCESS.getValue());
			resp.setStatusMsg(StatusCode.SUCCESS.name());
			logger.info(resp);
			return resp;
		} catch (Exception e) {
			logger.error("host:" + host + " param:" + paramMap, e);
			resp.setStatusCode(StatusCode.FAIL.getValue());
			resp.setStatusMsg(e.getMessage());
			return resp;
		}
	}

	private void putCommonParam(SupplierParam sp, String key, Map<String, String> paramMap) {
		paramMap.put("appKey", key);
		paramMap.put("v", "3.0");
		paramMap.put("format", "json");
		paramMap.put("transID", assembleTransID(sp.getGroupCode()));
	}

	private String createSign(Map<String, String> paramMap, String secrect) {
		StringBuilder buffer = new StringBuilder(secrect);
		for (Map.Entry<String, String> entry : paramMap.entrySet()) {
			buffer.append(entry.getKey()).append(entry.getValue());
		}
		buffer.append(secrect);
		logger.info("待签名:"+buffer.toString());
		return DigestUtils.sha1DigestAsHex(buffer.toString()).toUpperCase();
	}

	private String assembleTransID(String groupCode) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String time = sdf.format(new Date());
		return groupCode + time + getRandomString();
	}

	private String getRandomString() {
		int flag = new Random().nextInt(9999);
		if (flag < 1000) {
			flag += 1000;
		}
		return flag + "";
	}

	public static void main(String[] args) {
		CmccGdApi api = new CmccGdApi();
		String iccid = "898607B6191792616065";
		SupplierParam sp = new SupplierParam();
		sp.setHost("http://120.197.89.173:8081/openapi/router");
		sp.setGroupCode("2000488938");
		sp.setKey("h8jmcuoecb");
		sp.setSecret("37be12777528fc659bb1c1d8355debbd");
		api.usages(sp, iccid);
	}

}
