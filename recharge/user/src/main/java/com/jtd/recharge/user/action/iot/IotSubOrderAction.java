package com.jtd.recharge.user.action.iot;

import com.github.pagehelper.PageInfo;
import com.jtd.recharge.dao.po.IotProduct;
import com.jtd.recharge.dao.po.IotSubOrder;
import com.jtd.recharge.define.CardOperator;
import com.jtd.recharge.define.CardSize;
import com.jtd.recharge.define.IotOrderStatus;
import com.jtd.recharge.service.iot.IotProductService;
import com.jtd.recharge.service.iot.IotSubOrderService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by ${zyj} on 2018/4/11.
 */
@Controller
@RequestMapping("/iot/subOrder")
public class IotSubOrderAction {

    @Resource
    private IotProductService iotProductService;

    @Resource
    private IotSubOrderService iotSubOrderService;

    @RequestMapping("index")
    public String index(HttpServletRequest request){
        request.setAttribute("operatorList", CardOperator.values());
        request.setAttribute("cardSizeList", CardSize.values());
        request.setAttribute("iotOrderStatusList", IotOrderStatus.values());
        List<IotProduct> allProduct = iotProductService.listAllProduct();
        request.setAttribute("iotProductList",allProduct);
        return "/iot/iotOrderlist";
    }

    @RequestMapping("list")
    @ResponseBody
    public PageInfo<IotSubOrder> getIotOrderList(Integer pageNumber, Integer pageSize , IotSubOrder subOrder, HttpServletRequest request){
        Integer userId = (Integer) request.getSession().getAttribute("userId");
        subOrder.setUserId(userId);
        List<IotSubOrder> orderList = iotSubOrderService.selectOrderList(pageNumber,pageSize,subOrder);
        PageInfo<IotSubOrder> pageInfo = new PageInfo<>(orderList);
        return pageInfo;
    }
}
