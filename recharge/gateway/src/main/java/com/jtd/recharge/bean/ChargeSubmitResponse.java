package com.jtd.recharge.bean;

/**
 * @autor jipengkun
 * 供应商接口响应对象
 */
public class ChargeSubmitResponse {

    /**
     * 状态 1 成功 0失败
     */
    private String status;

    /**
     * 状态码
     */
    private String statusCode;

    /**
     * 消息描述
     */
    private String statusMsg;

    /**
     * 平台流水号
     */
    private String channelNum;

    /**
     * 供应商流水
     */
    private String supplyChannelNum;

    /**
     * 供应商订单号
     */
    private String supplyOrderNum;

    public static class Status {
        public static String SUCCESS = "1";//提交成功
        public static String FAIL = "2";//提交失败
        public static String UNKNOWN = "3";//提交异常
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public String getChannelNum() {
        return channelNum;
    }

    public void setChannelNum(String channelNum) {
        this.channelNum = channelNum;
    }

    public String getSupplyChannelNum() {
        return supplyChannelNum;
    }

    public void setSupplyChannelNum(String supplyChannelNum) {
        this.supplyChannelNum = supplyChannelNum;
    }

    public String getSupplyOrderNum() {
        return supplyOrderNum;
    }

    public void setSupplyOrderNum(String supplyOrderNum) {
        this.supplyOrderNum = supplyOrderNum;
    }
}
