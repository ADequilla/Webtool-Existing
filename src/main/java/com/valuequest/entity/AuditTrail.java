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
import com.valuequest.entity.util.CustomJsonDateSerializer;

@Entity
@Table(name = "mfs.log_audit_trail")
public class AuditTrail implements Serializable {

	private static final long serialVersionUID = -4766966230697392291L;
	
	@Id
	@SequenceGenerator(name = "sequence", sequenceName = "mfs.seq_log_audit_trail", initialValue = 1000, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence")
	@Column(name = "LOG_AUDIT_ID")
	private Long id;

	@ManyToOne
	@JoinColumn(name="module_id")
	private Module module;
	
	@Column(name="action_name", length=50)
	private String activity;
	
	@Column(name="cid", length=30)
	private String cid;
	
	@Column(name="data_before", length=4000)
	private String dataBefore;
	
	@Column(name="data_after", length=4000)
	private String dataAfter;
	
	@Column(name="app_type", length=10)
	private String appType;
	
	@Column(name="user_type", length=10)
	private String userType;
	
	@Column(name="ip_address", length=30)
	private String ipAddress;
	
	@Column(name="created_date")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonSerialize(using=CustomJsonDateSerializer.class)
	private Date createdDate;
	
	@Column(name="created_by")
	private Long createdBy;
	
	@Column(name="reset_id")
	private Long resetId;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public Module getModule() {
		return module;
	}
	public void setModule(Module module) {
		this.module = module;
	}
	
	public String getActivity() {
		return activity;
	}
	public void setActivity(String activity) {
		this.activity = activity;
	}
	
	public String getCid() {
		return cid;
	}
	public void setCid(String cid) {
		this.cid = cid;
	}
	
	public String getDataBefore() {
		return dataBefore;
	}
	public void setDataBefore(String dataBefore) {
		this.dataBefore = dataBefore;
	}
	
	public String getDataAfter() {
		return dataAfter;
	}
	public void setDataAfter(String dataAfter) {
		this.dataAfter = dataAfter;
	}
	
	public String getAppType() {
		return appType;
	}
	public void setAppType(String appType) {
		this.appType = appType;
	}
	
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	
	public String getIpAddress() {
		return ipAddress;
	}
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
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
	public Long getResetId() {
		return resetId;
	}
	public void setResetId(Long resetId) {
		this.resetId = resetId;
	}
}
