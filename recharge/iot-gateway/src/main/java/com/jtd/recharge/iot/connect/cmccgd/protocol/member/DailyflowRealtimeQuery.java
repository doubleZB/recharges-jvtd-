package com.jtd.recharge.iot.connect.cmccgd.protocol.member;

import java.util.List;

import com.jtd.recharge.iot.connect.cmccgd.protocol.CmccGdBaseResp;

public class DailyflowRealtimeQuery extends CmccGdBaseResp {
	private String msisdn;
	private List<Apn> apnList;

	public String getMsisdn() {
		return msisdn;
	}

	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}

	public List<Apn> getApnList() {
		return apnList;
	}

	public void setApnList(List<Apn> apnList) {
		this.apnList = apnList;
	}
}
