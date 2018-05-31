package com.jtd.recharge.action.iot;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.jtd.recharge.dao.bean.util.ReturnMsg;
import com.jtd.recharge.dao.po.IotCard;
import com.jtd.recharge.dao.po.IotProduct;
import com.jtd.recharge.dao.po.IotPurchase;
import com.jtd.recharge.dao.po.IotSubOrder;
import com.jtd.recharge.define.CardOperator;
import com.jtd.recharge.define.CardPeriod;
import com.jtd.recharge.define.CardType;
import com.jtd.recharge.service.iot.CardService;
import com.jtd.recharge.service.iot.IotProductService;
import com.jtd.recharge.service.iot.IotSubOrderService;
import com.jtd.recharge.service.iot.PurchaseService;

/**
 * Created by Administrator on 2018/3/20. 产品管理
 */
@Controller
@RequestMapping("/iotProduct")
public class ProductController {

	@Resource
	private IotProductService iotProductService;
	@Resource
	private IotSubOrderService iotSubOrderService;
	@Resource
	public PurchaseService purchaseService;
	@Resource
	public CardService cardService;

	/**
	 * 跳转到产品列表
	 * 
	 * @return
	 */
	@RequestMapping("/getProduct")
	public String getProduct(HttpServletRequest request) {
		request.setAttribute("operatorList", CardOperator.values());
		request.setAttribute("periodList", CardPeriod.values());
		request.setAttribute("typeList", CardType.values());
		return "iot/product/productList";
	}

	/**
	 * 获取标准产品数据
	 * 
	 * @param operator
	 * @param period
	 * @param type
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	@RequestMapping("/getProductList")
	@ResponseBody
	public PageInfo<IotProduct> getProductList(Integer operator, Integer period, Integer type, Integer pageNumber,
			Integer pageSize) {
		PageInfo<IotProduct> productList = iotProductService.getProductList(operator, period, type, pageNumber,
				pageSize);
		return productList;
	}

	/**
	 * 新增产品
	 * 
	 * @param cardOperator
	 * @param flowPackageSize
	 * @param cardPeriod
	 * @param cardType
	 * @param price
	 * @return
	 */
	@RequestMapping("/addOrUpdateProduct")
	@ResponseBody
	public ReturnMsg addProduct(Integer productId, int cardOperator, int flowPackageSize, int cardPeriod, int cardType,
			BigDecimal price) {
		String flowPackageSizeLiteral;
		String flowPackageSizeLiteralCode;
		String company;
		String companyCode;
		String period;
		String periodCode;
		String type = null;
		String typeCode = null;
		CardOperator oper = CardOperator.parse(cardOperator);
		company = oper.name();
		companyCode = oper.getCode();
		CardPeriod per = CardPeriod.parse(cardPeriod);
		period = per.getShortName();
		periodCode = per.getCode();
		CardType ct = CardType.parse(cardType);
		type = ct.name();
		typeCode = ct.getCode();
		
		IotProduct iotProduct = new IotProduct();
		iotProduct.setType(cardType);
		iotProduct.setPeriod(cardPeriod);
		iotProduct.setOperator(cardOperator);
		iotProduct.setFlowPackageSize(flowPackageSize);
		iotProduct.setPrice(price);
		if (flowPackageSize >= 1024) {
			flowPackageSizeLiteral = flowPackageSize / 1024 + "G";
			flowPackageSizeLiteralCode = flowPackageSize / 1024 + "g";
		} else {
			flowPackageSizeLiteral = flowPackageSize + "M";
			flowPackageSizeLiteralCode = flowPackageSize + "m";
		}
		iotProduct.setFlowPackageSizeLiteral(flowPackageSizeLiteral);
		String name = company + flowPackageSizeLiteral + period + type;
		String Code = companyCode + "_" + periodCode + "_" + typeCode + "_" + flowPackageSizeLiteralCode;
		iotProduct.setName(name);
		iotProduct.setCode(Code);
		ReturnMsg msg = new ReturnMsg();
		if (productId != null) {
			msg.setSuccess(false);
			msg.setMessage("修改成功");
			IotPurchase iotPurchase = new IotPurchase();
			iotPurchase.setFlowProductId(productId);
			List<IotPurchase> purchases = purchaseService.getPurchaseByOrderId(iotPurchase);
			if (purchases.size() > 0) {
				msg.setMessage("该产品已经与采购单关联,不能修改");
				return msg;
			}
			IotCard iotCard = new IotCard();
			iotCard.setFlowProductId(productId);
			List<IotCard> iotCards = cardService.selectByOutProductId(iotCard);
			if (iotCards.size() > 0) {
				msg.setMessage("该产品已经与卡关联,不能修改");
				return msg;
			}
			IotSubOrder iotSubOrder = new IotSubOrder();
			iotSubOrder.setFlowProductId(productId);
			List<IotSubOrder> iotSubOrders = iotSubOrderService.getByProductId(iotSubOrder);
			if (iotSubOrders.size() > 0) {
				msg.setMessage("该产品已经与订单关联,不能修改");
				return msg;
			}
			iotProduct.setId(productId);
			iotProduct.setUpdateTime(new Date());
			Integer integer = iotProductService.updateById(iotProduct);
			if (integer > 0) {
				msg.setSuccess(true);
				return msg;
			} else {
				msg.setMessage("修改失败");
				return msg;
			}
		} else {
			msg.setSuccess(false);
			msg.setMessage("新增成功");
			iotProduct.setCreateTime(new Date());
			try {
				iotProductService.addProduct(iotProduct);
				msg.setSuccess(true);
			} catch (Exception e) {
				if(e instanceof DuplicateKeyException) {
					msg.setMessage("产品"+iotProduct.getName()+"已存在");
					msg.setSuccess(false);
				}else {
					msg.setMessage(e.getMessage());
					msg.setSuccess(false);
				}
			}
			return msg;
		}
	}

	/**
	 * 根据id获取产品信息
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/getProductById")
	@ResponseBody
	public IotProduct getProductById(Integer id) {
		return iotProductService.getProductById(id);
	}

	/**
	 * 根据id删除产品
	 * 
	 * @param productId
	 * @return
	 */
	// @RequestMapping("/delProduct")
	// @ResponseBody
	// public Integer delProduct(Integer productId){
	// return iotProductService.delProduct(productId);
	// }

}
