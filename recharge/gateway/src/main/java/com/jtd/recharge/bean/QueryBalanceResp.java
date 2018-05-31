package com.jtd.recharge.bean;

import java.math.BigDecimal;

/**
 * @autor jipengkun
 */
public class QueryBalanceResp {

    /**
     * 状态 1查询成功 2查询无数据
     */
    private String status;

    /**
     * 描述信息
     */
    private String statusMsg;

    private BigDecimal userBalance;

    public static class QueryStatus {
        public static String QUERY_SUCCESS = "1";//查询成功
        public static String TOKEN_ERROR = "2";//token不正确
        public static String NO_PARAM = "3";//缺少必要参数
        public static String SYS_ERROR = "1099";//系统异常

    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public BigDecimal getUserBalance() {
        return userBalance;
    }

    public void setUserBalance(BigDecimal userBalance) {
        this.userBalance = userBalance;
    }

    public String getStatusMsg() {
        return statusMsg;
    }

    public void setStatusMsg(String statusMsg) {
        this.statusMsg = statusMsg;
    }
}
