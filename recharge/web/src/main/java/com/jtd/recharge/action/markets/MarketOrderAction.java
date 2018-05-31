package com.jtd.recharge.action.markets;

import com.github.pagehelper.PageInfo;
import com.jtd.recharge.dao.bean.CallbackReport;
import com.jtd.recharge.dao.bean.Order;
import com.jtd.recharge.dao.bean.util.DesignTimeUtil;
import com.jtd.recharge.dao.po.AdminUser;
import com.jtd.recharge.dao.po.User;
import com.jtd.recharge.service.admin.OperateLogService;
import com.jtd.recharge.service.charge.order.OrderService;
import com.jtd.recharge.service.charge.order.PushCallbackService;
import com.jtd.recharge.service.user.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by 流量话费交易订单
 * on 2017/09/13.
 * lhm
 */
@Controller
@RequestMapping("/order")
public class MarketOrderAction {

    @Resource
    public OrderService orderService;

    @Resource
    public UserService userservice;

    /**
     *  流量话费交易订单页面
     *
     */
    @RequestMapping("/marketsOrderList")
    public String chargeOrderList(){
        return "/market/marketOrder";
    }

    /**
     * 流量话费交易订单页面销售
     * @param pageNumber
     * @param pageSize
     * @param order
     * @return
     */
    @RequestMapping("/orderMarketList")
    @ResponseBody
    public Object orderMarketList(HttpServletRequest request,Integer pageNumber, Integer pageSize,Order order) throws ParseException {
        AdminUser adminUser = (AdminUser)request.getSession().getAttribute("adminLoginUser");
        //获取销售名字
        String marketName = adminUser.getName();
        order.setUserAllName(marketName);
        PageInfo<Order> orderList = null;
        //设开始时间
        String sTime = request.getParameter("startTime");
        //设结束时间
        String eTime = request.getParameter("endTime");
        DesignTimeUtil.orderTimeTwo(sTime,eTime,order);
        order.setVal1(order.getValue());
        try {
            orderList = orderService.selectMarketOrder(pageNumber,pageSize,order);
        }catch (Exception e){
            e.printStackTrace();
        }
        return orderList;
    }

    /**
     * 计算交易额销售商户订单
     * @param order
     * @return
     */
    @RequestMapping("/selectMarketOrderList")
    @ResponseBody
    public Object selectMarketOrderListMoney(HttpServletRequest request,Order order) throws ParseException {
        AdminUser adminUser = (AdminUser)request.getSession().getAttribute("adminLoginUser");
        String marketName = adminUser.getName();
        //获取销售名字
        order.setUserAllName(marketName);
        List<Order> listOne = null;
        //设开始时间
        String sTime = request.getParameter("startTime");
        //设结束时间
        String eTime = request.getParameter("endTime");
        DesignTimeUtil.orderTimeTwo(sTime, eTime, order);
        order.setVal1(order.getValue());
        try {
            //计算交易额
            listOne = orderService.selectMarketOrderListMoney(order);
        }catch (Exception e){
            e.printStackTrace();
        }
        return listOne;
    }
}
