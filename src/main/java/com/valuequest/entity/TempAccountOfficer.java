package com.valuequest.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "mfs.temp_account_officer")
public class TempAccountOfficer {
	@Id
	@SequenceGenerator(name = "sequence", sequenceName = "mfs.temp_account_officer_id_seq", initialValue = 1000, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence")
	@Column(name = "id")
	private Long id;
	
	@Column(name = "log_upload_file_id")
	private Long logUploadFileId;

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

	@ManyToOne
	@JoinColumn(name = "branch_code")
	private StructureBranch branch;

	@ManyToOne
	@JoinColumn(name = "unit_code")
	private StructureUnit unit;
	
	@Column(name = "remark")
	private String remark;

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

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public Long getLogUploadFileId() {
		return logUploadFileId;
	}

	public void setLogUploadFileId(Long logUploadFileId) {
		this.logUploadFileId = logUploadFileId;
	}

}
