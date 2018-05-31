package com.jtd.recharge.dao.po;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class IotSubOrder {
	private Integer id;

	private Integer parentId;

	private String serialNum;

	private BigDecimal priceDiscount;

	private BigDecimal price;

	private BigDecimal costDiscount;

	private BigDecimal cost;

	private BigDecimal amount;

	private Integer total;

	private Integer isRecharge;

	private Integer isSms;

	private Integer size;

	private Integer operator;

	private Integer status;

	private Integer flowProductId;

	private Integer createrId;
	
	private Integer userId;

	private String createTime;

	private String updateTime;
	/**
	 * 客户名称
	 */
	private String clientName;
	/**
	 * 下单人
	 */
	private String adminUserName;
	/**
	 * 产品名称
	 */
	private String productName;
	/**
	 * 卡尺寸
	 */
	private String cardSizeLiteral;
	/**
	 * 订单状态
	 */
	private String statusLiteral;
	/**
	 * 运营商
	 */
	private String operatorLiteral;
	/**
	 * 父订单编号
	 */
	private String parentSerialNum;
	/**
	 * 时间范围查询-开始时间
	 */
	private String createDate;
	/**
	 * 时间范围查询-结束时间
	 */
	private String endDate;
	/**
	 * 当前状态
	 */
	private Integer currentStatus;
	/**
	 * 原价
	 */
	private BigDecimal stdPrice;

	private List<Integer> statusList ;

	public List<Integer> getStatusList() {
		return statusList;
	}

	public void setStatusList(List<Integer> statusList) {
		this.statusList = statusList;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public String getSerialNum() {
		return serialNum;
	}

	public void setSerialNum(String serialNum) {
		this.serialNum = serialNum == null ? null : serialNum.trim();
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

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
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

	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	public Integer getOperator() {
		return operator;
	}

	public void setOperator(Integer operator) {
		this.operator = operator;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getFlowProductId() {
		return flowProductId;
	}

	public void setFlowProductId(Integer flowProductId) {
		this.flowProductId = flowProductId;
	}

	public Integer getCreaterId() {
		return createrId;
	}

	public void setCreaterId(Integer createrId) {
		this.createrId = createrId;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public String getAdminUserName() {
		return adminUserName;
	}

	public void setAdminUserName(String adminUserName) {
		this.adminUserName = adminUserName;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getCardSizeLiteral() {
		return cardSizeLiteral;
	}

	public void setCardSizeLiteral(String cardSizeLiteral) {
		this.cardSizeLiteral = cardSizeLiteral;
	}

	public String getStatusLiteral() {
		return statusLiteral;
	}

	public void setStatusLiteral(String statusLiteral) {
		this.statusLiteral = statusLiteral;
	}

	public String getParentSerialNum() {
		return parentSerialNum;
	}

	public void setParentSerialNum(String parentSerialNum) {
		this.parentSerialNum = parentSerialNum;
	}

	public String getOperatorLiteral() {
		return operatorLiteral;
	}

	public void setOperatorLiteral(String operatorLiteral) {
		this.operatorLiteral = operatorLiteral;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
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

	public Integer getCurrentStatus() {
		return currentStatus;
	}

	public void setCurrentStatus(Integer currentStatus) {
		this.currentStatus = currentStatus;
	}

	public BigDecimal getStdPrice() {
		return stdPrice;
	}

	public void setStdPrice(BigDecimal stdPrice) {
		this.stdPrice = stdPrice;
	}
}