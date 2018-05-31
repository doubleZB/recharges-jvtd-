package com.jtd.recharge.define;

/**
 * 流量包类型
 * Created by Administrator on 2018/3/21.
 */
public enum CardType {
    标准包(0,"s"),叠加包(1,"o");
    private int value;
    private String code;
    private CardType(int value,String code) {
        this.value = value;
        this.code = code;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
    public static CardType parse(int inValue) {
		for(CardType oper:CardType.values()) {
			if(oper.getValue() == inValue){
				return oper;
			}
		}
		throw new IllegalArgumentException("非法的包类型:"+inValue);
	}

	public String getCode() {
		return code;
	}
}
