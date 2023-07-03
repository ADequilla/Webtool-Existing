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
import com.valuequest.entity.util.CustomJsonDatetime2Serializer;

@Entity
@Table(name = "mfs.log_audit_trail_ao")
public class LogAuditTrailAo implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1746729700261434208L;

	@Id
	@SequenceGenerator(name="sequence", sequenceName="mfs.log_audit_trail_ao_id_seq", initialValue=1000, allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="sequence")
	@Column(name="id")
	private Long id;
	
	@Column(name="ao_id")
	private Long aoId;
	
	@Column(name="action_name")
	private String actionName;
	
	@Column(name="changes")
	private String changes;
	
	@Column(name="app_type")
	private String appType;
	
	@Column(name="approved_by")
	private Long approvedBy;
	
	@Column(name="created_date")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonSerialize(using = CustomJsonDatetime2Serializer.class)
	private Date createdDate;
	
	@Column(name="created_by")
	private Long createdBy;
	
	@Column(name="last_updated_date")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonSerialize(using = CustomJsonDatetime2Serializer.class)
	private Date lastUpdateDate;
	
	@Column(name="last_updated_by")
	private Long lastUpdateBy;
	
	public LogAuditTrailAo() {
		super();
	}
	
	public LogAuditTrailAo(Long aoId, String actionName, String changes, String appType, Long approvedBy, Long createdBy, Date lastUpdateDate, Long lastUpdateBy) {
		this.aoId = aoId;
		this.actionName = actionName;
		this.changes = changes;
		this.appType = appType;
		this.approvedBy = approvedBy;
		this.createdDate = new Date();
		this.createdBy = createdBy;
		this.lastUpdateDate = lastUpdateDate;
		this.lastUpdateBy = lastUpdateBy;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getAoId() {
		return aoId;
	}
	public void setAoId(Long aoId) {
		this.aoId = aoId;
	}
	public String getActionName() {
		return actionName;
	}
	public void setActionName(String actionName) {
		this.actionName = actionName;
	}
	public String getChanges() {
		return changes;
	}
	public void setChanges(String changes) {
		this.changes = changes;
	}
	public String getAppType() {
		return appType;
	}
	public void setAppType(String appType) {
		this.appType = appType;
	}
	public Long getApprovedBy() {
		return approvedBy;
	}
	public void setApprovedBy(Long approvedBy) {
		this.approvedBy = approvedBy;
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
	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}
	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}
	public Long getLastUpdateBy() {
		return lastUpdateBy;
	}
	public void setLastUpdateBy(Long lastUpdateBy) {
		this.lastUpdateBy = lastUpdateBy;
	}
	
}
