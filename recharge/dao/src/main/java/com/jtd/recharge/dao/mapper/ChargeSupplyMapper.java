package com.jtd.recharge.dao.mapper;

import com.jtd.recharge.dao.po.ChargeSupply;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChargeSupplyMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ChargeSupply record);

    int insertSelective(ChargeSupply record);

    ChargeSupply selectByPrimaryKey(Integer id);

    List<ChargeSupply> selectByParam(ChargeSupply chargeSupply);

    int updateByPrimaryKeySelective(ChargeSupply record);

    int updateByPrimaryKey(ChargeSupply record);

    List<ChargeSupply> getSuppleir(ChargeSupply chargeSupply);

    List<Integer> getSupplyIds(@Param("businessType") Integer businessType, @Param("seltype") String seltype);

}