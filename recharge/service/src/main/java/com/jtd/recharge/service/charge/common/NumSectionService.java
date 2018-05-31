package com.jtd.recharge.service.charge.common;

import com.alibaba.fastjson.JSON;
import com.jtd.recharge.base.cache.redis.RedisTemplate;
import com.jtd.recharge.dao.mapper.NumSectionMapper;
import com.jtd.recharge.dao.po.NumSection;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @autor jipengkun
 */
@Service
@Transactional(readOnly = true)
public class NumSectionService {

    @Resource
    NumSectionMapper numSectionMapper;

    @Resource(name="commRedisTemplate")
    RedisTemplate redisTemplate;

    /**
     * 根据手机号查询省份,运营商
     * @param mobile
     * @return
     */
    public NumSection findNumSectionByMobile(String mobile) {
        String num = mobile.substring(0,7);
        return numSectionMapper.selectNumSectionByMobile(num);
    }

    public String findNumSection(String mobile) {
        String num = mobile.substring(0, 7);
        String NumSection = redisTemplate.get(num);
        if (NumSection == null) {
            NumSection section = numSectionMapper.selectNumSectionByMobile(num);
            if (NumSection != null) {
                Map<String, Object> map = new HashMap<>();
                map.put("province_id", section.getProvinceId());
                map.put("mobile_type", section.getMobileType());
                NumSection = JSON.toJSONString(map);
                redisTemplate.set(section.getMobileNumber(), NumSection);
            }
        }
        return NumSection;
    }
}
