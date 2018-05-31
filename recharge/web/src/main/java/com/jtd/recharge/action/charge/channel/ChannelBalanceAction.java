package com.jtd.recharge.action.charge.channel;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by lyp on 2016/11/14.
 * 渠道余额管理
 */
@Controller
@RequestMapping("channels")
public class ChannelBalanceAction {
    /**
     * 渠道余额管理查询操作
     * @return
     */
    @RequestMapping("balance")
    public String getChannelBalance (){

    return "/charge/channel/channelBalance";
    }

    @RequestMapping("channelRemind")
    public String getChannelRemind(){

        return "/charge/channel/channelBalanceRemind";
    }
}
