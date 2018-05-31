package com.jtd.recharge.schedule;

import com.alibaba.fastjson.JSON;
import com.jtd.recharge.base.cache.redis.RedisTemplate;
import com.jtd.recharge.dao.mapper.ChargeWhiteListMapper;
import com.jtd.recharge.dao.po.ChargeWhiteList;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lyp on 2017/6/19.
 */
//@Service
public class WhiteListCache {

    private Log log = LogFactory.getLog(this.getClass());


    @Resource
    ChargeWhiteListMapper chargeWhiteListMapper;

    @Resource(name="commRedisTemplate")
    RedisTemplate redisTemplate;


    public void onApplicationEventWhiteList(ContextRefreshedEvent contextRefreshedEvent) {

        if (contextRefreshedEvent.getApplicationContext().getParent() == null) {
            log.info("init WhiteList---begin");

            List<ChargeWhiteList> numSections = chargeWhiteListMapper.selectByPrimaryKey();
            if (numSections != null && !numSections.isEmpty()) {
                for (int i = 0; i < numSections.size(); i++) {
                    ChargeWhiteList section = numSections.get(i);
                    Map<String, Object> map = new HashMap<>();
                    map.put("whiteList", section.getWhiteMobile());

                    redisTemplate.set("hubeiyidong-"+section.getWhiteMobile(), JSON.toJSONString(map));
                    log.info("redisTemplate.set--" + section.getWhiteMobile());
                }
            }
            log.info("init WhiteList---end");
        }
    }
}
