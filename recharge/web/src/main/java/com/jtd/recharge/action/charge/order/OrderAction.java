package com.jtd.recharge.action.charge.order;

import com.github.pagehelper.PageInfo;
import com.jtd.recharge.dao.bean.CallbackReport;
import com.jtd.recharge.dao.bean.Order;
import com.jtd.recharge.dao.bean.util.DesignTimeUtil;
import com.jtd.recharge.dao.po.AdminUser;
import com.jtd.recharge.service.admin.OperateLogService;
import com.jtd.recharge.service.charge.order.OrderService;
import com.jtd.recharge.service.charge.order.PushCallbackService;
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
 * on 2016/11/22.
 * lihuimin
 */
@Controller
@RequestMapping("/order")
public class OrderAction {

    @Resource
    public OrderService orderService;
    @Resource
    public PushCallbackService pushCallbackService;
    @Resource
    public OperateLogService operateLogService;

    /**
     *  流量话费交易订单页面
     *
     */
    @RequestMapping("/list")
    public String chargeOrderList(){
        return "/charge/order/orderInquiry";
    }

    /**
     * 流量话费交易订单页面
     * @param pageNumber
     * @param pageSize
     * @param order
     * @return
     */
    @RequestMapping("/orderList")
    @ResponseBody
    public Object orderList(HttpServletRequest request,String subData, Integer pageNumber, Integer pageSize,Order order) throws ParseException {
        //设开始时间
        String sTime = request.getParameter("startTime");
        //设结束时间
        String eTime = request.getParameter("endTime");
        DesignTimeUtil.orderTimeTwo(sTime,eTime,order);
        order.setVal1(order.getValue());
        PageInfo<Order> list = null;
        try {
            list = orderService.selectOrder(subData,pageNumber,pageSize,order);
        }catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }

    /**
     * order交易额
     * @param order
     * @return
     */
    @RequestMapping("/selectOrderList")
    @ResponseBody
    public Object selectOrderList(HttpServletRequest request,String subData,Order order) throws ParseException {
        //设开始时间
        String sTime = request.getParameter("startTime");
        //设结束时间
        String eTime = request.getParameter("endTime");
        DesignTimeUtil.orderTimeTwo(sTime,eTime,order);
        order.setVal1(order.getValue());
        //计算交易额
        List<Order> list =  orderService.selectOrderList(subData,order);
        return list;
    }

    /**
     * 充值流水查询
     * @param request
     * @param order
     * @return
     */
    @RequestMapping("/water")
    public String waterList(HttpServletRequest request,Order order){
        //获取订单表中的时间
        String orderTime = order.getOrderTime();
        //重置表名订单表
        order.setOrder("charge_order");
        //订单详情表
        order.setOrder_detail("charge_order_detail");

        SimpleDateFormat  fmt=new SimpleDateFormat("yyyy-MM-dd");
        Date dateStart=null;
        //根据时间日期 分表获取的时间不等于空并且不为空字符串
        if(orderTime!=null&&!"".equals(orderTime)) {
            try {
                dateStart = fmt.parse(orderTime);
                //赋值时间
                order.setOrderTimeOne(dateStart);
                //年月查询
                fmt = new SimpleDateFormat("yyyyMM");
                String startTimes = fmt.format(dateStart);
                //当前时间
                String now = fmt.format(new Date());
                //传进来的开始时间如果不等于系统当期那日期,重置表
                if(!startTimes.equals(now)){
                    order.setOrder("charge_order_"+startTimes);
                    order.setOrder_detail("charge_order_detail_"+startTimes);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        //获取订单号
        String orderNum = order.getOrderNum();
        order.setOrderNum(orderNum);

        List<Order> list = null;
        List<Order> listOne=null;
        try{
             list =  orderService.selectOrders(order);
            //流水详情
            listOne =  orderService.selectDatail(order);
        }catch(Exception e){
            e.printStackTrace();
        }
        request.setAttribute("list", list);
        request.setAttribute("listOne",listOne);
        return "/charge/order/water";
    }

    /**
     * 推送状态
     * @param ids
     * @return
     */
    @RequestMapping("pushStatusOrder")
    @ResponseBody
    public Object pushStatusOrder(String ids,String startTime,HttpServletRequest request){
        Order orders = new Order();
        DesignTimeUtil.submeter(startTime,orders);
        String[] split = ids.split(",");
        String success = null;
        if(split!=null && !split.equals("")){
            for(String str :split) {
                orders.setOrderNum(str);
                Order order = orderService.selectOrderByOrderNum(orders);
                //推送成功消息
                CallbackReport callbackReport = new CallbackReport();
                callbackReport.setOrderNum(order.getOrderNum());
                callbackReport.setToken(order.getToken());
                callbackReport.setStatus(order.getStatus().toString());
                callbackReport.setCallbackUrl( order.getCallbackUrl());
                callbackReport.setCustomId(order.getCustomId());
                callbackReport.setMobile(order.getRechargeMobile());
                callbackReport.setPushSum(CallbackReport.pushSumStatus.STATUS_ONE);
                success = pushCallbackService.pushCallback(callbackReport);

                AdminUser user = (AdminUser)request.getSession().getAttribute("adminLoginUser");
                String adminName = user.getName();
                operateLogService.logInfo(adminName,"流量话费交易订单",adminName+"在流量话费交易订单成功推送了手机号为："+order.getRechargeMobile()+" 的信息为"+success+"！");

            }
        }
        HashMap map = new HashMap();
        map.put("content",success);
        return map;
    }

    /**
     * 根据订单号修改是否人工退款状态
     * @param order
     * @return
     */
    @RequestMapping("ArtificialRefundOrder")
    @ResponseBody
    public boolean ArtificialRefundOrder(Order order,HttpServletRequest request){
        //获取订单表中的时间
        String orderTime = order.getOrderTime();
        //重置表名订单表
        order.setOrder("charge_order");

        SimpleDateFormat  fmt=new SimpleDateFormat("yyyy-MM-dd");
        Date dateStart=null;
        //根据时间日期 分表获取的时间不等于空并且不为空字符串
        if(orderTime!=null&&!"".equals(orderTime)) {
            try {
                dateStart = fmt.parse(orderTime);
                //赋值时间
                //年月查询
                fmt = new SimpleDateFormat("yyyyMM");
                String startTimes = fmt.format(dateStart);
                //当前时间
                String now = fmt.format(new Date());
                //传进来的开始时间如果不等于系统当期那日期,重置表
                if(!startTimes.equals(now)){
                    order.setOrder("charge_order_"+startTimes);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        order.setArtificialRefund(Order.artificialRefundStatus.YES);
        int i = orderService.updateOrderByStatus(order);
        if(i>0){
            AdminUser user = (AdminUser)request.getSession().getAttribute("adminLoginUser");
            String adminName = user.getName();
            operateLogService.logInfo(adminName,"流量话费交易订单",adminName+"在流量话费交易订单标注了订单号为："+order.getOrderNum()+" 的信息为手动退款！");
            return true;
        }else {
            return false;
        }
    }

}
