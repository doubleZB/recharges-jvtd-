package com.jtd.recharge.dao.bean;

/**
 * @autor jipengkun
 */
public class CallbackReport {

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 充值状态
     * 7 充值成功
     * 8 充值失败
     */
    private String status;

    /**
     * 商户流水号
     */
    private String customId;

    /**
     * 商户token
     */
    private String token;

    /**
     * 回调地址
     */
    private String callbackUrl;

    /**
     * 订单号
     */
    private String orderNum;

    /**
     * 推送次数
     */
    private Integer pushSum;

    public static class pushSumStatus{
        public static int STATUS_ONE = 1;
        public static int STATUS_TWO = 2;
        public static int STATUS_THREE = 3;
    }

    public static String CALLBACK_SUCCESS = "SUCCESS";

    public Integer getPushSum() {
        return pushSum;
    }

    public void setPushSum(Integer pushSum) {
        this.pushSum = pushSum;
    }

    public String getCallbackUrl() {
        return callbackUrl;
    }

    public void setCallbackUrl(String callbackUrl) {
        this.callbackUrl = callbackUrl;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
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

    public String getCustomId() {
        return customId;
    }

    public void setCustomId(String customId) {
        this.customId = customId;
    }
}
