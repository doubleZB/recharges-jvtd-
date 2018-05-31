package com.jtd.recharge.schedule.iot;

import java.util.Properties;

import com.jtd.recharge.base.util.PropertiesUtils;
/**
 * 配置文件
 * @author ninghui
 *
 */
public enum Config {
	iotGateWay,iotToken;
	private static Properties prop = PropertiesUtils.loadProperties("config.properties");
	public String getValue() {
		return prop.getProperty(name());
	}
}
