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
@Table(name = "mfs.m_biller_product")
public class StructureBillerProduct implements Serializable {

	private static final long serialVersionUID = -4760966239697392210L;

	@Id
	@SequenceGenerator(name = "sequence", sequenceName = "mfs.m_biller_product_id_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence")
	@Column(name = "id")
	private Long id;
	@Column(name = "product_category_id")
	private Long productCategoryId;
	@Column(name = "biller_product_id")
	private Long billerProductId;
	@Column(name = "biller_product_name")
	private String billerProductName;
	@Column(name = "description")
	private String description;
	@Column(name = "service_fee")
	private BigDecimal serviceFee;
	@Column(name = "bank_commission")
	private BigDecimal bankCommission;
	@Column(name = "status")
	private Long status;
	
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

	public StructureBillerProduct() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getProductCategoryId() {
		return productCategoryId;
	}

	public void setProductCategoryId(Long productCategoryId) {
		this.productCategoryId = productCategoryId;
	}

	public Long getBillerProductId() {
		return billerProductId;
	}

	public void setBillerProductId(Long billerProductId) {
		this.billerProductId = billerProductId;
	}

	public String getBillerProductName() {
		return billerProductName;
	}

	public void setBillerProductName(String billerProductName) {
		this.billerProductName = billerProductName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public BigDecimal getBankCommission() {
		return bankCommission;
	}

	public void setBankCommission(BigDecimal bankCommission) {
		this.bankCommission = bankCommission;
	}

	public BigDecimal getServiceFee() {
		return serviceFee;
	}

	public void setServiceFee(BigDecimal serviceFee) {
		this.serviceFee = serviceFee;
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

}
