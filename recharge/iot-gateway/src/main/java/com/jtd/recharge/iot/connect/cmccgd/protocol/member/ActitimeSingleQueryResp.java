package com.jtd.recharge.iot.connect.cmccgd.protocol.member;

import com.jtd.recharge.iot.connect.cmccgd.protocol.CmccGdBaseResp;

public class ActitimeSingleQueryResp extends CmccGdBaseResp{
	private String msisdn;
	/**
	 * yyyyMMddHHmmss
	 */
	private String time;
	public String getMsisdn() {
		return msisdn;
	}
	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
}
