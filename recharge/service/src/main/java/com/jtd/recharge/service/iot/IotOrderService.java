package com.jtd.recharge.service.iot;

import com.jtd.recharge.dao.mapper.IotOrderMapper;
import com.jtd.recharge.dao.po.IotOrder;
import com.jtd.recharge.define.SerialNum;

import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;

/**
 * Created by ${zyj} on 2018/3/23.
 */
@Service
public class IotOrderService {

    @Resource
    IotOrderMapper iotOrderMapper;
    public Integer addIotOrder(IotOrder iotOrder) {
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        String serialNum = SerialNum.订单.getPrefix()+sdf.format(new Date()).toUpperCase();
        iotOrder.setSerialNum(serialNum);
        return iotOrderMapper.insertSelective(iotOrder);
    }

    public Integer updateById(IotOrder iotOrder) {
       return  iotOrderMapper.updateByPrimaryKeySelective(iotOrder);
    }

    public IotOrder getById(Integer parentId) {
        return iotOrderMapper.selectByPrimaryKey(parentId);
    }
}
