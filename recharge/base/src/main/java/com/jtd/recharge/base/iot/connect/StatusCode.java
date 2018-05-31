package com.jtd.recharge.base.iot.connect;

public enum StatusCode {
	SUCCESS(1000,"提交成功"), PARAM(1001,"缺少必要的参数"), 
	USER(1004,"用户不存在"),SIGN(1005,"sign验证不通过"),
	IP(1012,"非法ip") ,FAIL(1099,"系统异常");
	private int value;
	private String desc;

	private StatusCode(int value,String desc) {
		this.value = value;
		this.desc = desc;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public static StatusCode parse(int inValue) {
		for (StatusCode oper : StatusCode.values()) {
			if (oper.getValue() == inValue) {
				return oper;
			}
		}
		throw new IllegalArgumentException("非法的状态码:" + inValue);
	}

	public String getDesc() {
		return desc;
	}
}
