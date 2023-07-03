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
import com.valuequest.entity.util.CustomJsonDatetime2Serializer;

@Entity
@Table(name = "mfs.view_cs_ticket")
public class ViewCsTicket implements Serializable {

	private static final long serialVersionUID = -4901527681908951111L;

	@Id
	@Column(name = "ticket_id")
	private Long id;

	@Column(name = "customer_ticket_id")
	private String ticketNo;

	@Column(name = "client_cid")
	private String cid;

	@Column(name = "client_type")
	private String clientType;

	@Column(name = "client_desc")
	private String clientDesc;

	@Column(name = "client_name")
	private String clientName;

	@Column(name = "user_type")
	private String userType;

	@Column(name = "message")
	private String message;

	@Column(name = "action_taken")
	private String action;

	@Column(name = "turn_around")
	private Long timeElapsed;

	@Column(name = "concern_id")
	private Long concernId;

	@Column(name = "concern_name")
	private String concernName;

	@Column(name = "concern_level")
	private String level;

	@Column(name = "concern_time")
	private String concernTime;

	@Column(name = "trans_type")
	private String transType;

	@Column(name = "trans_desc")
	private String transDesc;

	@Column(name = "status")
	private String statusCode;

	@Column(name = "status_desc")
	private String statusDesc;

	@Column(name = "branch_desc")
	private String branch;

	@Column(name = "unit_desc")
	private String unit;

	@Column(name = "center_desc")
	private String center;

	@Column(name = "client_mobile_no")
	private String mobileNo;

	@Column(name = "submitted_by")
	private String submittedBy;

	@Column(name = "assigned_to")
	private String assignedTo;

	@Column(name = "assigned_to_id")
	private Long assignedToId;

	@Column(name = "created_date")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonSerialize(using = CustomJsonDatetime2Serializer.class)
	private Date createdDate;

	@Column(name = "closed_date")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonSerialize(using = CustomJsonDatetime2Serializer.class)
	private Date closedDate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTicketNo() {
		return ticketNo;
	}

	public void setTicketNo(String ticketNo) {
		this.ticketNo = ticketNo;
	}

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public String getClientType() {
		return clientType;
	}

	public void setClientType(String clientType) {
		this.clientType = clientType;
	}

	public String getClientDesc() {
		return clientDesc;
	}

	public void setClientDesc(String clientDesc) {
		this.clientDesc = clientDesc;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public Long getTimeElapsed() {
		return timeElapsed;
	}

	public void setTimeElapsed(Long timeElapsed) {
		this.timeElapsed = timeElapsed;
	}

	public Long getConcernId() {
		return concernId;
	}

	public void setConcernId(Long concernId) {
		this.concernId = concernId;
	}

	public String getConcernName() {
		return concernName;
	}

	public void setConcernName(String concernName) {
		this.concernName = concernName;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getConcernTime() {
		return concernTime;
	}

	public void setConcernTime(String concernTime) {
		this.concernTime = concernTime;
	}

	public String getTransType() {
		return transType;
	}

	public void setTransType(String transType) {
		this.transType = transType;
	}

	public String getTransDesc() {
		return transDesc;
	}

	public void setTransDesc(String transDesc) {
		this.transDesc = transDesc;
	}

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public String getStatusDesc() {
		return statusDesc;
	}

	public void setStatusDesc(String statusDesc) {
		this.statusDesc = statusDesc;
	}

	public String getBranch() {
		return branch;
	}

	public void setBranch(String branch) {
		this.branch = branch;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getCenter() {
		return center;
	}

	public void setCenter(String center) {
		this.center = center;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getSubmittedBy() {
		return submittedBy;
	}

	public void setSubmittedBy(String submittedBy) {
		this.submittedBy = submittedBy;
	}

	public String getAssignedTo() {
		return assignedTo;
	}

	public void setAssignedTo(String assignedTo) {
		this.assignedTo = assignedTo;
	}

	public Long getAssignedToId() {
		return assignedToId;
	}

	public void setAssignedToId(Long assignedToId) {
		this.assignedToId = assignedToId;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getClosedDate() {
		return closedDate;
	}

	public void setClosedDate(Date closedDate) {
		this.closedDate = closedDate;
	}
}
