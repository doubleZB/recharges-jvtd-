package com.jtd.recharge.dao.po;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class ChargeOrder {
    private Integer id;

    private Integer businessType;
    private String business;

    private String orderNum;

    private String orderThirdNum;

    private Integer userId;

    private String rechargeMobile;

    private Integer operator;
    private String operatorName;
    private String userCnName;

    private Integer provinceId;

    private BigDecimal amount;

    /**
     * 成本折扣
     */
    private BigDecimal orderCostDiscount;

    /**
     * 成本
     */
    private BigDecimal orderCost;

    /**
     * 售价折扣
     */
    private BigDecimal orderPriceDiscount;

    private String positionCode;
    private String packageSize;
    private String value;

    private Integer source;
    private String sourceName;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date orderTime;

    private Integer status;
    private String statusName;

    private Integer supplyId;

    private String callbackUrl;

    private String customid;

    private Integer pushStatus;
    private String pushStatusName;

    private String table;

    private String dateStr;

    private List<Integer> statusList;
    /**
     * 退款状态
     * 1 退款成功 2 退款失败
     */
    private Integer refundStatus;
    private String refundStatusName;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date orderTimeend;

    /**
     * 订单备注
     */
    private String remark;

    /**
     * 订单回执时间
     */
    private Date orderReturnTime;


    /**
     * 订单状态
     */
    public static class OrderStatus {
        public static int CREATE_ORDER = 1;//创建订单
        public static int NO_STORE = 2;//无可充货架
        public static int NO_CHANNEL = 3;//无可用通道
        public static int NO_BALANCE = 4;//余额不足
        public static int CACHING = 5;//缓存中
        public static int CHARGEING = 6;//充值中
        public static int CHARGE_SUCCESS = 7;//充值成功
        public static int CHARGE_FAIL = 8;//充值失败
        public static int PAY_ERROR = 9;//支付失败
        public static int CHARGEING_UNKNOWN = 10;//充值等待中
    }

    /**
     * 业务类型
     */
    public static class Business_Type {
        public static int   FLOW = 1;//流量
        public static int   TELEPHONE_CHARGE= 2;//话费
        public static int   VIDEO_CHARGE= 3;//视频会员
    }
    /**
     * 运营商
     */
    public static class OperatorStatus {
        public static int MOVE = 1;//移动
        public static int UNI_INFO = 2;//联通
        public static int TELECOM = 3;//电信
        public static int YOUKU = 4;//优酷
        public static int iQIYI = 5;//爱奇艺
        public static int TENCENT = 6;//腾讯
        public static int SOHU = 7;//搜狐
        public static int LETV = 8;//乐视
    }
    /**
     * 推送状态
     */
    public static class PushState {
        public static int PUSH_SUCCESS = 1;//推送成功
        public static int PUSH_FAIL = 2;//推送失败
        public static int UNPUSH = 3;//未推送
    }

    public static class RefundState {
        public static int Refund_SUCCESS = 1;//退款成功
        public static int Refund_FAIL = 2;//退款失败
    }

    /**
     * 订单来源
     */
    public static class UserSource {
        public static int  PORT = 1;//接口
        public static int  PAGE = 2;//页面
    }

    public Date getOrderReturnTime() {
        return orderReturnTime;
    }

    public void setOrderReturnTime(Date orderReturnTime) {
        this.orderReturnTime = orderReturnTime;
    }

    public String getUserCnName() {
        return userCnName;
    }

    public void setUserCnName(String userCnName) {
        this.userCnName = userCnName;
    }

    public String getPackageSize() {
        return packageSize;
    }

    public void setPackageSize(String packageSize) {
        this.packageSize = packageSize;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }


    public String getBusiness() {
        return business;
    }

    public void setBusiness(String business) {
        this.business = business;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getPushStatusName() {
        return pushStatusName;
    }

    public void setPushStatusName(String pushStatusName) {
        this.pushStatusName = pushStatusName;
    }

    public String getRefundStatusName() {
        return refundStatusName;
    }

    public void setRefundStatusName(String refundStatusName) {
        this.refundStatusName = refundStatusName;
    }

    public Integer getRefundStatus() {
        return refundStatus;
    }

    public void setRefundStatus(Integer refundStatus) {
        this.refundStatus = refundStatus;
    }

    public Integer getPushStatus() {
        return pushStatus;
    }

    public void setPushStatus(Integer pushStatus) {
        this.pushStatus = pushStatus;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getCustomid() {
        return customid;
    }

    public void setCustomid(String customid) {
        this.customid = customid;
    }

    public String getCallbackUrl() {
        return callbackUrl;
    }

    public void setCallbackUrl(String callbackUrl) {
        this.callbackUrl = callbackUrl;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getBusinessType() {
        return businessType;
    }

    public void setBusinessType(Integer businessType) {
        this.businessType = businessType;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getRechargeMobile() {
        return rechargeMobile;
    }

    public void setRechargeMobile(String rechargeMobile) {
        this.rechargeMobile = rechargeMobile == null ? null : rechargeMobile.trim();
    }

    public Integer getOperator() {
        return operator;
    }

    public void setOperator(Integer operator) {
        this.operator = operator;
    }

    public Integer getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(Integer provinceId) {
        this.provinceId = provinceId;
    }

    public String getPositionCode() {
        return positionCode;
    }

    public void setPositionCode(String positionCode) {
        this.positionCode = positionCode;
    }

    public Integer getSource() {
        return source;
    }

    public void setSource(Integer source) {
        this.source = source;
    }

    public Date getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getSupplyId() {
        return supplyId;
    }

    public void setSupplyId(Integer supplyId) {
        this.supplyId = supplyId;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public Date getOrderTimeend() {
        return orderTimeend;
    }

    public void setOrderTimeend(Date orderTimeend) {
        this.orderTimeend = orderTimeend;
    }

    public void setDateStr(String dateStr) {
        this.dateStr = dateStr;
    }

    public String getDateStr() {
        return dateStr;
    }

    public void setStatusList(List<Integer> statusList) {
        this.statusList = statusList;
    }

    public List<Integer> getStatusList() {
        return statusList;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getOrderThirdNum() {
        return orderThirdNum;
    }

    public void setOrderThirdNum(String orderThirdNum) {
        this.orderThirdNum = orderThirdNum;
    }

    public BigDecimal getOrderCostDiscount() {
        return orderCostDiscount;
    }

    public void setOrderCostDiscount(BigDecimal orderCostDiscount) {
        this.orderCostDiscount = orderCostDiscount;
    }

    public BigDecimal getOrderCost() {
        return orderCost;
    }

    public void setOrderCost(BigDecimal orderCost) {
        this.orderCost = orderCost;
    }

    public BigDecimal getOrderPriceDiscount() {
        return orderPriceDiscount;
    }

    public void setOrderPriceDiscount(BigDecimal orderPriceDiscount) {
        this.orderPriceDiscount = orderPriceDiscount;
    }
}