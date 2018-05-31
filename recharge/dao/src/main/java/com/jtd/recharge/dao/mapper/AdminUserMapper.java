package com.jtd.recharge.dao.mapper;

import com.jtd.recharge.dao.po.AdminUser;
import com.jtd.recharge.dao.po.User;

import java.util.List;

public interface AdminUserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AdminUser record);

    int insertSelective(AdminUser record);

    AdminUser selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AdminUser record);

    int updateByPrimaryKey(AdminUser record);

    AdminUser selectAdminUser(AdminUser adminuser);

    List<AdminUser> selectAdminUserByParam(AdminUser adminuser);
}