package com.valuequest.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.valuequest.entity.security.SecUser;
import com.valuequest.entity.util.CustomJsonDateSerializer;
import com.valuequest.entity.util.CustomJsonDatetimeSerializer;

@Entity
@Table(name = "mfs.view_client")
public class ViewClient implements Serializable {

	private static final long serialVersionUID = -4766966230697392291L;

	@Id
	@SequenceGenerator(name = "sequence", sequenceName = "mfs.view_client_id_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence")
	@Column(name = "id")
	private Long id;

	@Column(name = "enrolled")
	@Temporal(TemporalType.TIMESTAMP)
		@JsonSerialize(using = CustomJsonDateSerializer.class)
	private Date enrolled;

	@Column(name = "approved")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonSerialize(using = CustomJsonDateSerializer.class)
	private Date approved;

	@ManyToOne
	@JoinColumn(name = "approved_by")
	private SecUser approver;

	@Column(name = "registered")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonSerialize(using = CustomJsonDateSerializer.class)
	private Date registered;

	@Column(name = "re_enrolled")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonSerialize(using = CustomJsonDateSerializer.class)
	private Date reEnrolled;

	@Column(name = "re_approved")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonSerialize(using = CustomJsonDateSerializer.class)
	private Date reApproved;

	@ManyToOne
	@JoinColumn(name = "re_approved_by")
	private SecUser reApprover;

	@Column(name = "act_code_expired")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonSerialize(using = CustomJsonDatetimeSerializer.class)
	private Date actCodeExpired;

	@Column(name = "app_expired")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonSerialize(using = CustomJsonDateSerializer.class)
	private Date appExpired;

	@Column(name = "dob")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonSerialize(using = CustomJsonDateSerializer.class)
	private Date dob;

	@Column(name = "activation_date")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonSerialize(using = CustomJsonDateSerializer.class)
	private Date activationDate;

	@Column(name = "account_type")
	private String accountType;

	@Column(name = "account_number")
	private String accountNumber;

	@Column(name = "cid")
	private String cid;

	@Column(name = "mobile_no")
	private String mobileNo;

	@Column(name = "firstname")
	private String firstname;

	@Column(name = "middlename")
	private String middlename;

	@Column(name = "lastname")
	private String lastname;

	@Column(name = "fullname")
	private String fullname;

	@Column(name = "maidenname")
	private String maidenname;

	@Column(name = "address")
	private String address;

	@Column(name = "type_code")
	private String typeCode;

	@Column(name = "type_desc")
	private String typeDesc;

	@Column(name = "inst_code")
	private String instCode;

	@Column(name = "inst_desc")
	private String instDesc;

	@Column(name = "branch_code")
	private String branchCode;

	@Column(name = "branch_desc")
	private String branchDesc;

	@Column(name = "unit_code")
	private String unitCode;

	@Column(name = "unit_desc")
	private String unitDesc;

	@Column(name = "center_code")
	private String centerCode;

	@Column(name = "center_desc")
	private String centerDesc;

	@Column(name = "acc_status_code")
	private String accStatusCode;

	@Column(name = "acc_status_desc")
	private String accStatusDesc;

	@Column(name = "sms_status_code")
	private String smsStatusCode;

	@Column(name = "sms_status_desc")
	private String smsStatusDesc;

	@Column(name = "approve_status_code")
	private String approveStatusCode;

	@Column(name = "approve_status_desc")
	private String approveStatusDesc;

	@Column(name = "enable_print")
	private Long enablePrint;

	@Column(name = "is_restrict")
	private Long restrict;

	@Column(name = "act_code")
	private String actCode;

	@Column(name = "account")
	private String account;

	@Column(name = "agent_feature")
	private Integer agentFeature;

	@Column(name = "client_imei")
	private String clientImei;

	@Column(name = "device_model")
	private String deviceModel;

	@Column(name = "type_account")
	private String typeAccount;

	@Column(name = "enable_agent_features")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonSerialize(using = CustomJsonDateSerializer.class)
	private Date enableAgentFeatures;

	@Column(name = "enable_agent_features_by")
	private Long enableAgentFeaturesBy;

	@Column(name = "enable_agent_features_byname")
	private String enableAgentFeaturesByName;
	
	@Transient
	private String email;

	@Transient
	private String birthDateStr;

	@Column(name = "merchant_id")
	private String merchantId;
	
	@Column(name = "merchant_name")
	private String merchantName;
	
	@Column(name = "merchant_city")
	private String merchantCity;
	
	@Column(name = "merchant_account_number")
	private String merchantAccountNumber;
	
	@Column(name = "merchant_activated_date")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonSerialize(using = CustomJsonDateSerializer.class)
	private Date merchantActivatedDate;
	
	@Column(name = "merchant_activated_by")
	private Long merchantActivatedBy;
	
	@Column(name = "merchant_expired_date")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonSerialize(using = CustomJsonDateSerializer.class)
	private Date merchantExpiredDate;
	
	@Column(name = "merchant_status")
	private Integer merchantStatus;
	
	@Column(name = "merchant_qr_code")
	private String merchantQrCode;
	
	@Column(name = "merchant_deactivated_date")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonSerialize(using = CustomJsonDateSerializer.class)
	private Date merchantDeactivatedDate;
	
	@Column(name = "merchant_deactivated_by")
	private Long merchantDeactivatedBy;
	
	@Column(name = "merchant_deactivated_remarks")
	private String merchantDeactivatedRemarks;
	
	@Column(name = "merchant_postal_code")
	private String merchantPostalCode;
	
	@Column(name = "monthly_accumulation_value")
	private String monthlyAccumulationValue;
	
	public Integer getAgentFeature() {
		return agentFeature;
	}

	public void setAgentFeature(Integer agentFeature) {
		this.agentFeature = agentFeature;
	}

	public String getClientImei() {
		return clientImei;
	}

	public void setClientImei(String clientImei) {
		this.clientImei = clientImei;
	}

	public String getDeviceModel() {
		return deviceModel;
	}

	public void setDeviceModel(String deviceModel) {
		this.deviceModel = deviceModel;
	}

	public String getBirthDateStr() {
		return birthDateStr;
	}

	public void setBirthDateStr(String birthDateStr) {
		this.birthDateStr = birthDateStr;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getEnrolled() {
		return enrolled;
	}

	public void setEnrolled(Date enrolled) {
		this.enrolled = enrolled;
	}

	public Date getApproved() {
		return approved;
	}

	public void setApproved(Date approved) {
		this.approved = approved;
	}

	public SecUser getApprover() {
		return approver;
	}

	public void setApprover(SecUser approver) {
		this.approver = approver;
	}

	public Date getRegistered() {
		return registered;
	}

	public void setRegistered(Date registered) {
		this.registered = registered;
	}

	public Date getReEnrolled() {
		return reEnrolled;
	}

	public void setReEnrolled(Date reEnrolled) {
		this.reEnrolled = reEnrolled;
	}

	public Date getReApproved() {
		return reApproved;
	}

	public void setReApproved(Date reApproved) {
		this.reApproved = reApproved;
	}

	public SecUser getReApprover() {
		return reApprover;
	}

	public void setReApprover(SecUser reApprover) {
		this.reApprover = reApprover;
	}

	public Date getActCodeExpired() {
		return actCodeExpired;
	}

	public void setActCodeExpired(Date actCodeExpired) {
		this.actCodeExpired = actCodeExpired;
	}

	public Date getAppExpired() {
		return appExpired;
	}

	public void setAppExpired(Date appExpired) {
		this.appExpired = appExpired;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getMiddlename() {
		return middlename;
	}

	public void setMiddlename(String middlename) {
		this.middlename = middlename;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public String getMaidenname() {
		return maidenname;
	}

	public void setMaidenname(String maidenname) {
		this.maidenname = maidenname;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getTypeCode() {
		return typeCode;
	}

	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}

	public String getTypeDesc() {
		return typeDesc;
	}

	public void setTypeDesc(String typeDesc) {
		this.typeDesc = typeDesc;
	}

	public String getInstCode() {
		return instCode;
	}

	public void setInstCode(String instCode) {
		this.instCode = instCode;
	}

	public String getInstDesc() {
		return instDesc;
	}

	public void setInstDesc(String instDesc) {
		this.instDesc = instDesc;
	}

	public String getBranchCode() {
		return branchCode;
	}

	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}

	public String getBranchDesc() {
		return branchDesc;
	}

	public void setBranchDesc(String branchDesc) {
		this.branchDesc = branchDesc;
	}

	public String getUnitCode() {
		return unitCode;
	}

	public void setUnitCode(String unitCode) {
		this.unitCode = unitCode;
	}

	public String getUnitDesc() {
		return unitDesc;
	}

	public void setUnitDesc(String unitDesc) {
		this.unitDesc = unitDesc;
	}

	public String getCenterCode() {
		return centerCode;
	}

	public void setCenterCode(String centerCode) {
		this.centerCode = centerCode;
	}

	public String getCenterDesc() {
		return centerDesc;
	}

	public void setCenterDesc(String centerDesc) {
		this.centerDesc = centerDesc;
	}

	public String getAccStatusCode() {
		return accStatusCode;
	}

	public void setAccStatusCode(String accStatusCode) {
		this.accStatusCode = accStatusCode;
	}

	public String getAccStatusDesc() {
		return accStatusDesc;
	}

	public void setAccStatusDesc(String accStatusDesc) {
		this.accStatusDesc = accStatusDesc;
	}

	public String getSmsStatusCode() {
		return smsStatusCode;
	}

	public void setSmsStatusCode(String smsStatusCode) {
		this.smsStatusCode = smsStatusCode;
	}

	public String getSmsStatusDesc() {
		return smsStatusDesc;
	}

	public void setSmsStatusDesc(String smsStatusDesc) {
		this.smsStatusDesc = smsStatusDesc;
	}

	public String getApproveStatusCode() {
		return approveStatusCode;
	}

	public void setApproveStatusCode(String approveStatusCode) {
		this.approveStatusCode = approveStatusCode;
	}

	public String getApproveStatusDesc() {
		return approveStatusDesc;
	}

	public void setApproveStatusDesc(String approveStatusDesc) {
		this.approveStatusDesc = approveStatusDesc;
	}

	public Long getEnablePrint() {
		return enablePrint;
	}

	public void setEnablePrint(Long enablePrint) {
		this.enablePrint = enablePrint;
	}

	public Long getRestrict() {
		return restrict;
	}

	public void setRestrict(Long restrict) {
		this.restrict = restrict;
	}

	public String getActCode() {
		return actCode;
	}

	public void setActCode(String actCode) {
		this.actCode = actCode;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public Date getActivationDate() {
		return activationDate;
	}

	public void setActivationDate(Date activationDate) {
		this.activationDate = activationDate;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getTypeAccount() {
		return typeAccount;
	}

	public void setTypeAccount(String typeAccount) {
		this.typeAccount = typeAccount;
	}

	public Date getEnableAgentFeatures() {
		return enableAgentFeatures;
	}

	public void setEnableAgentFeatures(Date enableAgentFeatures) {
		this.enableAgentFeatures = enableAgentFeatures;
	}

	public String getEnableAgentFeaturesByName() {
		return enableAgentFeaturesByName;
	}

	public void setEnableAgentFeaturesByName(String enableAgentFeaturesByName) {
		this.enableAgentFeaturesByName = enableAgentFeaturesByName;
	}

	public Long getEnableAgentFeaturesBy() {
		return enableAgentFeaturesBy;
	}

	public void setEnableAgentFeaturesBy(Long enableAgentFeaturesBy) {
		this.enableAgentFeaturesBy = enableAgentFeaturesBy;
	}
	
	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}
	
	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}
	
	public String getMerchantCity() {
		return merchantCity;
	}

	public void setMerchantCity(String merchantCity) {
		this.merchantCity = merchantCity;
	}
	
	public String getMerchantAccountNumber() {
		return merchantAccountNumber;
	}

	public void setMerchantAccountNumber(String merchantAccountNumber) {
		this.merchantAccountNumber = merchantAccountNumber;
	}
	
	public Date getMerchantActivatedDate() {
		return merchantActivatedDate;
	}

	public void setMerchantActivatedDate(Date merchantActivatedDate) {
		this.merchantActivatedDate = merchantActivatedDate;
	}
	
	public Long getMerchantActivatedBy() {
		return merchantActivatedBy;
	}

	public void setMerchantActivatedBy(Long merchantActivatedBy) {
		this.merchantActivatedBy = merchantActivatedBy;
	}
	
	public Date getMerchantExpiredDate() {
		return merchantExpiredDate;
	}

	public void setMerchantExpiredDate(Date merchantExpiredDate) {
		this.merchantExpiredDate = merchantExpiredDate;
	}
	
	public Integer getMerchantStatus() {
		return merchantStatus;
	}

	public void setMerchantStatus(Integer merchantStatus) {
		this.merchantStatus = merchantStatus;
	}
	
	public String getMerchantQrCode() {
		return merchantQrCode;
	}

	public void setMerchantQrCode(String merchantQrCode) {
		this.merchantQrCode = merchantQrCode;
	}
	
	public Date getMerchantDeactivatedDate() {
		return merchantDeactivatedDate;
	}

	public void setMerchantDeactivatedDate(Date merchantDeactivatedDate) {
		this.merchantDeactivatedDate = merchantDeactivatedDate;
	}
	
	public Long getMerchantDeactivatedBy() {
		return merchantDeactivatedBy;
	}

	public void setMerchantDeactivatedBy(Long merchantDeactivatedBy) {
		this.merchantDeactivatedBy = merchantDeactivatedBy;
	}
	
	public String getMerchantDeactivatedRemarks() {
		return merchantDeactivatedRemarks;
	}

	public void setMerchantDeactivatedRemarks(String merchantDeactivatedRemarks) {
		this.merchantDeactivatedRemarks = merchantDeactivatedRemarks;
	}
	
	public String getMerchantPostalCode() {
		return merchantPostalCode;
	}

	public void setMerchantPostalCode(String merchantPostalCode) {
		this.merchantPostalCode = merchantPostalCode;
	}

	public String getMonthlyAccumulationValue() {
		return monthlyAccumulationValue;
	}

	public void setMonthlyAccumulationValue(String monthlyAccumulationValue) {
		this.monthlyAccumulationValue = monthlyAccumulationValue;
	}
}