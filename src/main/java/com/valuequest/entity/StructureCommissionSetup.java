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
@Table(name = "mfs.t_commission_setup")
public class StructureCommissionSetup implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7699400499789549408L;
	@Id
	@SequenceGenerator(name = "sequence", sequenceName = "mfs.t_commission_setup_id_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence")
	@Column(name = "id")
	private Long id;
	@Column(name = "trans_type")
	private String transType;
	@Column(name = "commission_type")
	private String commissionType;
	@Column(name = "customer_income")
	private BigDecimal customerIncome;
	@Column(name = "agent_income")
	private BigDecimal agentIncome;
	@Column(name = "bank_income")
	private BigDecimal bankIncome;
	@Column(name = "bank_partner_income")
	private BigDecimal bankPartnerIncome;
	
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

	public StructureCommissionSetup() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTransType() {
		return transType;
	}

	public void setTransType(String transType) {
		this.transType = transType;
	}

	public String getCommissionType() {
		return commissionType;
	}

	public void setCommissionType(String commissionType) {
		this.commissionType = commissionType;
	}

	public BigDecimal getCustomerIncome() {
		return customerIncome;
	}

	public void setCustomerIncome(BigDecimal customerIncome) {
		this.customerIncome = customerIncome;
	}

	public BigDecimal getAgentIncome() {
		return agentIncome;
	}

	public void setAgentIncome(BigDecimal agentIncome) {
		this.agentIncome = agentIncome;
	}
	
	public BigDecimal getBankIncome() {
		return bankIncome;
	}

	public void setBankIncome(BigDecimal bankIncome) {
		this.bankIncome = bankIncome;
	}

	public BigDecimal getBankPartnerIncome() {
		return bankPartnerIncome;
	}

	public void setBankPartnerIncome(BigDecimal bankPartnerIncome) {
		this.bankPartnerIncome = bankPartnerIncome;
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

}
