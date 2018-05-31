package com.jtd.recharge.dao.mapper;

import com.jtd.recharge.dao.po.UserChannelAddfund;

import java.util.List;

public interface UserChannelAddfundMapper {
    int deleteByPrimaryKey(UserChannelAddfund userChannelAddfund);

    int insert(UserChannelAddfund record);

    int insertSelective(UserChannelAddfund record);

    int updateByPrimaryKeySelective(UserChannelAddfund record);

    List<UserChannelAddfund> selecctUserChannelAddfund(UserChannelAddfund userChannelAddfund);

}