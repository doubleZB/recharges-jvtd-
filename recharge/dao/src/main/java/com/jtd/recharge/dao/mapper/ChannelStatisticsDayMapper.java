package com.jtd.recharge.dao.mapper;

import com.jtd.recharge.dao.po.ChannelStatisticsDay;

import java.util.List;

public interface ChannelStatisticsDayMapper {
    int deleteByPrimaryKey(Integer id);


    int insertSelective(ChannelStatisticsDay record);

    ChannelStatisticsDay selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ChannelStatisticsDay record);


    List<ChannelStatisticsDay> selectSuccessChannelTotalOrder(String orderTime);

    List<ChannelStatisticsDay> selectChannelTotalOrder(String orderTime);

    int updateChannelStatisticsDay(ChannelStatisticsDay channelStatisticsDay);

    List<ChannelStatisticsDay> selectByPrimaryKeySelective(ChannelStatisticsDay channelStatisticsDay);
}