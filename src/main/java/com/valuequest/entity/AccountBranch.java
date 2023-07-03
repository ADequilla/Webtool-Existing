package com.valuequest.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "mfs.m_account")
public class AccountBranch implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 112123123213L;

	@Id
	@Column(name = "account_id")
	private Long accountId;

	@Column(name = "account_number")
	private String accountNumber;

	@Column(name = "account_name")
	private String accountName;

	@ManyToOne
	@JoinColumn(name = "branch_code", referencedColumnName = "branch_code")
	private StructureBranch branch;

	public AccountBranch() {
		super();
		// TODO Auto-generated constructor stub
	}

	public StructureBranch getBranch() {
		return branch;
	}

	public void setBranch(StructureBranch branch) {
		this.branch = branch;
	}

	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

}