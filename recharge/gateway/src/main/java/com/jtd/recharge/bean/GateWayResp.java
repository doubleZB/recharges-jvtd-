package com.jtd.recharge.bean;

/**
 * @autor jipengkun
 */
public class GateWayResp {


    /**
     * 状态码
     */
    public String statusCode;

    /**
     * 状态描述
     */
    public String statusMsg;
    /**
     * 第三方交易id
     */
    public String customId;

    public String orderNum;

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatusMsg() {
        return statusMsg;
    }

    public void setStatusMsg(String statusMsg) {
        this.statusMsg = statusMsg;
    }

    public String getCustomId() {
        return customId;
    }

    public void setCustomId(String customId) {
        this.customId = customId;
    }
}
