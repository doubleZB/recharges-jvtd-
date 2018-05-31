package com.jtd.recharge.iot.connect.cmccgd.protocol;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class CmccGdContextResp {
	private String code;
	private String error;
	private String data;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}
}
