package com.jtd.recharge.iot.connect.cucc;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.fastjson.JSON;
import com.jtd.recharge.iot.connect.cucc.protocol.CuccDeviceResp;
import com.jtd.recharge.iot.connect.cucc.protocol.CuccUsagesResp;
import com.jtd.recharge.base.iot.connect.IotApi;
import com.jtd.recharge.base.iot.connect.StatusCode;
import com.jtd.recharge.base.iot.connect.SupplierParam;
import com.jtd.recharge.base.iot.connect.protocol.DateActivatedResp;
import com.jtd.recharge.base.iot.connect.protocol.DeviceResp;
import com.jtd.recharge.base.iot.connect.protocol.UsagesResp;
import com.jtd.recharge.base.util.HttpTookit;
import com.jtd.recharge.define.CardStatus;

/**
 * 联通物联网平台接口
 * 
 * @author ninghui
 *
 */
public class CuccApi implements IotApi {

	private Log logger = LogFactory.getLog(this.getClass());
	/* (non-Javadoc)
	 * @see com.jtd.recharge.iot.connect.cucc.IotApi#usages(com.jtd.recharge.iot.connect.SupplierParam, java.lang.String, java.lang.String)
	 */
	@Override
	public UsagesResp usages(SupplierParam sp, String iccid) {
		UsagesResp resp = new UsagesResp();
		String host = sp.getHost() + "/devices/" + iccid+"/ctdUsages";
		String account = sp.getAccount();
		String key = sp.getKey();
		Map<String, String> headerMap = setHeaders(account, key);
		try {
			String resultContent = HttpTookit.doGet(host, headerMap, null);
			logger.info(resultContent);
			CuccUsagesResp cuccResp = JSON.parseObject(resultContent, CuccUsagesResp.class);
			BigDecimal used = new BigDecimal(cuccResp.getCtdDataUsage());
			resp.setUsedFlow(used.divide(new BigDecimal(1024*1024)).setScale(3, RoundingMode.HALF_UP).toString());
			resp.setStatusCode(StatusCode.SUCCESS.getValue());
			resp.setStatusMsg(StatusCode.SUCCESS.name());
			logger.info(resp);
			return resp;
		} catch (Exception e) {
			logger.error("host:"+host+" header:"+headerMap, e);
			resp.setStatusCode(StatusCode.FAIL.getValue());
			resp.setStatusMsg(e.getMessage());
			return resp;
		}

	}
	/* (non-Javadoc)
	 * @see com.jtd.recharge.iot.connect.cucc.IotApi#dateActivated(com.jtd.recharge.iot.connect.SupplierParam, java.lang.String, java.lang.String)
	 */
	@Override
	public DateActivatedResp dateActivated(SupplierParam sp, String iccid) {
		DateActivatedResp resp = new DateActivatedResp();
		String host = sp.getHost() + "/devices/" + iccid;
		String account = sp.getAccount();
		String key = sp.getKey();
		Map<String, String> headerMap = setHeaders(account, key);

		try {
			String resultContent = HttpTookit.doGet(host, headerMap, null);
			logger.info(resultContent);
			CuccDeviceResp cuccResp = JSON.parseObject(resultContent, CuccDeviceResp.class);
			if (cuccResp.getDateShipped() != null) {
				SimpleDateFormat sdfT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSZ");
				Date times = sdfT.parse(cuccResp.getDateShipped());
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				resp.setDateActivated(sdf.format(times));
			} else {
				resp.setDateActivated("");
			}
			resp.setStatusCode(StatusCode.SUCCESS.getValue());
			resp.setStatusMsg(StatusCode.SUCCESS.name());
			logger.info(resp);
			return resp;
		} catch (Exception e) {
			logger.error("host:"+host+" header:"+headerMap, e);
			resp.setStatusCode(StatusCode.FAIL.getValue());
			resp.setStatusMsg(e.getMessage());
			return resp;
		}

	}

	private Map<String, String> setHeaders(String account, String key) {
		String authData = account + ":" + key;
		String authCode = "";
		try {
			authCode = "Basic " + Base64.encodeBase64String(authData.getBytes("utf8"));
		} catch (UnsupportedEncodingException e) {
			logger.error(e.getMessage(), e);
		}
		Map<String, String> headerMap = new HashMap<>();
		headerMap.put("Accept", "application/json");
		headerMap.put("Authorization", authCode);
		return headerMap;
	}

	/* (non-Javadoc)
	 * @see com.jtd.recharge.iot.connect.cucc.IotApi#device(com.jtd.recharge.iot.connect.SupplierParam, java.lang.String, java.lang.String)
	 */
	@Override
	public DeviceResp device(SupplierParam sp, String iccid) {
		DeviceResp resp = new DeviceResp();
		String host = sp.getHost() + "/devices/" + iccid;
		String account = sp.getAccount();
		String key = sp.getKey();
		Map<String, String> headerMap = setHeaders(account, key);

		try {
			String resultContent = HttpTookit.doGet(host, headerMap, null);
			logger.info(resultContent);
			CuccDeviceResp cuccResp = JSON.parseObject(resultContent, CuccDeviceResp.class);
			resp.setIccid(cuccResp.getIccid());
			resp.setMsisdn(cuccResp.getMsisdn());
			resp.setImsi(cuccResp.getImsi());
			resp.setImei(cuccResp.getImei());
			String status = cuccResp.getStatus();
			CardStatus jtdStatus = CardStatus.未知;
			if (status.equalsIgnoreCase("test_ready")) {
				jtdStatus = CardStatus.测试;
			} else if (status.equalsIgnoreCase("activation_ready")) {
				jtdStatus = CardStatus.未激活;
			} else if (status.equalsIgnoreCase("activated")) {
				jtdStatus = CardStatus.正常;
			} else if (status.equalsIgnoreCase("deactivated")) {
				jtdStatus = CardStatus.停用;
			} else if (status.equalsIgnoreCase("retired")) {
				jtdStatus = CardStatus.注销;
			}
			resp.setCardStatus(jtdStatus.name());
			resp.setStatusCode(StatusCode.SUCCESS.getValue());
			resp.setStatusMsg(StatusCode.SUCCESS.name());
			logger.info(resp);
			return resp;
		} catch (Exception e) {
			logger.error("host:"+host+" header:"+headerMap, e);
			resp.setStatusCode(StatusCode.FAIL.getValue());
			resp.setStatusMsg(e.getMessage());
			return resp;
		}

	}

	public static void main(String[] args) {
		IotApi req = new CuccApi();
		SupplierParam sp = new SupplierParam();
		sp.setHost("https://api.10646.cn/rws/api/v1");
		sp.setAccount("JUTONGDA");
		sp.setKey("d133d5ea-ee3d-4aa0-89ae-25f92890f366");
		String iccid = "89860616010049046952";
		req.usages(sp, iccid);
	}
}
