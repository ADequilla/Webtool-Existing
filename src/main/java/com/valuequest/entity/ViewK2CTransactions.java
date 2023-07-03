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
@Table(name = "public.view_t_temp_transaction")
public class ViewK2CTransactions implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3343976345664202936L;
	
	@Id
	@Column(name = "id")
	private String id;
	@Column(name = "area")
	private String Area;
	@Column(name = "unit")
	private String Unit;
	@Column(name = "center")
	private String Center;
	@Column(name = "cid")
	private Long CID;
	@Column(name = "client_name")
	private String clientName;
	@Column(name = "accnt")
	private String account;
	@Column(name = "accnt_name")
	private String accountName;
	@Column(name = "debit")
	private Double debit;
	@Column(name = "credit")
	private Double credit;
	@Temporal(TemporalType.TIMESTAMP)
	@JsonSerialize(using = CustomJsonDateSerializer.class)
	@Column(name = "trndate")
	private Date transactionDate;
	@Column(name = "reference")
	private String reference;
	@Column(name = "trndesc")
	private String transactionType;
	@Column(name = "session_id")
	private String sessionId;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getArea() {
		return Area;
	}
	public void setArea(String area) {
		Area = area;
	}
	public String getUnit() {
		return Unit;
	}
	public void setUnit(String unit) {
		Unit = unit;
	}
	public String getCenter() {
		return Center;
	}
	public void setCenter(String center) {
		Center = center;
	}
	public Long getCID() {
		return CID;
	}
	public void setCID(Long cID) {
		CID = cID;
	}
	public String getClientName() {
		return clientName;
	}
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	public Double getDebit() {
		return debit;
	}
	public void setDebit(Double debit) {
		this.debit = debit;
	}
	public Double getCredit() {
		return credit;
	}
	public void setCredit(Double credit) {
		this.credit = credit;
	}
	public Date getTransactionDate() {
		return transactionDate;
	}
	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}
	public String getReference() {
		return reference;
	}
	public void setReference(String reference) {
		this.reference = reference;
	}
	public String getTransactionType() {
		return transactionType;
	}
	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}
	public String getSessionId() {
		return sessionId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	
	
	
}
