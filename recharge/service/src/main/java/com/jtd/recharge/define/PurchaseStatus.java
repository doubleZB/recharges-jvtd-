package com.jtd.recharge.define;

/**
 * 采购单状态
 * 
 * @author ninghui
 *
 */
public enum PurchaseStatus {
	待入库(1), 已入库(2), 已取消(3), 待审核(4),已驳回(5);
	private int value;
	private PurchaseStatus(int value) {
		this.value = value;
	}
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}
	public static PurchaseStatus parse(int inValue) {
		for(PurchaseStatus oper:PurchaseStatus.values()) {
			if(oper.getValue() == inValue){
				return oper;
			}
		}
		throw new IllegalArgumentException("非法的采购单状态:"+inValue);
	}
}
