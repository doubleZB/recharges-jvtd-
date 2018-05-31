package com.jtd.recharge.dao.mapper;

import com.jtd.recharge.dao.po.ChargeProductGroup;

import java.util.List;
public interface ChargeProductGroupMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ChargeProductGroup record);

    int insertSelective(ChargeProductGroup record);

    ChargeProductGroup selectByPrimaryKey(Integer id);


    ChargeProductGroup selectByName(String name);

    List<ChargeProductGroup> selectProductGroup();

    int updateByPrimaryKeySelective(ChargeProductGroup record);

    int updateByPrimaryKey(ChargeProductGroup record);

    List<ChargeProductGroup> selectProductGroupList(ChargeProductGroup chargeProductGroup);

    List<ChargeProductGroup> selectProductGroupCondition( ChargeProductGroup chargeProductGroup);
}