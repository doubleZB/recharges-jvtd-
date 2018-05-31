package com.jtd.recharge.base.util;

import groovy.util.ConfigSlurper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;


/**
 * @autor jipengkun
 */
public class GroovyConfigUtil {

    private static final Log log = LogFactory.getLog(GroovyConfigUtil.class);

    /**
     * 使用Groovy封装方法初始化
     *
     * @param configFile
     */
    public static Map init(String configFile) {

        // 1.得到ReadGroovyUtil.class的绝对路径,
        URL url = GroovyConfigUtil.class.getResource("/" + configFile);
        //初始化配置
        Map configObject = new HashMap();
        try {
            configObject = new ConfigSlurper().parse(url);
        } catch (Exception e) {
            log.error("找不到配置文件的路径,url:" + url, e);
        }
        return configObject;
    }

    public static void main(String[] args) {

    }
}

