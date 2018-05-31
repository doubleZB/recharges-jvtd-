package com.jtd.recharge.service.statistics;

import com.jtd.recharge.dao.mapper.UserStatisticsMonthMapper;
import com.jtd.recharge.dao.po.UserIdAndSelectTime;
import com.jtd.recharge.dao.po.UserStatisticsMonth;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by lyp on 2017/3/10.
 */
@Service
public class UserStatisticsMonthService {

    @Resource
    private UserStatisticsMonthMapper userStatisticsMonthMapper;

    /**
     * 统计用户每月消费记录
     * @param time
     * @return
     */
    public List<UserStatisticsMonth> countUserStatisticsMonth(String time){
        return userStatisticsMonthMapper.countUserStatisticsMonth(time);
    }

    /**
     * 用户每月统计数据
     * @param userStatisticsMonth
     */
    public void updateUserStatisticsMonth(UserStatisticsMonth userStatisticsMonth){

        userStatisticsMonthMapper.updateUserStatisticsMonth(userStatisticsMonth);
    }


    /**
     * 根据年，查询每个月的数据
     * @param userIdAndSelectTime
     * @return
     * caixia
     */
   public List<UserStatisticsMonth> userSelectByYear(UserIdAndSelectTime userIdAndSelectTime){
       return userStatisticsMonthMapper.userSelectByYear(userIdAndSelectTime);
   }

    /**.
     *统计用户每月数据
     * @param time
     * @return
     */
    public List<UserStatisticsMonth> countUserTotalOrder(String time){
        return userStatisticsMonthMapper.countUserTotalOrder(time);
    }
    /**
     * 修改每月用户相应应用的总订单数
     * @param userStatisticsMonth
     */
    public void updateTotalOrder(UserStatisticsMonth userStatisticsMonth){
        userStatisticsMonthMapper.updateTotalOrder(userStatisticsMonth);
    }

    /**
     * 增加用户应用到用户统计月的表
     */
    public void insertUserappUserStatisticsMonth(){
        userStatisticsMonthMapper.insertUserappUserStatisticsMonth();
    }

}
