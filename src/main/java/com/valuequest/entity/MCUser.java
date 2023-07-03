/**
 * 
 */
package com.valuequest.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.valuequest.entity.security.SecUser;
import com.valuequest.entity.util.CustomJsonDatetime2Serializer;

/**
 * @author Eki Nurhadi
 *
 */
@Entity
@Table(name = "mfs.m_account_officer")
public class MCUser implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3114487586195901595L;

	@Id
	@SequenceGenerator(name = "sequence", sequenceName = "mfs.m_account_officer_id_seq", initialValue = 1000, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence")
	@Column(name = "id")
	private Long id;
	
	@Column(name = "staff_id", length = 75)
	private String staffId;
	
	@Column(name = "mcu_id", length = 75)
	private String mcuId;
	
	@Column(name = "given_name", length = 75)
	private String givenName;
	
	@Column(name = "middle_name", length = 75)
	private String middleName;
	
	@Column(name = "surname", length = 75)
	private String surname;
	
	@Column(name = "designation", length = 2)
	private String designation;
	
	@Column(name = "mobile_number", length = 15)
	private String mobileNumber;
	
	@Column(name = "internal_account", length = 50)
	private String internalAccount;
	
	@Column(name = "email", length = 75)
	private String email;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "branch_code")
	@JsonIgnoreProperties({"id"})
	private StructureBranch branch;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "unit_code")
	@JsonIgnoreProperties({"id"})
	private StructureUnit unit;
	
	@Column(name = "device_id", length = 50)
	private String deviceId;
	
	@Column(name = "device_model", length = 50)
	private String deviceModel;
	
	@Column(name = "firebase_id", length = 500)
	private String firebaseId;
	
	@Column(name = "app_version", length = 15)
	private String appVersion;
	
	@Column(name = "ao_status", length = 20)
	private String aoStatus;
	
	@Column(name = "ao_password", length = 300)
	private String aoPassword;
	
	@Column(name = "ao_pin", length = 300)
	private String aoPin;
	
	@Column(name = "login_retry", precision = 10)
	private int loginRetry;
	
	@Column(name = "pin_retry", precision = 10)
	private int pinRetry;
	
	@Column(name = "reset_password", precision = 10)
	private int resetPassword;
	
	@Column(name = "reset_pin", precision = 10)
	private int resetPin;
	
	@Column(name = "enabled", precision = 10)
	private int enabled;
	
	@Column(name = "act_code", length = 6)
	private String actCode;
	
	@Column(name = "expired_act_code")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonSerialize(using = CustomJsonDatetime2Serializer.class)
	private Date expiredActCode;
	
	@Column(name = "sms_status", length = 10)
	private String smsStatus;
	
	@Column(name = "activated_date")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonSerialize(using = CustomJsonDatetime2Serializer.class)
	private Date activatedDate;
	
	@Column(name = "registered_date")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonSerialize(using = CustomJsonDatetime2Serializer.class)
	private Date registeredDate;
	
	@Column(name = "approved_date")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonSerialize(using = CustomJsonDatetime2Serializer.class)
	private Date approvedDate;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "approved_by")
	private SecUser approvedBy;
	
	@Column(name = "last_login")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonSerialize(using = CustomJsonDatetime2Serializer.class)
	private Date lastLogin;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "created_by")
	private SecUser createdBy;
	
	@Column(name = "created_date")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonSerialize(using = CustomJsonDatetime2Serializer.class)
	private Date createdOn;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "last_updated_by")
	private SecUser updatedBy;
	
	@Column(name = "last_updated_date")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonSerialize(using = CustomJsonDatetime2Serializer.class)
	private Date updatedOn;
	
	@Column(name = "approved_status")
	private String approvedStatus;
	
	@Column(name = "modified")
	private Boolean modified;
	
    @Column(name = "free_data")
	private String freeData;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getStaffId() {
		return staffId;
	}
	public void setStaffId(String staffId) {
		this.staffId = staffId;
	}
	public String getMcuId() {
		return mcuId;
	}
	public void setMcuId(String mcuId) {
		this.mcuId = mcuId;
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
	public String getDesignation() {
		return designation;
	}
	public void setDesignation(String designation) {
		this.designation = designation;
	}
	public String getMobileNumber() {
		return mobileNumber;
	}
	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}
	public String getInternalAccount() {
		return internalAccount;
	}
	public void setInternalAccount(String internalAccount) {
		this.internalAccount = internalAccount;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public StructureBranch getBranch() {
		return branch;
	}

	public void setBranch(StructureBranch branch) {
		this.branch = branch;
	}

	public StructureUnit getUnit() {
		return unit;
	}

	public void setUnit(StructureUnit unit) {
		this.unit = unit;
	}
	public String getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	public String getDeviceModel() {
		return deviceModel;
	}
	public void setDeviceModel(String deviceModel) {
		this.deviceModel = deviceModel;
	}
	public String getFirebaseId() {
		return firebaseId;
	}
	public void setFirebaseId(String firebaseId) {
		this.firebaseId = firebaseId;
	}
	public String getAppVersion() {
		return appVersion;
	}
	public void setAppVersion(String appVersion) {
		this.appVersion = appVersion;
	}
	public String getAoStatus() {
		return aoStatus;
	}
	public void setAoStatus(String aoStatus) {
		this.aoStatus = aoStatus;
	}
	public String getAoPassword() {
		return aoPassword;
	}
	public void setAoPassword(String aoPassword) {
		this.aoPassword = aoPassword;
	}
	public String getAoPin() {
		return aoPin;
	}
	public void setAoPin(String aoPin) {
		this.aoPin = aoPin;
	}
	public int getLoginRetry() {
		return loginRetry;
	}
	public void setLoginRetry(int loginRetry) {
		this.loginRetry = loginRetry;
	}
	public int getPinRetry() {
		return pinRetry;
	}
	public void setPinRetry(int pinRetry) {
		this.pinRetry = pinRetry;
	}
	public int getResetPassword() {
		return resetPassword;
	}
	public void setResetPassword(int resetPassword) {
		this.resetPassword = resetPassword;
	}
	public int getResetPin() {
		return resetPin;
	}
	public void setResetPin(int resetPin) {
		this.resetPin = resetPin;
	}
	public int getEnabled() {
		return enabled;
	}
	public void setEnabled(int enabled) {
		this.enabled = enabled;
	}
	public String getActCode() {
		return actCode;
	}
	public void setActCode(String actCode) {
		this.actCode = actCode;
	}
	public Date getExpiredActCode() {
		return expiredActCode;
	}
	public void setExpiredActCode(Date expiredActCode) {
		this.expiredActCode = expiredActCode;
	}
	public String getSmsStatus() {
		return smsStatus;
	}
	public void setSmsStatus(String smsStatus) {
		this.smsStatus = smsStatus;
	}
	public Date getActivatedDate() {
		return activatedDate;
	}
	public void setActivatedDate(Date activatedDate) {
		this.activatedDate = activatedDate;
	}
	public Date getRegisteredDate() {
		return registeredDate;
	}
	public void setRegisteredDate(Date registeredDate) {
		this.registeredDate = registeredDate;
	}
	public Date getApprovedDate() {
		return approvedDate;
	}
	public void setApprovedDate(Date approvedDate) {
		this.approvedDate = approvedDate;
	}
	public SecUser getApprovedBy() {
		return approvedBy;
	}
	public void setApprovedBy(SecUser approvedBy) {
		this.approvedBy = approvedBy;
	}
	public Date getLastLogin() {
		return lastLogin;
	}
	public void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
	}
	public SecUser getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(SecUser createdBy) {
		this.createdBy = createdBy;
	}
	public Date getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}
	public SecUser getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(SecUser updatedBy) {
		this.updatedBy = updatedBy;
	}
	public Date getUpdatedOn() {
		return updatedOn;
	}
	public void setUpdatedOn(Date updatedOn) {
		this.updatedOn = updatedOn;
	}
	public String getApprovedStatus() {
		return approvedStatus;
	}
	public void setApprovedStatus(String approvedStatus) {
		this.approvedStatus = approvedStatus;
	}
	public Boolean getModified() {
		return modified;
	}
	public void setModified(Boolean modified) {
		this.modified = modified;
	}
	public String getFreeData() {
		return freeData;
	}
	public void setFreeData(String freeData) {
		this.freeData = freeData;
	}
	
}
