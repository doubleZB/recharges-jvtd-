package com.jtd.recharge.define;

/**
 * 卡尺寸
 * @author ninghui
 *
 */
public enum CardSize {
	 三切卡(0),双切卡(1);
	 private int value;
	 private CardSize(int value) {
		 this.value = value;
	 }
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}
	public static CardSize parse(int inValue) {
		for(CardSize oper:CardSize.values()) {
			if(oper.getValue() == inValue){
				return oper;
			}
		}
		throw new IllegalArgumentException("非法的卡尺寸:"+inValue);
	}
}
