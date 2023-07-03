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


@Entity
@Table(name = "mfs.m_type_concern")
public class Concern  implements Serializable {
	
	private static final long serialVersionUID = -4766966230697392291L;
	
	@Id
	@SequenceGenerator(name = "sequence", sequenceName = "mfs.seq_m_type_concern", initialValue = 1000, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence")
	@Column(name = "concern_code")
	private Long id;
	
	@Column (name="concern_time")
	private String time;
	
	@Column (name="concern_name")
	private String name;
	
	@Column (name="concern_level")
	private String level;
	
	@Column (name="concern_desc")
	private String desc;
	
	@Column (name="created_date")
	@Temporal(TemporalType.DATE)
	@JsonSerialize(using=CustomJsonDateSerializer.class)
	private Date date;
	
	@Column(name="created_by")
	private Long createdBy;
	
	@Column(name="last_updated_date")
	@Temporal(TemporalType.DATE)
	private Date updatedDate;
	
	@Column(name="last_updated_by")
	private Long updatedBy;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getTime() {
		return time;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public Date getDate() {
		return date;
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
