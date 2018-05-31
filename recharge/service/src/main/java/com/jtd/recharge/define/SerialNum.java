package com.jtd.recharge.define;

/**
 * 表单前缀
 * 
 * @author ninghui
 *
 */
public enum SerialNum {
	订单("OR"), 采购单("PU"), 入库单("IN"),子订单("ORS"),出库单("OU");
	private String prefix;

	private SerialNum(String prefix) {
		this.prefix = prefix;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
}
