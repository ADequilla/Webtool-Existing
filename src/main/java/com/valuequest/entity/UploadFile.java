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
import javax.persistence.Transient;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.valuequest.entity.security.SecUser;
import com.valuequest.entity.util.CustomJsonDatetimeSerializer;

@Entity
@Table(name = "mfs.t_upload_file")
public class UploadFile implements Serializable {
	
	private static final long serialVersionUID = -4766966230697392291L;
	
	@Id
	@SequenceGenerator(name = "sequence", sequenceName = "mfs.seq_t_upload_file", initialValue = 1000, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence")
	@Column(name = "UPLOAD_ID")
	private Long id;

	@Column(name = "ORIGINAL_FILENAME", length = 255)
	private String originalFilename;
	
	@Column(name = "DISPLAY_FILENAME", length = 255)
	private String displayFilename;
	
	@Column(name = "INVALID_FILENAME", length = 255)
	private String invalidFilename;
	
	@Column(name = "STATUS", length = 50)
	private String status;
	
	@Column(name = "TOTAL_CLIENT")
	private Long totalClient;
	
	@Column(name = "VALID_CLIENT")
	private Long validClient;
	
	@Column(name = "INVALID_CLIENT")
	private Long invalidClient;
	
	@Column(name = "UPLOAD_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonSerialize(using=CustomJsonDatetimeSerializer.class)
	private Date uploadDate;
	
	@Column(name = "PROCESS_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonSerialize(using=CustomJsonDatetimeSerializer.class)
	private Date processDate;

	@ManyToOne
	@JoinColumn(name = "UPLOAD_BY")
	private SecUser user;
	
	@Transient
	private String branchs;
	@Transient
	private String units;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOriginalFilename() {
		return originalFilename;
	}

	public void setOriginalFilename(String originalFilename) {
		this.originalFilename = originalFilename;
	}

	public String getDisplayFilename() {
		return displayFilename;
	}

	public void setDisplayFilename(String displayFilename) {
		this.displayFilename = displayFilename;
	}

	public String getInvalidFilename() {
		return invalidFilename;
	}

	public void setInvalidFilename(String invalidFilename) {
		this.invalidFilename = invalidFilename;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Long getTotalClient() {
		return totalClient;
	}

	public void setTotalClient(Long totalClient) {
		this.totalClient = totalClient;
	}

	public Long getValidClient() {
		return validClient;
	}

	public void setValidClient(Long validClient) {
		this.validClient = validClient;
	}

	public Long getInvalidClient() {
		return invalidClient;
	}

	public void setInvalidClient(Long invalidClient) {
		this.invalidClient = invalidClient;
	}

	public Date getUploadDate() {
		return uploadDate;
	}

	public void setUploadDate(Date uploadDate) {
		this.uploadDate = uploadDate;
	}

	public Date getProcessDate() {
		return processDate;
	}

	public void setProcessDate(Date processDate) {
		this.processDate = processDate;
	}

	public SecUser getUser() {
		return user;
	}

	public void setUser(SecUser user) {
		this.user = user;
	}

	public String getBranch() {
		return branchs;
	}

	public void setBranch(String branch) {
		this.branchs = branch;
	}

	public String getUnit() {
		return units;
	}

	public void setUnit(String unit) {
		this.units = unit;
	}
}
