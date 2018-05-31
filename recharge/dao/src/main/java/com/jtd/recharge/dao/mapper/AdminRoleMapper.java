package com.jtd.recharge.dao.mapper;

import com.jtd.recharge.dao.po.AdminRole;

import java.util.List;

public interface AdminRoleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AdminRole record);

    int insertSelective(AdminRole record);

    AdminRole selectByPrimaryKey(Integer id);

    List<AdminRole> selectAll(AdminRole roleName);

    int updateByPrimaryKeySelective(AdminRole record);

    int updateByPrimaryKey(AdminRole record);
}