package com.jtd.recharge.dao.mapper;

import java.util.List;

import com.jtd.recharge.dao.po.IotOutReceipt;

public interface IotOutReceiptMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(IotOutReceipt record);

    int insertSelective(IotOutReceipt record);

    IotOutReceipt selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(IotOutReceipt record);

    int updateByPrimaryKey(IotOutReceipt record);

	List<IotOutReceipt> selectByCondition(IotOutReceipt outReceipt);

	IotOutReceipt selectBySerialNum(String serialNum);

	int changeStatus(IotOutReceipt outReceipt);

	List<IotOutReceipt> selectBySubOrderId(Integer iotSubOrderId);
}