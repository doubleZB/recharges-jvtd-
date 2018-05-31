package com.jtd.recharge.action.iot;

import java.math.BigDecimal;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.jtd.recharge.dao.bean.util.ReturnMsg;
import com.jtd.recharge.dao.po.AdminUser;
import com.jtd.recharge.dao.po.IotProduct;
import com.jtd.recharge.dao.po.IotSubOrder;
import com.jtd.recharge.dao.po.User;
import com.jtd.recharge.define.CardOperator;
import com.jtd.recharge.define.CardSize;
import com.jtd.recharge.define.IotOrderStatus;
import com.jtd.recharge.service.iot.IotOrderService;
import com.jtd.recharge.service.iot.IotProductService;
import com.jtd.recharge.service.iot.IotSubOrderService;
import com.jtd.recharge.service.iot.PurchaseService;
import com.jtd.recharge.service.iot.SuppplierService;
import com.jtd.recharge.service.user.UserService;

/**
 * 我的订单管理
 */
@Controller
@RequestMapping("/iotMyOrder")
public class IotMyOrderAction {

    @Resource
    private UserService userService;
    @Resource
    private IotProductService iotProductService;
    @Resource
    private IotOrderService iotOrderService;
    @Resource
    private IotSubOrderService iotSubOrderService;
    @Resource
    private SuppplierService suppplierService;
    @Resource
    private PurchaseService purchaseService;
    /**
     * 跳转到订单页面
     * @param request
     * @return
     */
    @RequestMapping("/index")
    public String index(HttpServletRequest request){
        request.setAttribute("operatorList", CardOperator.values());
        request.setAttribute("cardSizeList", CardSize.values());
        request.setAttribute("iotOrderStatusList", IotOrderStatus.values());
        List<IotProduct> allProduct = iotProductService.listAllProduct();
        List<User> userList = userService.selectUser();
        request.setAttribute("userList",userList);
        request.setAttribute("iotProductList",allProduct);
        return "iot/order/myOrder";
    }

  
  

    /**
     * 获取订单列表信息
     * @param pageNumber
     * @param iotOrderStatus
     * @param pageSize
     * @param request
     * @return
     */
    @RequestMapping("/list")
    @ResponseBody
    public PageInfo<IotSubOrder> list(Integer pageNumber,Integer pageSize ,IotSubOrder subOrder,HttpServletRequest request){
    	AdminUser adminLoginUser = (AdminUser)request.getSession().getAttribute("adminLoginUser");
    	subOrder.setCreaterId(adminLoginUser.getId());
    	List<IotSubOrder> orderList = iotSubOrderService.selectOrderList(pageNumber,pageSize,subOrder);
        PageInfo<IotSubOrder> pageInfo = new PageInfo<>(orderList);
        return pageInfo;
    }

    /**
     * 新增订单
     * @param request
     * @param userId
     * @param productId
     * @param size
     * @param priceDiscount
     * @param total
     * @return
     */
    @RequestMapping("/addIotOrder")
    @ResponseBody
    public ReturnMsg addIotOrder(HttpServletRequest request, IotSubOrder iotSubOrder){
    	ReturnMsg msg = new ReturnMsg();
    	msg.setSuccess(false);
    	msg.setMessage("创建成功");
    	if(iotSubOrder.getUserId() == null) {
    		msg.setMessage("企业名称不能为空");
    		return msg;
    	}
    	if(iotSubOrder.getFlowProductId() == null) {
    		msg.setMessage("流量套餐不能为空");
    		return msg;
    	}
    	if(iotSubOrder.getPriceDiscount() == null) {
    		msg.setMessage("折扣不能为空");
    		return msg;
    	}
    	if(iotSubOrder.getTotal() == null) {
    		msg.setMessage("购买数量不能为空");
    		return msg;
    	}
        AdminUser adminLoginUser = (AdminUser)request.getSession().getAttribute("adminLoginUser");
        iotSubOrderService.addIotOrderAndSubOrder(iotSubOrder,adminLoginUser.getId());
        msg.setSuccess(true);
        return msg;
    }
    
    /**
     * 修改订单
     * @param request
     * @param userId
     * @param productId
     * @param size
     * @param priceDiscount
     * @param total
     * @return
     */
    @RequestMapping("/updateIotOrder")
    @ResponseBody
    public ReturnMsg updateIotOrder(HttpServletRequest request,IotSubOrder toUpdateSubOrder){
    	ReturnMsg msg = new ReturnMsg();
    	msg.setSuccess(false);
    	msg.setMessage("更新成功");
    	
    	 if(toUpdateSubOrder.getUserId() == null) {
     		msg.setMessage("企业名称不能为空");
     		return msg;
     	}
     	if(toUpdateSubOrder.getFlowProductId() == null) {
     		msg.setMessage("流量套餐不能为空");
     		return msg;
     	}
     	if(toUpdateSubOrder.getPriceDiscount() == null) {
     		msg.setMessage("折扣不能为空");
     		return msg;
     	}
     	if(toUpdateSubOrder.getTotal() == null) {
     		msg.setMessage("购买数量不能为空");
     		return msg;
     	}
       
        IotSubOrder iotSubOrder = iotSubOrderService.getById(toUpdateSubOrder.getId());
        AdminUser adminLoginUser = (AdminUser)request.getSession().getAttribute("adminLoginUser");
        if(!adminLoginUser.getId().equals(iotSubOrder.getCreaterId())) {
        	msg.setMessage("您无权修改该订单!");
        	return msg;
        }
        
        IotProduct product = iotProductService.getProductById(toUpdateSubOrder.getFlowProductId());
        iotSubOrder.setOperator(product.getOperator());
        iotSubOrder.setFlowProductId(product.getId());
        iotSubOrder.setPriceDiscount(toUpdateSubOrder.getPriceDiscount());
        iotSubOrder.setTotal(toUpdateSubOrder.getTotal());
        iotSubOrder.setSize(toUpdateSubOrder.getSize());
        iotSubOrder.setUserId(toUpdateSubOrder.getUserId());
        BigDecimal price = iotSubOrder.getPriceDiscount().multiply(product.getPrice());
        iotSubOrder.setPrice(price);
        iotSubOrder.setAmount(price.multiply(new BigDecimal(iotSubOrder.getTotal())));
        iotSubOrder.setCurrentStatus(IotOrderStatus.已驳回.getValue());
        iotSubOrder.setStatus(IotOrderStatus.待审核.getValue());
        iotSubOrderService.safeUpdateIotSubOrderById(iotSubOrder);
        msg.setSuccess(true);
        return msg;
    }

    /**
     * 取消订单
     * @param iotSubOrderId 子订单的id
     * @return
     */
    @RequestMapping("/cancelIotOrder")
    @ResponseBody
    public ReturnMsg cancelIotOrder(HttpServletRequest request, Integer iotSubOrderId,Integer currentStatus){
    	ReturnMsg msg = new ReturnMsg();
		msg.setSuccess(false);
		msg.setMessage("订单取消成功!");
        IotSubOrder iotSubOrder = new IotSubOrder();
        IotSubOrder origSubOrder = iotSubOrderService.getById(iotSubOrderId);
        AdminUser adminLoginUser = (AdminUser)request.getSession().getAttribute("adminLoginUser");
        if(!adminLoginUser.getId().equals(origSubOrder.getCreaterId())) {
        	msg.setMessage("您无权取消该订单!");
        	return msg;
        }
        iotSubOrder.setId(iotSubOrderId);
        iotSubOrder.setStatus(IotOrderStatus.已取消.getValue());
        iotSubOrder.setCurrentStatus(currentStatus);
        try {
        	iotSubOrderService.changeOrderStatus(iotSubOrder);
        	msg.setSuccess(true);
        }catch(Exception e) {
        	msg.setMessage(e.getMessage());
        }
        return msg;
    }

    

    /**
     * 根据id获取订单信息
     * @param id
     * @return
     */
    @RequestMapping("getOrderById")
    @ResponseBody
    public IotSubOrder getOrderById(Integer id){
    	IotSubOrder sub = new IotSubOrder();
    	sub.setId(id);
        return iotSubOrderService.selectDetailById(sub);
    }
}
