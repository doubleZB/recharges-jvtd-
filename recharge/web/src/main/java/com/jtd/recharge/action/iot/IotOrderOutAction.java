package com.jtd.recharge.action.iot;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.jtd.recharge.dao.po.IotProduct;
import com.jtd.recharge.dao.po.IotSupply;
import com.jtd.recharge.define.CardOperator;
import com.jtd.recharge.define.CardSize;
import com.jtd.recharge.service.iot.IotProductService;
import com.jtd.recharge.service.iot.SuppplierService;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.jtd.recharge.dao.bean.util.ReturnMsg;
import com.jtd.recharge.dao.po.IotSubOrder;
import com.jtd.recharge.define.IotOrderStatus;
import com.jtd.recharge.service.iot.IotSubOrderService;

/**
 * 订单出库
 * @author ninghui
 *
 */
@Controller
@RequestMapping("/iotOrderOut")
public class IotOrderOutAction {
	private Logger logger = Logger.getLogger(this.getClass());
    @Resource
    private IotSubOrderService iotSubOrderService;
    @Resource
    private IotProductService iotProductService;
    @Resource
    private SuppplierService suppplierService;
	 /**
     * 跳转到待分配订单列表页
     * @param request
     * @return
     */
    @RequestMapping("index")
    public String index(HttpServletRequest request){
        request.setAttribute("operatorList", CardOperator.values());
        request.setAttribute("cardSizeList", CardSize.values());
        List<IotProduct> allProduct = iotProductService.listAllProduct();
        request.setAttribute("iotProductList",allProduct);
        PageInfo<IotSupply> allSupply = suppplierService.getAllSupply();
        request.setAttribute("iotSupplyList",allSupply.getList());
        return "iot/order/toBeOut";
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
    	subOrder.setStatus(IotOrderStatus.待出库.getValue());
    	List<IotSubOrder> orderList = iotSubOrderService.selectOrderList(pageNumber,pageSize,subOrder);
        PageInfo<IotSubOrder> pageInfo = new PageInfo<>(orderList);
        return pageInfo;
    }
    
    /**
     * 去采购
     * @param request
     * @param subOrderId
     * @return
     */
    @RequestMapping("toAllocate")
    @ResponseBody
    public ReturnMsg toAllocate(HttpServletRequest request,Integer subOrderId){
    	ReturnMsg msg = new ReturnMsg();
    	msg.setSuccess(false);
    	IotSubOrder iotSubOrder = new IotSubOrder();
    	iotSubOrder.setId(subOrderId);
    	iotSubOrder.setStatus(IotOrderStatus.待处理.getValue());
    	iotSubOrder.setCurrentStatus(IotOrderStatus.待出库.getValue());
    	try {
    		iotSubOrderService.changeOrderStatus(iotSubOrder);
        	msg.setSuccess(true);
    	}catch(Exception e) {
    		logger.error(e.getMessage(),e);
    		msg.setMessage(e.getMessage());
    	}
    	return msg;
    }
}
