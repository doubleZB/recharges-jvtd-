package com.jtd.recharge.schedule.user;

import com.jtd.recharge.dao.po.User;
import com.jtd.recharge.dao.po.UserStatisticsDay;
import com.jtd.recharge.dao.po.UserStatisticsMonth;
import com.jtd.recharge.service.statistics.UserStatisticsDayService;
import com.jtd.recharge.service.statistics.UserStatisticsMonthService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by lyp on 2017/3/10.
 */
@Controller
@RequestMapping("/UserStatisticsMonth")
public class UserStatisticsMonthTime {

    private Log log = LogFactory.getLog(this.getClass());
    @Resource
    private UserStatisticsMonthService userStatisticsMonthService;

    /**
     *
     * 统计用户每月成功单数和金额
     * @return
     */
    @RequestMapping("/month")
    @ResponseBody
    public void userStatisticsMonth() throws Exception{
        SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
        log.info("开始每月记录数据!"+df.format(new Date()));
        userStatisticsMonthService.insertUserappUserStatisticsMonth();
        updateUserStatisticsMonth(userStatisticsMonthService.countUserStatisticsMonth(countTime()));
        updateTotalOrder(userStatisticsMonthService.countUserTotalOrder(countTime()));
    }

    /**
     * 用户每月统计数据成功的单数和交易金额
     * @param list
     */
    public void updateUserStatisticsMonth(List<UserStatisticsMonth> list)throws Exception{
        for(int i = 0;i < list.size(); i ++){

            UserStatisticsMonth userStatisticsMonth=list.get(i);
            userStatisticsMonth.setUpdateTime(countTime());
            log.info("充值成功详细的数据用户"+i+":"+userStatisticsMonth.getUserId());
            log.info("充值成功详细的数据总订单数"+i+":"+userStatisticsMonth.getSumOrderNum());
            log.info("充值成功详细的数据订单金额"+i+":"+userStatisticsMonth.getAmount());
            userStatisticsMonthService.updateUserStatisticsMonth(userStatisticsMonth);

        }
    }
    /**
     *    修改用户总订单数
     * @param list
     */
    public void updateTotalOrder(List<UserStatisticsMonth> list)throws Exception{

        UserStatisticsMonth userStatisticsMonth=new UserStatisticsMonth();
        for(int i = 0;i < list.size(); i ++){

            userStatisticsMonth.setBusinessType(list.get(i).getBusinessType());
            userStatisticsMonth.setUserId(list.get(i).getUserId());
            userStatisticsMonth.setSumOrderNum(list.get(i).getSumOrderNum());
            userStatisticsMonth.setUpdateTime(countTime());
            userStatisticsMonthService.updateTotalOrder(userStatisticsMonth);
        }
    }

    /**
     * 计算统计时间
     * @return
     */
    public String countTime(){
        SimpleDateFormat df=new SimpleDateFormat("yyyy-MM");
        Date beginDate = new Date();
        Calendar date = Calendar.getInstance();
        date.setTime(beginDate);
        date.set(Calendar.DAY_OF_MONTH,-1);
        return df.format(date.getTime())+"%";
    }


}
