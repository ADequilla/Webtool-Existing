package com.valuequest.controller.maintenance.model;
import java.math.BigDecimal;

public class CommissionSetupModel {
	private Long id;
	private String transType;
	private String commissionType;
	private BigDecimal customerIncome;
	private BigDecimal agentIncome;
	private BigDecimal bankIncome;
	private BigDecimal bankPartnerIncome;
	private Boolean isNew;

	public CommissionSetupModel() {
		super();
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public String getTransType() {
		return transType;
	}

	public void setTransType(String transType) {
		this.transType = transType;
	}

	public String getCommissionType() {
		return commissionType;
	}

	public void setCommissionType(String commissionType) {
		this.commissionType = commissionType;
	}

	public BigDecimal getCustomerIncome() {
		return customerIncome;
	}

	public void setCustomerIncome(BigDecimal customerIncome) {
		this.customerIncome = customerIncome;
	}
	
	public BigDecimal getAgentIncome() {
		return agentIncome;
	}
	
	public void setAgentIncome(BigDecimal agentIncome) {
		this.agentIncome = agentIncome;
	}

	public BigDecimal getBankIncome() {
		return bankIncome;
	}
	
	public void setBankIncome(BigDecimal bankIncome) {
		this.bankIncome = bankIncome;
	}
	
	public BigDecimal getBankPartnerIncome() {
		return bankPartnerIncome;
	}
	
	public void setBankPartnerIncome(BigDecimal bankPartnerIncome) {
		this.bankPartnerIncome = bankPartnerIncome;
	}

	public Boolean getIsNew() {
		return isNew;
	}

	public void setIsNew(Boolean isNew) {
		this.isNew = isNew;
	}

}
