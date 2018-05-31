package com.jtd.recharge.base.iot.connect.protocol;
/**
 * 卡激活时间
 * @author ninghui
 *
 */
public class DateActivatedResp extends BaseResp{
	/**
	 * yyyy-MM-dd HH:mm:ss
	 */
	private String dateActivated;

	public String getDateActivated() {
		return dateActivated;
	}

	public void setDateActivated(String dateActivated) {
		this.dateActivated = dateActivated;
	}
}
