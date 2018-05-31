package com.jtd.recharge.dao.bean;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by 订单相关的字段
 * lihuimin
 * on 2016/11/22.
 */
public class Order {

    //商户id集合
    private List<Integer> userMarketOrderId;

    //设置订单详情表
    private String order_detail;
    //订单表
    private String order;

    private Integer id;

    private String val1;

    private Integer businessType;

    private String orderNum;

    private Integer provinceId;

    private Integer pushStatus;

    private String table;

    private String dateStr;

    private String userAllName;
    private String userCnName;

    private Integer userId;

    private String rechargeMobile  ;

    private Integer operator;

    private Integer productId;

    private BigDecimal amount;


    private Integer source;

    private String orderTime;
    private String orderReturnTime;
    /**
     * 交易额
     */
    private String money;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date orderTimeOne;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date orderTimeTwo;

    private Integer status;
    private Integer refundStatus;

    public static class artificialRefundStatus {
        public static int  YES = 1;//是
        public static int   NONE= 2;//否
    }

    private Integer artificialRefund;
    private String callbackUrl;
    /**
     * 档位编码
     */
    private String positionCode;
    private List<Integer> positionCodes;

    private String minute;

    private String customId;
    /**
     * 字典value
     */
    private String value;
    /**
     * 流量包大小
     */
    private String packageSize;
    /**
     * 供应商
     */
    private String supplyName;

    /**
     * 订单详情
     */

    private String channelNum;

    private String mobile;

    private String token;

    private Integer supplyId;

    private String submitTime;

    private String returnTime;

    private String submitRspcode;

    private String returnRspcode;

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

    public List<Integer> getUserMarketOrderId() {
        return userMarketOrderId;
    }

    public void setUserMarketOrderId(List<Integer> userMarketOrderId) {
        this.userMarketOrderId = userMarketOrderId;
    }

    public List<Integer> getPositionCodes() {
        return positionCodes;
    }

    public void setPositionCodes(List<Integer> positionCodes) {
        this.positionCodes = positionCodes;
    }

    public String getOrderReturnTime() {
        return orderReturnTime;
    }

    public void setOrderReturnTime(String orderReturnTime) {
        this.orderReturnTime = orderReturnTime;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getMinute() {
        return minute;
    }

    public void setMinute(String minute) {
        this.minute = minute;
    }

    public String getOrder_detail() {
        return order_detail;
    }

    public void setOrder_detail(String order_detail) {
        this.order_detail = order_detail;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getVal1() {
        return val1;
    }

    public void setVal1(String val1) {
        this.val1 = val1;
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

    public Integer getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(Integer provinceId) {
        this.provinceId = provinceId;
    }


    public Integer getPushStatus() {
        return pushStatus;
    }

    public void setPushStatus(Integer pushStatus) {
        this.pushStatus = pushStatus;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public String getDateStr() {
        return dateStr;
    }

    public void setDateStr(String dateStr) {
        this.dateStr = dateStr;
    }

    public String getUserAllName() {
        return userAllName;
    }

    public void setUserAllName(String userAllName) {
        this.userAllName = userAllName;
    }

    public String getUserCnName() {
        return userCnName;
    }

    public void setUserCnName(String userCnName) {
        this.userCnName = userCnName;
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
        this.rechargeMobile = rechargeMobile;
    }

    public Integer getOperator() {
        return operator;
    }

    public void setOperator(Integer operator) {
        this.operator = operator;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Integer getSource() {
        return source;
    }

    public void setSource(Integer source) {
        this.source = source;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public Date getOrderTimeOne() {
        return orderTimeOne;
    }

    public void setOrderTimeOne(Date orderTimeOne) {
        this.orderTimeOne = orderTimeOne;
    }

    public Date getOrderTimeTwo() {
        return orderTimeTwo;
    }

    public void setOrderTimeTwo(Date orderTimeTwo) {
        this.orderTimeTwo = orderTimeTwo;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getRefundStatus() {
        return refundStatus;
    }

    public void setRefundStatus(Integer refundStatus) {
        this.refundStatus = refundStatus;
    }

    public String getCallbackUrl() {
        return callbackUrl;
    }

    public void setCallbackUrl(String callbackUrl) {
        this.callbackUrl = callbackUrl;
    }

    public String getPositionCode() {
        return positionCode;
    }

    public void setPositionCode(String positionCode) {
        this.positionCode = positionCode;
    }

    public String getCustomId() {
        return customId;
    }

    public void setCustomId(String customId) {
        this.customId = customId;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getPackageSize() {
        return packageSize;
    }

    public void setPackageSize(String packageSize) {
        this.packageSize = packageSize;
    }

    public String getSupplyName() {
        return supplyName;
    }

    public void setSupplyName(String supplyName) {
        this.supplyName = supplyName;
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

    public Integer getSupplyId() {
        return supplyId;
    }

    public void setSupplyId(Integer supplyId) {
        this.supplyId = supplyId;
    }

    public String getSubmitTime() {
        return submitTime;
    }

    public void setSubmitTime(String submitTime) {
        this.submitTime = submitTime;
    }

    public String getReturnTime() {
        return returnTime;
    }

    public void setReturnTime(String returnTime) {
        this.returnTime = returnTime;
    }

    public String getSubmitRspcode() {
        return submitRspcode;
    }

    public void setSubmitRspcode(String submitRspcode) {
        this.submitRspcode = submitRspcode;
    }

    public String getReturnRspcode() {
        return returnRspcode;
    }

    public void setReturnRspcode(String returnRspcode) {
        this.returnRspcode = returnRspcode;
    }

    public Integer getArtificialRefund() {
        return artificialRefund;
    }

    public void setArtificialRefund(Integer artificialRefund) {
        this.artificialRefund = artificialRefund;
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
