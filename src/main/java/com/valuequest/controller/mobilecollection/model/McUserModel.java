/**
 * 
 */
package com.valuequest.controller.mobilecollection.model;

import java.util.Date;
import com.valuequest.entity.security.SecUser;

public class McUserModel {
	
	private Long id;
	private String staffId;
	private String mcuId;
	private String givenName;
	private String middleName;
	private String surname;
	private String designation;
	private String mobileNumber;
	private String internalAccount;
	private String email;
	private String deviceId;
	private String deviceModel;
	private String firebaseId;
	private String appVersion;
	private String aoStatus;
	private String aoPassword;
	private String aoPin;
	private int loginRetry;
	private int pinRetry;
	private int resetPassword;
	private int resetPin;
	private int enabled;
	private String actCode;
	private Date expiredActCode;
	private String smsStatus;
	private Date activatedDate;
	private Date registeredDate;
	private Date approvedDate;
	private SecUser approvedBy;
	private Date lastLogin;
	private SecUser createdBy;
	private Date createdOn;
	private SecUser updatedBy;
	private Date updatedOn;
	private String approvedStatus;
	private Boolean modified;
	private String freeData;
	
	private String[] branch;
	private String[] unit;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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


	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	public String[] getBranch() {
		return branch;
	}

	public void setBranch(String[] branch) {
		this.branch = branch;
	}

	public String[] getUnit() {
		return unit;
	}

	public void setUnit(String[] unit) {
		this.unit = unit;
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