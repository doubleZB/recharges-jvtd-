package com.jtd.recharge.dao.po;

import java.math.BigDecimal;

public class IotInReceipt {
    private Integer id;

    private String serialNum;

    private Integer purchaseId;

    private Integer createrId;

    private Integer total;
    
    private String errorDesc;
    
    private Integer inReceiptStatus;
   
    private BigDecimal costDiscount;
    
    private BigDecimal cost;

    private String createTime;

    private String updateTime;
    /**
     * 入库单状态显示
     */
    private String inReceiptStatusLiteral;
    /**
     * 采购单编号
     */
    private String purchaseSerialNum;
    /**
     * 入库人
     */
    private String createrName;
    /**
     * 流量产品名称
     */
    private String productName;
    /**
     * 卡尺寸
     */
    private Integer cardSize;
	/**
	 * 于采购单关联的子订单的id
	 */
	private Integer subOrderId;
    /**
     * 供应商
     */
    private String supplyName;
	/**
	 * 开始时间
	 */
	private String beginTime;
	/**
	 * 结束时间
	 */
	private String endTime;
	/**
	 * 运营商
	 */
	private Integer operator;
	/**
	 * 供应商id
	 */
	private Integer supplyId;
	/**
	 * 流量套餐id
	 */
	private Integer productId;
    /**
     * 物理大小
     */
	private String cardSizeLiteral;
	/**
	 * 当前入库单状态
	 */
	private Integer currentInReceiptStatus;
	
    public Integer getSubOrderId() {
		return subOrderId;
	}

	public void setSubOrderId(Integer subOrderId) {
		this.subOrderId = subOrderId;
	}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSerialNum() {
        return serialNum;
    }

    public void setSerialNum(String serialNum) {
        this.serialNum = serialNum == null ? null : serialNum.trim();
    }

    public Integer getPurchaseId() {
        return purchaseId;
    }

    public void setPurchaseId(Integer purchaseId) {
        this.purchaseId = purchaseId;
    }

    public Integer getCreaterId() {
        return createrId;
    }

    public void setCreaterId(Integer createrId) {
        this.createrId = createrId;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

	public String getPurchaseSerialNum() {
		return purchaseSerialNum;
	}

	public void setPurchaseSerialNum(String purchaseSerialNum) {
		this.purchaseSerialNum = purchaseSerialNum;
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

	public String getCreaterName() {
		return createrName;
	}

	public void setCreaterName(String createrName) {
		this.createrName = createrName;
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

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Integer getCardSize() {
		return cardSize;
	}

	public void setCardSize(Integer cardSize) {
		this.cardSize = cardSize;
	}

	public String getSupplyName() {
		return supplyName;
	}

	public void setSupplyName(String supplyName) {
		this.supplyName = supplyName;
	}

	public Integer getInReceiptStatus() {
		return inReceiptStatus;
	}

	public void setInReceiptStatus(Integer inReceiptStatus) {
		this.inReceiptStatus = inReceiptStatus;
	}

	public String getInReceiptStatusLiteral() {
		return inReceiptStatusLiteral;
	}

	public void setInReceiptStatusLiteral(String inReceiptStatusLiteral) {
		this.inReceiptStatusLiteral = inReceiptStatusLiteral;
	}

	public String getErrorDesc() {
		return errorDesc;
	}

	public void setErrorDesc(String errorDesc) {
		this.errorDesc = errorDesc;
	}

	public String getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public Integer getOperator() {
		return operator;
	}

	public void setOperator(Integer operator) {
		this.operator = operator;
	}

	public Integer getSupplyId() {
		return supplyId;
	}

	public void setSupplyId(Integer supplyId) {
		this.supplyId = supplyId;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public String getCardSizeLiteral() {
		return cardSizeLiteral;
	}

	public void setCardSizeLiteral(String cardSizeLiteral) {
		this.cardSizeLiteral = cardSizeLiteral;
	}

	public Integer getCurrentInReceiptStatus() {
		return currentInReceiptStatus;
	}

	public void setCurrentInReceiptStatus(Integer currentInReceiptStatus) {
		this.currentInReceiptStatus = currentInReceiptStatus;
	}
}