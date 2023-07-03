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
@Table(name = "mfs.m_param_config")
public class ParamConfig implements Serializable {

	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1710854997481361361L;
	
	public static final String WEB_PARAMCONFIG_UPLOAD_DIR 			= "WEB_UPLOAD_DIR";
	public static final String WEB_PARAMCONFIG_UPLOAD_DIR_FAILED 	= "WEB_UPLOAD_DIR_FAILED";
	public static final String WEB_PARAMCONFIG_PROMO_BANNER 		= "WEB_PROMO_BANNER_DIR";
	public static final String WS_PARAMCONFIG_BANNER_AGENT_URL 		= "WS_PROMO_BANNER_AGENT_URL";
	public static final String WS_PARAMCONFIG_BANNER_MEMBER_URL 	= "WS_PROMO_BANNER_MEMBER_URL";
	public static final String WS_PARAMCONFIG_REFRESH_AGENT_URL 	= "WS_REFRESH_AGENT_URL";
	public static final String WS_PARAMCONFIG_REFRESH_MEMBER_URL 	= "WS_REFRESH_MEMBER_URL";
	public static final String WS_PARAMCONFIG_REFRESH_MOBILE_C_URL 	= "WS_REFRESH_MOBILE_C_URL";

	public static final String WEB_USER_DEFAULT_PASSWORD 			= "WEB_USER_DEFAULT_PASSWORD";
	public static final String WEB_ACTIVATION_TIME_LIMIT 			= "WEB_ACTIVATION_TIME_LIMIT";
	public static final String WEB_INVALID_PASSWD_LIMIT 			= "WEB_INVALID_PASSWD_LIMIT";
	public static final String WEB_CHANGE_PASSWD_PERIOD 			= "WEB_CHANGE_PASSWD_PERIOD";
	public static final String WEB_USED_PASSWD_ITERATION 			= "WEB_USED_PASSWD_ITERATION";
	public static final String WEB_UPLOAD_IMAGE_SIZE_MAX 			= "WEB_UPLOAD_IMAGE_SIZE_MAX";
	
	public static final String AGENT_ACT_CODE_LIFETIME				= "AGENT_ACTIVATION_CODE_LIFETIME";
	public static final String MEMBER_ACT_CODE_LIFETIME				= "EXPIRED_ACT_CODE";
	public static final String AGENT_ACTIVATION_LIFETIME			= "AGENT_ACTIVATION_LIFETIME";
	public static final String MEMBER_ACTIVATION_LIFETIME			= "EXPIRED_CUST_MONTH";
	public static final String SESSION_TIMEOUT_WEBTOOL	 			= "SESSION_TIMEOUT_WEBTOOL";
	public static final String MERCHANT_QR_CODE_EXPIRATION	 		= "MERCHANT_QR_CODE_EXPIRATION";
	public static final String MERCHANT_TRANSACTION_PERIOD	 		= "MERCHANT_TRANSACTION_PERIOD";

	public static final String WEB_DASHBOARD_REGISTERED_CLIENT	 	= "WEB_DASHBOARD_REGISTERED_CLIENT";
	public static final String WEB_DASHBOARD_ACTIVE_CLIENT	 		= "WEB_DASHBOARD_ACTIVE_CLIENT";
	public static final String WEB_DASHBOARD_CLIENT_TRANSACTION	 	= "WEB_DASHBOARD_CLIENT_TRANSACTION";
	public static final String WEB_DASHBOARD_ELOAD_TRANSACTION	 	= "WEB_DASHBOARD_ELOAD_TRANSACTION";

	
	@Id
	@SequenceGenerator(name = "sequence", sequenceName = "mfs.seq_m_param_config", initialValue = 3018, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence")
	@Column(name = "param_id")
	private Long id;

	@Column(name = "app_type", length = 10)
	private String type;
	@Column(name = "param_name", length = 50)
	private String name;
	@Column(name = "param_value", length = 50)
	private String value;
	@Column(name = "param_desc", length = 255)
	private String description;
	@Column(name = "value_type", length = 10)
	private String valueType;
	@Column(name = "value_lookup", length = 50)
	private String valueLookup;
	@Column(name = "param_status", length = 255)
	private String status;

	@Column(name = "created_date")
	@Temporal(TemporalType.DATE)
	private Date createdDate;
	@Column(name = "created_by")
	private Long createdBy;
	@Column(name = "last_updated_date")
	@Temporal(TemporalType.DATE)
	private Date lastUpdatedDate;
	@Column(name = "last_updated_by")
	private Long lastUpdatedBy;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getValueType() {
		return valueType;
	}

	public void setValueType(String valueType) {
		this.valueType = valueType;
	}

	public String getValueLookup() {
		return valueLookup;
	}

	public void setValueLookup(String valueLookup) {
		this.valueLookup = valueLookup;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}