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
import com.valuequest.entity.util.CustomJsonDateSerializer;

@Entity
@Table(name = "mfs.view_author_reset_password")
public class ViewAuthorResetPassword implements Serializable {

	private static final long serialVersionUID = -4766966230697392210L;

	@Id
	@Column(name = "id")
	private Long id;

	@Column(name = "client_id")
	private Long clientId;

	@Column(name = "cid")
	private String cid;

	@Column(name = "client_name", length = 15)
	private String clientName;

	@Column(name = "Status", length = 50)
	private String status;

	@Column(name = "type", length = 50)
	private String type;

	@Column(name = "created_date")
	@Temporal(TemporalType.DATE)
	@JsonSerialize(using = CustomJsonDateSerializer.class)
	private Date createdDate;

	@Column(name = "created_by")
	private Long createdBy;

	@Column(name = "last_updated_date")
	@Temporal(TemporalType.DATE)
	private Date updatedDate;

	@Column(name = "last_updated_by")
	private Long lastUpdatedBy;

	@Column(name = "client_mobile_no")
	private String clientMobileNo;

	@Column(name = "branch_code")
	private String branchCode;

	@Column(name = "unit_code")
	private String unitCode;

	@Column(name = "center_code")
	private String centerCode;

	@Column(name = "user_login")
	private String lastUpdatedByName;

	public ViewAuthorResetPassword() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getClientId() {
		return clientId;
	}

	public void setClientId(Long clientId) {
		this.clientId = clientId;
	}

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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

	public String getClientMobileNo() {
		return clientMobileNo;
	}

	public void setClientMobileNo(String clientMobileNo) {
		this.clientMobileNo = clientMobileNo;
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

	public String getLastUpdatedByName() {
		return lastUpdatedByName;
	}

	public void setLastUpdatedByName(String lastUpdatedByName) {
		this.lastUpdatedByName = lastUpdatedByName;
	}

}
