package com.jtd.recharge.action.iot;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.data.redis.core.script.DigestUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.github.pagehelper.PageInfo;
import com.jtd.recharge.base.util.HttpTookit;
import com.jtd.recharge.dao.bean.util.ReturnMsg;
import com.jtd.recharge.dao.po.IotCard;
import com.jtd.recharge.dao.po.IotProduct;
import com.jtd.recharge.dao.po.IotSupply;
import com.jtd.recharge.define.CardOperator;
import com.jtd.recharge.define.CardSize;
import com.jtd.recharge.define.CardStatus;
import com.jtd.recharge.define.CardTimeType;
import com.jtd.recharge.define.SaleStatus;
import com.jtd.recharge.service.iot.CardService;
import com.jtd.recharge.service.iot.IotProductService;
import com.jtd.recharge.service.iot.SuppplierService;

/**
 * 卡信息管理
 *
 * @author ninghui
 *
 */
@Controller
@RequestMapping("/iot/cardCenter")
public class CardCenterAction {
	private Logger logger = Logger.getLogger(this.getClass());
	@Resource
	public CardService cardService;
	@Resource
	private SuppplierService suppplierService;
	@Resource
	private IotProductService iotProductService;

	/**
	 * 到列表页面
	 *
	 * @return
	 */
	@RequestMapping("index")
	public String index(HttpServletRequest request) throws UnsupportedEncodingException {
		PageInfo<IotSupply> allSupply = suppplierService.getAllSupply();
		PageInfo<IotProduct> allProduct = iotProductService.getAllProduct();
		request.setAttribute("cardSizeList", CardSize.values());
		request.setAttribute("cardStatusList", CardStatus.values());
		request.setAttribute("operatorList", CardOperator.values());
		request.setAttribute("saleStatusList", SaleStatus.values());
		request.setAttribute("CardTimeTypeList", CardTimeType.values());
		request.setAttribute("supplyList", allSupply.getList());
		request.setAttribute("productList", allProduct.getList());
		return "/iot/cardCenter";
	}

	@RequestMapping("list")
	@ResponseBody
	public PageInfo<IotCard> list(Integer pageNumber, Integer pageSize, IotCard card) {
		logger.info("user.dir:"+System.getProperty("user.dir"));
		PageInfo<IotCard> list = cardService.selectByCondition(pageNumber, pageSize, card);
		return list;
	}

	@RequestMapping("getCardById")
	@ResponseBody
	public IotCard getCardById(Integer id) {
		IotCard iotCard = new IotCard();
		iotCard.setId(id);
		return cardService.selectOneByCondition(iotCard);
	}

	@RequestMapping("usages")
	@ResponseBody
	public ReturnMsg usages(String iccid,String msisdn) {
		ReturnMsg msg = new ReturnMsg();
		msg.setSuccess(false);
		msg.setMessage("查询成功");
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
		try {
			String result = HttpTookit.doGet(host, params);
			HashMap<String,String> map = JSON.parseObject(result,new TypeReference<HashMap<String,String>>(){});
			msg.setObject(map);
			msg.setSuccess(true);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			msg.setMessage(e.getMessage());
		}
		return msg;
	}
}
