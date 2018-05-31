package com.jtd.recharge.dao.mapper;

import com.jtd.recharge.dao.po.AdminUserRole;

import java.util.List;

public interface AdminUserRoleMapper {
    int deleteByPrimaryKey(Integer id);

    int deleteByParam(AdminUserRole param);

    int insert(AdminUserRole record);

    int insertSelective(AdminUserRole record);

    AdminUserRole selectByPrimaryKey(Integer id);

    List<AdminUserRole> selectByUid(Integer uid);

    int updateByPrimaryKeySelective(AdminUserRole record);

    int updateByPrimaryKey(AdminUserRole record);
}