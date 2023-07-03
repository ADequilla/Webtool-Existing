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
import com.valuequest.entity.util.CustomJsonDatetimeSerializer;

@Entity
@Table(name = "mfs.t_inbox")
public class Inbox implements Serializable {

	private static final long serialVersionUID = -4766966230697392291L;

	@Id
	@SequenceGenerator(name = "sequence", sequenceName = "mfs.seq_m_product", initialValue = 1000, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence")
	@Column(name = "inbox_id")
	private Long id;

	@Column(name = "inbox_date")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonSerialize(using = CustomJsonDatetimeSerializer.class)
	private Date date;

	@Column(name = "trans_type")
	private String type;

	@Column(name = "inbox_desc")
	private String desc;

	@Column(name = "subject")
	private String subject;

	@Column(name = "period_start")
	@Temporal(TemporalType.DATE)
	@JsonSerialize(using = CustomJsonDateSerializer.class)
	private Date periodStart;

	@Column(name = "period_end")
	@Temporal(TemporalType.DATE)
	@JsonSerialize(using = CustomJsonDateSerializer.class)
	private Date periodEnd;

	@Column(name = "client_type")
	private String clientType;

	@Column(name = "branch_code")
	private String branchCode;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public Date getPeriodStart() {
		return periodStart;
	}

	public void setPeriodStart(Date periodStart) {
		this.periodStart = periodStart;
	}

	public Date getPeriodEnd() {
		return periodEnd;
	}

	public void setPeriodEnd(Date periodEnd) {
		this.periodEnd = periodEnd;
	}

	public String getClientType() {
		return clientType;
	}

	public void setClientType(String clientType) {
		this.clientType = clientType;
	}

	public String getBranchCode() {
		return branchCode;
	}

	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}

}
