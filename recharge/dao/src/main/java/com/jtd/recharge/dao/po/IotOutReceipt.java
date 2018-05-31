package com.jtd.recharge.dao.po;

import java.math.BigDecimal;

public class IotOutReceipt {
    private Integer id;

    private String serialNum;

    private BigDecimal costDiscount;

    private BigDecimal cost;

    private BigDecimal priceDiscount;

    private BigDecimal price;

    private BigDecimal amount;

    private Integer total;

    private Integer subOrderId;
    
    private Integer outReceiptStatus;

    private Integer createrId;

    private String createTime;

    private String updateTime;
    /**
     * 子订单编号
     */
    private String subOrderSerialNum;
    /**
     * 订单编号
     */
    private String orderSerialNum;
    /**
     * 客户名称
     */
    private String customerName;
    /**
     * 出库人
     */
    private String createrName;
    /**
     * 出库单状态
     */
    private String outReceiptStatusLiteral;
    /**
     * 当前状态
     */
    private Integer currentOutReceiptStatus;
    /**
     * 流量套餐
     */
    private String productName;

    /**
     * 开始时间
     */
    private String beginTime;
    /**
     * 结束时间
     */
    private String endTime;
    /**
     * 卡尺寸
     */
    private Integer cardSize;
    /**
     * 套餐id
     */
    private Integer productId;
    /**
     * 运营商
     */
    private Integer operator;

    private String cardSizeLiteral;


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

    public Integer getCreaterId() {
        return createrId;
    }

    public void setCreaterId(Integer createrId) {
        this.createrId = createrId;
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

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCreaterName() {
		return createrName;
	}

	public void setCreaterName(String createrName) {
		this.createrName = createrName;
	}

	public Integer getSubOrderId() {
		return subOrderId;
	}

	public void setSubOrderId(Integer subOrderId) {
		this.subOrderId = subOrderId;
	}

	public Integer getOutReceiptStatus() {
		return outReceiptStatus;
	}

	public void setOutReceiptStatus(Integer outReceiptStatus) {
		this.outReceiptStatus = outReceiptStatus;
	}

	public String getOutReceiptStatusLiteral() {
		return outReceiptStatusLiteral;
	}

	public void setOutReceiptStatusLiteral(String outReceiptStatusLiteral) {
		this.outReceiptStatusLiteral = outReceiptStatusLiteral;
	}

	public Integer getCurrentOutReceiptStatus() {
		return currentOutReceiptStatus;
	}

	public void setCurrentOutReceiptStatus(Integer currentOutReceiptStatus) {
		this.currentOutReceiptStatus = currentOutReceiptStatus;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
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

    public Integer getCardSize() {
        return cardSize;
    }

    public void setCardSize(Integer cardSize) {
        this.cardSize = cardSize;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getOperator() {
        return operator;
    }

    public void setOperator(Integer operator) {
        this.operator = operator;
    }

    public String getCardSizeLiteral() {
        return cardSizeLiteral;
    }

    public void setCardSizeLiteral(String cardSizeLiteral) {
        this.cardSizeLiteral = cardSizeLiteral;
    }
}