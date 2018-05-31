package com.jtd.recharge.bean;

/**
 * @autor jipengkun
 */
public class QueryResp {

    /**
     * 状态 1查询成功 2查询无数据
     */
    private String status;

    /**
     * 充值状态 6充值中 7充值成功 8充值失败
     */
    private String statusCode;

    /**
     * 描述信息
     */
    private String statusMsg;

    public static class QueryStatus {
        public static String QUERY_SUCCESS = "1";//查询成功
        public static String QUERY_NODATA = "2";//查询无数据
        public static String TOKEN_ERROR = "3";//token不正确
        public static String NO_PARAM = "4";//缺少必要参数
        public static String ORDERNUM_ERROR = "5";//订单号不合法
        public static String SYS_ERROR = "1099";//系统异常

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
}
