package com.jtd.recharge.service.admin;

import com.jtd.recharge.dao.mapper.ChargeOperateLogsMapper;
import com.jtd.recharge.dao.po.ChargeOperateLogs;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * Created by wxp 2017-01-06.
 */

@Service
@Transactional(readOnly = true)
public class OperateLogService {

    @Resource
    public  ChargeOperateLogsMapper chargeOperateLogsMapper;

    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public void logInfo(String operater,String menu,String content){
        ChargeOperateLogs operateLogs = new ChargeOperateLogs();
        operateLogs.setContent(content);
        operateLogs.setMenu(menu);
        operateLogs.setOperater(operater);
        chargeOperateLogsMapper.insertOperateLogs(operateLogs);
    }
}
