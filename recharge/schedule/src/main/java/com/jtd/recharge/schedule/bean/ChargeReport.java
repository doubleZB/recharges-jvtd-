package com.jtd.recharge.schedule.bean;

/**
 * @autor jipengkun
 */
public class ChargeReport {

    /**
     * 平台通道流水
     */
    public String channelNum;

    /**
     * 手机号
     */
    public String mobile;

    /**
     * 状态
     * 3 回执成功
     * 4 回执失败
     */
    public String status;

    /**
     * 回执消息
     */
    public String message;

    public static class ChargeReportStatus {
        public static String SUCCESS = "3";
        public static String FAIL = "4";

    }

    public String getChannelNum() {
        return channelNum;
    }

    public void setChannelNum(String channelNum) {
        this.channelNum = channelNum;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
