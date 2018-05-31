package com.jtd.recharge.connect.flow.henanyidong;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jtd.recharge.base.util.HttpTookit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 获取河南移动access_token
 */
public class TokenSingleton {

    private Logger log = LoggerFactory.getLogger(TokenSingleton.class);

    private Map<String, String> map = new HashMap<>();

    private String url;

    private TokenSingleton(String url) {
        this.url = url;
    }

    private static TokenSingleton single = null;

    // 静态工厂方法
    public static TokenSingleton getInstance(String url) {
        if (single == null) {
            single = new TokenSingleton(url);
        }
        return single;
    }

    public Map<String, String> getMap() {
        String time = this.map.get("time");
        String accessToken = this.map.get("access_token");
        Long nowDate = new Date().getTime();

        if (accessToken != null && time != null && nowDate - Long.parseLong(time) < 72000 * 1000) {

            log.info("accessToken存在，且没有超时，返回单例 {}", this.map);
            return this.map;
        } else {
            log.info("accessToken 超时，或者不存在，重新获取");

            return this.forcedUpdate(nowDate);
        }
    }

    public Map<String, String> forcedUpdate(long nowDate) {
        if (nowDate <= 0){
            nowDate = new Date().getTime();
        }

        try {
            log.info("获取access_token请求地址：{}", url);
            JSONObject json = JSON.parseObject(HttpTookit.doGet(url, null));
            log.debug("请求结果 信息：{}", json);

            if (json.getString("access_token") == null){
                log.error("获取access_token失败：");
                return this.map;
            }
            // "access_token";
            this.map.put("time", String.valueOf(nowDate));
            this.map.put("access_token", json.getString("access_token"));
            return this.map;
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            return this.map;
        }
    }

    public void setMap(Map<String, String> map) {
        this.map = map;
    }

    public static TokenSingleton getSingle() {
        return single;
    }

    public static void setSingle(TokenSingleton single) {
        TokenSingleton.single = single;
    }

}
