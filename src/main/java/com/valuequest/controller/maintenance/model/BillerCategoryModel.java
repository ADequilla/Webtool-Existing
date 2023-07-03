package com.valuequest.controller.maintenance.model;

public class BillerCategoryModel {
	private Long id;
	private String billerCategoryName;
	private Long status;
	private Boolean isNew;

	public BillerCategoryModel() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getBillerCategoryName() {
		return billerCategoryName;
	}

	public void setBillerCategoryName(String billerCategoryName) {
		this.billerCategoryName = billerCategoryName;
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
