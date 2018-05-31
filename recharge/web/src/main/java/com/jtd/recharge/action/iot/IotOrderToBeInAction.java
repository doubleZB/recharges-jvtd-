package com.jtd.recharge.action.iot;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.jtd.recharge.dao.bean.util.ReturnMsg;
import com.jtd.recharge.dao.po.*;
import com.jtd.recharge.define.PurchaseStatus;
import com.jtd.recharge.service.iot.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.jtd.recharge.define.CardOperator;
import com.jtd.recharge.define.CardSize;
import com.jtd.recharge.define.IotOrderStatus;

/**
 * 待入库订单
 * 
 * @author ninghui
 *
 */
@Controller
@RequestMapping("/iotOrderToBeIn")
public class IotOrderToBeInAction {
	@Resource
    private IotSubOrderService iotSubOrderService;
	@Resource
	private IotProductService iotProductService;

	@Resource
	private SuppplierService suppplierService;
	@Resource
    private PurchaseService purchaseService;
	@Resource
	private InReceiptService inReceiptService;

	/**
	 * 跳转到待采购订单列表页
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/index")
	public String index(HttpServletRequest request) {
		request.setAttribute("operatorList", CardOperator.values());
		request.setAttribute("cardSizeList", CardSize.values());
		List<IotProduct> allProduct = iotProductService.listAllProduct();
		request.setAttribute("iotProductList", allProduct);
		PageInfo<IotSupply> allSupply = suppplierService.getAllSupply();
		request.setAttribute("iotSupplyList", allSupply.getList());
		return "iot/order/toBeIn";
	}
	
	 /**
     * 获取订单列表信息
     * @param pageNumber
     * @param pageSize
     * @param request
     * @return
     */
    @RequestMapping("/list")
    @ResponseBody
    public PageInfo<IotSubOrder> list(Integer pageNumber,Integer pageSize ,IotSubOrder subOrder,HttpServletRequest request){
    	subOrder.setStatus(IotOrderStatus.待入库.getValue());
    	List<IotSubOrder> orderList = iotSubOrderService.selectOrderList(pageNumber,pageSize,subOrder);
        PageInfo<IotSubOrder> pageInfo = new PageInfo<>(orderList);
        return pageInfo;
    }

	/**
	 * 根据订单id获取采购单的信息
	 * @param subOrderId
	 * @return
	 */
	@RequestMapping("/getPurchaseBySubOrderId")
    @ResponseBody
    public ReturnMsg getPurchaseBySubOrderId(Integer subOrderId){
		ReturnMsg msg = new ReturnMsg();
		msg.setSuccess(false);
        IotPurchase iotPurchase = new IotPurchase();
        iotPurchase.setSubOrderId(subOrderId);
        iotPurchase.setPurchaseStatus(PurchaseStatus.待入库.getValue());
		List<IotPurchase> list = purchaseService.getPurchaseByOrderId(iotPurchase);
		if(list.size()>0){
			List<Object> ret = new ArrayList<Object>();
			IotInReceipt inReceipt = inReceiptService.getByPurchaseId(list.get(0).getId());
			if(inReceipt==null){
				ret.add(list.get(0));
				msg.setObject(ret);
				msg.setSuccess(true);
				return msg;
			} else {
				ret.add(list.get(0));
				ret.add(inReceipt);
				msg.setObject(ret);
				msg.setSuccess(true);
				return msg;
			}
		}else{
			msg.setMessage("未查到采购单,请确认采购单处于待入库状态");
			return msg ;
		}
    }
}
