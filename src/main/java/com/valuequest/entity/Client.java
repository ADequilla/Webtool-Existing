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

import com.valuequest.entity.security.SecUser;

@Entity
@Table(name = "mfs.m_client")
public class Client implements Serializable {
	private static final long serialVersionUID = -4766966230697392291L;

	public static final String CLIENT_TYPE_AGENT = "AGENT";
	public static final String CLIENT_TYPE_MEMBER = "MEMBER";
	public static final String CLIENT_TYPE_MBO = "MBO";
	public static final String CLIENT_TYPE_MC = "MOBILE_COLLECTION";

	@Id
	@SequenceGenerator(name = "sequence", sequenceName = "mfs.seq_m_agent", initialValue = 1000, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence")
	@Column(name = "client_id")
	private Long id;

	@Column(name = "cid")
	private String cid;

	@Column(name = "account_number")
	private String account;

	@Column(name = "client_name")
	private String name;

	@Column(name = "client_firstname")
	private String firstName;

	@Column(name = "client_middlename")
	private String middleName;

	@Column(name = "client_lastname")
	private String lastName;

	@Column(name = "client_login")
	private String login;

	@Column(name = "client_passwd")
	private String password;

	@Column(name = "client_mobile_no")
	private String mobileNo;
 
	@Column(name = "client_email")
	private String email;

	@Column(name = "act_code")
	private String actCode;

	@Column(name = "client_pin")
	private String pin;

	@Column(name = "last_token_id")
	private String lastTokenId;

	@Column(name = "client_enabled")
	private Integer enabled;

	@Column(name = "client_login_status")
	private String loginStatus;

	@Column(name = "client_status")
	private String status;

	@Column(name = "client_rst_passwd_flag")
	private Integer resetPasswdFlag;

	@Column(name = "client_rst_pin_flag")
	private Integer resetPinFlag;

	@Column(name = "client_login_retry")
	private Integer loginRetry;

	@Column(name = "client_pin_retry")
	private Integer pinRetry;

	@Column(name = "failed_token_attempts")
	private Integer failTokenAttempts;

	@Column(name = "enable_print")
	private Long enablePrint;

	@Column(name = "approver_status")
	private String approverStatus;

	@Column(name = "client_broadcast_status")
	private String broadcastStatus;

	@Column(name = "client_type")
	private String type;

	@Column(name = "client_address")
	private String address;

	@Column(name = "client_dob")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dob;

	@Column(name = "restrict")
	private Integer restrict;

	@Column(name = "inst_code")
	private String instCode;

	@Column(name = "branch_code")
	private String branchCode;

	@Column(name = "unit_code")
	private String unitCode;

	@Column(name = "center_code")
	private String centerCode;

	@ManyToOne
	@JoinColumn(name = "approved_by")
	private SecUser approver;

	@ManyToOne
	@JoinColumn(name = "reapprove_by")
	private SecUser reapprover;

	@Column(name = "enrollment_approve_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date approveDate;

	@Column(name = "reenrollment_approve_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date reapproveDate;

	@Column(name = "client_expired_date")
	@Temporal(TemporalType.DATE)
	private Date expiredDate;

	@Column(name = "client_expired_actcode")
	@Temporal(TemporalType.TIMESTAMP)
	private Date expiredActcode;

	@Column(name = "created_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate;

	@Column(name = "created_by")
	private Long createdBy;

	@Column(name = "last_updated_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastUpdatedDate;

	@Column(name = "last_updated_by")
	private Long lastUpdatedBy;

	@Column(name = "agent_feature")
	private Integer agentFeature;

	@Column(name = "client_imei")
	private String clientImei;

	@Column(name = "device_model")
	private String deviceModel;

	@Column(name = "client_rst_credential")
	private int clientRstCredential;

	@Column(name = "client_reg_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date clientRegDate;

	@Column(name = "enable_agent_features")
	@Temporal(TemporalType.TIMESTAMP)
	private Date enableAgentFeatures;

	@Column(name = "enable_agent_features_by")
	private Long enableAgentFeaturesBy;
	
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

	public Integer getAgentFeature() {
		return agentFeature;
	}

	public void setAgentFeature(Integer agentFeature) {
		this.agentFeature = agentFeature;
	}

	public Long getId() {
		return id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getActCode() {
		return actCode;
	}

	public void setActCode(String actCode) {
		this.actCode = actCode;
	}

	public String getPin() {
		return pin;
	}

	public void setPin(String pin) {
		this.pin = pin;
	}

	public String getLastTokenId() {
		return lastTokenId;
	}

	public void setLastTokenId(String lastTokenId) {
		this.lastTokenId = lastTokenId;
	}

	public Integer getEnabled() {
		return enabled;
	}

	public void setEnabled(Integer enabled) {
		this.enabled = enabled;
	}

	public String getLoginStatus() {
		return loginStatus;
	}

	public void setLoginStatus(String loginStatus) {
		this.loginStatus = loginStatus;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getResetPasswdFlag() {
		return resetPasswdFlag;
	}

	public void setResetPasswdFlag(Integer resetPasswdFlag) {
		this.resetPasswdFlag = resetPasswdFlag;
	}

	public Integer getResetPinFlag() {
		return resetPinFlag;
	}

	public void setResetPinFlag(Integer resetPinFlag) {
		this.resetPinFlag = resetPinFlag;
	}

	public Integer getLoginRetry() {
		return loginRetry;
	}

	public void setLoginRetry(Integer loginRetry) {
		this.loginRetry = loginRetry;
	}

	public Integer getPinRetry() {
		return pinRetry;
	}

	public void setPinRetry(Integer pinRetry) {
		this.pinRetry = pinRetry;
	}

	public Integer getFailTokenAttempts() {
		return failTokenAttempts;
	}

	public void setFailTokenAttempts(Integer failTokenAttempts) {
		this.failTokenAttempts = failTokenAttempts;
	}

	public Long getEnablePrint() {
		return enablePrint;
	}

	public void setEnablePrint(Long enablePrint) {
		this.enablePrint = enablePrint;
	}

	public String getApproverStatus() {
		return approverStatus;
	}

	public void setApproverStatus(String approverStatus) {
		this.approverStatus = approverStatus;
	}

	public String getBroadcastStatus() {
		return broadcastStatus;
	}

	public void setBroadcastStatus(String broadcastStatus) {
		this.broadcastStatus = broadcastStatus;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public Integer getRestrict() {
		return restrict;
	}

	public void setRestrict(Integer restrict) {
		this.restrict = restrict;
	}

	public String getInstCode() {
		return instCode;
	}

	public void setInstCode(String instCode) {
		this.instCode = instCode;
	}

	public String getBranchCode() {
		return branchCode;
	}

	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}

	public String getUnitCode() {
		return unitCode;
	}

	public void setUnitCode(String unitCode) {
		this.unitCode = unitCode;
	}

	public String getCenterCode() {
		return centerCode;
	}

	public void setCenterCode(String centerCode) {
		this.centerCode = centerCode;
	}

	public SecUser getApprover() {
		return approver;
	}

	public void setApprover(SecUser approver) {
		this.approver = approver;
	}

	public SecUser getReapprover() {
		return reapprover;
	}

	public void setReapprover(SecUser reapprover) {
		this.reapprover = reapprover;
	}

	public Date getApproveDate() {
		return approveDate;
	}

	public void setApproveDate(Date approveDate) {
		this.approveDate = approveDate;
	}

	public Date getReapproveDate() {
		return reapproveDate;
	}

	public void setReapproveDate(Date reapproveDate) {
		this.reapproveDate = reapproveDate;
	}

	public Date getExpiredDate() {
		return expiredDate;
	}

	public void setExpiredDate(Date expiredDate) {
		this.expiredDate = expiredDate;
	}

	public Date getExpiredActcode() {
		return expiredActcode;
	}

	public void setExpiredActcode(Date expiredActcode) {
		this.expiredActcode = expiredActcode;
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

	public Date getClientRegDate() {
		return clientRegDate;
	}

	public void setClientRegDate(Date clientRegDate) {
		this.clientRegDate = clientRegDate;
	}

	public int getClientRstCredential() {
		return clientRstCredential;
	}

	public void setClientRstCredential(int clientRstCredential) {
		this.clientRstCredential = clientRstCredential;
	}

	public Date getEnableAgentFeatures() {
		return enableAgentFeatures;
	}

	public void setEnableAgentFeatures(Date enableAgentFeatures) {
		this.enableAgentFeatures = enableAgentFeatures;
	}

	public Long getEnableAgentFeaturesBy() {
		return enableAgentFeaturesBy;
	}

	public void setEnableAgentFeaturesBy(Long enableAgentFeaturesBy) {
		this.enableAgentFeaturesBy = enableAgentFeaturesBy;
	}
}
