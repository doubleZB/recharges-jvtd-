package com.jtd.recharge.dao.mapper;

import com.jtd.recharge.dao.bean.BalanceRecord;
import com.jtd.recharge.dao.po.UserBalanceRecord;

import java.util.List;
import java.util.Map;

public interface UserBalanceRecordMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserBalanceRecord record);

    int insertSelective(UserBalanceRecord record);

    UserBalanceRecord selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserBalanceRecord record);

    int updateByPrimaryKey(UserBalanceRecord record);

}