package com.jtd.recharge.dao.mapper;

import com.jtd.recharge.dao.po.AdminRolelMenu;

import java.util.List;

public interface AdminRolelMenuMapper {
    int deleteByPrimaryKey(Integer id);

    int deleteByParam(AdminRolelMenu record);

    int insert(AdminRolelMenu record);

    int insertSelective(AdminRolelMenu record);

    AdminRolelMenu selectByPrimaryKey(Integer id);

    List<AdminRolelMenu> selectByParam(AdminRolelMenu param);

    int updateByPrimaryKeySelective(AdminRolelMenu record);

    int updateByPrimaryKey(AdminRolelMenu record);
}