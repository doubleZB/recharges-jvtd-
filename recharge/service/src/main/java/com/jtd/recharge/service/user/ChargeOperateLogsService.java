package com.jtd.recharge.service.user;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jtd.recharge.dao.bean.BalanceRecord;
import com.jtd.recharge.dao.mapper.ChargeOperateLogsMapper;
import com.jtd.recharge.dao.po.ChargeOperateLogs;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2017/1/6.
 */
@Service
@Transactional(readOnly = true)
public class ChargeOperateLogsService {

    @Resource
    public ChargeOperateLogsMapper chargeOperateLogsMapper;

    /**
     * 查询日志
     * @param pageNumber
     * @param pageSize
     * @param operateLogs
     * @return
     */
    public PageInfo<ChargeOperateLogs> selectChargeOperateLogs(Integer pageNumber, Integer pageSize, ChargeOperateLogs operateLogs) {
        PageHelper.startPage(pageNumber,pageSize,"operate_time desc");
        HashMap map = new HashMap<>();
        map.put("startOperateTime",operateLogs.getStartOperateTime());
        map.put("endOperateTime",operateLogs.getEndOperateTime());
        map.put("operater",operateLogs.getOperater());
        List<ChargeOperateLogs> user =chargeOperateLogsMapper.selectChargeOperateLogs(map);
        PageInfo<ChargeOperateLogs> pageInfo = new PageInfo<>(user);
        return pageInfo;
    }
}
