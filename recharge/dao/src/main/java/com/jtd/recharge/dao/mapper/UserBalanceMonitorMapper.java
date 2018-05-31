package com.jtd.recharge.dao.mapper;

import com.jtd.recharge.dao.po.UserBalanceMonitor;

import java.util.List;

public interface UserBalanceMonitorMapper {


    List<UserBalanceMonitor> selectUserBalanceMonitor(UserBalanceMonitor userBalanceMonitor);

    int insertUserBalanceMonitor(UserBalanceMonitor userBalanceMonitor);

    int updateUserBalanceMonitor(UserBalanceMonitor userBalanceMonitor);

    int updateUserBalanceMonitors(UserBalanceMonitor userBalanceMonitor);

    List<UserBalanceMonitor> selectUserBalanceMonitorStatus();

    List<UserBalanceMonitor> selectUserBalanceMonitorStatusTwo();
}