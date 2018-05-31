package com.jtd.recharge.define;

/**
 * Created by Administrator on 2018/3/22.
 */
public enum IotOrderStatus {
    待审核(0),待出库(2),待加款(3),待处理(4),已完成(6),
    已取消(7),已驳回(8),待入库(9),待配货(10),已退款(11);
    private int value;
    private IotOrderStatus(int value){
        this.value=value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
    
    public static IotOrderStatus parse(int inValue) {
		for(IotOrderStatus oper:IotOrderStatus.values()) {
			if(oper.getValue() == inValue){
				return oper;
			}
		}
		throw new IllegalArgumentException("非法的订单状态:"+inValue);
	}
}
