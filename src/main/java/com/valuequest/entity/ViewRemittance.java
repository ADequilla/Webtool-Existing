package com.valuequest.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import java.util.Date;

import javax.persistence.Column;
import org.hibernate.annotations.Formula;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.valuequest.entity.util.CustomJsonDateSerializer;
import com.valuequest.entity.util.CustomJsonDatetimeSerializer;

@Entity
@Table(name = "mfs.view_remittance")
public class ViewRemittance implements Serializable {
	
	private static final long serialVersionUID = 883552689768915107L;
	@Id
	@Column(name = "id")
	private Long id;
	@Column(name = "client_id")
	private Long clientId;
	@Column(name = "account_number")
	private String accountNumber;
	@Column(name = "sender_name")
	private String senderName;
	@Column(name = "sender_birthday")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonSerialize(using = CustomJsonDatetimeSerializer.class)
	private Date senderBirthday;
	@Column(name = "sender_mobile_number")
	private String senderMobileNumber;
	@Column(name = "given_name")
	private String givenName;
	@Column(name = "middle_name")
	private String middleName;
	@Column(name = "surname")
	private String surname;
	@Formula(value = "given_name || ' ' || middle_name || ' ' || surname")
	private String receiverName;
	
	
	@Column(name = "receiver_birthday")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonSerialize(using = CustomJsonDatetimeSerializer.class)
	private Date receiverBirthday;
	@Column(name = "receiver_mobile_number")
	private String receiverMobileNumber;
	@Column(name = "amount")
	private BigDecimal amount;
	@Column(name = "reference_number")
	private String referenceNumber;
	@Column(name = "trans_type")
	private String transType;
	@Column(name = "claimed_type")
	private String claimedType;
	@Column(name = "otp_code")
	private String otpCode;
	@Column(name = "expired_otp_code")
	private String expiredOtpCode;
	@Column(name = "status")
	private Long status;
	
	@Column(name = "sent_mobile_ref_id")
	private String sentMobileRefId;
	@Column(name = "sent_core_ref_id")
	private String sentCoreRefId;
	@Column(name = "claimed_mobile_ref_id")
	private String claimedMobileRefId;
	@Column(name = "claimed_core_ref_id")
	private String claimedCoreRefId;
	
	@Column(name = "agent_source_fee")
	private BigDecimal agentSourceFee;
	@Column(name = "agent_target_fee")
	private BigDecimal agentTargetFee;
	@Column(name = "bank_fee")
	private BigDecimal bankFee;
	
	@Column(name = "created_date")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonSerialize(using = CustomJsonDatetimeSerializer.class)
	private Date createdDate;
	@Column(name = "created_by")
	private Long createdBy;
	@Column(name = "last_updated_date")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonSerialize(using = CustomJsonDatetimeSerializer.class)
	private Date updatedDate;
	@Column(name = "last_updated_by")
	private Long updatedBy;

	@Column(name = "source_branch")
	private String sourceBranch;
	@Column(name = "processed_by_fullname")
	private String processedByFullname;
	@Column(name = "target_branch")
	private String targetBranch;
	@Column(name = "disbursed_by_fullname")
	private String disbursedByFullname;
	@Column(name = "cancelled_by_fullname")
	private String cancelledByFullname;
	@Column(name = "cancelled_date")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonSerialize(using = CustomJsonDatetimeSerializer.class)
	private Date cancelledDate;
	
	public ViewRemittance() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getClientId() {
		return clientId;
	}

	public void setClientId(Long clientId) {
		this.clientId = clientId;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getSenderName() {
		return senderName;
	}

	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}

	public Date getSenderBirthday() {
		return senderBirthday;
	}

	public void setSenderBirthday(Date senderBirthday) {
		this.senderBirthday = senderBirthday;
	}

	public String getSenderMobileNumber() {
		return senderMobileNumber;
	}

	public void setSenderMobileNumber(String senderMobileNumber) {
		this.senderMobileNumber = senderMobileNumber;
	}

	public String getGivenName() {
		return givenName;
	}

	public void setGivenName(String givenName) {
		this.givenName = givenName;
	}
	
	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}
	
	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}
	
	public String getReceiverName() {
		return receiverName;
	}

	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}
	
	public Date getReceiverBirthday() {
		return receiverBirthday;
	}

	public void setReceiverBirthday(Date receiverBirthday) {
		this.receiverBirthday = receiverBirthday;
	}
	
	public String getReceiverMobileNumber() {
		return receiverMobileNumber;
	}

	public void setReceiverMobileNumber(String receiverMobileNumber) {
		this.receiverMobileNumber = receiverMobileNumber;
	}
	
	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	
	public String getReferenceNumber() {
		return referenceNumber;
	}

	public void setReferenceNumber(String referenceNumber) {
		this.referenceNumber = referenceNumber;
	}
	
	public String getTransType() {
		return transType;
	}

	public void setTransType(String transType) {
		this.transType = transType;
	}
	
	public String getClaimedType() {
		return claimedType;
	}

	public void setClaimedType(String claimedType) {
		this.claimedType = claimedType;
	}
	
	public String getOtpCode() {
		return otpCode;
	}

	public void setOtpCode(String otpCode) {
		this.otpCode = otpCode;
	}
	
	public String getExpiredOtpCode() {
		return expiredOtpCode;
	}

	public void setExpiredOtpCode(String expiredOtpCode) {
		this.expiredOtpCode = expiredOtpCode;
	}
	
	public Long getStatus() {
		return status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}
	
	public String getSentMobileRefId() {
		return sentMobileRefId;
	}

	public void setSentMobileRefId(String sentMobileRefId) {
		this.sentMobileRefId = sentMobileRefId;
	}
	
	public String getSentCoreRefId() {
		return sentCoreRefId;
	}

	public void setSentCoreRefId(String sentCoreRefId) {
		this.sentCoreRefId = sentCoreRefId;
	}
	
	public String getClaimedMobileRefId() {
		return claimedMobileRefId;
	}

	public void setClaimedMobileRefId(String claimedMobileRefId) {
		this.claimedMobileRefId = claimedMobileRefId;
	}
	
	public String getClaimedCoreRefId() {
		return claimedCoreRefId;
	}

	public void setClaimedCoreRefId(String claimedCoreRefId) {
		this.claimedCoreRefId = claimedCoreRefId;
	}
	
	public BigDecimal getAgentSourceFee() {
		return agentSourceFee;
	}

	public void setAgentSourceFee(BigDecimal agentSourceFee) {
		this.agentSourceFee = agentSourceFee;
	}
	
	public BigDecimal getAgentTargetFee() {
		return agentTargetFee;
	}

	public void setAgentTargetFee(BigDecimal agentTargetFee) {
		this.agentTargetFee = agentTargetFee;
	}
	
	public BigDecimal getBankFee() {
		return bankFee;
	}

	public void setBankFee(BigDecimal bankFee) {
		this.bankFee = bankFee;
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
	
	public String getSourceBranch() {
		return sourceBranch;
	}

	public void setSourceBranch(String sourceBranch) {
		this.sourceBranch = sourceBranch;
	}
	
	public String getProcessedByFullname() {
		return processedByFullname;
	}

	public void setProcessedByFullname(String processedByFullname) {
		this.processedByFullname = processedByFullname;
	}
	
	public String getTargetBranch() {
		return targetBranch;
	}

	public void setTargetBranch(String targetBranch) {
		this.targetBranch = targetBranch;
	}
	
	public String getDisbursedByFullname() {
		return disbursedByFullname;
	}

	public void setDisbursedByFullname(String disbursedByFullname) {
		this.disbursedByFullname = disbursedByFullname;
	}
	
	public String getCancelledByFullname() {
		return cancelledByFullname;
	}

	public void setCancelledByFullname(String cancelledByFullname) {
		this.cancelledByFullname = cancelledByFullname;
	}
	
	public Date getCancelledDate() {
		return cancelledDate;
	}

	public void setCancelledDate(Date cancelledDate) {
		this.cancelledDate = cancelledDate;
	}
}
