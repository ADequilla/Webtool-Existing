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

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.valuequest.entity.security.SecUser;
import com.valuequest.entity.util.CustomJsonDateSerializer;

@Entity
@Table(name = "mfs.m_biller_temp")
public class BillerTemp implements Serializable {
	
	private static final long serialVersionUID 	= -4766966230697392291L;
	
	public static final String BILLER_STATUS_PENDING	= "PENDING_APPROVAL";
	public static final String BILLER_STATUS_APPROVED	= "APPROVED";
	public static final String BILLER_STATUS_REJECTED	= "REJECTED";
	
	@Id
	@SequenceGenerator(name = "sequence", sequenceName = "mfs.seq_m_biller_temp", initialValue = 1000, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence")
	@Column(name = "biller_id")
	private Long id;

	@Column(name = "biller_name", length = 100)
	private String name;

	@Column(name = "biller_account_number", length = 20)
	private String account;

	@Column(name = "enable")
	private int enabled;

	@Column(name = "approve_status", length = 20)
	private String status;

	@Column(name = "biller_type", length = 15)
	private String type;
	
	@ManyToOne
	@JoinColumn(name = "approve_by")
	private SecUser approveBy;

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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public int getEnabled() {
		return enabled;
	}

	public void setEnabled(int enabled) {
		this.enabled = enabled;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public SecUser getApproveBy() {
		return approveBy;
	}

	public void setApproveBy(SecUser approveBy) {
		this.approveBy = approveBy;
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
