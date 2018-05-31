package com.jtd.recharge.gateway.action;

import com.jtd.recharge.bean.QueryBalanceResp;
import com.jtd.recharge.bean.QueryResp;
import com.jtd.recharge.dao.po.ChargeOrder;
import com.jtd.recharge.dao.po.User;
import com.jtd.recharge.dao.po.UserBalance;
import com.jtd.recharge.gateway.service.GateWayService;
import com.jtd.recharge.service.charge.position.ChargePostionService;
import com.jtd.recharge.service.user.BalanceService;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;

/**
 * @autor jipengkun
 */
@Controller
@RequestMapping("/gateway")
public class GateWayQuery {

    private Log log = LogFactory.getLog(this.getClass());

    @Autowired
    GateWayService gateWayService;

    @Autowired
    ChargePostionService chargePostionService;

    @Autowired
    public BalanceService balanceService;

    /**
     * 查询订单
     *
     * @param request
     * @param business 业务类型 flow 流量 telbill 话费
     * @return
     */
    @RequestMapping("/{business}/queryOrder")
    @ResponseBody
    public Object charge(@PathVariable String business, HttpServletRequest request) {
        String orderNum = request.getParameter("orderNum");
        String customId = request.getParameter("customId");
        String phone = request.getParameter("phone");
        String token = request.getParameter("token");

        log.info("客户主动查询订单，参数 customId---" + customId+"  token---" + token+"   token---" + token);

        QueryResp queryResp = new QueryResp();
//        //区分业务类型
//        int businessType = -1;
//        if ("flow".equals(business)) {
//            businessType = new Integer(SysConstants.BusinessType.flow);
//        } else if ("telbill".equals(business)) {
//            businessType = new Integer(SysConstants.BusinessType.telbill);
//        }

        if (StringUtils.isEmpty(token)||(StringUtils.isEmpty(orderNum)&&StringUtils.isEmpty(customId))) {
            queryResp.setStatus(QueryResp.QueryStatus.NO_PARAM);
            queryResp.setStatusMsg("缺少必要参数");
            return queryResp;
        }
        try {
            //验证token
            long starts =System.currentTimeMillis();
            User user = gateWayService.findUserByToken(token);
            log.info("验证订单号为"+customId+"商户token状态执行时长:"+String.valueOf(System.currentTimeMillis()-starts) );
            if (user == null) {
                queryResp.setStatus(QueryResp.QueryStatus.TOKEN_ERROR);
                queryResp.setStatusMsg("token验证错误");
                return queryResp;
            }

            long start =System.currentTimeMillis();
            ChargeOrder chargeOrder = gateWayService.findChargeOrder(orderNum, customId, phone);
            log.info("查询商户订单为"+customId+"，平台订单号为:"+orderNum+"状态执行时长:"+String.valueOf(System.currentTimeMillis()-start) );

            if (chargeOrder == null) {
                queryResp.setStatus(QueryResp.QueryStatus.QUERY_NODATA);//无数据
                queryResp.setStatusMsg("查无数据！");
                return queryResp;
            }

            int statusCode = chargeOrder.getStatus();
            if ((ChargeOrder.OrderStatus.CHARGEING == statusCode) ||
                    (ChargeOrder.OrderStatus.CREATE_ORDER == statusCode)||
                    (ChargeOrder.OrderStatus.CACHING == statusCode)||
                    (ChargeOrder.OrderStatus.CHARGEING_UNKNOWN == statusCode)) {
                queryResp.setStatus(QueryResp.QueryStatus.QUERY_SUCCESS);
                queryResp.setStatusCode(new Integer(ChargeOrder.OrderStatus.CHARGEING).toString());
                queryResp.setStatusMsg("充值中");
                return queryResp;
            }
            if (ChargeOrder.OrderStatus.CHARGE_SUCCESS == statusCode) {
                queryResp.setStatus(QueryResp.QueryStatus.QUERY_SUCCESS);
                queryResp.setStatusCode(new Integer(statusCode).toString());
                queryResp.setStatusMsg("充值成功");
                return queryResp;
            }
            if (ChargeOrder.OrderStatus.CHARGE_FAIL == statusCode||
                    ChargeOrder.OrderStatus.NO_STORE == statusCode||
                    ChargeOrder.OrderStatus.NO_CHANNEL == statusCode||
                    ChargeOrder.OrderStatus.NO_BALANCE == statusCode||
                    ChargeOrder.OrderStatus.PAY_ERROR == statusCode) {
                queryResp.setStatus(QueryResp.QueryStatus.QUERY_SUCCESS);
                queryResp.setStatusCode(new Integer(statusCode).toString());
                queryResp.setStatusMsg("充值失败");
                return queryResp;
            }

        } catch (Exception e) {
            log.error("客户主动查询订单，参数 customId---" + customId+"  token---" + token+"   token---" + token+" 查询异常"+e.getMessage()+e.getLocalizedMessage(), e);
            e.printStackTrace();
            queryResp.setStatus(QueryResp.QueryStatus.SYS_ERROR);
            queryResp.setStatusMsg("查询异常");
        }

        return queryResp;
    }

    @RequestMapping("/queryBalance")
    @ResponseBody
    public Object queryBalance(HttpServletRequest request) {

        QueryBalanceResp queryBalanceResp = new QueryBalanceResp();
        String token = request.getParameter("token");

        if (StringUtils.isEmpty(token)) {
            queryBalanceResp.setStatus(QueryBalanceResp.QueryStatus.NO_PARAM);
            queryBalanceResp.setStatusMsg("缺少必要参数");
            return queryBalanceResp;
        }

        //验证token
        User user = gateWayService.findUserByToken(token);
        if (user == null) {
            queryBalanceResp.setStatus(QueryBalanceResp.QueryStatus.TOKEN_ERROR);
            queryBalanceResp.setStatusMsg("token验证错误");
            return queryBalanceResp;
        }

        try {

            UserBalance userBalance = balanceService.queryBalanceByUserId(user.getId());
            BigDecimal balance = userBalance.getUserBalance();
            queryBalanceResp.setStatus(QueryBalanceResp.QueryStatus.QUERY_SUCCESS);
            queryBalanceResp.setStatusMsg("query success");
            queryBalanceResp.setUserBalance(balance);

        } catch (Exception e) {
            log.error("query balance exception", e);
            queryBalanceResp.setStatus(QueryResp.QueryStatus.SYS_ERROR);
            queryBalanceResp.setStatusMsg("查询异常");
        }

        return queryBalanceResp;
    }

    public static void main(String[] args) {
        String orderNum = "o2016121715353971208749";
        System.out.println(orderNum.startsWith("o"));
        System.out.println(orderNum.length());
    }
}
