package com.jtd.recharge.base.cache.redis;

import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import redis.clients.jedis.*;
import redis.clients.jedis.exceptions.JedisException;


/**
 * @autor jipengkun
 * Redis模板工具类
 */
public class RedisTemplate {

    private JedisPool jedisPool;

    /**
     * 设置缓存
     * @param key
     * @param value
     * @return
     */
    public String set(String key ,String value){
        String result = null;
        Jedis jedis = null;
        try {
            jedis = getResource();
            result = jedis.set(key, value);
        } catch (Exception e) {
            closeResource(jedis);
            e.printStackTrace();
        } finally {
            closeResource(jedis);
        }
        return result;
    }

    /**
     * 修改缓存
     * @param key
     * @param value
     * @return
     */
    public String update(String key ,String value){
        String result = null;
        Jedis jedis = null;
        try {
            jedis = getResource();
            result = jedis.rename(key, value);
        } catch (Exception e) {
            closeResource(jedis);
            e.printStackTrace();
        } finally {
            closeResource(jedis);
        }
        return result;
    }

    /**
     * 设置缓存,超时
     * @param key 键
     * @param value 值
     * @param cacheSeconds 超时时间，0为不超时
     * @return
     */
    public String setExpire(String key, String value, int cacheSeconds) {
        String result = null;
        Jedis jedis = null;
        try {
            jedis = getResource();
            result = jedis.set(key, value);
            if (cacheSeconds != 0) {
                jedis.expire(key, cacheSeconds);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeResource(jedis);
        }
        return result;
    }

    /**
     * 获取缓存
     * @param key 键
     * @return 值
     */
    public String get(String key) {
        String value = null;
        Jedis jedis = null;
        try {
            jedis = getResource();
            if (jedis.exists(key)) {
                value = jedis.get(key);
                value = StringUtils.isNotBlank(value)
                        && !"nil".equalsIgnoreCase(value) ? value : null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeResource(jedis);
        }
        return value;
    }

    /**
     * 获取缓存
     * @param key 键
     * @return 值
     */
    public JSONObject getObject(String key) {
        String value = null;
        Jedis jedis = null;
        try {
            jedis = getResource();
            if (jedis.exists(key)) {
                value = jedis.get(key);
                value = StringUtils.isNotBlank(value)
                        && !"nil".equalsIgnoreCase(value) ? value : null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeResource(jedis);
        }
        JSONObject jsStr = JSONObject.fromObject(value);
        return jsStr;
    }


    /**
     * 获取缓存
     * @param key 键
     * @return 值
     */
    public void delete(String key) {
        Jedis jedis = null;
        try {
            jedis = getResource();
            jedis.del(key);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeResource(jedis);
        }
    }


    /**
     * 获取资源
     * @return
     * @throws JedisException
     */
    public Jedis getResource() throws JedisException {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
        } catch (JedisException e) {
            e.printStackTrace();
            closeResource(jedis);
            throw e;
        }
        return jedis;
    }


    /**
     * 释放资源
     * @param jedis
     */
    public void closeResource(Jedis jedis) {
        if (jedis != null) {
            jedisPool.returnResource(jedis);
        }
    }

    public JedisPool getJedisPool() {
        return jedisPool;
    }

    public void setJedisPool(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }
}
