package com.valuequest.controller.configuration.model;

public class RoutesModel {
	private Long id;
	private String trnCode;
	private String description;
	private Long status;
	private Boolean isNew;
	
	
	public RoutesModel () {
		super();
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTrnCode() {
		return trnCode;
	}
	public void setTrnCode(String trnCode) {
		this.trnCode = trnCode;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Long getStatus() {
		return status;
	}
	public void setStatus(Long status) {
		this.status = status;
	}
	public Boolean getIsNew() {
		return isNew;
	}
	public void setIsNew(Boolean isNew) {
		this.isNew = isNew;
	}

	
}
