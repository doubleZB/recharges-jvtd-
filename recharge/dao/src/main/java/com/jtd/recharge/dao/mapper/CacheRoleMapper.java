package com.jtd.recharge.dao.mapper;

import com.jtd.recharge.dao.po.CacheRole;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CacheRoleMapper {
    int deleteByPrimaryKey(Long id);

    int insert(CacheRole record);

    int insertSelective(CacheRole record);

    CacheRole selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CacheRole record);

    int updateByPrimaryKey(CacheRole record);

    List<CacheRole> selectCache(CacheRole record);

    List<CacheRole> selectRuleListByConditions(CacheRole cacheRole);

    List<CacheRole> checkRepeatRule(CacheRole cacheRole);

    Integer checkRuleIlegle(CacheRole cacheRole);
}