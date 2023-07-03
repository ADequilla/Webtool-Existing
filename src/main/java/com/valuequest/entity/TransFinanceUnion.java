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
@Table(name = "mfs.view_union_trans_finance")
public class TransFinanceUnion implements Serializable {

	private static final long serialVersionUID = -4766966230697312291L;

	@Id
	@Column(name = "trans_id")
	private Long id;

	@Column(name = "trans_amount_approved")
	private Float amountApproved;

	@Column(name = "trans_amount")
	private Double transAmount;

	@Column(name = "trans_amount_fee")
	private Double transactionCharge;

	@Column(name = "agent_income")
	private Double agentIncome;

	@Column(name = "bank_income")
	private Double bankIncome;

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

	// @ManyToOne
	// @JoinColumn(name = "client_id")
	// private Client client;

	@Column(name = "ref_branch")
	private String refBranch;

	@Column(name = "branch_desc")
	private String sourceBranch;

	@Column(name = "trg_acct_no")
	private String targetAccount;

	@Column(name = "ref_name")
	private String targetName;

//	@ManyToOne
//	@JoinColumn(name = "src_acct_no", referencedColumnName = "account_number")
	@Column(name = "src_acct_no")
	private String account;

	@Column(name = "cust_cid")
	private String custCid;

	@Column(name = "source_name")
	private String sourceName;

	@Column(name = "msg")
	private String msg;

	@Column(name = "agent_cid")
	private String agentCid;

	@Column(name = "source_client_type")
	private String sourceClientType;
	@Column(name = "source_cid")
	private String sourceCid;
	@Column(name = "source_account_type")
	private String sourceAccountType;

	@Column(name = "target_client_type")
	private String targetClientType;
	@Column(name = "target_cid")
	private String targetCid;
	@Column(name = "target_account_type")
	private String targetAccountType;

	@Column(name = "agent_feature")
	private Integer agentFeature;
	
	@Column(name = "ar_or_number")
	private String arOrNumber;
	
	@Column(name = "trans_remark")
	private String transRemark;
	
	@Column(name = "bancnet_income")
	private Double bancnetIncome;
	
	@Column(name = "bank_name")
	private String bankName;
	
	public String getSourceAccountTypeName(){
		if(sourceAccountType != null){
			if("1001".equals(sourceAccountType)){
				return "CA Interest Bearing";
			}else if("1002".equals(sourceAccountType)){
				return "CA Non Interest Bearing";
			}else if("6001".equals(sourceAccountType)){
				return "Matapat";
			}else if("6002".equals(sourceAccountType)){
				return "Kayang Kaya";
			}else if("6003".equals(sourceAccountType)){
				return "Pledge";
			}else if("6004".equals(sourceAccountType)){
				return "Maagap";
			}else if("6005".equals(sourceAccountType)){
				return "FCDU";
			}else if("6006".equals(sourceAccountType)){
				return "AO ACCOUNT";
			}else if("6601".equals(sourceAccountType)){
				return "Tiwala";
			}else if("6602".equals(sourceAccountType)){
				return "Tagumpay Member";
			}else if("6603".equals(sourceAccountType)){
				return "Tagumpay Non Member";
			}else if("6604".equals(sourceAccountType)){
				return "Tagumpay Employee";
			}
		}
		return "-";
	}

	public String getTargetAccountTypeName(){
		if(targetAccountType != null){
			if("1001".equals(targetAccountType)){
				return "CA Interest Bearing";
			}else if("1002".equals(targetAccountType)){
				return "CA Non Interest Bearing";
			}else if("6001".equals(targetAccountType)){
				return "Matapat";
			}else if("6002".equals(targetAccountType)){
				return "Kayang Kaya";
			}else if("6003".equals(targetAccountType)){
				return "Pledge";
			}else if("6004".equals(targetAccountType)){
				return "Maagap";
			}else if("6005".equals(targetAccountType)){
				return "FCDU";
			}else if("6006".equals(targetAccountType)){
				return "AO ACCOUNT";
			}else if("6601".equals(targetAccountType)){
				return "Tiwala";
			}else if("6602".equals(targetAccountType)){
				return "Tagumpay Member";
			}else if("6603".equals(targetAccountType)){
				return "Tagumpay Non Member";
			}else if("6604".equals(targetAccountType)){
				return "Tagumpay Employee";
			}
		}
		return "-";
	}
	
	
	
	public String getAgentFeatureStr(){
		if(agentFeature != null){
			if(1 == agentFeature){
				return "YES";
			}else{
				return "NO";
			}
		}
		return "-";
	}

	public Integer getAgentFeature() {
		return agentFeature;
	}

	public void setAgentFeature(Integer agentFeature) {
		this.agentFeature = agentFeature;
	}

	public String getSourceClientType() {
		return sourceClientType;
	}

	public void setSourceClientType(String sourceClientType) {
		this.sourceClientType = sourceClientType;
	}

	public String getSourceCid() {
		return sourceCid;
	}

	public void setSourceCid(String sourceCid) {
		this.sourceCid = sourceCid;
	}

	public String getSourceAccountType() {
		return sourceAccountType;
	}

	public void setSourceAccountType(String sourceAccountType) {
		this.sourceAccountType = sourceAccountType;
	}

	public String getTargetClientType() {
		return targetClientType;
	}

	public void setTargetClientType(String targetClientType) {
		this.targetClientType = targetClientType;
	}

	public String getTargetCid() {
		return targetCid;
	}

	public void setTargetCid(String targetCid) {
		this.targetCid = targetCid;
	}

	public String getTargetAccountType() {
		return targetAccountType;
	}

	public void setTargetAccountType(String targetAccountType) {
		this.targetAccountType = targetAccountType;
	}

	public String getAgentCid() {
		return agentCid;
	}

	public void setAgentCid(String agentCid) {
		this.agentCid = agentCid;
	}

	public String getSourceName() {
		return sourceName;
	}

	public void setSourceName(String sourceName) {
		this.sourceName = sourceName;
	}

	public String getSourceBranch() {
		return sourceBranch;
	}

	public void setSourceBranch(String sourceBranch) {
		this.sourceBranch = sourceBranch;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

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

	public Float getAmountApproved() {
		return amountApproved;
	}

	public void setAmountApproved(Float amountApproved) {
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

	// public Client getClient() {
	// 	return client;
	// }

	// public void setClient(Client client) {
	// 	this.client = client;
	// }

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

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}
	
	public String getArOrNumber() {
		return arOrNumber;
	}

	public void setArOrNumber(String arOrNumber) {
		this.arOrNumber = arOrNumber;
	}

	public String getTransRemark() {
		return transRemark;
	}

	public void setTransRemark(String transRemark) {
		this.transRemark = transRemark;
	}
	
	public Double getBancnetIncome() {
		return bancnetIncome;
	}

	public void setBancnetIncome(Double bancnetIncome) {
		this.bancnetIncome = bancnetIncome;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	
}