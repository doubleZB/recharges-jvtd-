package com.jtd.recharge.base.iot.connect.protocol;
/**
 * 
 * @author ninghui
 *
 */
public class DeviceResp extends BaseResp{
	private String iccid;
	private String imsi;
	private String msisdn;
	private String imei;
	/**
	 * 状态
	 */
	private String cardStatus;
	
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
	public String getCardStatus() {
		return cardStatus;
	}
	public void setCardStatus(String cardStatus) {
		this.cardStatus = cardStatus;
	}
	

}
