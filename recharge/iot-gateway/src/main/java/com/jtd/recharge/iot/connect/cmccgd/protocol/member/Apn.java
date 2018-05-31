package com.jtd.recharge.iot.connect.cmccgd.protocol.member;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class Apn {
	private String apnName;
	private String extraPkgFlow;
	private String lastFlowTime;
	private PkgInfoListWrapper pkgInfoList;
	private String restFlow;
	private String usedFlow;
	private String totalFlow;

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	public String getApnName() {
		return apnName;
	}

	public void setApnName(String apnName) {
		this.apnName = apnName;
	}

	public String getExtraPkgFlow() {
		return extraPkgFlow;
	}

	public void setExtraPkgFlow(String extraPkgFlow) {
		this.extraPkgFlow = extraPkgFlow;
	}

	public String getLastFlowTime() {
		return lastFlowTime;
	}

	public void setLastFlowTime(String lastFlowTime) {
		this.lastFlowTime = lastFlowTime;
	}

	public PkgInfoListWrapper getPkgInfoList() {
		return pkgInfoList;
	}

	public void setPkgInfoList(PkgInfoListWrapper pkgInfoList) {
		this.pkgInfoList = pkgInfoList;
	}

	public String getRestFlow() {
		return restFlow;
	}

	public void setRestFlow(String restFlow) {
		this.restFlow = restFlow;
	}

	public String getUsedFlow() {
		return usedFlow;
	}

	public void setUsedFlow(String usedFlow) {
		this.usedFlow = usedFlow;
	}

	public String getTotalFlow() {
		return totalFlow;
	}

	public void setTotalFlow(String totalFlow) {
		this.totalFlow = totalFlow;
	}
}
