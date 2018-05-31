package com.jtd.recharge.iot.connect.cucc.protocol;

public class CuccUsagesResp {
	/**
	 * 自计费周期开始后使用的流量(以字节计)
	 */
	private int ctdDataUsage;
	/**
	 * 自计费周期开始后的移动台始发消息和移动台终止消息的计数
	 */
	private int ctdSMSUsage;
	/**
	 * 自计费周期开始后使用的通话秒数
	 */
	private int ctdVoiceUsage;
	
	public int getCtdDataUsage() {
		return ctdDataUsage;
	}
	public void setCtdDataUsage(int ctdDataUsage) {
		this.ctdDataUsage = ctdDataUsage;
	}
	public int getCtdSMSUsage() {
		return ctdSMSUsage;
	}
	public void setCtdSMSUsage(int ctdSMSUsage) {
		this.ctdSMSUsage = ctdSMSUsage;
	}
	public int getCtdVoiceUsage() {
		return ctdVoiceUsage;
	}
	public void setCtdVoiceUsage(int ctdVoiceUsage) {
		this.ctdVoiceUsage = ctdVoiceUsage;
	}
}
