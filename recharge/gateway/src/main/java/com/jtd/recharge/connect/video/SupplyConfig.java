package com.jtd.recharge.connect.video;

import com.jtd.recharge.base.util.GroovyConfigUtil;

import java.util.HashMap;
import java.util.Map;

public class SupplyConfig {

    private static Map config = new HashMap();

    static {
        config = GroovyConfigUtil.init("config/supply.groovy");
    }

    public static Map getConfig() {
        return config;
    }

    public static void setConfig(Map config) {
        SupplyConfig.config = config;
    }
}
