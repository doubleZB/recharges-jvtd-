package com.jtd.recharge.action.iot;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

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
 * Created by Administrator on 2018/3/22.
 * 订单管理
 */
@Controller
@RequestMapping("/iotOrder")
public class IotOrderAction {
    @Resource
    private UserService userService;
    @Resource
    private IotProductService iotProductService;
    @Resource
    private IotSubOrderService iotSubOrderService;
    @Resource
    private UserBalanceService userBalanceService;
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
        return "iot/order/iotOrder";
    }

  
  

    /**
     * 获取订单列表信息
     * @param pageNumber
     * @param iotOrderStatus
     * @param pageSize
     * @param request
     * @return
     */
    @RequestMapping("/getIotOrderList")
    @ResponseBody
    public PageInfo<IotSubOrder> getIotOrderList(Integer pageNumber,Integer pageSize ,IotSubOrder subOrder,HttpServletRequest request){
        List<IotSubOrder> orderList = iotSubOrderService.selectOrderList(pageNumber,pageSize,subOrder);
        PageInfo<IotSubOrder> pageInfo = new PageInfo<>(orderList);
        return pageInfo;
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
