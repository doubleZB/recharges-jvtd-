package com.jtd.recharge.action.iot;

import java.math.BigDecimal;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.jtd.recharge.dao.bean.util.ReturnMsg;
import com.jtd.recharge.dao.po.AdminUser;
import com.jtd.recharge.dao.po.IotProduct;
import com.jtd.recharge.dao.po.IotPurchase;
import com.jtd.recharge.dao.po.IotSubOrder;
import com.jtd.recharge.dao.po.IotSupply;
import com.jtd.recharge.define.CardOperator;
import com.jtd.recharge.define.CardSize;
import com.jtd.recharge.define.IotOrderStatus;
import com.jtd.recharge.service.iot.IotProductService;
import com.jtd.recharge.service.iot.IotSubOrderService;
import com.jtd.recharge.service.iot.PurchaseService;
import com.jtd.recharge.service.iot.SuppplierService;

/**
 * 待采购订单
 * @author ninghui
 *
 */
@Controller
@RequestMapping("/iotOrderPurchase")
public class IotOrderPurchaseAction {
	private Logger logger = Logger.getLogger(this.getClass());
    @Resource
    private IotSubOrderService iotSubOrderService;
    @Resource
    private IotProductService iotProductService;
    @Resource
    private PurchaseService purchaseService;
    @Resource
    private SuppplierService suppplierService;
    
	@RequestMapping("/index")
    public String purchase(HttpServletRequest request){
        request.setAttribute("operatorList", CardOperator.values());
        request.setAttribute("cardSizeList", CardSize.values());
        List<IotProduct> allProduct = iotProductService.listAllProduct();
        request.setAttribute("iotProductList",allProduct);
        PageInfo<IotSupply> allSupply = suppplierService.getAllSupply();
        request.setAttribute("iotSupplyList",allSupply.getList());
        return "iot/order/purchase";
    }
	
	/**
     * 新增采购单
     * @param companyName
     * @param iotSubOrderId
     * @param productId
     * @param cardSize
     * @param costCount
     * @param total
     * @param request
     * @return
     */
    @RequestMapping("addPurchase")
    @ResponseBody
    public ReturnMsg addPurchase(Integer companyName,Integer iotSubOrderId, Integer productId, Integer cardSize, 
    		BigDecimal costCount,BigDecimal priceDiscount,Integer total,Integer currentOrderStatus,HttpServletRequest request ){
    	ReturnMsg msg = new ReturnMsg();
    	msg.setSuccess(false);
    	msg.setMessage("新增成功");
    	if(costCount.compareTo(priceDiscount)==1){
            msg.setMessage("成本折扣不能大于售价折扣");
            return msg;
        }
    	IotProduct product = iotProductService.getProductById(productId);
        IotPurchase iotPurchase = new IotPurchase();
        iotPurchase.setSupplyId(companyName);
        iotPurchase.setSubOrderId(iotSubOrderId);
        iotPurchase.setFlowProductId(productId);
        iotPurchase.setCardSize(cardSize);
        iotPurchase.setTotal(total);
        iotPurchase.setIsRecharge(0);
        iotPurchase.setIsSms(0);
        iotPurchase.setCostDiscount(costCount);
        iotPurchase.setCost(product.getPrice().multiply(costCount));
        iotPurchase.setAmount(iotPurchase.getCost().multiply(new BigDecimal(total)));
        AdminUser user = (AdminUser)request.getSession().getAttribute("adminLoginUser");
        iotPurchase.setCreaterId(user.getId());
        IotSubOrder iotSubOrder = new IotSubOrder();
        iotSubOrder.setId(iotSubOrderId);
        iotSubOrder.setStatus(IotOrderStatus.待入库.getValue());
        iotSubOrder.setCurrentStatus(currentOrderStatus);
        try {
        	purchaseService.safeAdd(iotPurchase,iotSubOrder);
        	msg.setSuccess(true);
        }catch(Exception e) {
        	logger.error(e.getMessage(),e);
        	msg.setMessage(e.getMessage());
        }
        return msg;
    }

}
