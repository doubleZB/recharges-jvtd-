package com.jtd.recharge.dao.mapper;

import java.util.List;

import com.jtd.recharge.dao.po.IotPurchase;

public interface IotPurchaseMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(IotPurchase record);

    int insertSelective(IotPurchase record);

    IotPurchase selectByPrimaryKey(Integer id);
    
    IotPurchase selectBySerialNum(String serialNum);
    
    List<IotPurchase> selectPurchaseList(IotPurchase purchaser);

    int updateByPrimaryKeySelective(IotPurchase record);

    int updateByPrimaryKey(IotPurchase record);

	int changePurchaseStatus(IotPurchase purchase);

	List<IotPurchase> selectBySubOrderId(Integer subOrderId);

}