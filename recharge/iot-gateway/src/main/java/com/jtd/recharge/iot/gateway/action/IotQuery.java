package com.jtd.recharge.iot.gateway.action;

import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.script.DigestUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.jtd.recharge.base.iot.connect.IotApi;
import com.jtd.recharge.base.iot.connect.StatusCode;
import com.jtd.recharge.base.iot.connect.SupplierParam;
import com.jtd.recharge.base.iot.connect.protocol.BaseResp;
import com.jtd.recharge.base.iot.connect.protocol.DateActivatedResp;
import com.jtd.recharge.base.iot.connect.protocol.DeviceResp;
import com.jtd.recharge.base.iot.connect.protocol.UsagesResp;
import com.jtd.recharge.base.util.CommonUtil;
import com.jtd.recharge.dao.po.IotSupply;
import com.jtd.recharge.dao.po.User;
import com.jtd.recharge.service.iot.SuppplierService;
import com.jtd.recharge.service.iot.UserApiAuthService;

/**
 * 
 * @author ninghui
 *
 */
@Controller
@RequestMapping("/iotQuery")
public class IotQuery {
	private Log logger = LogFactory.getLog(this.getClass());
	@Autowired
	public UserApiAuthService userAuthService;
	@Autowired
	public SuppplierService suppplierService;

	/**
	 * 查询流量
	 * 
	 * @param sp
	 * @param iccid
	 * @param msisdn
	 * @return
	 */
	@RequestMapping("/usages")
	@ResponseBody
	public UsagesResp usages(HttpServletRequest request, String iccid, String msisdn) {
		UsagesResp resp = new UsagesResp();
		StatusCode validParam = validParam(request);
		if (validParam != StatusCode.SUCCESS) {
			resp.setStatus(validParam);
			return resp;
		}
		SupplierParam suParam = createSupplierParam(resp,iccid);
		if(suParam == null) {
			return resp;
		}
		IotApi api = createIotApi(resp,suParam);
		return api == null ? resp : api.usages(suParam, iccid);
	}
	
	
	/**
	 * 查询激活时间
	 * 
	 * @param sp
	 * @param iccid
	 * @param msisdn
	 * @return
	 */
	@RequestMapping("/dateActivated")
	@ResponseBody
	public DateActivatedResp dateActivated(HttpServletRequest request, String iccid, String msisdn) {
		DateActivatedResp resp = new DateActivatedResp();
		StatusCode validParam = validParam(request);
		if (validParam != StatusCode.SUCCESS) {
			resp.setStatus(validParam);
			return resp;
		}
		SupplierParam suParam = createSupplierParam(resp,iccid);
		if(suParam == null) {
			return resp;
		}
		IotApi api = createIotApi(resp,suParam);
		return api == null ? resp : api.dateActivated(suParam, iccid);
	}

	/**
	 * 查询设备信息
	 * 
	 * @param sp
	 * @param iccid
	 * @param msisdn
	 * @return
	 */
	@RequestMapping("/device")
	@ResponseBody
	public DeviceResp device(HttpServletRequest request, String iccid, String msisdn) {
		DeviceResp resp = new DeviceResp();
		StatusCode validParam = validParam(request);
		if (validParam != StatusCode.SUCCESS) {
			resp.setStatus(validParam);
			return resp;
		}
		SupplierParam suParam = createSupplierParam(resp,iccid);
		if(suParam == null) {
			return resp;
		}
		IotApi api = createIotApi(resp,suParam);
		return api == null ? resp : api.device(suParam, iccid);
	}
	
	private IotApi createIotApi(BaseResp resp, SupplierParam suParam) {
		try {
			Class<?> cls = Class.forName(suParam.getClassName());
			return ((IotApi) cls.newInstance());
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			resp.setStatusCode(StatusCode.FAIL.getValue());
			resp.setStatusMsg(e.getMessage());
			return null;
		} 
	}

	
	private SupplierParam createSupplierParam(BaseResp resp,String iccid) {
		IotSupply supplier = suppplierService.getSupplierByIccid(iccid);
		if(StringUtils.isBlank(supplier.getParams())) {
			resp.setStatusCode(StatusCode.FAIL.getValue());
			resp.setStatusMsg("供应商参数未配置");
		}
		SupplierParam suParam = JSON.parseObject(supplier.getParams(), SupplierParam.class);
		if(suParam == null) {
			resp.setStatusCode(StatusCode.FAIL.getValue());
			resp.setStatusMsg("供应商参数解析错误");
		}
		return suParam;
	}
	
	
	/**
	 * 验证接口请求参数
	 * 
	 * @param request
	 * @return
	 */
	private StatusCode validParam(HttpServletRequest request) {
		String sign = request.getParameter("sign");
		String token = request.getParameter("token");
		String iccid = request.getParameter("iccid");
		logger.info("sign:" + sign + " token:" + token + " iccid:" + iccid);
		if (StringUtils.isEmpty(token) || StringUtils.isEmpty(sign) || StringUtils.isEmpty(iccid)) {
			return StatusCode.PARAM;
		}
		User user = userAuthService.findUserByToken(token);
		/**
		 * 验证ip
		 */
		String userIp = CommonUtil.getRemortIP(request);
		logger.info("user ip:" + userIp + " user white ip:" + user.getIpAddress());
		if (user.getIpAddress() != null && !("".equals(user.getIpAddress()))) {
			if (user.getIpAddress().indexOf(userIp) == -1) {
				return StatusCode.IP;
			}
		}
		Map<String, String[]> paramMap = request.getParameterMap();
		Map<String, String> treeParamMap = new TreeMap<>();
		for (Map.Entry<String, String[]> entry : paramMap.entrySet()) {
			if (!entry.getKey().equals("sign")) {
				treeParamMap.put(entry.getKey(), entry.getValue()[0]);
			}
		}
		StringBuilder buffer = new StringBuilder(token);
		for (Map.Entry<String, String> entry : treeParamMap.entrySet()) {
			buffer.append(entry.getKey()).append(entry.getValue());
		}
		buffer.append(token);
		String targetSign = DigestUtils.sha1DigestAsHex(buffer.toString());
		if (!sign.equals(targetSign)) {
			return StatusCode.SIGN;
		}
		return StatusCode.SUCCESS;
	}
}
