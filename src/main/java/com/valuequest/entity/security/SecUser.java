package com.valuequest.entity.security;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import com.valuequest.entity.Entity;

public class SecUser implements Serializable, Entity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6751516406395916644L;
	
	public static final String USER_SUPER_ADMIN = "SUPER_ADMIN";
	public static final String USER_USER 		= "USER";
	
	private Long id;
	private String usrLoginname;
	private String usrPassword;
	private String usrName;
	private String givenName;
	private String middleName;
	private String lastName;
	private String usrEmail;
	private String usrPhone;
	private Date usrExpiredPassword;
	private String usrStatus;
	private Boolean isLogin;
	private String usrPosition;
	private Long usrParent;
	private boolean usrEnabled;
	private Integer loginAttempts;
	private boolean passwordDefault;
	private Date activationLimit;
	private String checkStatus;
	
	private String institutions;
	private String branchs;
	private String roles;

	private Date createdDate;
	private Long createdBy;
	private Long lastUpdatedBy;
	private Date lastUpdatedDate;
	
	private Set<SecUserrole> userroles = new HashSet<SecUserrole>(0);
	
	public boolean isNew() {
        return getId() == null;
    }

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public String getUsrLoginname() {
		return usrLoginname;
	}

	public void setUsrLoginname(String usrLoginname) {
		this.usrLoginname = usrLoginname;
	}

	public String getUsrPassword() {
		return usrPassword;
	}

	public void setUsrPassword(String usrPassword) {
		this.usrPassword = usrPassword;
	}

	public String getUsrName() {
		return usrName;
	}

	public void setUsrName(String usrName) {
		this.usrName = usrName;
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

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getUsrEmail() {
		return usrEmail;
	}

	public void setUsrEmail(String usrEmail) {
		this.usrEmail = usrEmail;
	}

	public String getUsrPhone() {
		return usrPhone;
	}

	public void setUsrPhone(String usrPhone) {
		this.usrPhone = usrPhone;
	}

	public Date getUsrExpiredPassword() {
		return usrExpiredPassword;
	}

	public void setUsrExpiredPassword(Date usrExpiredPassword) {
		this.usrExpiredPassword = usrExpiredPassword;
	}

	public String getUsrStatus() {
		return usrStatus;
	}

	public Boolean getIsLogin() {
		return isLogin;
	}

	public void setIsLogin(Boolean isLogin) {
		this.isLogin = isLogin;
	}

	public void setUsrStatus(String usrStatus) {
		this.usrStatus = usrStatus;
	}

	public String getUsrPosition() {
		return usrPosition;
	}

	public void setUsrPosition(String usrPosition) {
		this.usrPosition = usrPosition;
	}

	public Long getUsrParent() {
		return usrParent;
	}

	public void setUsrParent(Long usrParent) {
		this.usrParent = usrParent;
	}

	public boolean isUsrEnabled() {
		return usrEnabled;
	}

	public void setUsrEnabled(boolean usrEnabled) {
		this.usrEnabled = usrEnabled;
	}

	public Integer getLoginAttempts() {
		return loginAttempts;
	}

	public void setLoginAttempts(Integer loginAttempts) {
		this.loginAttempts = loginAttempts;
	}

	public boolean isPasswordDefault() {
		return passwordDefault;
	}

	public void setPasswordDefault(boolean passwordDefault) {
		this.passwordDefault = passwordDefault;
	}

	public Date getActivationLimit() {
		return activationLimit;
	}

	public void setActivationLimit(Date activationLimit) {
		this.activationLimit = activationLimit;
	}
	
	public String getCheckStatus() {
		return checkStatus;
	}

	public void setCheckStatus(String checkStatus) {
		this.checkStatus = checkStatus;
	}
	
	public void setUserroles(Set<SecUserrole> userroles) {
		this.userroles = userroles;
	}
	
	public String getBranchs() {
		return branchs;
	}

	public void setBranchs(String branchs) {
		this.branchs = branchs;
	}

	public String getRoles() {
		return roles;
	}

	public void setRoles(String roles) {
		this.roles = roles;
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

	public Long getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdatedBy(Long lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	public Date getLastUpdatedDate() {
		return lastUpdatedDate;
	}

	public void setLastUpdatedDate(Date lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}

	public Set<SecUserrole> getUserroles() {
		return userroles;
	}
	
	
	
	public String getInstitutions() {
		return institutions;
	}

	public void setInstitutions(String institutions) {
		this.institutions = institutions;
	}

	public String getName(){
		String name	= "";
		if(StringUtils.isNotBlank(getGivenName()))
			name	= getGivenName();
		
		// if(StringUtils.isNotBlank(getMiddleName()))
		// 	name	+= " " + getMiddleName();
		
		// if(StringUtils.isNotBlank(getLastName()))
		// 	name	+= " " +  getLastName();
		
		return name;
	}
}