package com.jtd.recharge.schedule.cache;

import com.jtd.recharge.base.cache.redis.RedisTemplate;
import com.jtd.recharge.dao.mapper.NumSectionMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * Created by WXP  2016-12-8 11:50:18.
 */
@Controller
@RequestMapping("/cache")
public class CacheController {

    @Resource(name="commRedisTemplate")
    RedisTemplate redisTemplate;

    @Resource
    NumSectionMapper numSectionMapper;

    @RequestMapping("/cacheControlPage")
    @ResponseBody
    public String cacheControlPage(){
        return redisTemplate.get("1300001");
    }

}
