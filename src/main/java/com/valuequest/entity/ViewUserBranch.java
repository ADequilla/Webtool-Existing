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

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.valuequest.entity.security.SecUser;
import com.valuequest.entity.util.CustomJsonDateSerializer;

@Entity
@Table(name = "mfs.view_user_branch")
public class ViewUserBranch implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2858210613989827513L;
	
	@Id
	@Column(name = "user_branch_id", unique = true, length = 15)
	private String userBranchId;
	
	@Column(name = "user_id", length = 50)
	private String userId;
	
	@Column(name = "branch_code")
	private String branchCode;
	
	@Column(name = "branch_desc")
	private String branchDesc;

	public String getUserBranchId() {
		return userBranchId;
	}

	public void setUserBranchId(String userBranchId) {
		this.userBranchId = userBranchId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
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

		
	
}
