package com.jtd.recharge.schedule.user;

import com.jtd.recharge.dao.po.UserStatisticsDay;
import com.jtd.recharge.service.statistics.UserStatisticsDayService;
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
 * Created by lyp on 2017/3/8.
 */
@Controller
@RequestMapping("/UserStatisticsDay")
public class UserStatisticsDayTime {

    private Log log = LogFactory.getLog(this.getClass());

    @Resource
    private UserStatisticsDayService userStatisticsDayService;
    /**
     *
     * 统计用户每天成功单数和金额
     * @return
     */
    @RequestMapping("/day")
    @ResponseBody
    public void userStatisticsDay()throws Exception{
        SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
        log.info("开始每天记录数据!"+df.format(new Date()));
        userStatisticsDayService.insertUserappUserStatisticsDay();
        List list= userStatisticsDayService.userStatisticsDay(countTime());
        log.info("充值成功的数据"+list.size());
        updateUserStatisticsDay(list);
        List listTotal= userStatisticsDayService.countUserTotalOrder(countTime());
        log.info("总的的订单数据"+listTotal.size());
        updateTotalOrder(listTotal);

    }

    /**
     * 修改用户每天统计出来的成功的单数和 交易金额
     * @param list
     */
    public void updateUserStatisticsDay(List<UserStatisticsDay> list)throws Exception{

        for(int i = 0;i < list.size(); i ++){

            UserStatisticsDay userStatisticsDay=list.get(i);
            userStatisticsDay.setUpdateTime(countTime());
            log.info("充值成功详细的数据用户"+i+":"+userStatisticsDay.getUserId());
            log.info("充值成功详细的数据总订单数"+i+":"+userStatisticsDay.getSumOrderNum());
            log.info("充值成功详细的数据订单金额"+i+":"+userStatisticsDay.getAmount());
            userStatisticsDayService.updateUserStatisticsDay(userStatisticsDay);
        }
    }

    /**
     *    修改用户总订单数
     * @param list
     */
    public void updateTotalOrder(List<UserStatisticsDay> list)throws Exception{
        UserStatisticsDay userStatisticsDay=new UserStatisticsDay();
        for(int i = 0;i < list.size(); i ++){

            userStatisticsDay.setBusinessType(list.get(i).getBusinessType());
            userStatisticsDay.setUserId(list.get(i).getUserId());
            userStatisticsDay.setSumOrderNum(list.get(i).getSumOrderNum());
            userStatisticsDay.setUpdateTime(countTime());

            userStatisticsDayService.updateTotalOrder(userStatisticsDay);
        }
    }

    /**
     * 计算统计时间
     * @return
     */
    public String countTime(){
        SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
        Date beginDate = new Date();
        Calendar date = Calendar.getInstance();
        date.setTime(beginDate);
        date.set(Calendar.DATE, date.get(Calendar.DATE) - 1);
        return df.format(date.getTime())+"%";
    }


}
