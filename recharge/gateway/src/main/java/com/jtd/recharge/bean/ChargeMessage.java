package com.jtd.recharge.bean;

import java.util.List;

/**
 * @autor jipengkun
 * 流量发送消息
 */
public class ChargeMessage {

    /**
     * 供应商名称 英文
     */
    private List<ChargeRequest> supplyList;

    /**
     * 平台订单号
     */
    private String orderNum;

    /**
     * 业务类型
     */
    private int businessType;

    public int getBusinessType() {
        return businessType;
    }

    public void setBusinessType(int businessType) {
        this.businessType = businessType;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public List<ChargeRequest> getSupplyList() {
        return supplyList;
    }

    public void setSupplyList(List<ChargeRequest> supplyList) {
        this.supplyList = supplyList;
    }
}
