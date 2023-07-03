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
@Table(name = "mfs.m_product_service")
public class ProductAndServicesEntity implements Serializable {
	
	private static final long serialVersionUID = -8570432506266718160L;
	@Id
	@SequenceGenerator(name = "sequence", sequenceName = "mfs.seq_m_product_service_id", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence")
	@Column(name = "service_id")
	private Long id;
	@Column(name = "service_name")
	private String serviceName;
	@Column(name = "service_description")
	private String serviceDescription;
	@Column(name = "service_banner")
	private String serviceBanner;
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
	
	
	public ProductAndServicesEntity() {
		super();
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getServiceName() {
		return serviceName;
	}


	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}


	public String getServiceDescription() {
		return serviceDescription;
	}


	public void setServiceDescription(String serviceDescription) {
		this.serviceDescription = serviceDescription;
	}


	public String getServiceBanner() {
		return serviceBanner;
	}

	public void setServiceBanner(String serviceBanner) {
		this.serviceBanner = serviceBanner;
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
	
	