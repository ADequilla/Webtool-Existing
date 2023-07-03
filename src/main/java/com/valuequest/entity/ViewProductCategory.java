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
@Table(name = "mfs.view_product_category")
public class ViewProductCategory implements Serializable {

	private static final long serialVersionUID = -4766992230697392210L;

	@Id
	@Column(name = "id")
	private Long id;
	@Column(name = "product_type_id")
	private Long productTypeId;
	@Column(name = "product_type_name")
	private String productTypeName;
	@Column(name = "product_category_id")
	private Long productCategoryId;
	@Column(name = "product_category_name")
	private String productCategoryName;
	@Column(name = "description")
	private String description;
	@Column(name = "status")
	private Long status;
	@Column(name = "provider_id")
	private Long providerId;
	@Column(name = "provider_name")
	private String providerName;
	
	@Column(name = "last_sync")
	@Temporal(TemporalType.DATE)
	@JsonSerialize(using = CustomJsonDateSerializer.class)
	private Date lastSync;
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

	public ViewProductCategory() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getProductTypeId() {
		return productTypeId;
	}

	public void setProductTypeId(Long productTypeId) {
		this.productTypeId = productTypeId;
	}

	public String getProductTypeName() {
		return productTypeName;
	}

	public void setProductTypeName(String productTypeName) {
		this.productTypeName = productTypeName;
	}

	public Long getProductCategoryId() {
		return productCategoryId;
	}

	public void setProductCategoryId(Long productCategoryId) {
		this.productCategoryId = productCategoryId;
	}

	public String getProductCategoryName() {
		return productCategoryName;
	}

	public void setProductCategoryName(String productCategoryName) {
		this.productCategoryName = productCategoryName;
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

	public Date getLastSync() {
		return lastSync;
	}

	public void setLastSync(Date lastSync) {
		this.lastSync = lastSync;
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

	public Long getProviderId() {
		return providerId;
	}

	public void setProviderId(Long providerId) {
		this.providerId = providerId;
	}

	public String getProviderName() {
		return providerName;
	}

	public void setProviderName(String providerName) {
		this.providerName = providerName;
	}
}
