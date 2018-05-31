package com.jtd.recharge.iot.connect.cucc.protocol;

public class CuccDeviceResp {
	private String iccid;
	private String imsi;
	private String msisdn;
	private String imei;
	/**
	 * 状态
	 */
	private String status;
	/**
	 * 与设备关联的资费计划的名称
	 */
	private String ratePlan;
	/**
	 * 与设备关联的通信计划的名称
	 */
	private String communicationPlan;
	/**
	 * 首次激活设备的日期
	 */
	private String dateActivated;
	/**
	 * 上次更新设备信息的日期
	 */
	private String dateUpdated;
	/**
	 * 设备 SIM 卡从运营商库存转移到企业账户的日期
	 */
	private String dateShipped;
	/**
	 * 与设备关联的企业账户的 ID
	 */
	private String accountId;
	
	
	public String getIccid() {
		return iccid;
	}
	public void setIccid(String iccid) {
		this.iccid = iccid;
	}
	public String getImsi() {
		return imsi;
	}
	public void setImsi(String imsi) {
		this.imsi = imsi;
	}
	public String getMsisdn() {
		return msisdn;
	}
	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}
	public String getImei() {
		return imei;
	}
	public void setImei(String imei) {
		this.imei = imei;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getRatePlan() {
		return ratePlan;
	}
	public void setRatePlan(String ratePlan) {
		this.ratePlan = ratePlan;
	}
	public String getCommunicationPlan() {
		return communicationPlan;
	}
	public void setCommunicationPlan(String communicationPlan) {
		this.communicationPlan = communicationPlan;
	}
	public String getDateActivated() {
		return dateActivated;
	}
	public void setDateActivated(String dateActivated) {
		this.dateActivated = dateActivated;
	}
	public String getDateUpdated() {
		return dateUpdated;
	}
	public void setDateUpdated(String dateUpdated) {
		this.dateUpdated = dateUpdated;
	}
	public String getDateShipped() {
		return dateShipped;
	}
	public void setDateShipped(String dateShipped) {
		this.dateShipped = dateShipped;
	}
	public String getAccountId() {
		return accountId;
	}
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
}
