package com.jtd.recharge.base.iot.connect.protocol;
/**
 * 使用流量查询
 * @author ninghui
 *
 */
public class UsagesResp extends BaseResp{
	/**
	 * 已使用流量
	 */
	private String usedFlow;
	/**
	 * 剩余流量
	 */
	private String restFlow;
	/**
	 * 总流量
	 */
	private String totalFlow;
	public String getUsedFlow() {
		return usedFlow;
	}
	public void setUsedFlow(String usedFlow) {
		this.usedFlow = usedFlow;
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
	
	
}
