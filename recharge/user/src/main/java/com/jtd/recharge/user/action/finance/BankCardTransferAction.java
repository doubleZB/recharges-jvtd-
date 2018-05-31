package com.jtd.recharge.user.action.finance;

import com.jtd.recharge.dao.bean.UserBalances;
import com.jtd.recharge.dao.po.User;
import com.jtd.recharge.dao.po.UserPayOrder;
import com.jtd.recharge.service.user.UserPayOrderService;
import com.jtd.recharge.service.user.UserService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 商户端银行卡转账业务
 * Created by wcx on 2017/4/12.
 */
@RequestMapping("/finance")
@Controller
public class BankCardTransferAction {
    private Log log = LogFactory.getLog(this.getClass());
    @Resource
    public UserService userService;
    @Resource
    public UserPayOrderService userPayOrderService;
    /**
     * 得到用户额度数据，返回银行卡转账页面
     *
     * @return
     */
    @RequestMapping({"/bankCardTransferUI"})
    public String bankCardTransfer(HttpServletRequest request, UserBalances balance){
        User users =(User) request.getSession().getAttribute("users");
        Integer userId = users.getId();
        balance.setUserId(userId);
        List<UserBalances> balanceList = userService.selectUserBalanceList(balance);
        request.setAttribute("balanceList",balanceList);
        return "bill/bankCardTransfer";
    }

    /**
     * 添加银行卡转账数据
     * @param request
     * @param userPayOrder
     * @return
     */
    @RequestMapping({"/addUserPayOrder"})
    @ResponseBody
    public Boolean addUserPayOrder(HttpServletRequest request, UserPayOrder userPayOrder){
        User users =(User) request.getSession().getAttribute("users");
        Integer userId = users.getId();
        userPayOrder.setUserId(userId);
        userPayOrder.setAuditState(UserPayOrder.auditState.THREE);
       int i= userPayOrderService.addPayUserOrder(userPayOrder);
        if(i>0){
            return true;
        }else {
            return false;
        }
    }

}
