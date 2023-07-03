package com.valuequest.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "mfs.t_broadcast_sms")
public class SmsLogs implements Serializable {

	private static final long serialVersionUID = -4766966230697392291L;
	
	@Id
	@Column(name = "msg_id")
	private Long id;

	@Column(name = "msg_desc")
	private String smsDesc;

	@Column(name = "msg_status")
	private String smsStatus;

	@Column(name = "msg_command")
	private String smsCommand;

	@Column(name = "activity")
	private String activity;

	@Column(name = "msisdn")
	private String msisdn;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSmsDesc() {
		return smsDesc;
	}

	public void setSmsDesc(String smsDesc) {
		this.smsDesc = smsDesc;
	}

	public String getSmsStatus() {
		return smsStatus;
	}

	public void setSmsStatus(String smsStatus) {
		this.smsStatus = smsStatus;
	}

	public String getSmsCommand() {
		return smsCommand;
	}

	public void setSmsCommand(String smsCommand) {
		this.smsCommand = smsCommand;
	}

	public String getActivity() {
		return activity;
	}

	public void setActivity(String activity) {
		this.activity = activity;
	}

	public String getMsisdn() {
		return msisdn;
	}

	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}
}
