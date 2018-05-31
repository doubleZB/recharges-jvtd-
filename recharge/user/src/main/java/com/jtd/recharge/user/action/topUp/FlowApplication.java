package com.jtd.recharge.user.action.topUp;

import com.jtd.recharge.dao.po.ChargeOrder;
import com.jtd.recharge.service.charge.order.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by lenovo on 2016/12/1.
 */
@Controller
@RequestMapping("/flow")
public class FlowApplication {

    @Resource
    private OrderService orderService;

    /**
     * 流量概况
     *
     * @param chargeOrder
     * @return
     */

    @RequestMapping("/flowapplication")
    public String flowApplication(HttpServletRequest request, ChargeOrder chargeOrder) {
        List<ChargeOrder> ChargeOrder = orderService.floworderList(chargeOrder);
        //发送概况
        List<ChargeOrder> list = orderService.floworderListup(chargeOrder);
        request.setAttribute("order", list);
        request.setAttribute("list", ChargeOrder);
        return "/flow/flowapplication";
    }

    /**
     * 话费概况
     * @param chargeOrder
     * @return
     */
    @RequestMapping("/chargeapplication")
    public String chargeapplication(HttpServletRequest request, ChargeOrder chargeOrder) {
        List<ChargeOrder> ChargeOrder = orderService.chargeorderList(chargeOrder);
        //发送概况
        List<ChargeOrder> list = orderService.chargeorderListup(chargeOrder);
        request.setAttribute("order", list);
        request.setAttribute("list", ChargeOrder);
        return "/tel/teleapplication";
    }

    @RequestMapping("/floworderhistory")
    public String flowOrderHistory() {
        return "/flow/floworderhistory";
    }

    @RequestMapping("/flowRechargeNumber")
    public String flowRechargeNumber() {
        return "/flow/flowRechargeNumber";
    }

    @RequestMapping("/flowBatchRecharge")
    public String flowBatchRecharge() {
        return "/flow/flowBatchRecharge";
    }

    /**
     * 转到流量概览页面
     *
     * @param request
     * @return
     */
    @RequestMapping("/flowOverview")
    public String flowOverview(HttpServletRequest request) {
        String businessType = request.getParameter("businessType");
        request.setAttribute("businessType", businessType);
        return "/flow/flowOverview";
    }

    @RequestMapping("/telephonehistory")
    public String telephoneHistory() {
        return "/telephone/telephonehistory";
    }

    @RequestMapping("/teleRecharge")
    public String teleRecharge() {
        return "/telephone/teleRechargeNumber";
    }

    @RequestMapping("/teleBatchRecharge")
    public String teleBatchRecharge() {
        return "/telephone/teleBatchRecharge";
    }

    /**
     * 转到话费应用概览页面
     *
     * @param request
     * @return
     */
    @RequestMapping("/teleOverview")
    public String teleOverview(HttpServletRequest request) {
        String businessType = request.getParameter("businessType");
        request.setAttribute("businessType", businessType);
        return "/telephone/teleOverview";
    }


}
