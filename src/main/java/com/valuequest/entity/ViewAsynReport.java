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
@Table(name = "mfs.view_report_asyn")
public class ViewAsynReport implements Serializable {

	private static final long serialVersionUID = -4766966230697392291L;

	@Id
	@Column(name = "report_id")
	private Long id;

	@Column(name = "report_type")
	private String type;

	@Column(name = "report_param")
	private String param;

	@Column(name = "report_status")
	private String status;

	@Column(name = "submited_by")
	private Long submitedBy;

	@Column(name = "submited_date")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonSerialize(using = CustomJsonDatetimeSerializer.class)
	private Date submitedDate;

	@Column(name = "completed_date")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonSerialize(using = CustomJsonDatetimeSerializer.class)
	private Date completedDate;

	@Column(name = "file_path")
	private String filePath;

	@Column(name = "file_type")
	private String fileType;

	@Column(name = "remark")
	private String remark;

	@Column(name = "user_name")
	private String username;

	@Column(name = "branch_code")
	private String branchCodes;

	@Column(name = "branch_desc")
	private String branches;

	@Column(name = "roles")
	private String roles;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getParam() {
		return param;
	}

	public void setParam(String param) {
		this.param = param;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Long getSubmitedBy() {
		return submitedBy;
	}

	public void setSubmitedBy(Long submitedBy) {
		this.submitedBy = submitedBy;
	}

	public Date getSubmitedDate() {
		return submitedDate;
	}

	public void setSubmitedDate(Date submitedDate) {
		this.submitedDate = submitedDate;
	}

	public Date getCompletedDate() {
		return completedDate;
	}

	public void setCompletedDate(Date completedDate) {
		this.completedDate = completedDate;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
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

	public String getCriteria() {
		String[] params = getParam().split("\\|");

		return params[0] + " - " + params[1];
	}
}