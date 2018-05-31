package com.jtd.recharge.dao.mapper;

import com.jtd.recharge.dao.po.UserApp;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface UserAppMapper {

    int insertSelective(UserApp record);

    List<UserApp> selectUserAppList(Map map);

    UserApp selectUserAppListById(UserApp userApp);

    int updateApp(UserApp userApp);

    List<UserApp> selectUserAppByUserId(UserApp userApp);

    UserApp selectUserAppByUserAppType(UserApp userApp);

    List<Integer> getGroupIds(@Param("businessType") Integer businessType, @Param("seltype") Integer seltype);
}