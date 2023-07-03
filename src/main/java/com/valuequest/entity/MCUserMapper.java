package com.valuequest.entity;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.valuequest.entity.util.CustomJsonDateSerializer;
import com.valuequest.entity.util.CustomJsonDatetime2Serializer;

@Entity
@Table(name = "mfs.view_mcuser_report")
public class MCUserMapper implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7035824885928256744L;
	
	@Id
	@Column(name = "id")
	private Long id;
	
	@Column(name = "mcu_id")
    private String mcuId;
	
	@Column(name = "staff_id")
    private String staffId;
	
	@Column(name = "full_name")
    private String fullName;
	
	@Column(name = "designation")
    private String designation;
	
	@Column(name = "mobile_number")
    private String mobileNumber;
	
	@Column(name = "branch_desc")
    private String branchDesc;
	
	@Column(name = "unit_desc")
    private String unitDesc;
	
	@Column(name = "internal_account")
    private String internalAccount;
	
	@Column(name = "email")
    private String email;
	
	@Column(name = "ao_status")
    private String aoStatus;
	
	@Column(name = "imei")
    private String deviceId;
	
	@Column(name = "enabled")
	private int enabled;
	
	@Column(name = "device_model")
    private String deviceModel;
	
	@Column(name = "created_by")
    private String createdBy;
	
	@Column(name = "created_date")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonSerialize(using = CustomJsonDatetime2Serializer.class)
    private Date createdOn;
	
    @Column(name = "approved_date")
	@Temporal(TemporalType.DATE)
	@JsonSerialize(using = CustomJsonDateSerializer.class)
    private Date approvedDate;
    
    @Column(name = "approved_by")
    private String approvedBy;
    
    @Column(name = "activated_date")
	@Temporal(TemporalType.DATE)
	@JsonSerialize(using = CustomJsonDateSerializer.class)
    private Date activatedDate;
    
    @Column(name = "registered_date")
	@Temporal(TemporalType.TIMESTAMP)
    @JsonSerialize(using = CustomJsonDatetime2Serializer.class)
    private Date registeredDate;
    
    @Column(name = "authorized_date")
   	@Temporal(TemporalType.TIMESTAMP)
   	@JsonSerialize(using = CustomJsonDatetime2Serializer.class)
    private Date authorizedDate;
       
    @Column(name = "authorized_by")
    private String authorizedBy;
    
    public MCUserMapper() {
		super();
	}
    
	public String getStaffId() {
		return staffId;
	}
	public void setStaffId(String staffId) {
		this.staffId = staffId;
	}
	public String getMcuId() {
		return mcuId;
	}
	public void setMcuId(String mcuId) {
		this.mcuId = mcuId;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getDesignation() {
		return designation;
	}
	public void setDesignation(String designation) {
		this.designation = designation;
	}
	public String getMobileNumber() {
		return mobileNumber;
	}
	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}
	public String getBranchDesc() {
		return branchDesc;
	}
	public void setBranchDesc(String branchDesc) {
		this.branchDesc = branchDesc;
	}
	public String getUnitDesc() {
		return unitDesc;
	}
	public void setUnitDesc(String unitDesc) {
		this.unitDesc = unitDesc;
	}
	public String getInternalAccount() {
		return internalAccount;
	}
	public void setInternalAccount(String internalAccount) {
		this.internalAccount = internalAccount;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getAoStatus() {
		return aoStatus;
	}
	public void setAoStatus(String aoStatus) {
		this.aoStatus = aoStatus;
	}
	public String getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	public String getDeviceModel() {
		return deviceModel;
	}
	public void setDeviceModel(String deviceModel) {
		this.deviceModel = deviceModel;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public Date getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}
	public Date getApprovedDate() {
		return approvedDate;
	}
	public void setApprovedDate(Date approvedDate) {
		this.approvedDate = approvedDate;
	}
	public String getApprovedBy() {
		return approvedBy;
	}
	public void setApprovedBy(String approvedBy) {
		this.approvedBy = approvedBy;
	}
	public Date getActivatedDate() {
		return activatedDate;
	}
	public void setActivatedDate(Date activatedDate) {
		this.activatedDate = activatedDate;
	}
	public Date getRegisteredDate() {
		return registeredDate;
	}
	public void setRegisteredDate(Date registeredDate) {
		this.registeredDate = registeredDate;
	}
	public int getEnabled() {
		return enabled;
	}
	public void setEnabled(int enabled) {
		this.enabled = enabled;
	}
	public Date getAuthorizedDate() {
		return authorizedDate;
	}
	public void setAuthorizedDate(Date authorizedDate) {
		this.authorizedDate = authorizedDate;
	}
	public String getAuthorizedBy() {
		return authorizedBy;
	}
	public void setAuthorizedBy(String authorizedBy) {
		this.authorizedBy = authorizedBy;
	}
	/*public String getCreatedDateString() {
		return createdDateString;
	}
	public void setCreatedDateString(String createdDateString) {
		this.createdDateString = createdDateString;
	}
	public String getRegisteredDateString() {
		return registeredDateString;
	}
	public void setRegisteredDateString(String registeredDateString) {
		this.registeredDateString = registeredDateString;
	}
	public String getAuthorizedDateString() {
		return authorizedDateString;
	}
	public void setAuthorizedDateString(String authorizedDateString) {
		this.authorizedDateString = authorizedDateString;
	}*/
   
}
