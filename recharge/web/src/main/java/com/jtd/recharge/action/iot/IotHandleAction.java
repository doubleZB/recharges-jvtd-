package com.jtd.recharge.action.iot;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.jtd.recharge.dao.bean.util.ReturnMsg;
import com.jtd.recharge.dao.po.IotSupply;
import com.jtd.recharge.service.iot.SuppplierService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.jtd.recharge.dao.po.IotProduct;
import com.jtd.recharge.dao.po.IotSubOrder;
import com.jtd.recharge.dao.po.User;
import com.jtd.recharge.define.CardOperator;
import com.jtd.recharge.define.CardSize;
import com.jtd.recharge.define.IotOrderStatus;
import com.jtd.recharge.service.iot.IotProductService;
import com.jtd.recharge.service.iot.IotSubOrderService;
import com.jtd.recharge.service.iot.UserBalanceService;
import com.jtd.recharge.service.user.UserService;

/**
 * 待处理订单
 * 
 */
@Controller
@RequestMapping("/iot/handle")
public class IotHandleAction {
    private Logger logger = Logger.getLogger(this.getClass());
    @Resource
    private UserService userService;
    @Resource
    private IotProductService iotProductService;
    @Resource
    private IotSubOrderService iotSubOrderService;
    @Resource
    private UserBalanceService userBalanceService;
    @Resource
    private SuppplierService suppplierService;

    /**
     * 跳转到待处理订单页面
     * @param request
     * @return
     */
    @RequestMapping("index")
    public String index(HttpServletRequest request){
        request.setAttribute("operatorList", CardOperator.values());
        request.setAttribute("cardSizeList", CardSize.values());
        request.setAttribute("iotOrderStatusList", IotOrderStatus.values());
        List<IotProduct> allProduct = iotProductService.listAllProduct();
        PageInfo<IotSupply> allSupply = suppplierService.getAllSupply();
        request.setAttribute("iotSupplyList",allSupply.getList());
        List<User> userList = userService.selectUser();
        request.setAttribute("userList",userList);
        request.setAttribute("iotProductList",allProduct);
        return "iot/order/handle";
    }

    @RequestMapping("list")
    @ResponseBody
    public PageInfo<IotSubOrder> list(Integer pageNumber,Integer pageSize ,IotSubOrder subOrder,HttpServletRequest request){
        List<Integer> statusList = new ArrayList<>();
        statusList.add(IotOrderStatus.待审核.getValue());
        statusList.add(IotOrderStatus.已完成.getValue());
        statusList.add(IotOrderStatus.已取消.getValue());
        statusList.add(IotOrderStatus.已驳回.getValue());
        statusList.add(IotOrderStatus.已退款.getValue());
        subOrder.setStatusList(statusList);
        List<IotSubOrder> orderList =  iotSubOrderService.selectOrderList(pageNumber,pageSize,subOrder);
        PageInfo<IotSubOrder> pageInfo = new PageInfo<>(orderList);
        return pageInfo;
    }

    /**
     * 退款操作
     * @return
     */
    @RequestMapping("/refund")
    @ResponseBody
    public ReturnMsg refund(Integer iotSubOrderId, Integer status){
        ReturnMsg msg = new ReturnMsg();
        msg.setSuccess(false);
        msg.setMessage("订单退款成功!");
        if(status==IotOrderStatus.待审核.getValue() || status== IotOrderStatus.已驳回.getValue() || status==IotOrderStatus.待加款.getValue()){
            msg.setMessage("订单还未支付,不能退款");
            return msg;
        }
        if(status==IotOrderStatus.已完成.getValue()){
            msg.setMessage("订单已完成,不能退款");
            return msg;
        }
        if(status==IotOrderStatus.已退款.getValue()){
            msg.setMessage("订单已经退款");
            return msg;
        }

        try {
            iotSubOrderService.updateRefund(iotSubOrderId,status);
            msg.setSuccess(true);
        }catch(Exception e) {
            logger.error(e.getMessage(),e);
            msg.setMessage(e.getMessage());
        }
        return msg;
    }

}
