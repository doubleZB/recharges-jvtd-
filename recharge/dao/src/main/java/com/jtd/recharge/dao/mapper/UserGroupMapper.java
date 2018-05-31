package com.jtd.recharge.dao.mapper;

import com.jtd.recharge.dao.bean.UserGroup;
import com.jtd.recharge.dao.po.ChargeOperateLogs;

/**
 * Created by 用户货架组公用 on 2016/12/19.
 */
public interface UserGroupMapper {

    int insertUser(UserGroup user_group);

    UserGroup selectUserByid(UserGroup usergroup);

    int updateUserList(UserGroup user_group);

    int updateUserLists(UserGroup usergroup);

}
