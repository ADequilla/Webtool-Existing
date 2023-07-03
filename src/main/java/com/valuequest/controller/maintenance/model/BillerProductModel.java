package com.valuequest.controller.maintenance.model;

import java.math.BigDecimal;

public class BillerProductModel {
	private Long productCategoryId;
	private Long billerProductId;
	private String billerProductName;
	private String description;
	private BigDecimal bankCommission;
	private BigDecimal serviceFee;
	private Long status;
	private Boolean isNew;

	public BillerProductModel() {
		super();
	}

	public Long getProductCategoryId() {
		return productCategoryId;
	}

	public void setProductCategoryId(Long productCategoryId) {
		this.productCategoryId = productCategoryId;
	}

	public Long getBillerProductId() {
		return billerProductId;
	}

	public void setBillerProductId(Long billerProductId) {
		this.billerProductId = billerProductId;
	}

	public String getBillerProductName() {
		return billerProductName;
	}

	public void setBillerProductName(String billerProductName) {
		this.billerProductName = billerProductName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public BigDecimal getBankCommission() {
		return bankCommission;
	}

	public void setBankCommission(BigDecimal bankCommission) {
		this.bankCommission = bankCommission;
	}

	public BigDecimal getServiceFee() {
		return serviceFee;
	}

	public void setServiceFee(BigDecimal serviceFee) {
		this.serviceFee = serviceFee;
	}

	public Long getStatus() {
		return status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	public Boolean getIsNew() {
		return isNew;
	}

	public void setIsNew(Boolean isNew) {
		this.isNew = isNew;
	}

}
