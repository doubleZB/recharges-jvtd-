package com.jtd.recharge.dao.po;

import java.math.BigDecimal;

public class IotPurchase {
    private Integer id;

    private String serialNum;

    private BigDecimal costDiscount;

    private BigDecimal cost;

    private BigDecimal amount;

    private Integer total;

    private Integer isRecharge;

    private Integer isSms;

    private Integer cardSize;

    private Integer purchaseStatus;

    private Integer flowProductId;

    private Integer subOrderId;

    private Integer supplyId;
    
    private String remark;

    private Integer createrId;

    private String createTime;

    private String updateTime;
    /**
     * 创建人名称
     */
    private String createrName;
    /**
     * 供应商名称
     */
    private String supplyName;
    /**
     * 采购单状态显示
     */
    private String purchaseStatusLiteral;
    /**
     * 卡尺寸显示
     */
    private String cardSizeLiteral;
    /**
     * 流量产品名称
     */
    private String productName;
    /**
     * 关联的子订单编号
     */
    private String subOrderSerialNum;
    /**
     * 父订单编号
     */
    private String orderSerialNum;
    /**
     * 标准价
     */
    private BigDecimal stdPrice;
    /**
     * 当前状态
     */
    private Integer currentPurchaseStatus;

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

	public String getCreaterName() {
		return createrName;
	}

	public void setCreaterName(String createrName) {
		this.createrName = createrName;
	}

	public Integer getSupplyId() {
		return supplyId;
	}

	public void setSupplyId(Integer supplyId) {
		this.supplyId = supplyId;
	}

	public Integer getCardSize() {
		return cardSize;
	}

	public void setCardSize(Integer cardSize) {
		this.cardSize = cardSize;
	}

	public Integer getPurchaseStatus() {
		return purchaseStatus;
	}

	public void setPurchaseStatus(Integer purchaseStatus) {
		this.purchaseStatus = purchaseStatus;
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

	public String getPurchaseStatusLiteral() {
		return purchaseStatusLiteral;
	}

	public void setPurchaseStatusLiteral(String purchaseStatusLiteral) {
		this.purchaseStatusLiteral = purchaseStatusLiteral;
	}

	public String getSupplyName() {
		return supplyName;
	}

	public void setSupplyName(String supplyName) {
		this.supplyName = supplyName;
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

	public Integer getSubOrderId() {
		return subOrderId;
	}

	public void setSubOrderId(Integer subOrderId) {
		this.subOrderId = subOrderId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getSubOrderSerialNum() {
		return subOrderSerialNum;
	}

	public void setSubOrderSerialNum(String subOrderSerialNum) {
		this.subOrderSerialNum = subOrderSerialNum;
	}

	public String getOrderSerialNum() {
		return orderSerialNum;
	}

	public void setOrderSerialNum(String orderSerialNum) {
		this.orderSerialNum = orderSerialNum;
	}

	public BigDecimal getStdPrice() {
		return stdPrice;
	}

	public void setStdPrice(BigDecimal stdPrice) {
		this.stdPrice = stdPrice;
	}

	public Integer getCurrentPurchaseStatus() {
		return currentPurchaseStatus;
	}

	public void setCurrentPurchaseStatus(Integer currentPurchaseStatus) {
		this.currentPurchaseStatus = currentPurchaseStatus;
	}
}