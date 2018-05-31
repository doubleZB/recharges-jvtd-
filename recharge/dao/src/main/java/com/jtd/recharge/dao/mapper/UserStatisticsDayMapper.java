package com.jtd.recharge.dao.mapper;

import com.jtd.recharge.dao.po.UserIdAndSelectTime;
import com.jtd.recharge.dao.po.UserStatisticsDay;

import java.util.List;

/**
 * Created by lyp on 2017/3/7.
 */
public interface UserStatisticsDayMapper {
    //查询用户每天成功消费统计
    List<UserStatisticsDay> userStatisticsDay(String time);
    //计算用户每天消费总订单数
    List<UserStatisticsDay> countUserTotalOrder(String time);
    //修改用户应用每天消费统计
    void updateUserStatisticsDay(UserStatisticsDay userStatisticsDay);
    //修改每天用户相应应用的总订单数
    void updateTotalOrder(UserStatisticsDay userStatisticsDay);

    // 查询昨天的数据订单总数、成功率、总交易额 caixia
    List<UserStatisticsDay> userYesterday(UserStatisticsDay userStatisticsDay);
    //根据月份查询每一天的时间、交易金额、成功订单数、订单数、成功率根据月份
    List<UserStatisticsDay> userSelectByMonth(UserIdAndSelectTime userIdAndSelectTime);

    //首页应用统计
    List<UserStatisticsDay> countUserYesterday(UserStatisticsDay userStatisticsDay);
    // 增加用户应用到用户统计天的表
    int insertUserappUserStatisticsDay();

}
