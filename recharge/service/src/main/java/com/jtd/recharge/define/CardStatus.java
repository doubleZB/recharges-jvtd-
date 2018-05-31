package com.jtd.recharge.define;

/**
 * 卡状态
 * @author ninghui
 *
 */
public enum CardStatus {
	未知(0),未激活(1),正常(2),停用(3),注销(4),测试(5);
	 private int value;
	 private CardStatus(int value) {
		 this.value = value;
	 }
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}
	public static CardStatus parse(int inValue) {
		for(CardStatus oper:CardStatus.values()) {
			if(oper.getValue() == inValue){
				return oper;
			}
		}
		throw new IllegalArgumentException("非法的卡状态:"+inValue);
	}
}
