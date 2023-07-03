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
import javax.persistence.Transient;


@Entity
@Table(name = "mfs.m_csr_hotline")
public class CSRHotlainView implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1736362603848350063L;

	@Id
	@SequenceGenerator(name = "sequence", sequenceName = "mfs.seq_m_csr_hotline", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence")
	@Column(name = "id")
	private Long id;

	@Column(name = "contact_number")
	private String contactNumber;
	@Column(name = "network_provider")
	private String networkProvider;

	@Column(name = "created_date")
	@Temporal(TemporalType.DATE)
	private Date createdDate;
	@Column(name = "created_by")
	private Long createdBy;
	@Column(name = "last_updated_date")
	@Temporal(TemporalType.DATE)
	private Date updatedDate;
	@Column(name = "last_updated_by")
	private Long updatedBy;
	@Column(name = "inst_code")
	private Long instCode;




	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

	public String getNetworkProvider() {
		return networkProvider;
	}

	public void setNetworkProvider(String networkProvider) {
		this.networkProvider = networkProvider;
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

	public Long getInstCode() {
		return instCode;
	}

	public void setInstCode(Long instCode) {
		this.instCode = instCode;
	}

}
