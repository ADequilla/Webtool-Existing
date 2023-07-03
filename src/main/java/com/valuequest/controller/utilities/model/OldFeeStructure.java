package com.valuequest.controller.utilities.model;

import java.util.Date;

import com.valuequest.entity.FeeStructure;

public class OldFeeStructure extends FeeStructure {

    private Long id;
	private String clientType;
	private String transType;
	private Long startRange;
	private Long endRange;
	private String startEndRange;
	private Double totalCharge;
	private Double agentIncome;
	private Double fdsFee;
	private Double cmitFee;
	private Double bankIncome;
	private boolean bankIncomeFlag;
	private Double telcoFee;
	private Double agentTargetIncome;
	private Double bancnetIncome;
	private Date createdDate;
	private Long createdBy;
	private Date lastUpdatedDate;
	private Long lastUpdatedBy;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getClientType() {
        return clientType;
    }
    public void setClientType(String clientType) {
        this.clientType = clientType;
    }
    public String getTransType() {
        return transType;
    }
    public void setTransType(String transType) {
        this.transType = transType;
    }
    public Long getStartRange() {
        return startRange;
    }
    public void setStartRange(Long startRange) {
        this.startRange = startRange;
    }
    public Long getEndRange() {
        return endRange;
    }
    public void setEndRange(Long endRange) {
        this.endRange = endRange;
    }
    public String getStartEndRange() {
        return startEndRange;
    }
    public void setStartEndRange(String startEndRange) {
        this.startEndRange = startEndRange;
    }
    public Double getTotalCharge() {
        return totalCharge;
    }
    public void setTotalCharge(Double totalCharge) {
        this.totalCharge = totalCharge;
    }
    public Double getAgentIncome() {
        return agentIncome;
    }
    public void setAgentIncome(Double agentIncome) {
        this.agentIncome = agentIncome;
    }
    public Double getFdsFee() {
        return fdsFee;
    }
    public void setFdsFee(Double fdsFee) {
        this.fdsFee = fdsFee;
    }
    public Double getCmitFee() {
        return cmitFee;
    }
    public void setCmitFee(Double cmitFee) {
        this.cmitFee = cmitFee;
    }
    public Double getBankIncome() {
        return bankIncome;
    }
    public void setBankIncome(Double bankIncome) {
        this.bankIncome = bankIncome;
    }
    public boolean isBankIncomeFlag() {
        return bankIncomeFlag;
    }
    public void setBankIncomeFlag(boolean bankIncomeFlag) {
        this.bankIncomeFlag = bankIncomeFlag;
    }
    public Double getTelcoFee() {
        return telcoFee;
    }
    public void setTelcoFee(Double telcoFee) {
        this.telcoFee = telcoFee;
    }
    public Double getAgentTargetIncome() {
        return agentTargetIncome;
    }
    public void setAgentTargetIncome(Double agentTargetIncome) {
        this.agentTargetIncome = agentTargetIncome;
    }
    public Double getBancnetIncome() {
        return bancnetIncome;
    }
    public void setBancnetIncome(Double bancnetIncome) {
        this.bancnetIncome = bancnetIncome;
    }
    public Date getCreatedDate() {
        return createdDate;
    }
    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }
    public Long getCreatedBy() {
        return createdBy;
    }
    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }
    public Date getLastUpdatedDate() {
        return lastUpdatedDate;
    }
    public void setLastUpdatedDate(Date lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }
    public Long getLastUpdatedBy() {
        return lastUpdatedBy;
    }
    public void setLastUpdatedBy(Long lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    
}
