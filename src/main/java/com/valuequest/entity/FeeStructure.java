package com.valuequest.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.Formula;

@Entity
@Table(name="mfs.m_fee_structure")
public class FeeStructure implements Serializable {

	private static final long serialVersionUID = -4766966230697392291L;
	
	@Id
	@SequenceGenerator(name="sequence", sequenceName="mfs.seq_m_fee_structure", initialValue=1000, allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="sequence")
	@Column(name="fee_id")
	private Long id;
	
	@Column(name="client_type", length=15)
	private String clientType;
	@Column(name="trans_type", length=10)
	private String transType;
	@Column(name="start_range")
	private Long startRange;
	@Column(name="end_range")
	private Long endRange;
	@Formula(value = "start_range || ' - ' || end_range")
	private String startEndRange;
	@Column(name="total_charge")
	private Double totalCharge;
	@Column(name="agent_income")
	private Double agentIncome;
	@Column(name="fds_fee")
	private Double fdsFee;
	@Column(name="cmit_fee")
	private Double cmitFee;
	@Column(name="bank_income")
	private Double bankIncome;
	@Column(name="bank_income_flag")
	private boolean bankIncomeFlag;
	@Column(name="telco_fee")
	private Double telcoFee;
	@Column(name="agent_target_income")
	private Double agentTargetIncome;
	@Column(name="bancnet_income")
	private Double bancnetIncome;
	
	@Column(name="created_date")
	@Temporal(TemporalType.DATE)
	private Date createdDate;
	@Column(name="created_by")
	private Long createdBy;
	@Column(name="last_updated_date")
	@Temporal(TemporalType.DATE)
	private Date lastUpdatedDate;
	@Column(name="last_updated_by")
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
