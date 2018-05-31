package com.jtd.recharge.dao.mapper;

import java.util.List;
import java.util.Map;

import com.jtd.recharge.dao.po.IotSubOrder;

public interface IotSubOrderMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(IotSubOrder record);

    int insertSelective(IotSubOrder record);

    IotSubOrder selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(IotSubOrder record);

    int updateByPrimaryKey(IotSubOrder record);

    List<IotSubOrder> selectOrderList(IotSubOrder sub);

	IotSubOrder selectBySerialNum(Map<String, Object> serialNum);

	Integer changeOrderStatus(IotSubOrder sub);

}