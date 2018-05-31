package com.jtd.recharge.dao.mapper;

import com.jtd.recharge.dao.po.ChargeUserGroup;
import com.jtd.recharge.dao.po.UserApp;
import org.apache.ibatis.annotations.Param;

public interface ChargeUserGroupMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ChargeUserGroup record);

    int insertSelective(ChargeUserGroup record);

    ChargeUserGroup selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ChargeUserGroup record);

    int updateByPrimaryKey(ChargeUserGroup record);

    int selectGroupIdByUserId(UserApp userApp);
    //获取应用groupId lyp
    int  selectGroupIdByUserIdAndappType(UserApp userApp);


}