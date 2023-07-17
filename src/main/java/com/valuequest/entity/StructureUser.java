package com.valuequest.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.valuequest.entity.util.CustomJsonDatetimeSerializer;

@Entity
@Table(name = "mfs.m_user")
public class StructureUser implements Serializable{

    private static final long serialVersionUID = 7748618923608859923L;
	@Id
	@Column(name = "user_id", length = 15)
	private String userId;

    @Column(name = "user_login", length = 50)
	private String userLogin;

	@Column(name = "user_passwd", length = 50)
	private String userPassword;
	
    @Column(name = "user_name", length = 50)
	private String userName;

    @Column(name = "user_email", length = 50)
	private String userEmail;

    @Column(name = "user_phone", length = 50)
	private String userPhone;

    @Column(name = "created_date")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonSerialize(using = CustomJsonDatetimeSerializer.class)
	private Date createdDate;
	

    @Column(name = "created_by", length = 50)
	private String createdBy;

    @Column(name = "last_updated_date")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonSerialize(using = CustomJsonDatetimeSerializer.class)
	private Date lastUpdatedDate;

    @Column(name = "last_updated_by", length = 50)
	private String lastUpdateBy;

    @Column(name = "user_expired_passwd", length = 50)
	private String userExpiredPasswd;
    
    @Column(name = "user_type", length = 50)
	private String userType;

    @Column(name = "user_parent", length = 50)
	private String userParent;

    @Column(name = "user_status", length = 50)
	private String userStatus;

    @Column(name = "user_position", length = 50)
	private String userPosition;

    @Column(name = "login_attempts", length = 50)
	private String loginAttempts;

    @Column(name = "user_enabled", length = 50)
	private String userEnabled;

    @Column(name = "inst_code", length = 50)
	private String instCode;

    @Column(name = "branch_code", length = 50)
	private String branchCode;

    @Column(name = "unit_code", length = 50)
	private String unitCode;

    @Column(name = "center_code", length = 50)
	private String centerCode;

    @Column(name = "last_name", length = 50)
	private String lastName;

    @Column(name = "given_name", length = 50)
	private String givenName;

    @Column(name = "middle_name", length = 50)
	private String middleName;

    @Column(name = "passwd_default", length = 50)
	private String passwdDefault;

    @Column(name = "activation_limit", length = 50)
	private String activationLimit;

    @Column(name = "check_status", length = 50)
	private String checkStatus;

    @Column(name = "last_login_date")
    @Temporal(TemporalType.TIMESTAMP)
	@JsonSerialize(using = CustomJsonDatetimeSerializer.class)
	private Date lastLoginDate;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserLogin() {
		return userLogin;
	}

	public void setUserLogin(String userLogin) {
		this.userLogin = userLogin;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getUserPhone() {
		return userPhone;
	}

	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getLastUpdatedDate() {
		return lastUpdatedDate;
	}

	public void setLastUpdatedDate(Date lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}

	public String getLastUpdateBy() {
		return lastUpdateBy;
	}

	public void setLastUpdateBy(String lastUpdateBy) {
		this.lastUpdateBy = lastUpdateBy;
	}

	public String getUserExpiredPasswd() {
		return userExpiredPasswd;
	}

	public void setUserExpiredPasswd(String userExpiredPasswd) {
		this.userExpiredPasswd = userExpiredPasswd;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getUserParent() {
		return userParent;
	}

	public void setUserParent(String userParent) {
		this.userParent = userParent;
	}

	public String getUserStatus() {
		return userStatus;
	}

	public void setUserStatus(String userStatus) {
		this.userStatus = userStatus;
	}

	public String getUserPosition() {
		return userPosition;
	}

	public void setUserPosition(String userPosition) {
		this.userPosition = userPosition;
	}

	public String getLoginAttempts() {
		return loginAttempts;
	}

	public void setLoginAttempts(String loginAttempts) {
		this.loginAttempts = loginAttempts;
	}

	public String getUserEnabled() {
		return userEnabled;
	}

	public void setUserEnabled(String userEnabled) {
		this.userEnabled = userEnabled;
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

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
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

	public String getPasswdDefault() {
		return passwdDefault;
	}

	public void setPasswdDefault(String passwdDefault) {
		this.passwdDefault = passwdDefault;
	}

	public String getActivationLimit() {
		return activationLimit;
	}

	public void setActivationLimit(String activationLimit) {
		this.activationLimit = activationLimit;
	}

	public String getCheckStatus() {
		return checkStatus;
	}

	public void setCheckStatus(String checkStatus) {
		this.checkStatus = checkStatus;
	}

	public Date getLastLoginDate() {
		return lastLoginDate;
	}

	public void setLastLoginDate(Date lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}
}
	