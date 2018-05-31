package com.jtd.recharge.action.user;

import com.github.pagehelper.PageInfo;
import com.jtd.recharge.dao.po.AdminUser;
import com.jtd.recharge.dao.po.UserPayOrder;
import com.jtd.recharge.service.admin.OperateLogService;
import com.jtd.recharge.service.user.UserPayOrderService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 银行卡转账页面的数据审核以及详情页数据
 */
@Controller
@RequestMapping("/userMerchant")
public class UserMerchantAuditAction {
    private Log log = LogFactory.getLog(this.getClass());
    @Resource
    public UserPayOrderService userPayOrderService;
    @Resource
    public OperateLogService operateLogService;

    /**
     * 根据条件查询条件返回前台页面
     * @param userPayOrder
     * @return
     */
    @RequestMapping("/findUserOrderListByName")
    @ResponseBody
    public Object findUserOrderListByName(UserPayOrder userPayOrder,Integer pageNumber,Integer pageSize){
        PageInfo<UserPayOrder> list =  userPayOrderService.findUserOrderListByName(userPayOrder,pageNumber,pageSize);
        return  list;
    }
    /**
     * 跳转到查看详情的页面
     * @return
     */
    @RequestMapping("/merchantRechargeAuditDetailsUI")
    public String merchantRechargeAuditDetails(HttpServletRequest request){
        String id = request.getParameter("id");
        request.setAttribute("userPayId",id);
        return  "/user/merchantRechargeAuditDetails";
    }

    /**
     *通过用户Id获取详情数据
     * @return
     */
    @RequestMapping("/merchantRechargeAuditDetailsData")
    @ResponseBody
    public List<UserPayOrder> findUserOrderListDetailsById(Integer id){
        List<UserPayOrder> userOrderListDetails = userPayOrderService.findUserOrderListDetails(id);
        return userOrderListDetails ;
    }
    /**
     * 审核通过、不通过
     */
    @RequestMapping("/checkPassBank")
    @ResponseBody
    public Boolean updateUserOrderAuditState(UserPayOrder userPayOrder,HttpServletRequest request){
        Integer auditState = userPayOrder.getAuditState();
        int i = userPayOrderService.updateUserOrderList(userPayOrder);
        if(i>0){
            if(auditState==1){
                AdminUser user = (AdminUser)request.getSession().getAttribute("adminLoginUser");
                String adminName = user.getName();
                operateLogService.logInfo(adminName,"商户充值审核",adminName+"在商户充值审核审核了审核ID为："+userPayOrder.getId()+" 的信息为审核通过");
            }else if(auditState==2){
                AdminUser user = (AdminUser)request.getSession().getAttribute("adminLoginUser");
                String adminName = user.getName();
                operateLogService.logInfo(adminName,"商户充值审核",adminName+"在商户充值审核审核了审核ID为："+userPayOrder.getId()+" 的信息为审核不通过");
            }
            return true;
        }else{
            return false;
        }
    }
}
