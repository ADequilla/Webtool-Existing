package com.valuequest.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.valuequest.entity.util.CustomJsonDatetimeSerializer;

@Entity
@Table(name = "mfs.t_trans_finance")
public class TransFinance implements Serializable {

	private static final long serialVersionUID = -4766966230697392291L;

	@Id
	@Column(name = "trans_id")
	private Long id;

	@Column(name = "trans_amount_approved")
	private Double amountApproved;

	@Column(name = "trans_amount")
	private Double transAmount;

	@Column(name = "trans_amount_fee")
	private Double transactionCharge;

	@Column(name = "agent_income")
	private Double agentIncome;

	@Column(name = "bank_income")
	private Double bankIncome;

	@Column(name = "cmit_fee")
	private Double cmitIncome;

	@Column(name = "fds_fee")
	private Double fdsIncome;

	@Column(name = "trans_status")
	private String status;

	@Column(name = "last_updated_date")
	@Temporal(TemporalType.DATE)
	private Date updatedDate;

	@Column(name = "last_updated_by")
	private Long updatedBy;

	@Column(name = "trans_date")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonSerialize(using = CustomJsonDatetimeSerializer.class)
	private Date transDate;

	@Column(name = "trans_post_date")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonSerialize(using = CustomJsonDatetimeSerializer.class)
	private Date transPostDate;

	@Column(name = "trans_type")
	private String transType;

	@Column(name = "trans_mobile_refno")
	private String transMobileRefNo;

	@Column(name = "trans_core_refno")
	private String transCoreRefNo;

	@ManyToOne
	@JoinColumn(name = "client_id")
	private Client client;

	@Column(name = "ref_branch")
	private String refBranch;

	@Column(name = "trg_acct_no")
	private String targetAccount;

	@Column(name = "ref_name")
	private String targetName;

	@ManyToOne
	@JoinColumn(name = "src_acct_no", referencedColumnName = "account_number")
	private AccountBranch account;

	@Column(name = "cust_cid")
	private String custCid;

	public Double getTransAmount() {
		return transAmount;
	}

	public void setTransAmount(Double transAmount) {
		this.transAmount = transAmount;
	}

	public Date getTransPostDate() {
		return transPostDate;
	}

	public void setTransPostDate(Date transPostDate) {
		this.transPostDate = transPostDate;
	}

	public Double getBankIncome() {
		return bankIncome;
	}

	public void setBankIncome(Double bankIncome) {
		this.bankIncome = bankIncome;
	}

	public Double getCmitIncome() {
		return cmitIncome;
	}

	public void setCmitIncome(Double cmitIncome) {
		this.cmitIncome = cmitIncome;
	}

	public Double getFdsIncome() {
		return fdsIncome;
	}

	public void setFdsIncome(Double fdsIncome) {
		this.fdsIncome = fdsIncome;
	}

	public Double getAgentIncome() {
		return agentIncome;
	}

	public void setAgentIncome(Double agentIncome) {
		this.agentIncome = agentIncome;
	}

	public Double getTransactionCharge() {
		return transactionCharge;
	}

	public void setTransactionCharge(Double transactionCharge) {
		this.transactionCharge = transactionCharge;
	}

	public String getTransCoreRefNo() {
		return transCoreRefNo;
	}

	public void setTransCoreRefNo(String transCoreRefNo) {
		this.transCoreRefNo = transCoreRefNo;
	}

	public String getCustCid() {
		return custCid;
	}

	public void setCustCid(String custCid) {
		this.custCid = custCid;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Double getAmountApproved() {
		return amountApproved;
	}

	public void setAmountApproved(Double amountApproved) {
		this.amountApproved = amountApproved;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public Long getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(Long updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Date getTransDate() {
		return transDate;
	}

	public void setTransDate(Date transDate) {
		this.transDate = transDate;
	}

	public String getTransType() {
		return transType;
	}

	public void setTransType(String transType) {
		this.transType = transType;
	}

	public String getTransMobileRefNo() {
		return transMobileRefNo;
	}

	public void setTransMobileRefNo(String transMobileRefNo) {
		this.transMobileRefNo = transMobileRefNo;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public String getRefBranch() {
		return refBranch;
	}

	public void setRefBranch(String refBranch) {
		this.refBranch = refBranch;
	}

	public String getTargetAccount() {
		return targetAccount;
	}

	public void setTargetAccount(String targetAccount) {
		this.targetAccount = targetAccount;
	}

	public String getTargetName() {
		return targetName;
	}

	public void setTargetName(String targetName) {
		this.targetName = targetName;
	}

	public AccountBranch getAccount() {
		return account;
	}

	public void setAccountBranch(AccountBranch account) {
		this.account = account;
	}

}