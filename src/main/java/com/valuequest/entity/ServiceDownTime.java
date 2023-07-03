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

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.valuequest.entity.util.CustomJsonTimestampDeserializer;
import com.valuequest.entity.util.CustomJsonDateSerializer;
import com.valuequest.entity.util.CustomJsonDatetimeSerializer;


@Entity
@Table(name = "mfs.t_service_downtime")
public class ServiceDownTime  implements Serializable {
												  
	private static final long serialVersionUID = -4766966230697392291L;
	
	@Id
	@SequenceGenerator(name = "sequence", sequenceName = "mfs.seq_t_service_downtime", initialValue = 1000, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence")
	@Column(name = "downtime_id")
	private Long id;
	
	@Column (name="downtime_start")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonDeserialize(using=CustomJsonTimestampDeserializer.class)
	@JsonSerialize(using=CustomJsonDatetimeSerializer.class)
	private Date startDate;
	
	@Column (name="downtime_end")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonDeserialize(using=CustomJsonTimestampDeserializer.class)
	@JsonSerialize(using=CustomJsonDatetimeSerializer.class)
	private Date endDate;
	
	@Column (name="downtime_desc")
	private String desc;
	
	@Column (name="created_dt")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonSerialize(using=CustomJsonDateSerializer.class)
	private Date date;
	
	@Column (name="created_by")
	private Long createdBy;
	
	@Column (name="last_updated_by")
	private Long updatedBy;
	
	@Column (name="last_updated_dt")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonSerialize(using=CustomJsonDateSerializer.class)
	private Date updatedDate;
	
	@Column (name ="client_type")
	private String type;
	
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	public Long getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(Long updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}
}