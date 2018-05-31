package com.jtd.recharge.action.charge.channel;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by lyp on 2016/11/14.
 * 渠道加款记录
 */
@Controller
@RequestMapping("channels")
public class ChannelAddMoneyAction {

    @RequestMapping("addMoney")
    public String getChannelAddMoney(){
            return "/charge/channel/channelAddMoney";
        }

}
