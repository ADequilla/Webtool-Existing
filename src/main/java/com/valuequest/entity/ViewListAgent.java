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
@Table(name = "mfs.view_list_agent")
public class ViewListAgent implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8400085615623318337L;

	@Id
	@Column(name = "id")
	private Long id;
	@Column(name = "device_id")
	private String deviceId;
	@Column(name = "device_model")
	private String deviceModel;
	@Column(name = "cid")
	private String cid;
	@Column(name = "branch_code")
	private String branchCode;
	@Column(name = "mobile_number")
	private String mobileNumber;
	@Column(name = "client_name")
	private String clientName;
	@Column(name = "client_type")
	private String clientType;
	@Column(name = "device_status")
	private Long deviceStatus;
	@Column(name = "android_version")
	private String androidVersion;
	@Column(name = "created_date")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonSerialize(using = CustomJsonDatetimeSerializer.class)
	private Date createdDate;
	@Column(name = "created_by")
	private Long createdBy;
	@Column(name = "last_updated_date")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonSerialize(using = CustomJsonDatetimeSerializer.class)
	private Date updatedDate;
	@Column(name = "last_updated_by")
	private Long updatedBy;

	

}
