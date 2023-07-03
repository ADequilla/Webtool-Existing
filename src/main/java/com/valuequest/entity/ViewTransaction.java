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
@Table(name = "mfs.view_transaction_config")
public class ViewTransaction implements  Serializable{

	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	@Id
	@SequenceGenerator(name = "sequence", sequenceName = "mfs.t_transaction_configuration_id_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence")
	@Column(name = "id")
	private Long id;
	
	@Column(name = "area")
	private String area;
	
	@Column(name = "unit")
	private String unit;
	
	@Column(name = "center")
	private String center;
	
	@Column(name = "cid")
	private String cid;
	
	@Column(name = "client_name")
	private String clientName;
	
	@Column(name = "account_number")
	private String accNumber;
	
	@Column(name = "trn_date")
	@Temporal(TemporalType.DATE)
	@JsonSerialize(using = CustomJsonDateSerializer.class)
	private Date trnDate;
	
	
	@Column(name = "ref_number")
	private String refNumber;
	
	@Column(name = "trn_type")
	private String trnType;
	
	@Column(name = "status")
	private String status;
	
	@Column(name = "debit_amount")
	private String debitAmount;
	
	@Column(name = "credit_amount")
	private String creditAmount;
	
	public ViewTransaction() {
		super();
	}
	
	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
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

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public String getAccNumber() {
		return accNumber;
	}

	public void setAccNumber(String accNumber) {
		this.accNumber = accNumber;
	}

	public Date getTrnDate() {
		return trnDate;
	}

	public void setTrnDate(Date trnDate) {
		this.trnDate = trnDate;
	}

	public String getRefNumber() {
		return refNumber;
	}

	public void setRefNumber(String refNumber) {
		this.refNumber = refNumber;
	}

	public String getTrnType() {
		return trnType;
	}

	public void setTrnType(String trnType) {
		this.trnType = trnType;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDebitAmount() {
		return debitAmount;
	}

	public void setDebitAmount(String debitAmount) {
		this.debitAmount = debitAmount;
	}

	public String getCreditAmount() {
		return creditAmount;
	}

	public void setCreditAmount(String creditAmount) {
		this.creditAmount = creditAmount;
	}

	
	
	
	
}
