package com.valuequest.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "mfs.t_mbo_operation")
public class MboOperation implements Serializable {

	private static final long serialVersionUID = -4766966230697392291L;

	@Id
	@Column(name = "operation_id")
	private Long id;

	@Column(name = "days_code")
	private int daysCode;

	@Column(name = "days_name", length = 20)
	private String daysName;

	@Column(name = "hours_start", length = 10)
	private String hoursStart;

	@Column(name = "hours_end", length = 10)
	private String hoursEnd;

	@Column(name = "enabled")
	private int enabled;

	@Column(name = "created_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate;
	@Column(name = "created_by")
	private Long createdBy;
	@Column(name = "last_updated_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastUpdatedDate;
	@Column(name = "last_updated_by")
	private Long lastUpdatedBy;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getDaysCode() {
		return daysCode;
	}

	public void setDaysCode(int daysCode) {
		this.daysCode = daysCode;
	}

	public String getDaysName() {
		return daysName;
	}

	public void setDaysName(String daysName) {
		this.daysName = daysName;
	}

	public String getHoursStart() {
		return hoursStart;
	}

	public void setHoursStart(String hoursStart) {
		this.hoursStart = hoursStart;
	}

	public String getHoursEnd() {
		return hoursEnd;
	}

	public void setHoursEnd(String hoursEnd) {
		this.hoursEnd = hoursEnd;
	}

	public int getEnabled() {
		return enabled;
	}

	public void setEnabled(int enabled) {
		this.enabled = enabled;
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

	public Date getLastUpdatedDate() {
		return lastUpdatedDate;
	}

	public void setLastUpdatedDate(Date lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}

	public Long getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdatedBy(Long lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}
}