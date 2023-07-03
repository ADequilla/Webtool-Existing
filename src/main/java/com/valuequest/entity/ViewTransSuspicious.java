package com.valuequest.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.valuequest.entity.util.CustomJsonDatetimeSerializer;

@Entity
@Table(name = "mfs.view_trans_suspicious")
public class ViewTransSuspicious implements Serializable {

	private static final long serialVersionUID = -4766966230697392291L;

	@Id
	@Column(name = "trans_code", unique = true)
	private String code;

	@Column(name = "trans_type")
	private String type;

	@ManyToOne
	@JoinColumn(name = "trans_id")
	private TransFinance trans;

	@Column(name = "trans_desc")
	private String desc;

	@Column(name = "trans_date")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonSerialize(using = CustomJsonDatetimeSerializer.class)
	private Date date;

	@Column(name = "client_type")
	private String clienType;

	@ManyToOne
	@JoinColumn(name = "client_id")
	private Client client;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
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

	public TransFinance getTrans() {
		return trans;
	}

	public void setTrans(TransFinance trans) {
		this.trans = trans;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getClienType() {
		return clienType;
	}

	public void setClienType(String clienType) {
		this.clienType = clienType;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}
}
