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
@Table(name = "mfs.view_trans_confirmation")
public class ViewTransConfirmation implements Serializable {

	private static final long serialVersionUID = -4766966230697392291L;

	@Id
	@Column(name = "suspicious_id")
	private Long id;

	@Column(name = "suspicious_code")
	private String code;

	@Column(name = "trans_date")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonSerialize(using = CustomJsonDatetimeSerializer.class)
	private Date transDate;

	@Column(name = "trans_desc")
	private String transDesc;
	
	@Column(name = "trans_type")
	private String transType;

	@Column(name = "client_mobile_no")
	private String clientMobile;

	@Column(name = "cid")
	private String clientCid;

	@Column(name = "client_name")
	private String clientName;

	@Column(name = "branch_code")
	private String branchCode;

	@Column(name = "branch_desc")
	private String branchDesc;

	@Column(name = "unit_code")
	private String unitCode;

	@Column(name = "unit_desc")
	private String unitDesc;

	@Column(name = "center_code")
	private String centerCode;

	@Column(name = "center_desc")
	private String centerDesc;

	@Column(name = "note")
	private String note;

	@Column(name = "status")
	private String status;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Date getTransDate() {
		return transDate;
	}

	public void setTransDate(Date transDate) {
		this.transDate = transDate;
	}

	public String getTransDesc() {
		return transDesc;
	}

	public void setTransDesc(String transDesc) {
		this.transDesc = transDesc;
	}

	public String getClientMobile() {
		return clientMobile;
	}

	public void setClientMobile(String clientMobile) {
		this.clientMobile = clientMobile;
	}

	public String getClientCid() {
		return clientCid;
	}

	public void setClientCid(String clientCid) {
		this.clientCid = clientCid;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
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

	public String getUnitCode() {
		return unitCode;
	}

	public void setUnitCode(String unitCode) {
		this.unitCode = unitCode;
	}

	public String getUnitDesc() {
		return unitDesc;
	}

	public void setUnitDesc(String unitDesc) {
		this.unitDesc = unitDesc;
	}

	public String getCenterCode() {
		return centerCode;
	}

	public void setCenterCode(String centerCode) {
		this.centerCode = centerCode;
	}

	public String getCenterDesc() {
		return centerDesc;
	}

	public void setCenterDesc(String centerDesc) {
		this.centerDesc = centerDesc;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}