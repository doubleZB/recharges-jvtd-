package com.jtd.recharge.define;

/**
 * Created by ${zyj} on 2018/4/16.
 * 卡信息中的 出库时间 入库时间  激活时间
 */
public enum CardTimeType {
    入库时间(1),激活时间(2),出库时间(3);
    private int value;
    private CardTimeType(int value) {
        this.value = value;
    }
    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
    public static CardTimeType parse(int inValue) {
        for(CardTimeType oper: CardTimeType.values()) {
            if(oper.getValue() == inValue){
                return oper;
            }
        }
        throw new IllegalArgumentException("非法的时间:"+inValue);
    }
}
