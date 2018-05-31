package com.jtd.recharge.dao.mapper;

import java.util.List;

import com.jtd.recharge.dao.po.IotInReceipt;

public interface IotInReceiptMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(IotInReceipt record);

    int insertSelective(IotInReceipt record);

    IotInReceipt selectByPrimaryKey(Integer id);
    
    IotInReceipt selectBySerialNum(String serialNum);
    
    List<IotInReceipt> selectByCondition(IotInReceipt inReceipt);

    int updateByPrimaryKeySelective(IotInReceipt record);

    int updateByPrimaryKey(IotInReceipt record);

	IotInReceipt selectByPurchaseId(Integer purchaseId);

	int changeStatus(IotInReceipt inReceipt);
}