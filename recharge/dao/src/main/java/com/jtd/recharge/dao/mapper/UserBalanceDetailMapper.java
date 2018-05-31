package com.jtd.recharge.dao.mapper;

import com.jtd.recharge.dao.bean.BalanceRecord;
import com.jtd.recharge.dao.po.UserBalanceDetail;

import java.util.List;

public interface UserBalanceDetailMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserBalanceDetail record);

    int insertSelective(UserBalanceDetail record);

    UserBalanceDetail selectByPrimaryKey(Integer id);

    List<UserBalanceDetail> selectByParam(UserBalanceDetail detail);

    int updateByPrimaryKeySelective(UserBalanceDetail record);

    int updateByPrimaryKey(UserBalanceDetail record);

    int insertBalanceDetail(UserBalanceDetail detail);
}