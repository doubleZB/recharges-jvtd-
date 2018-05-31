package com.jtd.recharge.dao.mapper;

import com.jtd.recharge.dao.po.ChargeWhiteList;

import java.util.List;

public interface ChargeWhiteListMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ChargeWhiteList record);

    int insertSelective(ChargeWhiteList record);

    List<ChargeWhiteList> selectByPrimaryKey();

    int updateByPrimaryKeySelective(ChargeWhiteList record);

    int updateByPrimaryKey(ChargeWhiteList record);
}