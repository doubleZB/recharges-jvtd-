package com.jtd.recharge.schedule.user;

import com.jtd.recharge.base.util.SmsUtil;
import com.jtd.recharge.dao.po.UserBalanceMonitor;
import com.jtd.recharge.service.user.UserService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

/**
 * @autor jipengkun
 */
public class UserBalanceTime{
    private Log log = LogFactory.getLog(this.getClass());
    @Resource
    public UserService userService;

    public void  work() {
        log.info("开始记录数据!");
        List<UserBalanceMonitor> list = userService.selectUserBalanceMonitorStatus();
        List<UserBalanceMonitor> listTwo = userService.selectUserBalanceMonitorStatusTwo();
        if(list!=null && !"".equals(list)){
                for(int i = 0 ; i < list.size() ; i++) {
                    UserBalanceMonitor balanceMonitor = list.get(i);
                    String userName = balanceMonitor.getUserName();
                    String monitorMobile = balanceMonitor.getMonitorMobile();
                    BigDecimal userBalance = balanceMonitor.getUserBalance();
                    BigDecimal monitorBalance = balanceMonitor.getMonitorBalance();
                    log.info("手机号："+monitorMobile+"账户名："+userName+"余额:"+monitorBalance+"元");
                    //-1表示小于,0是等于,1是大于
                    if(monitorBalance.compareTo(userBalance)==1){
                        log.info("手机号："+monitorMobile+"账户名："+userName+"余额不足:"+monitorBalance+"元");
                        SmsUtil.SendSms(monitorMobile, "【聚通达】您的聚通达账户"+userName+"余额不足"+monitorBalance+"元，请及时加款，以免业务中断。");
                }
            }
        }
        if(listTwo!=null && !"".equals(listTwo)){
            for(int j = 0 ; j < listTwo.size() ; j++) {
                UserBalanceMonitor monitor = listTwo.get(j);
                String userNames = monitor.getUserName();
                String monitorMobiles = monitor.getMonitorMobile();
                BigDecimal userBalances = monitor.getUserBalance();
                BigDecimal monitorBalances = monitor.getMonitorBalance();
                //-1表示小于,0是等于,1是大于
                if(monitorBalances.compareTo(userBalances)==1){
                    log.info("手机号："+monitorMobiles+"账户名："+userNames+"余额不足:"+monitorBalances+"元");
                    SmsUtil.SendSms(monitorMobiles, "【聚通达】您的聚通达账户"+userNames+"余额不足"+monitorBalances+"元，请及时加款，以免业务中断。");
                }
            }
        }
    }
}
