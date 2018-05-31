package com.jtd.recharge.dao.mapper;

import com.jtd.recharge.dao.po.UserAuth;

import java.util.List;

public interface UserAuthMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserAuth record);

    int insertSelective(UserAuth record);

    int updateUserAuthByID(UserAuth record);

    List<UserAuth> selectUserAuthList(UserAuth userAuth);
}