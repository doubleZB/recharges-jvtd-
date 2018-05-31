package com.jtd.recharge.define;

/**
 * 运营商
 * @author ninghui
 *
 */
public enum CardOperator {
	 移动(1,"cmcc"),联通(2,"cucc"),电信(3,"ctcc");
	 private int value;
	 private String code;
	 private CardOperator(int value,String code) {
		 this.value = value;
		 this.code = code;
	 }
	public int getValue() {
		return value;
	}
	public String getCode() {
		return code;
	}
	public static CardOperator parse(int inValue) {
		for(CardOperator oper:CardOperator.values()) {
			if(oper.getValue() == inValue){
				return oper;
			}
		}
		throw new IllegalArgumentException("非法的运营商:"+inValue);
	}
}
