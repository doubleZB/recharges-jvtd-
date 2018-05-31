package com.jtd.recharge.service.iot;

import com.jtd.recharge.dao.mapper.IotConfigMapper;
import com.jtd.recharge.dao.po.IotConfig;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by ${zyj} on 2018/4/16.
 */
@Service
public class IotConfigService {
    @Resource
    private IotConfigMapper iotConfigMapper;
    public IotConfig getByName(IotConfig config){
      return   iotConfigMapper.getByName(config);
    }

    public Integer addStockAlarm(IotConfig config) {
      return  iotConfigMapper.insertSelective(config);
    }

    public Integer updateStockAlarm(IotConfig config) {
        return iotConfigMapper.updateConfig(config);
    }
}
