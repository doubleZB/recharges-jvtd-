package com.jtd.recharge.service.statistics;

import com.jtd.recharge.dao.mapper.UserStatisticsDayMapper;
import com.jtd.recharge.dao.po.UserIdAndSelectTime;
import com.jtd.recharge.dao.po.UserStatisticsDay;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by lyp on 2017/3/7.
 */
@Service
public class UserStatisticsDayService {
     @Resource
     private UserStatisticsDayMapper userStatisticsDayMapper;
    /**
     * 统计用户每天消费
     * @param time
     * @return
     */
    public List<UserStatisticsDay> userStatisticsDay(String time){
        return userStatisticsDayMapper.userStatisticsDay(time);
    }
    /**
     * 统计用户每天消费
     * @param time
     * @return
     */
    public List<UserStatisticsDay> countUserTotalOrder(String time){
        return userStatisticsDayMapper.countUserTotalOrder(time);
    }
    /**
     * 修改每个用户应用每天的统计数据
     * @param userStatisticsDay
     */
    public void updateUserStatisticsDay(UserStatisticsDay userStatisticsDay){
        userStatisticsDayMapper.updateUserStatisticsDay(userStatisticsDay);
    }
    /**
     * 修改每天用户相应应用的总订单数
     * @param userStatisticsDay
     */
    public void updateTotalOrder(UserStatisticsDay userStatisticsDay){
        userStatisticsDayMapper.updateTotalOrder(userStatisticsDay);
    }
    /**
     * 查询昨天的数据根据用户id
     * @param userStatisticsDay
     * @return
     * caixia
     */
      public  List userStatisticsByUserId(UserStatisticsDay userStatisticsDay){
          return userStatisticsDayMapper.userYesterday(userStatisticsDay);
      }

    /**
     * 根据月份查询每一天的时间、交易金额、成功订单数、订单数、成功率根据月份
     * @param userIdAndSelectTime
     * @return
     * caixia
     */
      public  List userSelectByMonth(UserIdAndSelectTime userIdAndSelectTime){
          return userStatisticsDayMapper.userSelectByMonth(userIdAndSelectTime);
      }


    public List<UserStatisticsDay> countUserYesterday(UserStatisticsDay userStatisticsDay){
        return userStatisticsDayMapper.countUserYesterday(userStatisticsDay);
    }

    /**
     * 增加用户应用到用户统计天的表
     */
    public void insertUserappUserStatisticsDay(){
        userStatisticsDayMapper.insertUserappUserStatisticsDay();
    }
}
