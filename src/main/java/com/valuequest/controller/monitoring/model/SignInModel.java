package com.valuequest.controller.monitoring.model;

import java.util.Date;
import java.util.List;

public class SignInModel {
    public int id;
    public String username;
    public String mobile;
    public Date birthday;
    public int cid;
    public boolean mPin;
    public int instiCode;
    public String firstName;
    public String middleName;
    public String lastName;
    public List<String> roles;
    public int isDefault;
    public String brCode;
    public String unitCode;
    public String centerCode;
    public List<Object> accounts;
    public String accessToken;
    public String tokenType;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	public int getCid() {
		return cid;
	}
	public void setCid(int cid) {
		this.cid = cid;
	}
	public boolean ismPin() {
		return mPin;
	}
	public void setmPin(boolean mPin) {
		this.mPin = mPin;
	}
	public int getInstiCode() {
		return instiCode;
	}
	public void setInstiCode(int instiCode) {
		this.instiCode = instiCode;
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
	public List<String> getRoles() {
		return roles;
	}
	public void setRoles(List<String> roles) {
		this.roles = roles;
	}
	public int getIsDefault() {
		return isDefault;
	}
	public void setIsDefault(int isDefault) {
		this.isDefault = isDefault;
	}
	public String getBrCode() {
		return brCode;
	}
	public void setBrCode(String brCode) {
		this.brCode = brCode;
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
	public List<Object> getAccounts() {
		return accounts;
	}
	public void setAccounts(List<Object> accounts) {
		this.accounts = accounts;
	}
	public String getAccessToken() {
		return accessToken;
	}
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	public String getTokenType() {
		return tokenType;
	}
	public void setTokenType(String tokenType) {
		this.tokenType = tokenType;
	}
    
    
}
