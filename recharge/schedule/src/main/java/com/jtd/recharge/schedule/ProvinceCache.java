package com.jtd.recharge.schedule;

import com.alibaba.fastjson.JSON;
import com.jtd.recharge.base.cache.redis.RedisTemplate;
import com.jtd.recharge.dao.mapper.NumSectionMapper;
import com.jtd.recharge.dao.po.NumSection;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @autor jipengkun
 * 刷新号段
 */
@Service
public class ProvinceCache implements ApplicationListener<ContextRefreshedEvent> {
    private Log log = LogFactory.getLog(this.getClass());

    @Resource
    NumSectionMapper numSectionMapper;

    @Resource(name="commRedisTemplate")
    RedisTemplate redisTemplate;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {

        if (contextRefreshedEvent.getApplicationContext().getParent() == null) {
            log.info("init numsection---begin");

            List<NumSection> numSections = numSectionMapper.selectByParam(new NumSection());
            if (numSections != null && !numSections.isEmpty()) {
                for (int i = 0; i < numSections.size(); i++) {
                    NumSection section = numSections.get(i);
                    Map<String, Object> map = new HashMap<>();
                    map.put("province_id", section.getProvinceId());
                    map.put("mobile_type", section.getMobileType());
                    redisTemplate.set(section.getMobileNumber(), JSON.toJSONString(map));
                    log.info("redisTemplate.set--" + section.getMobileNumber() + "---" +  JSON.toJSONString(map));
                }
            }
            log.info("init numsection---end");
        }
    }

}
