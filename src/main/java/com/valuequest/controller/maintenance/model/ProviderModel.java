package com.valuequest.controller.maintenance.model;

public class ProviderModel {
	private Long id;
	private String providerName;
	private String description;
	private String providerAlias;
	private Long status;
	private Boolean isNew;

	public ProviderModel() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getProviderName() {
		return providerName;
	}

	public void setProviderName(String providerName) {
		this.providerName = providerName;
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

	public String getProviderAlias() {
		return providerAlias;
	}

	public void setProviderAlias(String providerAlias) {
		this.providerAlias = providerAlias;
	}

}
