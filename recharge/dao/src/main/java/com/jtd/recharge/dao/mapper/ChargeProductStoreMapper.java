package com.jtd.recharge.dao.mapper;

import com.jtd.recharge.dao.po.ChargeProductStore;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChargeProductStoreMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ChargeProductStore record);

    int insertSelective(ChargeProductStore record);

    ChargeProductStore selectByPrimaryKey(Integer id);

    List<ChargeProductStore> selectStoreSelective(ChargeProductStore record);

    int updateByPrimaryKeySelective(ChargeProductStore record);

    int updateByPrimaryKey(ChargeProductStore record);

    ChargeProductStore selectProductStore(@Param("groupId") int groupId, @Param("productId") int productId);

    List<Integer> getStoreIds(@Param("groupIds") List<Integer> groupIds);
}