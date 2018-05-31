package com.jtd.recharge.dao.mapper;

import com.jtd.recharge.dao.po.IotOrder;

public interface IotOrderMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(IotOrder record);

    int insertSelective(IotOrder record);

    IotOrder selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(IotOrder record);

    int updateByPrimaryKey(IotOrder record);
}