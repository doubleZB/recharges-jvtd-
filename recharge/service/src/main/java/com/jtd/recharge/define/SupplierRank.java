package com.jtd.recharge.define;

/**
 * 供应商等级
 * @author ninghui
 */
public enum SupplierRank {
	A(0),B(1),C(2);
    private int value;
    private SupplierRank(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
    public static SupplierRank parse(int inValue) {
		for(SupplierRank oper:SupplierRank.values()) {
			if(oper.getValue() == inValue){
				return oper;
			}
		}
		throw new IllegalArgumentException("非法的供应商等级:"+inValue);
	}
}
