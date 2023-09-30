package com.valuequest.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "mfs.view_user")
public class ViewUser implements Serializable {

	private static final long serialVersionUID = -4766966230697392291L;

	@Id
	@Column(name = "user_id")
	private Long id;

	@Column(name = "user_login")
	private String login;

	@Column(name = "given_name")
	private String givenName;

	@Column(name = "middle_name")
	private String middleName;

	@Column(name = "last_name")
	private String lastName;

	@Column(name = "user_name")
	private String name;
	
	@Column(name = "user_status")
	private String status;

	@Column(name = "branch_codes")
	private String branchCodes;

	@Column(name = "branch_names")
	private String branches;

	@Column(name = "roles")
	private String roles;
	
	@Column(name = "check_status")
	private String checkStatus;

	// @Column(name = "is_login")
	// private Boolean isLogin;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getBranchCodes() {
		return branchCodes;
	}

	public void setBranchCodes(String branchCodes) {
		this.branchCodes = branchCodes;
	}

	public String getBranches() {
		return branches;
	}

	public void setBranches(String branches) {
		this.branches = branches;
	}

	public String getRoles() {
		return roles;
	}

	public void setRoles(String roles) {
		this.roles = roles;
	}
	
	public String getCheckStatus() {
		return checkStatus;
	}

	public void setCheckStatus(String checkStatus) {
		this.checkStatus = checkStatus;
	}

	// public Boolean getIsLogin() {
	// 	return isLogin;
	// }

	// public void setIsLogin(Boolean isLogin) {
	// 	this.isLogin = isLogin;
	// }
}