package com.jtd.recharge.schedule.iot;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.script.DigestUtils;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.jtd.recharge.base.iot.connect.protocol.UsagesResp;
import com.jtd.recharge.base.util.HttpTookit;
import com.jtd.recharge.dao.po.IotCard;
import com.jtd.recharge.dao.po.IotSupply;
import com.jtd.recharge.service.iot.CardService;
import com.jtd.recharge.service.iot.SuppplierService;

public class CardInfoSync {
	private Logger logger = Logger.getLogger(this.getClass());
	@Autowired
	private CardService cardService;
	@Autowired
	private SuppplierService suppplierService;
	
	public void work() {
		List<IotSupply> supplyList = suppplierService.getAllSupply().getList();
		for (IotSupply supply : supplyList) {
			if(StringUtils.isBlank(supply.getParams())) {
				continue;
			}
			Integer pageNumber = 1;
			Integer pageSize = 10;
			PageInfo<IotCard> cardPageInfo = cardService.selectForSyncBySupplyId(pageNumber, pageSize, supply.getId());
			int pages = cardPageInfo.getPages();
			for (IotCard card : cardPageInfo.getList()) {
				
			}
		
			for (int index = 2; index <= pages; index++) {
				cardPageInfo = cardService.selectForSyncBySupplyId(pageNumber, pageSize, supply.getId());
				
				
			}
			
		}
	}
	

	public UsagesResp usages(String iccid,String msisdn) {
		String host = Config.iotGateWay.getValue()+"/iotQuery/usages";
		Map<String, String> params = new TreeMap<>();
		String token = Config.iotToken.getValue();
		params.put("token", token);
		params.put("iccid", iccid);
		params.put("msisdn", msisdn);
		StringBuilder buffer = new StringBuilder(token);
		for (Map.Entry<String, String> entry : params.entrySet()) {
			buffer.append(entry.getKey()).append(entry.getValue());
		}
		buffer.append(token);
		String sign = DigestUtils.sha1DigestAsHex(buffer.toString());
		params.put("sign", sign);
		UsagesResp resp = null;
		try {
			String result = HttpTookit.doGet(host, params);
			resp = JSON.parseObject(result,UsagesResp.class);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
		return resp;
	}
	
}
