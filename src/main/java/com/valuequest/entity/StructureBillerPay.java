package com.valuequest.entity;

import java.io.Serializable;
import java.math.BigDecimal;
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
@Table(name = "mfs.m_pay_biller")
public class StructureBillerPay implements Serializable {

	private static final long serialVersionUID = -4766966230697392210L;

	@Id
	@SequenceGenerator(name = "sequence", sequenceName = "mfs.m_pay_biller_id_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence")
	@Column(name = "id")
	private Long id;
	@Column(name = "provider_id")
	private Long providerId;
	@Column(name = "transaction_id")
	private Long transactionId;
	@Column(name = "biller_code")
	private String billerCode;
	@Column(name = "biller_name")
	private String billerName;
	@Column(name = "description")
	private String description;
	@Column(name = "status")
	private Long status;
	@Column(name = "total_rebates")
	private BigDecimal totalRebates;
	@Column(name = "customer_rebates")
	private BigDecimal customerRebates;
	@Column(name = "bank_rebates")
	private BigDecimal bankRebates;

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
	private Long updatedBy;

	public StructureBillerPay() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getProviderId() {
		return providerId;
	}

	public void setProviderId(Long providerId) {
		this.providerId = providerId;
	}

	public Long getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(Long transactionId) {
		this.transactionId = transactionId;
	}

	public String getBillerCode() {
		return billerCode;
	}

	public void setBillerCode(String billerCode) {
		this.billerCode = billerCode;
	}

	public String getBillerName() {
		return billerName;
	}

	public void setBillerName(String billerName) {
		this.billerName = billerName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getStatus() {
		return status;
	}

	public void setStatus(Long status) {
		this.status = status;
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

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public Long getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(Long updatedBy) {
		this.updatedBy = updatedBy;
	}

	public BigDecimal getTotalRebates() {
		return totalRebates;
	}

	public void setTotalRebates(BigDecimal totalRebates) {
		this.totalRebates = totalRebates;
	}

	public BigDecimal getCustomerRebates() {
		return customerRebates;
	}

	public void setCustomerRebates(BigDecimal customerRebates) {
		this.customerRebates = customerRebates;
	}

	public BigDecimal getBankRebates() {
		return bankRebates;
	}

	public void setBankRebates(BigDecimal bankRebates) {
		this.bankRebates = bankRebates;
	}

}
