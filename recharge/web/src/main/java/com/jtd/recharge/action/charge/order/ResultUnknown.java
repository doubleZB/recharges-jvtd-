package com.jtd.recharge.action.charge.order;

import com.github.pagehelper.PageInfo;
import com.jtd.recharge.dao.bean.CallbackReport;
import com.jtd.recharge.dao.bean.Order;
import com.jtd.recharge.dao.bean.util.DesignTimeUtil;
import com.jtd.recharge.dao.po.AdminUser;
import com.jtd.recharge.dao.po.ChargeOrder;
import com.jtd.recharge.dao.po.ChargeOrderDetail;
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

/**
 * Created by 结果未知订单 lhm on 2017/1/16.
 */
@Controller
@RequestMapping("/order")
public class ResultUnknown {

    @Resource
    public OrderService orderService;
    @Resource
    public PushCallbackService pushCallbackService;
    @Resource
    public OperateLogService operateLogService;

    /**
     *  结果未知订单人工干预
     */
    @RequestMapping("/resultUnknown")
    public String resultUnknown(){
        return "/charge/order/resultUnknownOrder";
    }


    /**
     * 结果未知订单人工干预
     * @param pageNumber
     * @param pageSize
     * @param order
     * @return
     */
    @RequestMapping("resultUnknownList")
    @ResponseBody
    public Object orderList(HttpServletRequest request,String subData, Integer pageNumber, Integer pageSize, Order order) throws ParseException {
        //设开始时间
        String sTime = request.getParameter("startTime");
        //设结束时间
        String eTime = request.getParameter("endTime");
        String st = "00:00:00";
        String et = "23:59:59";
        //重置表名
        order.setOrder("charge_order");
        //订单详情表
        order.setOrder_detail("charge_order_detail");

        //转换格式
        SimpleDateFormat fmt=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //设置开始时间
        Date dateStart=null;
        //设置结束时间
        Date dateEnd=null;
        //根据时间日期 分表
        if(sTime!=null&&!"".equals(sTime)) {
//            String starTime = sTime + " " + st ;
            String starTime = sTime;
            try {
                //转换格式并且付给开始时间
                dateStart = fmt.parse(starTime);
                //赋值开始时间
                order.setOrderTimeOne(dateStart);
//                System.out.println(dateStart);
                fmt = new SimpleDateFormat("yyyyMM");
                String starTimes = fmt.format(dateStart);
                String now = fmt.format(new Date());
                //传进来的开始时间如果不等于系统当期那日期
                if(!starTimes.equals(now)){
                    order.setOrder("charge_order_"+starTimes);
                    order.setOrder_detail("charge_order_detail_"+starTimes);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        //结束时间
        if(eTime!=null && !"".equals(eTime)){
//            String endTime = eTime + " " + et ;
            String endTime = eTime;
            try {
                fmt=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                order.setOrderTimeTwo(fmt.parse(endTime));
//                System.out.println(endTime);
            }catch (ParseException e){
                e.printStackTrace();
            }
        }
        //时间为空时查询当天
        else{
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
            Date date=new Date();
            String format = sdf.format(date);
            String starTime = format + " " + st ;
            String endTime = format + " " + et ;
            Date parseOne = fmt.parse(starTime);
            Date parseTwo = fmt.parse(endTime);
            order.setOrderTimeOne(parseOne);
            order.setOrderTimeTwo(parseTwo);
        }

        order.setVal1(order.getValue());
        PageInfo<Order> list = orderService.selectResultUnknownOrderList(subData,pageNumber,pageSize,order);
        return list;
    }


    /**
     * 设置成功
     * @param ids
     * @return
     */
    @RequestMapping("successfullyOrder")
    @ResponseBody
    public boolean successfullyOrder(String ids,String startTime,HttpServletRequest request){
        Order orders = new Order();
        DesignTimeUtil.submeter(startTime,orders);
        String[] split = ids.split(",");
        if(split!=null && !split.equals("")){
            for(String str :split) {
                //更改订单状态
                orders.setOrderNum(str);
                orders.setStatus(ChargeOrder.OrderStatus.CHARGE_SUCCESS);
                orderService.updateOrderByStatus(orders);

                Order order = orderService.selectOrderByOrderNum(orders);
                //推送成功消息
                CallbackReport callbackReport = new CallbackReport();
                callbackReport.setOrderNum(order.getOrderNum());
                callbackReport.setToken(order.getToken());
                callbackReport.setStatus(order.getStatus().toString());
                callbackReport.setCallbackUrl( order.getCallbackUrl());
                callbackReport.setCustomId(order.getCustomId());
                callbackReport.setMobile(order.getRechargeMobile());
                pushCallbackService.pushCallback(callbackReport);

                AdminUser user = (AdminUser)request.getSession().getAttribute("adminLoginUser");
                String adminName = user.getName();
                operateLogService.logInfo(adminName,"结果未知人工干预",adminName+"在结果未知人工干预成功推送了手机号为："+order.getRechargeMobile()+" 的信息为推送成功！");

                //更改详情订单状态
                orders.setStatus(ChargeOrderDetail.OrderDetailStatus.BACK_SUCCEED_OK);
                orderService.updateOrderDetailByStatus(orders);

            }
        }
        return true;
    }

    /**
     * 设置失败
     * @param ids
     * @return
     */
    @RequestMapping("defeatedOrder")
    @ResponseBody
    public boolean defeatedOrder(String ids,String startTime,HttpServletRequest request){
        Order orders = new Order();
        DesignTimeUtil.submeter(startTime,orders);
        String[] split = ids.split(",");
        if(split!=null && !split.equals("")){
            for(String str :split) {
                //更改订单状态
                orders.setStatus(ChargeOrder.OrderStatus.CHARGE_FAIL);
                orders.setOrderNum(str);
                //标示手动退款
                orders.setArtificialRefund(Order.artificialRefundStatus.YES);
                orderService.updateOrderByStatus(orders);

                Order order = orderService.selectOrderByOrderNum(orders);
                //推送失败消息
                CallbackReport callbackReport = new CallbackReport();
                callbackReport.setOrderNum(order.getOrderNum());
                callbackReport.setToken(order.getToken());
                callbackReport.setStatus(order.getStatus().toString());
                callbackReport.setCallbackUrl( order.getCallbackUrl());
                callbackReport.setCustomId(order.getCustomId());
                callbackReport.setMobile(order.getRechargeMobile());
                pushCallbackService.pushCallback(callbackReport);

                AdminUser user = (AdminUser)request.getSession().getAttribute("adminLoginUser");
                String adminName = user.getName();
                operateLogService.logInfo(adminName,"结果未知人工干预",adminName+"在结果未知人工干预成功推送了手机号为："+order.getRechargeMobile()+" 的信息为推送失败！");

                //更改详情订单状态
                orders.setStatus(ChargeOrderDetail.OrderDetailStatus.RECEIPT_DEFEATED);
                orders.setOrderNum(str);
                orderService.updateOrderDetailByStatus(orders);
            }
        }
        return true;
    }
}
