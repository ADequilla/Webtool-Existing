package com.valuequest.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="mfs.m_lookup")
public class Lookup implements Serializable {

	private static final long serialVersionUID = 4657650934308043988L;
	
	public static final String LOOKUP_MUSER_STATUS						= "MCUSER_STATUS";			// MC User Web Status Lookup Type
	public static final String LOOKUP_MUSER_STATUS_ACTIVE				= "ACTIVE";					// Active MC User Status
	public static final String LOOKUP_MUSER_STATUS_INACTIVE				= "INACTIVE";				// Inactive MC User Status
	public static final String LOOKUP_MUSER_STATUS_BLOCKED_PASS			= "BLOCKED_PASS";			// Blocked pass MC User Status
	public static final String LOOKUP_MUSER_STATUS_BLOCKED_PIN			= "BLOCKED_PIN";			// Blocked pin MC User Status
	public static final String LOOKUP_MUSER_STATUS_WAIT_ACTIOVATION		= "WAIT_ACTIVATION";		// Wait for actiovation MC User Status
	public static final String LOOKUP_MUSER_STATUS_WAIT_REGISTRATION	= "WAIT_REGISTRATION";		// Wait for registration MC User Status
	public static final String LOOKUP_MUSER_STATUS_BLOCK_ACTCODE		= "BLOCK_ACTCODE";			// Blocked act. code MC User Status
	
	public static final String LOOKUP_USER_STATUS				= "USER_STATUS";	// User Web Status Lookup Type
	public static final String LOOKUP_USER_STATUS_ACTIVE		= "ACTIVE";			// Active User Status
	public static final String LOOKUP_USER_STATUS_INACTIVE		= "INACTIVE";		// Inactive User Status
	public static final String LOOKUP_USER_STATUS_LOCK			= "LOCK";			// Lock User Status
	public static final Boolean LOOKUP_LOGIN_STATUS 			= true;
	
	public static final String LOOKUP_USER_STATUS_VALIDATE		= "ACTIVE";			// Validate Active User Status
	
	public static final String LOOKUP_CONCURRENT_USER			= "CONCURRENT_USER";	// Concurrent User Web Status Lookup Type
	public static final String LOOKUP_CONCURRENT_USER_ACTIVE	= "ACTIVE";			// Active Concurrent User Status
	public static final String LOOKUP_CONCURRENT_USER_INACTIVE	= "INACTIVE";		// Inactive Concurrent User Status
	
	public static final String LOOKUP_ACTIVATION_STATUS			= "ACTIVATION_STATUS";	// Activation Status Lookup Type
	public static final String LOOKUP_ACTIVATION_STATUS_ACTIVE	= "ACTIVE";				// Active Status
	public static final String LOOKUP_ACTIVATION_STATUS_INACTIVE= "INACTIVE";			// Inactive Status
	public static final String LOOKUP_ACTIVATION_STATUS_WAIT	= "WAIT_ACTIVATION";	// Wait activation Status

	public static final String LOOKUP_APPROVAL_STATUS			= "APPROVAL_STATUS";	// Approval Lookup Type
	public static final String LOOKUP_APPROVAL_STATUS_APPROVED	= "APPROVED";			// Approval Approved
	public static final String LOOKUP_APPROVAL_STATUS_REJECTED	= "REJECTED";			// Approval Rejected
	public static final String LOOKUP_APPROVAL_STATUS_PENDING	= "PENDING_APPROVAL";	// Approval Pending
	
	public static final String LOOKUP_SLF_REQUEST				= "SLF_REQUEST";	// SLF Request Lookup Type
	public static final String LOOKUP_SLF_REQUEST_CONFIRMED		= "CONFIRMED";		// SLF Confirmed
	public static final String LOOKUP_SLF_REQUEST_REJECTED		= "REJECTED";		// SLF Rejecter
	
	public static final String LOOKUP_CS_STATUS					= "CS_STATUS"; 	// Customer Service Ticket Status Lookup Type
	public static final String LOOKUP_CS_STATUS_OPEN			= "OPEN"; 		// open customer service ticket
	public static final String LOOKUP_CS_STATUS_CLOSE			= "CLOSED";		// close customer service ticket
	
	public static final String LOOKUP_REPORT_STATUS				= "REPORT_STATUS";	// Report Status Lookup Type
	public static final String LOOKUP_REPORT_STATUS_WAITING		= "WAITING";		// initial status for asyn report
	public static final String LOOKUP_REPORT_STATUS_COMPLETED	= "COMPLETED";		// initial status for asyn report
	
	public static final String LOOKUP_REPORT_TYPE				= "REPORT_TYPE";	// Report Type Lookup
	public static final String LOOKUP_REPORT_TYPE_PDF			= "PDF";			// report file type = PDF
	
	public static final String LOOKUP_SMS_STATUS				= "SMS_STATUS";
	public static final String LOOKUP_SMS_STATUS_UNSENT			= "UNSENT";
	
	public static final String LOOKUP_CLIENT_TYPE				= "CLIENT_TYPE";
	public static final String LOOKUP_DESIGNATION				= "DESIGNATION";
	public static final String LOOKUP_SMS_LOG_STATUS			= "SMS_LOG_STATUS";
	public static final String LOOKUP_SUSPICIOUS_TYPE			= "SUSPICIOUS_TYPE";
	public static final String LOOKUP_SUSPICIOUS_STATUS			= "SUSPICIOUS_STATUS";
	public static final String LOOKUP_CS_ACTION					= "CS_ACTION";
	public static final String LOOKUP_TRANSACTION_TYPE			= "TRAN_TYPE";
	public static final String LOOKUP_TRANSACTION_LOG_TYPE		= "TRAN_LOG_TYPE";
	public static final String LOOKUP_COMPLEXITY_LEVEL			= "COMPLEXITY_LEVEL";
	public static final String LOOKUP_PARAMCONFIG_TYPE			= "PARAMCONFIG_TYPE";
	public static final String LOOKUP_TRANSACTION_STATUS		= "TRAN_STATUS";
	public static final String LOOKUP_MODIFICATION_TYPE			= "MODIFICATION_TYPE";
	public static final String LOOKUP_EWALLET_TRANS_TYPE		= "EWALLET_TRANS_TYPE";
	public static final String LOOKUP_ALL_CLIENT_TYPE			= "ALL_CLIENT_TYPE";
	public static final String LOOKUP_BILLER_TYPE				= "BILLER_TYPE";
	public static final String LOOKUP_FEE_CLIENT_TYPE			= "FEE_CLIENT_TYPE";
	public static final String LOOKUP_MC_ACTION					= "MC_ACTION";
	public static final String LOOKUP_MC_CHANGES				= "MC_CHANGES";
	public static final String LOOKUP_APPLICATION				= "APPLICATION";
	public static final String LOOKUP_MC_APPROVAL_STATUS		= "MC_APPROVAL_STATUS";
	
	public static final String LOOKUP_UPLOAD_STATUS				= "UPLOAD_STATUS";
	public static final String LOOKUP_UPLOAD_STATUS_UPLOADED	= "UPLOADED";			// initial status for upload csv from web application
	public static final String LOOKUP_REMITTANCE_STATUS			= "REMITTANCE_STATUS";
	public static final String LOOKUP_BILLS_PARAMETER			= "BILLS_PARAMETER";
	public static final String LOOKUP_STATUS_DEVICE				= "STATUS_DEVICE";
	public static final String LOOKUP_CLIENT_TYPE_DEVICE		= "CLIENT_TYPE_DEVICE";
	public static final String LOOKUP_PARTNER					= "STATUS_PARTNER";
	public static final String LOOKUP_MRI_GROUP					= "MRI_GROUP_PARTNER";
	public static final String LOOKUP_PRODUCT_SERVICES_STATUS	= "PRODUCT_SERVICES_STATUS";
	
	@Id
	@Column(name="lookup_id")
	private Long id;
	@Column(name="lookup_type", length=30)
	private String type;
	@Column(name="lookup_value", length=50)
	private String value;
	@Column(name="lookup_desc", length=100)
	private String description;
	@Column(name="lookup_display", length=1)
	private String activeFlag;
	
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
	
	public String getActiveFlag() {
		return activeFlag;
	}
	public void setActiveFlag(String activeFlag) {
		this.activeFlag = activeFlag;
	}
}
