package com.jtd.recharge.define;

/**
 * 入库单状态
 * 
 * @author ninghui
 *
 */
public enum InReceiptStatus {
	待入库(0), 入库中(1), 已完成(2), 失败(3), 未完成(4);
	private int value;
	private InReceiptStatus(int value) {
		this.value = value;
	}
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}
	public static InReceiptStatus parse(int inValue) {
		for(InReceiptStatus oper:InReceiptStatus.values()) {
			if(oper.getValue() == inValue){
				return oper;
			}
		}
		throw new IllegalArgumentException("非法的入库单状态:"+inValue);
	}
}
