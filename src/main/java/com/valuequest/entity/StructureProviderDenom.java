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
@Table(name = "mfs.m_provider_denomination")
public class StructureProviderDenom implements Serializable {

	private static final long serialVersionUID = -4766961239697392210L;

	@Id
	@SequenceGenerator(name = "sequence", sequenceName = "mfs.m_provider_denomination_id_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence")
	@Column(name = "id")
	private Long id;
	@Column(name = "product_denomination_id")
	private Long productDenomId;
	@Column(name = "price", length = 15)
	private Long price;
	@Column(name = "fee", length = 15)
	private Long fee;
	@Column(name = "bank_income", length = 15)
	private BigDecimal bankIncome;
	@Column(name = "fds_income", length = 15)
	private BigDecimal fdsIncome;
	@Column(name = "agent_income", length = 15)
	private BigDecimal agentIncome;
	@Column(name = "description", length = 256)
	private String description;

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

	public StructureProviderDenom() {
		super();
	}

	public BigDecimal getBankIncome() {
		return bankIncome;
	}

	public void setBankIncome(BigDecimal bankIncome) {
		this.bankIncome = bankIncome;
	}

	public BigDecimal getFdsIncome() {
		return fdsIncome;
	}

	public void setFdsIncome(BigDecimal fdsIncome) {
		this.fdsIncome = fdsIncome;
	}

	public BigDecimal getAgentIncome() {
		return agentIncome;
	}

	public void setAgentIncome(BigDecimal agentIncome) {
		this.agentIncome = agentIncome;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getProductDenomId() {
		return productDenomId;
	}

	public void setProductDenomId(Long productDenomId) {
		this.productDenomId = productDenomId;
	}

	public Long getPrice() {
		return price;
	}

	public void setPrice(Long price) {
		this.price = price;
	}

	public Long getFee() {
		return fee;
	}

	public void setFee(Long fee) {
		this.fee = fee;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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
