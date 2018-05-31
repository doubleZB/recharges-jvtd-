package com.jtd.recharge.base.iot.connect.protocol;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.jtd.recharge.base.iot.connect.StatusCode;


public class BaseResp {
    /**
     * 状态码
     */
    private int statusCode;

    /**
     * 消息描述
     */
    private String statusMsg;
    
    
    public void setStatus(StatusCode st) {
    	this.statusCode = st.getValue();
    	this.statusMsg = st.getDesc();
    }

	

	public String getStatusMsg() {
		return statusMsg;
	}

	public void setStatusMsg(String statusMsg) {
		this.statusMsg = statusMsg;
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}
}
