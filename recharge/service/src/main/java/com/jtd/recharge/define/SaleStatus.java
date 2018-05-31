package com.jtd.recharge.define;

/**
 * 卡销售状态
 * @author ninghui
 */
public enum SaleStatus {
	待售(0),已售(1);
    private int value;
    private SaleStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
    public static SaleStatus parse(int inValue) {
		for(SaleStatus oper:SaleStatus.values()) {
			if(oper.getValue() == inValue){
				return oper;
			}
		}
		throw new IllegalArgumentException("非法的销售状态:"+inValue);
	}
}
