package com.jtd.recharge.action.iot;

import com.github.pagehelper.PageInfo;
import com.jtd.recharge.dao.bean.util.ReturnMsg;
import com.jtd.recharge.dao.po.IotProduct;
import com.jtd.recharge.dao.po.IotSubOrder;
import com.jtd.recharge.dao.po.IotSupply;
import com.jtd.recharge.define.CardOperator;
import com.jtd.recharge.define.CardSize;
import com.jtd.recharge.define.IotOrderStatus;
import com.jtd.recharge.service.iot.IotProductService;
import com.jtd.recharge.service.iot.IotSubOrderService;
import com.jtd.recharge.service.iot.SuppplierService;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by ${zyj} on 2018/4/2.
 */
@Controller
@RequestMapping("/delivery")
public class IotOrderDeliveryAction {
	private Logger logger = Logger.getLogger(this.getClass());
    @Resource
    private IotProductService iotProductService;
    @Resource
    private SuppplierService suppplierService;
    @Resource
    private IotSubOrderService iotSubOrderService;

    @RequestMapping("index")
    public String delivery(HttpServletRequest request){
        request.setAttribute("operatorList", CardOperator.values());
        request.setAttribute("cardSizeList", CardSize.values());
        List<IotProduct> allProduct = iotProductService.listAllProduct();
        request.setAttribute("iotProductList",allProduct);
        PageInfo<IotSupply> allSupply = suppplierService.getAllSupply();
        request.setAttribute("iotSupplyList",allSupply.getList());
        return "/iot/order/delivery";
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
    public PageInfo<IotSubOrder> list(Integer pageNumber, Integer pageSize , IotSubOrder subOrder, HttpServletRequest request){
        subOrder.setStatus(IotOrderStatus.待配货.getValue());
        List<IotSubOrder> orderList = iotSubOrderService.selectOrderList(pageNumber,pageSize,subOrder);
        PageInfo<IotSubOrder> pageInfo = new PageInfo<>(orderList);
        return pageInfo;
    }

    /**
     * 修改订单为已配货的状态
     * @param iotSubOrderId
     * @return
     */
    @RequestMapping("/delivered")
    @ResponseBody
    public ReturnMsg delivered(Integer iotSubOrderId){
        ReturnMsg msg = new ReturnMsg();
        msg.setSuccess(false);
        msg.setMessage("订单送货成功!");
        try {
        	iotSubOrderService.picked(iotSubOrderId);
        	msg.setSuccess(true);
        }catch(Exception e) {
        	logger.error(e.getMessage(),e);
        	msg.setMessage(e.getMessage());
        }
        return msg;
    }
}
