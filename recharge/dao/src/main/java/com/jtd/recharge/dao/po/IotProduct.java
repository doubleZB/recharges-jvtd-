package com.jtd.recharge.dao.po;

import java.math.BigDecimal;
import java.util.Date;

public class IotProduct {
    private Integer id;

    private String name;

    private String code;

    private Integer operator;

    private Integer flowPackageSize;

    private Integer period;

    private Integer type;

    private BigDecimal price;

    private Date createTime;

    private Date updateTime;
    /**
     * 运营商名称
     */
    private String operatorLiteral;
    /**
     * 计费周期名称
     */
    private String periodLiteral;
    /**
     * 包类型
     */
    private String typeLiteral;


    public String getFlowPackageSizeLiteral() {
        return flowPackageSizeLiteral;
    }

    public void setFlowPackageSizeLiteral(String flowPackageSizeLiteral) {
        this.flowPackageSizeLiteral = flowPackageSizeLiteral;
    }

    private String flowPackageSizeLiteral;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public Integer getOperator() {
        return operator;
    }

    public void setOperator(Integer operator) {
        this.operator = operator;
    }

    public Integer getFlowPackageSize() {
        return flowPackageSize;
    }

    public void setFlowPackageSize(Integer flowPackageSize) {
        this.flowPackageSize = flowPackageSize;
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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

	public String getOperatorLiteral() {
		return operatorLiteral;
	}

	public void setOperatorLiteral(String operatorLiteral) {
		this.operatorLiteral = operatorLiteral;
	}

	public String getPeriodLiteral() {
		return periodLiteral;
	}

	public void setPeriodLiteral(String periodLiteral) {
		this.periodLiteral = periodLiteral;
	}

	public String getTypeLiteral() {
		return typeLiteral;
	}

	public void setTypeLiteral(String typeLiteral) {
		this.typeLiteral = typeLiteral;
	}
}