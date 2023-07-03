package com.valuequest.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.valuequest.entity.security.SecUser;
import com.valuequest.entity.util.CustomJsonDateDeserializer;
import com.valuequest.entity.util.CustomJsonDateSerializer;

@Entity
@Table(name = "mfs.m_product")
public class Product implements Serializable {
	
	private static final long serialVersionUID = -4766966230697392291L;
	
	@Id
	@SequenceGenerator(name = "sequence", sequenceName = "mfs.seq_m_product", initialValue = 1000, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence")
	@Column(name = "product_id")
	private Long id;

	@Column(name = "product_name")
	private String productName;

	@Column(name = "product_date")
	@Temporal(TemporalType.DATE)
	@JsonSerialize(using = CustomJsonDateSerializer.class)
	private Date productDate;

	@Column(name = "product_summary")
	private String productSummary;

	@Column(name = "product_description")
	private String productDesc;

	@Column(name = "product_periode_start")
	@Temporal(TemporalType.DATE)
	@JsonDeserialize(using = CustomJsonDateDeserializer.class)
	private Date productPeriodStart;

	@Column(name = "product_periode_end")
	@Temporal(TemporalType.DATE)
	@JsonDeserialize(using = CustomJsonDateDeserializer.class)
	private Date productPeriodEnd;

	@Column(name = "product_img_name")
	private String productImg;

	@Column(name = "created_by")
	private Long createdBy;

	@Column(name = "created_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate;

	@Column(name = "last_updated_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastUpdate;

	@Column(name = "last_updated_by")
	private Long updatedBy;

	@ManyToOne
	@JoinColumn(name = "created_by", insertable = false, updatable = false)
	private SecUser user;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Date getProductDate() {
		return productDate;
	}

	public void setProductDate(Date productDate) {
		this.productDate = productDate;
	}

	public String getProductSummary() {
		return productSummary;
	}

	public void setProductSummary(String productSummary) {
		this.productSummary = productSummary;
	}

	public String getProductDesc() {
		return productDesc;
	}

	public void setProductDesc(String productDesc) {
		this.productDesc = productDesc;
	}

	public Date getProductPeriodStart() {
		return productPeriodStart;
	}

	public void setProductPeriodStart(Date productPeriodStart) {
		this.productPeriodStart = productPeriodStart;
	}

	public Date getProductPeriodEnd() {
		return productPeriodEnd;
	}

	public void setProductPeriodEnd(Date productPeriodEnd) {
		this.productPeriodEnd = productPeriodEnd;
	}

	public String getProductImg() {
		return productImg;
	}

	public void setProductImg(String productImg) {
		this.productImg = productImg;
	}

	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public Long getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(Long updatedBy) {
		this.updatedBy = updatedBy;
	}

	public SecUser getUser() {
		return user;
	}

	public void setUser(SecUser user) {
		this.user = user;
	}
}
