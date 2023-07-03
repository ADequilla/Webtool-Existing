package com.valuequest.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.valuequest.entity.util.CustomJsonDateSerializer;

@Entity
@Table(name = "mfs.t_cs_ticket")
public class CsTicket implements Serializable {

	private static final long serialVersionUID = -4766966230697392291L;
	
	@Id
	@SequenceGenerator(name = "sequence", sequenceName = "mfs.seq_t_cs_ticket", initialValue = 1000, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence")
	@Column(name = "ticket_id")
	private Long id;
	
	@Column(name="subject", length=100)
	private String subject;
	
	@Column(name="user_type", length=10)
	private String userType;
	
	@Column(name="client_type", length=10)
	private String clientType;
	
	@Column(name="client_cid", length=100)
	private String cid;
	
	@Column(name="client_name", length=100)
	private String clientName;

	@Column(name="concern_id")
	private Long concernId;
	
	@Column(name="action_taken", length=25)
	private String actionTaken;
	
	@Column(name="action_detail", length=512)
	private String actionDetails;
	
	@Column(name="message", length=1000)
	private String message;
	
	@Column(name="status", length=10)
	private String status;
	
	@Column(name="assigned_to")
	private Long assignedTo;
	
	@Column(name="closed_date")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonSerialize(using=CustomJsonDateSerializer.class)
	private Date closedDate;
	
	@Column(name="turn_around")
	private Long totalTime;
	
	@Column(name="received_by")
	private Long receivedBy;
	
	@Column(name="created_date")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonSerialize(using=CustomJsonDateSerializer.class)
	private Date createdDate;
	
	@Column(name="created_by")
	private Long createdBy;
	
	@Column(name="customer_ticket_id")
	private String ticketNo;

	@Column(name="trans_type")
	private String transType;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getClientType() {
		return clientType;
	}

	public void setClientType(String clientType) {
		this.clientType = clientType;
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

	public Long getConcernId() {
		return concernId;
	}

	public void setConcernId(Long concernId) {
		this.concernId = concernId;
	}

	public String getActionTaken() {
		return actionTaken;
	}

	public void setActionTaken(String actionTaken) {
		this.actionTaken = actionTaken;
	}

	public String getActionDetails() {
		return actionDetails;
	}

	public void setActionDetails(String actionDetails) {
		this.actionDetails = actionDetails;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Long getAssignedTo() {
		return assignedTo;
	}

	public void setAssignedTo(Long assignedTo) {
		this.assignedTo = assignedTo;
	}

	public Date getClosedDate() {
		return closedDate;
	}

	public void setClosedDate(Date closedDate) {
		this.closedDate = closedDate;
	}

	public Long getTotalTime() {
		return totalTime;
	}

	public void setTotalTime(Long totalTime) {
		this.totalTime = totalTime;
	}

	public Long getReceivedBy() {
		return receivedBy;
	}

	public void setReceivedBy(Long receivedBy) {
		this.receivedBy = receivedBy;
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

	public String getTicketNo() {
		return ticketNo;
	}

	public void setTicketNo(String ticketNo) {
		this.ticketNo = ticketNo;
	}

	public String getTransType() {
		return transType;
	}

	public void setTransType(String transType) {
		this.transType = transType;
	}
}
