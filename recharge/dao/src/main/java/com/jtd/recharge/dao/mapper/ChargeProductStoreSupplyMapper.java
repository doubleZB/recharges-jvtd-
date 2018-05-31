package com.jtd.recharge.dao.mapper;

import com.jtd.recharge.dao.po.ChargeProductStoreSupply;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChargeProductStoreSupplyMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ChargeProductStoreSupply record);

    int insertSelective(ChargeProductStoreSupply record);

    ChargeProductStoreSupply selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ChargeProductStoreSupply record);

    int updateByPrimaryKey(ChargeProductStoreSupply record);

    List<ChargeProductStoreSupply> selectProductStoreSupplyByCondition(ChargeProductStoreSupply ProductStoreSupply);

    List<Integer> getSupplyId(@Param("businessType") Integer businessType, @Param("storeIds") List<Integer> storeIds);
}