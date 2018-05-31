package com.jtd.recharge.define;

/**
 * 出库单状态
 * 
 * @author ninghui
 *
 */
public enum OutReceiptStatus {
	待配货(0), 已完成(1), 已取消(2);
	private int value;
	private OutReceiptStatus(int value) {
		this.value = value;
	}
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}
	public static OutReceiptStatus parse(int inValue) {
		for(OutReceiptStatus oper:OutReceiptStatus.values()) {
			if(oper.getValue() == inValue){
				return oper;
			}
		}
		throw new IllegalArgumentException("非法的入库单状态:"+inValue);
	}
}
