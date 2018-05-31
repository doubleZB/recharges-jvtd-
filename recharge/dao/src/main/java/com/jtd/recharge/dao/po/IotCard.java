package com.jtd.recharge.dao.po;

import java.math.BigDecimal;
import java.util.Date;

public class IotCard {
    private Integer id;

    private String iccid;

    private String msisdn;

    private String imsi;

    private String imei;

    private Integer status;

    private Integer saleStatus;

    private Date activeTime;

    private Date outTime;

    private BigDecimal leftFlow;

    private BigDecimal totalFlow;

    private BigDecimal usedFlow;

    private Integer supplyId;

    private Integer customerId;

    private Integer isRecharge;

    private Integer isSms;

    private Integer operator;

    private Integer cardSize;

    private BigDecimal costDiscount;

    private BigDecimal cost;

    private BigDecimal priceDiscount;

    private BigDecimal price;

    private Integer flowProductId;

    private Integer purchaseId;

    private Integer inReceiptId;

    private Integer outReceiptId;

    private String createTime;

    private String updateTime;
    /**
     * 运营商显示
     */
    private String operatorLiteral;
    /**
     * 卡状态显示
     */
    private String statusLiteral;
    /**
     * 销售状态显示
     */
    private String saleStatusLiteral;
    /**
     * 卡尺寸显示
     */
    private String cardSizeLiteral;
    /**
     * 流量产品名称
     */
    private String productName;
    /**
     * 套餐周期
     */
    private Integer period;
    /**
     * 套餐类型
     */
    private Integer type;
    /**
     * 标准价
     */
    private BigDecimal standardPrice;
    /**
     * 采购单编号
     */
    private String purchaseSerialNum;
    /**
     * 入库单编号
     */
    private String inReceiptSerialNum;
    /**
     * 出库单编号
     */
    private String outReceiptSerialNum;
    /**
     * 供应商名称
     */
    private String supplyName;
    /**
     * 更新数量
     */
    private Integer updateLimit;
    /**
     * 售价折扣放入查询条件
     */
    private BigDecimal limitPriceDiscount;
    /**
     * 售价放入查询条件
     */
    private BigDecimal limitPrice;
    /**
     * 采购价
     */
    private BigDecimal purchasePrice;
    /**
     * 采购价折扣
     */
    private BigDecimal purchaseCost;
    /**
     * 库存数量
     */
    private Integer total;
    /**
     * 订单编号
     */
    private String orderSerialNum;
    /**
     * 子订单编号
     */
    private String subOrderSerialNum;

    /**
     * 客户名称
     */
    private String userName;

    /**
     * 时间范围查询-开始时间
     */
    private String createDate;
    /**
     * 时间范围查询-结束时间
     */
    private String endDate;

    private Integer cardTimeType;

    public Integer getCardTimeType() {
        return cardTimeType;
    }

    public void setCardTimeType(Integer cardTimeType) {
        this.cardTimeType = cardTimeType;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIccid() {
        return iccid;
    }

    public void setIccid(String iccid) {
        this.iccid = iccid == null ? null : iccid.trim();
    }

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn == null ? null : msisdn.trim();
    }

    public String getImsi() {
        return imsi;
    }

    public void setImsi(String imsi) {
        this.imsi = imsi == null ? null : imsi.trim();
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei == null ? null : imei.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getSaleStatus() {
        return saleStatus;
    }

    public void setSaleStatus(Integer saleStatus) {
        this.saleStatus = saleStatus;
    }

    public Date getActiveTime() {
        return activeTime;
    }

    public void setActiveTime(Date activeTime) {
        this.activeTime = activeTime;
    }

    public Date getOutTime() {
        return outTime;
    }

    public void setOutTime(Date outTime) {
        this.outTime = outTime;
    }

    public BigDecimal getLeftFlow() {
        return leftFlow;
    }

    public void setLeftFlow(BigDecimal leftFlow) {
        this.leftFlow = leftFlow;
    }

    public BigDecimal getTotalFlow() {
        return totalFlow;
    }

    public void setTotalFlow(BigDecimal totalFlow) {
        this.totalFlow = totalFlow;
    }

    public BigDecimal getUsedFlow() {
        return usedFlow;
    }

    public void setUsedFlow(BigDecimal usedFlow) {
        this.usedFlow = usedFlow;
    }

    public Integer getSupplyId() {
        return supplyId;
    }

    public void setSupplyId(Integer supplyId) {
        this.supplyId = supplyId;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public Integer getIsRecharge() {
        return isRecharge;
    }

    public void setIsRecharge(Integer isRecharge) {
        this.isRecharge = isRecharge;
    }

    public Integer getIsSms() {
        return isSms;
    }

    public void setIsSms(Integer isSms) {
        this.isSms = isSms;
    }

    public Integer getOperator() {
        return operator;
    }

    public void setOperator(Integer operator) {
        this.operator = operator;
    }

    public Integer getCardSize() {
        return cardSize;
    }

    public void setCardSize(Integer cardSize) {
        this.cardSize = cardSize;
    }

    public Integer getFlowProductId() {
        return flowProductId;
    }

    public void setFlowProductId(Integer flowProductId) {
        this.flowProductId = flowProductId;
    }

    public Integer getPurchaseId() {
        return purchaseId;
    }

    public void setPurchaseId(Integer purchaseId) {
        this.purchaseId = purchaseId;
    }

    public Integer getInReceiptId() {
        return inReceiptId;
    }

    public void setInReceiptId(Integer inReceiptId) {
        this.inReceiptId = inReceiptId;
    }

    public Integer getOutReceiptId() {
        return outReceiptId;
    }

    public void setOutReceiptId(Integer outReceiptId) {
        this.outReceiptId = outReceiptId;
    }

    public String getStatusLiteral() {
        return statusLiteral;
    }

    public void setStatusLiteral(String statusLiteral) {
        this.statusLiteral = statusLiteral;
    }

    public String getCardSizeLiteral() {
        return cardSizeLiteral;
    }

    public void setCardSizeLiteral(String cardSizeLiteral) {
        this.cardSizeLiteral = cardSizeLiteral;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getPurchaseSerialNum() {
        return purchaseSerialNum;
    }

    public void setPurchaseSerialNum(String purchaseSerialNum) {
        this.purchaseSerialNum = purchaseSerialNum;
    }

    public String getInReceiptSerialNum() {
        return inReceiptSerialNum;
    }

    public void setInReceiptSerialNum(String inReceiptSerialNum) {
        this.inReceiptSerialNum = inReceiptSerialNum;
    }

    public String getOutReceiptSerialNum() {
        return outReceiptSerialNum;
    }

    public void setOutReceiptSerialNum(String outReceiptSerialNum) {
        this.outReceiptSerialNum = outReceiptSerialNum;
    }

    public Integer getPeriod() {
        return period;
    }

    public void setPeriod(Integer period) {
        this.period = period;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public BigDecimal getStandardPrice() {
        return standardPrice;
    }

    public void setStandardPrice(BigDecimal standardPrice) {
        this.standardPrice = standardPrice;
    }

    public BigDecimal getCostDiscount() {
        return costDiscount;
    }

    public void setCostDiscount(BigDecimal costDiscount) {
        this.costDiscount = costDiscount;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public BigDecimal getPriceDiscount() {
        return priceDiscount;
    }

    public void setPriceDiscount(BigDecimal priceDiscount) {
        this.priceDiscount = priceDiscount;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getOperatorLiteral() {
        return operatorLiteral;
    }

    public void setOperatorLiteral(String operatorLiteral) {
        this.operatorLiteral = operatorLiteral;
    }

    public String getSupplyName() {
        return supplyName;
    }

    public void setSupplyName(String supplyName) {
        this.supplyName = supplyName;
    }

    public String getSaleStatusLiteral() {
        return saleStatusLiteral;
    }

    public void setSaleStatusLiteral(String saleStatusLiteral) {
        this.saleStatusLiteral = saleStatusLiteral;
    }

    public Integer getUpdateLimit() {
        return updateLimit;
    }

    public void setUpdateLimit(Integer updateLimit) {
        this.updateLimit = updateLimit;
    }

    public BigDecimal getLimitPriceDiscount() {
        return limitPriceDiscount;
    }

    public void setLimitPriceDiscount(BigDecimal limitPriceDiscount) {
        this.limitPriceDiscount = limitPriceDiscount;
    }

    public BigDecimal getLimitPrice() {
        return limitPrice;
    }

    public void setLimitPrice(BigDecimal limitPrice) {
        this.limitPrice = limitPrice;
    }

    public BigDecimal getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(BigDecimal purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public BigDecimal getPurchaseCost() {
        return purchaseCost;
    }

    public void setPurchaseCost(BigDecimal purchaseCost) {
        this.purchaseCost = purchaseCost;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public String getOrderSerialNum() {
        return orderSerialNum;
    }

    public void setOrderSerialNum(String orderSerialNum) {
        this.orderSerialNum = orderSerialNum;
    }

    public String getSubOrderSerialNum() {
        return subOrderSerialNum;
    }

    public void setSubOrderSerialNum(String subOrderSerialNum) {
        this.subOrderSerialNum = subOrderSerialNum;
    }
}