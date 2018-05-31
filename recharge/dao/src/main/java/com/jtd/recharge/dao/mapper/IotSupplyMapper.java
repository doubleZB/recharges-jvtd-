package com.jtd.recharge.dao.mapper;

import com.jtd.recharge.dao.po.IotSupply;

import java.util.List;

public interface IotSupplyMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(IotSupply record);

    int insertSelective(IotSupply record);

    IotSupply selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(IotSupply record);

    int updateByPrimaryKey(IotSupply record);

    List<IotSupply> selectBySupply(IotSupply supply);

    List<IotSupply> selectAllSupply();
    /**
     * 根据iccid查询供应商
     * @param iccid
     * @return
     */
	IotSupply selectByIccid(String iccid);
}