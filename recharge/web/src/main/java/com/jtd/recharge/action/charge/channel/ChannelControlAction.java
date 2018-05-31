package com.jtd.recharge.action.charge.channel;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by lyp on 2016/11/14.
 * 渠道充值监控
 */
@Controller
@RequestMapping("channels")
public class ChannelControlAction {

    @RequestMapping("control")
    public String channelControl(){

        return "/charge/channel/channelRechargeControl";
    }
}
