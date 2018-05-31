package com.jtd.recharge.iot.connect.cmccgd.protocol.member;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class PkgInfo {
	private String pkgCode;
	private String pkgEfftDate;
	private String pkgExpireDate;
	private String pkgName;
	private String subSprodId;
	private String restFlow;
	private String totalFlow;
	private String usedFlow;

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	public String getPkgCode() {
		return pkgCode;
	}

	public void setPkgCode(String pkgCode) {
		this.pkgCode = pkgCode;
	}

	public String getPkgEfftDate() {
		return pkgEfftDate;
	}

	public void setPkgEfftDate(String pkgEfftDate) {
		this.pkgEfftDate = pkgEfftDate;
	}

	public String getPkgExpireDate() {
		return pkgExpireDate;
	}

	public void setPkgExpireDate(String pkgExpireDate) {
		this.pkgExpireDate = pkgExpireDate;
	}

	public String getPkgName() {
		return pkgName;
	}

	public void setPkgName(String pkgName) {
		this.pkgName = pkgName;
	}

	public String getSubSprodId() {
		return subSprodId;
	}

	public void setSubSprodId(String subSprodId) {
		this.subSprodId = subSprodId;
	}

	public String getRestFlow() {
		return restFlow;
	}

	public void setRestFlow(String restFlow) {
		this.restFlow = restFlow;
	}

	public String getTotalFlow() {
		return totalFlow;
	}

	public void setTotalFlow(String totalFlow) {
		this.totalFlow = totalFlow;
	}

	public String getUsedFlow() {
		return usedFlow;
	}

	public void setUsedFlow(String usedFlow) {
		this.usedFlow = usedFlow;
	}
}
