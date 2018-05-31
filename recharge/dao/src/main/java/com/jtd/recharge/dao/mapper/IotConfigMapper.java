package com.jtd.recharge.dao.mapper;

import com.jtd.recharge.dao.po.IotConfig;

public interface IotConfigMapper {
    int insert(IotConfig record);

    int insertSelective(IotConfig record);

    IotConfig getByName(IotConfig config);

    Integer updateConfig(IotConfig config);
}