package com.jtd.recharge.dao.mapper;

import com.jtd.recharge.dao.po.CacheOrder;
import com.jtd.recharge.dao.po.CacheOrderForQuery;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CacheOrderMapper {
    int deleteByPrimaryKey(String orderNum);

    int insert(CacheOrder record);

    int insertSelective(CacheOrder record);

    CacheOrder selectByPrimaryKey(String orderNum);

    int updateByPrimaryKeySelective(CacheOrder record);

    int updateByPrimaryKey(CacheOrder record);

    List<CacheOrder> selectCacheOrder(CacheOrder cacheOrder);

    int updateChargeStatus(@Param("orderNums") String[] orderNums, @Param("status") int status);

    List<CacheOrder> getOrderListByCondition(@Param("cacheOrder") CacheOrder cacheOrder, @Param("startTime") String startTime, @Param("endTime") String endTime);

    List<CacheOrder> getChechorder(@Param("orderNums") String[] orderNums);
}