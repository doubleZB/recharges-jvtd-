package com.jtd.recharge.dao.mapper;

import com.jtd.recharge.dao.po.AdminMenu;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface AdminMenuMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AdminMenu record);

    int insertSelective(AdminMenu record);

    AdminMenu selectByPrimaryKey(Integer id);

    List<AdminMenu> select();

    int updateByPrimaryKeySelective(AdminMenu record);

    int updateByPrimaryKey(AdminMenu record);
}