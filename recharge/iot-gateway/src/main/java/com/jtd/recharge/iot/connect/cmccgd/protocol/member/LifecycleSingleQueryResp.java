package com.jtd.recharge.iot.connect.cmccgd.protocol.member;

import com.jtd.recharge.iot.connect.cmccgd.protocol.CmccGdBaseResp;
public class LifecycleSingleQueryResp extends CmccGdBaseResp{
	private String status;
	private String statusTime;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatusTime() {
		return statusTime;
	}

	public void setStatusTime(String statusTime) {
		this.statusTime = statusTime;
	}
}
