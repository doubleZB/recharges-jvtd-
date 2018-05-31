package com.jtd.recharge.action.iot;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.jtd.recharge.dao.bean.util.ReturnMsg;
import com.jtd.recharge.dao.po.IotProduct;
import com.jtd.recharge.dao.po.IotSubOrder;
import com.jtd.recharge.dao.po.IotSupply;
import com.jtd.recharge.dao.po.UserBalance;
import com.jtd.recharge.define.CardOperator;
import com.jtd.recharge.define.CardSize;
import com.jtd.recharge.define.IotOrderStatus;
import com.jtd.recharge.service.iot.IotProductService;
import com.jtd.recharge.service.iot.IotSubOrderService;
import com.jtd.recharge.service.iot.SuppplierService;
import com.jtd.recharge.service.iot.UserBalanceService;

/**
 * Created by ${zyj} on 2018/4/2.
 */
@Controller
@RequestMapping("/iotOrderAddmount")
public class IotOrderAddAmountAction {
    private Logger logger = Logger.getLogger(this.getClass());

    @Resource
    private IotSubOrderService iotSubOrderService;

    @Resource
    private UserBalanceService userBalanceService;
    @Resource
    private IotProductService iotProductService;
    @Resource
    private SuppplierService suppplierService;

    @RequestMapping("/addAmount")
    public String addMount(HttpServletRequest request){
        request.setAttribute("operatorList", CardOperator.values());
        request.setAttribute("cardSizeList", CardSize.values());
        List<IotProduct> allProduct = iotProductService.listAllProduct();
        request.setAttribute("iotProductList",allProduct);
        PageInfo<IotSupply> allSupply = suppplierService.getAllSupply();
        request.setAttribute("iotSupplyList",allSupply.getList());
        return "/iot/order/addAmount";
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
        subOrder.setStatus(IotOrderStatus.待加款.getValue());
        List<IotSubOrder> orderList = iotSubOrderService.selectOrderList(pageNumber,pageSize,subOrder);
        PageInfo<IotSubOrder> pageInfo = new PageInfo<>(orderList);
        return pageInfo;
    }

    @RequestMapping("/payment")
    @ResponseBody
    public ReturnMsg payment(Integer iotSubOrderId){
        ReturnMsg returnMsg = new ReturnMsg();
        returnMsg.setSuccess(false);
        returnMsg.setMessage("订单支付成功!");
        IotSubOrder iotSubOrder = iotSubOrderService.getById(iotSubOrderId);
        UserBalance userBalance = userBalanceService.queryBalanceByUserId(iotSubOrder.getUserId());
        if(userBalance.getUserBalance().compareTo(iotSubOrder.getAmount())>=0){
            try {
                iotSubOrderService.updatePayment(iotSubOrderId);
                returnMsg.setSuccess(true);
            }catch (Exception e){
                logger.error(e.getMessage(),e);
                returnMsg.setMessage(e.getMessage());
            }
        }else{
            returnMsg.setMessage("可用余额不足,不能支付!");
            return returnMsg;
        }
        return returnMsg;
    }
}
