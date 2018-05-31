package com.jtd.recharge.dao.mapper;

import java.util.List;

import com.jtd.recharge.dao.po.IotProduct;

public interface IotProductMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(IotProduct record);

    int insertSelective(IotProduct record);

    IotProduct selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(IotProduct record);

    int updateByPrimaryKey(IotProduct record);

    List<IotProduct> selectByIotProduct(IotProduct iotProduct);
    
    List<IotProduct> selectAllIotProduct();
}