package com.jtd.recharge.action.iot;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.jtd.recharge.dao.bean.util.ReturnMsg;
import com.jtd.recharge.dao.po.AdminUser;
import com.jtd.recharge.dao.po.IotCard;
import com.jtd.recharge.dao.po.IotOutReceipt;
import com.jtd.recharge.dao.po.IotProduct;
import com.jtd.recharge.dao.po.IotSubOrder;
import com.jtd.recharge.define.CardOperator;
import com.jtd.recharge.define.CardSize;
import com.jtd.recharge.define.SaleStatus;
import com.jtd.recharge.service.iot.CardService;
import com.jtd.recharge.service.iot.IotProductService;
import com.jtd.recharge.service.iot.IotSubOrderService;
import com.jtd.recharge.service.iot.OutReceiptService;
/**
 * 卡出库
 * @author ninghui
 *
 */
@Controller
@RequestMapping("/iot/cardOut")
public class CardOutAction {
	private Logger logger = Logger.getLogger(this.getClass());
	@Resource
	public CardService cardService;
	@Resource
    private IotSubOrderService iotSubOrderService;
	@Resource
	public IotProductService iotProductService;
	@Resource
	public OutReceiptService outReceiptService;
	
	/**
	 * 到列表页面
	 * 
	 * @return
	 */
	@RequestMapping("index")
	public String index(HttpServletRequest request) throws UnsupportedEncodingException {
		request.setAttribute("cardSizeList", CardSize.values());
		request.setAttribute("operatorList", CardOperator.values());
		request.setAttribute("saleStatusList", SaleStatus.values());
		return "/iot/cardOut";
	}
	
	@RequestMapping("list")
	@ResponseBody
	public PageInfo<IotCard> list(Integer pageNumber, Integer pageSize, IotCard card) {
		card.setSaleStatus(SaleStatus.待售.getValue());
		PageInfo<IotCard> list = cardService.selectByCondition(pageNumber, pageSize, card);
		return list;
	}
	
	/**
	 * 查询子订单
	 * 
	 * @return
	 */
	@RequestMapping("/toDisplay")
	@ResponseBody
	public ReturnMsg toDisplay(String parentSerialNum,String subSerialNum) {
		ReturnMsg msg = new ReturnMsg();
    	msg.setSuccess(false);
    	IotSubOrder pur = null;
    	if(StringUtils.isNotBlank(subSerialNum)){
    		pur = iotSubOrderService.getToOutBySerialNum(subSerialNum);
    	}else if(StringUtils.isNotBlank(parentSerialNum)){
    		pur = iotSubOrderService.getToOutByParentSerialNum(parentSerialNum);
    	}else {
    		msg.setMessage("请输入订单或子订单编号");
    		return msg;
    	}
		if(pur != null) {
			msg.setSuccess(true);
			msg.setObject(pur);
		}else {
			msg.setMessage("该订单不存在!");
		}
		return msg;
	}
	
	 /**
     * 查询流量产品
     * @param supplyName
     * @return
     */
    @RequestMapping("getProduct")
    @ResponseBody
    public  List<IotProduct> getProduct(){
         PageInfo<IotProduct> productList = iotProductService.getAllProduct();
         return  productList.getList();
    }
    
    @RequestMapping("addOutReceipt")
	@ResponseBody
	public ReturnMsg addOutReceipt(HttpServletRequest request,Integer subOrderId,String subData,IotCard card) {
    	logger.info("出库条件 subOrderId:"+subOrderId+" ,subData:"+subData+" ,cardSize:"+card.getCardSize()
    	+" ,operator:"+card.getOperator()+" ,supplyId:"+card.getSupplyId()+" ,flowProductId:"+card.getFlowProductId()+" ,iccid:"+card.getIccid()
    	+" ,msisdn:"+card.getMsisdn()+" ,updateLimit:"+card.getUpdateLimit());
    	ReturnMsg msg = new ReturnMsg();
    	List<Integer> cardIdList = JSON.parseArray(subData, Integer.class);
		msg.setSuccess(false);
		msg.setMessage("出库成功!");
		IotSubOrder subOrder = iotSubOrderService.getById(subOrderId);
		if(subOrder == null) {
			msg.setMessage("该订单不存在！");
			return msg;
		}
		if(card.getUpdateLimit() > subOrder.getTotal()) {
			msg.setMessage("出库数不能大于订购数!");
			return msg;
		}
		IotOutReceipt outReceipt = new IotOutReceipt();
		outReceipt.setPriceDiscount(subOrder.getPriceDiscount());
		outReceipt.setPrice(subOrder.getPrice());
		outReceipt.setSubOrderId(subOrder.getId());
		AdminUser user = (AdminUser) request.getSession().getAttribute("adminLoginUser");
		outReceipt.setCreaterId(user.getId());
		try {
			if(CollectionUtils.isNotEmpty(cardIdList)) {
				outReceiptService.outByCardIdList(subOrder, outReceipt, cardIdList);
			}else {
				outReceiptService.outByCondition(subOrder, outReceipt, card);
			}
			msg.setSuccess(true);
		}catch(Exception e) {
			logger.error(e.getMessage(),e);
			msg.setMessage(e.getMessage());
		}
		return msg;
    }
}
