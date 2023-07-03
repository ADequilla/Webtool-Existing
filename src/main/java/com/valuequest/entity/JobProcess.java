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

@Entity
@Table(name = "mfs.t_job_process")
public class JobProcess implements Serializable {

	private static final long serialVersionUID = -4766966230697392291L;
	
	public static final String JOB_SENT_SMS			= "SENTSMS";
	public static final String JOB_ENROLMENT		= "ENROLMENT_LOADER";
	public static final String JOB_ENROLMENT_EDIT	= "ENROLMENT_EDIT";
	public static final String JOB_REACCT_CODE		= "REACCTCODE";
	public static final String JOB_STATUS_WAITING	= "WAITING";
	
	@Id
	@SequenceGenerator(name = "sequence", sequenceName = "mfs.seq_t_job_process", initialValue = 1000, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence")
	@Column(name = "job_id")
	private Long id;

	@Column(name = "activity_name")
	private String name;
	
	@Column(name = "activity_status")
	private String status;
	
	@Column(name = "flag_user")
	private String userType;
	
	@Column(name = "activity_ref_id")
	private Long refId;
	
	@Column(name = "activity_flag")
	private int flag;
	
	@Column(name = "activity_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date date;

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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public Long getRefId() {
		return refId;
	}

	public void setRefId(Long refId) {
		this.refId = refId;
	}

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
}
