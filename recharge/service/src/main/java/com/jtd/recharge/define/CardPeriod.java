package com.jtd.recharge.define;

/**
 * 流量周期
 * Created by Administrator on 2018/3/21.
 */
public enum CardPeriod {
    月包(0,"m"),季包(1,"q"),半年包(2,"h"),年包(3,"y");
    private int value;
    private String code;
    private String shortName;
    private CardPeriod(int value,String code) {
        this.value = value;
        this.code = code;
        this.shortName = this.name().replace(this.name(), "包");
    }

    public int getValue() {
        return value;
    }
    
    public String getCode() {
		return code;
	}
    
    public void setValue(int value) {
        this.value = value;
    }
    
    public static CardPeriod parse(int inValue) {
		for(CardPeriod oper:CardPeriod.values()) {
			if(oper.getValue() == inValue){
				return oper;
			}
		}
		throw new IllegalArgumentException("非法的流量周期:"+inValue);
	}

	public String getShortName() {
		return shortName;
	}

	
}
