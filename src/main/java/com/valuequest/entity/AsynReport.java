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
import javax.persistence.Transient;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.valuequest.entity.security.SecUser;
import com.valuequest.entity.util.CustomJsonDatetimeSerializer;

@Entity
@Table(name = "mfs.t_report_asyn")
public class AsynReport implements Serializable {

	private static final long serialVersionUID = -4766966230697392291L;
	
	public static final String REPORT_TYPE_WEB_USER				= "WEB_USER";
	public static final String REPORT_TYPE_WEB_ACTIVITY			= "WEB_ACTIVITY";
	public static final String REPORT_TYPE_WEB_SMS_RESEND		= "WEB_SMS_RESEND";
	public static final String REPORT_TYPE_WEB_MOBILE_CHANGE	= "WEB_MOBILE_CHANGE";
	public static final String REPORT_TYPE_TRANSACTION			= "TRANSACTION";
	public static final String REPORT_TYPE_TRANSACTION_REPORT			= "TRANSACTION_REPORT";
	public static final String REPORT_TYPE_TRANS_SUSPICIOUS		= "TRANSACTION_SUSPICIOUS";
	public static final String REPORT_TYPE_TRANS_VALID			= "TRANSACTION_VALID";
	public static final String REPORT_TYPE_ACTIVITY_HISTORY		= "ACTIVITY_HISTORY";
	public static final String REPORT_TYPE_REMITTANCE_SENT		= "REMITTANCE_SENT_REPORT";
	public static final String REPORT_TYPE_REMITTANCE_CLAIMED	= "REMITTANCE_CLAIMED_REPORT";
	public static final String REPORT_TYPE_REMITTANCE_CANCELLED	= "REMITTANCE_CANCELLED_REPORT";
	public static final String REPORT_TYPE_PINPASSWD_CHANGE		= "PINPASSWD_CHANGE";
	public static final String REPORT_TYPE_LOGIN_LOGOUT			= "LOGIN_LOGOUT";
	public static final String REPORT_TYPE_JOURNALS				= "JOURNALS";
	public static final String REPORT_TYPE_STATEMENT_ACCOUNT	= "STATEMENT_ACCOUNT";
	public static final String REPORT_TYPE_REGISTERED_CLIENT	= "USER_ACTIVE";
	public static final String REPORT_TYPE_CS_DASHBOARD			= "CS_DASHBOARD";
	public static final String REPORT_TYPE_RECON_CCM			= "TRANSACTION_RECON_CCM";
	public static final String REPORT_TYPE_ENABLE_AGENT_FEATURES = "ENABLE_AGENT_FEATURES_REPORT";
	public static final String REPORT_TYPE_SMS_LOG   			= "SMS_LOG_REPORT";
	public static final String REPORT_TYPE_ACCOUNT_STATUS_REPORT   			= "ACCOUNT_STATUS_REPORT";
	public static final String REPORT_TYPE_MOBILE_COLLECTION_USER			= "MOBCOL_USER_REPORT";
	public static final String REPORT_TYPE_DIGIPAY_RECONCILIATION			= "DIGIPAY_RECONCILIATION_REPORT";
	public static final String REPORT_TYPE_IGATE_RECONCILIATION			= "IGATE_RECONCILIATION_REPORT";
	public static final String REPORT_TYPE_ACTIVATED_MERCHANT	= "ACTIVATED_MERCHANT_REPORT";
	public static final String REPORT_TYPE_DEACTIVATED_MERCHANT	= "DEACTIVATED_MERCHANT_REPORT";
	public static final String REPORT_TYPE_MC_AUDIT_TRAIL	= "MC_AUDIT_TRAIL_REPORT";
	public static final String REPORT_TYPE_MC_TRANSACTION	= "MC_TRANSACTION_REPORT";
	public static final String REPORT_TYPE_FAILED_ENROLLMENT	= "FAILED_ENROLLMENT_LIST_REPORT";
	public static final String REPORT_TYPE_USED_DEVICE	= "LIST_USED_DEVICE_REPORT";
	public static final String REPORT_TYPE_K2C_TRANSACTION_REPORT	= "K2C_TRANSACTION_REPORT";
	public static final String REPORT_TYPE_CORE_TRANSACTION_REPORT	= "CORE_TRANSACTION_REPORT";
	public static final String REPORT_TYPE_LIST_OF_AGENT	= "LIST_OF_AGENT_REPORT";

	@Id
	@SequenceGenerator(name = "sequence", sequenceName = "mfs.seq_report_asyn", initialValue = 1000, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence")
	@Column(name = "report_id")
	private Long id;

	@Column(name="report_type", length=100)
	private String type;
	
	@Column(name="report_param", length=500)
	private String param;
	
	@Column(name="report_status", length=10)
	private String status;
	
	@ManyToOne
	@JoinColumn(name="submited_by")
	private SecUser submitBy;
	
	@Column(name="submited_date")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonSerialize(using=CustomJsonDatetimeSerializer.class)
	private Date submitedDate;
	
	@Column(name="completed_date")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonSerialize(using=CustomJsonDatetimeSerializer.class)
	private Date completedDate;
	
	@Column(name="file_path", length=500)
	private String filePath;
	
	@Column(name="file_type", length=10)
	private String fileType;
	
	@Column(name="remark", length=500)
	private String remark;
	
	@Transient
	private String branch;
	@Transient
	private String roles;

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

	public String getParam() {
		return param;
	}

	public void setParam(String param) {
		this.param = param;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public SecUser getSubmitBy() {
		return submitBy;
	}

	public void setSubmitBy(SecUser submitBy) {
		this.submitBy = submitBy;
	}

	public Date getSubmitedDate() {
		return submitedDate;
	}

	public void setSubmitedDate(Date submitedDate) {
		this.submitedDate = submitedDate;
	}

	public Date getCompletedDate() {
		return completedDate;
	}

	public void setCompletedDate(Date completedDate) {
		this.completedDate = completedDate;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getBranch() {
		return branch;
	}

	public void setBranch(String branch) {
		this.branch = branch;
	}

	public String getRole() {
		return roles;
	}

	public void setRole(String role) {
		this.roles = role;
	}
	
	public String getCriteria() {
		String[] params = getParam().split("\\|");
		
		return params[0] + " - " + params[1];
	}
}