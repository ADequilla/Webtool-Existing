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
import com.valuequest.entity.security.SecUser;
import com.valuequest.entity.util.CustomJsonDatetime2Serializer;
import com.valuequest.entity.util.CustomJsonDatetimeSerializer;

@Entity
@Table(name = "mfs.view_log_upload_file")
public class ViewLogUploadFile implements Serializable {

	private static final long serialVersionUID = 595288756478053515L;

	@Id
	@Column(name = "id")
	private Long id;
	
	@Column (name="upload_date")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonSerialize(using = CustomJsonDatetime2Serializer.class)
	private Date uploadDate;
	
	@ManyToOne
	@JoinColumn (name="upload_by")
	private SecUser uploadBy;
	
	@Column (name="file_name")
	private String fileName;
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getUploadDate() {
		return uploadDate;
	}

	public void setUploadDate(Date uploadDate) {
		this.uploadDate = uploadDate;
	}

	public SecUser getUploadBy() {
		return uploadBy;
	}

	public void setUploadBy(SecUser uploadBy) {
		this.uploadBy = uploadBy;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
}