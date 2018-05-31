package com.jtd.recharge.dao.mapper;


import com.jtd.recharge.dao.po.UserIdAndSelectTime;



import com.jtd.recharge.dao.po.UserStatisticsMonth;

import java.util.List;

/**
 * Created by lyp on 2017/3/7.
 */
public interface UserStatisticsMonthMapper {
    //统计用户每月消费记录
    List<UserStatisticsMonth> countUserStatisticsMonth(String time);
    //统计用户每月数据
    void updateUserStatisticsMonth(UserStatisticsMonth userStatisticsMonth);

    //根据年查询本年每个月的数据
    List<UserStatisticsMonth>  userSelectByYear(UserIdAndSelectTime userIdAndSelectTime);


    //统计用户每月消费总订单数
    List<UserStatisticsMonth>  countUserTotalOrder(String time);
    //修改每月用户相应应用的总订单数
    void updateTotalOrder(UserStatisticsMonth userStatisticsMonth);
    // 增加用户应用到用户统计月的表
    int insertUserappUserStatisticsMonth();
}
