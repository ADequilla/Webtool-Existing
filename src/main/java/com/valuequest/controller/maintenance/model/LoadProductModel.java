package com.valuequest.controller.maintenance.model;

import java.math.BigDecimal;

public class LoadProductModel {
	private Long productCategoryId;
	private Long loadProductId;
	private String loadProductName;
	private String description;
	private Long status;
	private Boolean isNew;

	public LoadProductModel() {
		super();
	}

	public Long getProductCategoryId() {
		return productCategoryId;
	}

	public void setProductCategoryId(Long productCategoryId) {
		this.productCategoryId = productCategoryId;
	}

	public Long getLoadProductId() {
		return loadProductId;
	}

	public void setLoadProductId(Long loadProductId) {
		this.loadProductId = loadProductId;
	}

	public String getLoadProductName() {
		return loadProductName;
	}

	public void setLoadProductName(String loadProductName) {
		this.loadProductName = loadProductName;
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
