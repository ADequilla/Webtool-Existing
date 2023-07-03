package com.valuequest.controller.monitoring.model;


public class ListUsedDeviceModel {
	private Long id;
	private String deviceId;
	private String deviceModel;
	private String cid;
	private String branchCode;
	private String mobileNumber;
	private String clientName;
	private String clientType;
	private Long deviceStatus;
	private Boolean isNew;

	public ListUsedDeviceModel() {
		super();
	}

	

	public Boolean getIsNew() {
		return isNew;
	}

	public void setIsNew(Boolean isNew) {
		this.isNew = isNew;
	}



	public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
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



	public String getCid() {
		return cid;
	}



	public void setCid(String cid) {
		this.cid = cid;
	}



	public String getBranchCode() {
		return branchCode;
	}



	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}



	public String getMobileNumber() {
		return mobileNumber;
	}



	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}



	public String getClientName() {
		return clientName;
	}



	public void setClientName(String clientName) {
		this.clientName = clientName;
	}



	public String getClientType() {
		return clientType;
	}



	public void setClientType(String clientType) {
		this.clientType = clientType;
	}



	public Long getDeviceStatus() {
		return deviceStatus;
	}



	public void setDeviceStatus(Long deviceStatus) {
		this.deviceStatus = deviceStatus;
	}

}
