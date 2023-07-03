package com.valuequest.controller.maintenance.model;

import java.math.BigDecimal;

public class BillerPayModel {
	private Long providerId;
	private Long transactionId;
	private String billerCode;
	private String billerName;
	private String description;
	private Long status;
	private Boolean isNew;

	private BigDecimal totalRebates;
	private BigDecimal customerRebates;
	private BigDecimal bankRebates;

	public BillerPayModel() {
		super();
	}

	public Long getProviderId() {
		return providerId;
	}

	public void setProviderId(Long providerId) {
		this.providerId = providerId;
	}

	public Long getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(Long transactionId) {
		this.transactionId = transactionId;
	}

	public String getBillerCode() {
		return billerCode;
	}

	public void setBillerCode(String billerCode) {
		this.billerCode = billerCode;
	}

	public String getBillerName() {
		return billerName;
	}

	public void setBillerName(String billerName) {
		this.billerName = billerName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public BigDecimal getTotalRebates() {
		return totalRebates;
	}

	public void setTotalRebates(BigDecimal totalRebates) {
		this.totalRebates = totalRebates;
	}

	public BigDecimal getCustomerRebates() {
		return customerRebates;
	}

	public void setCustomerRebates(BigDecimal customerRebates) {
		this.customerRebates = customerRebates;
	}

	public BigDecimal getBankRebates() {
		return bankRebates;
	}

	public void setBankRebates(BigDecimal bankRebates) {
		this.bankRebates = bankRebates;
	}

}
