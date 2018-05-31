package com.jtd.recharge.action.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by lyp on 2017/4/18.
 */
@Controller
@RequestMapping("/user")
public class MerchantAuditAction {

    /**
     * 渠道管理页面用于左侧跳转商户充值审核
     * @return
     */
    @RequestMapping("/merchantAudit")
    public String channelPlusConfirmation(){

        return "/user/merchantAudit";
    }
}
