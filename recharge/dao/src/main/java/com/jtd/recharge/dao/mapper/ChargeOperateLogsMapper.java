package com.jtd.recharge.dao.mapper;

import com.jtd.recharge.dao.po.ChargeOperateLogs;

import java.util.HashMap;
import java.util.List;

public interface ChargeOperateLogsMapper {
    int deleteByPrimaryKey(Integer id);

    ChargeOperateLogs selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ChargeOperateLogs record);

    int updateByPrimaryKey(ChargeOperateLogs record);

    int insertOperateLogs(ChargeOperateLogs operateLogs);

    List<ChargeOperateLogs> selectChargeOperateLogs(HashMap map);
}