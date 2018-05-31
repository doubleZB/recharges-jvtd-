package com.jtd.recharge.dao.mapper;

import com.jtd.recharge.dao.po.NumSection;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NumSectionMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(NumSection record);

    int insertSelective(NumSection record);

    NumSection selectByPrimaryKey(Integer id);

    List<NumSection> selectByParam(NumSection id);

    int updateByPrimaryKeySelective(NumSection record);

    int updateByPrimaryKey(NumSection record);

    NumSection selectNumSectionByMobile(String mobile);
}