package com.valuequest.controller.maintenance.model;

public class MobilePrefixModel {
	private Long id;
	private Long productCategoryId;
	private Long billerProductId;
	private String prefixValue;
	private String productName;
	private Boolean isNew;

	public MobilePrefixModel() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getProductCategoryId() {
		return productCategoryId;
	}

	public void setProductCategoryId(Long productCategoryId) {
		this.productCategoryId = productCategoryId;
	}

	public Long getBillerProductId() {
		return billerProductId;
	}

	public void setBillerProductId(Long billerProductId) {
		this.billerProductId = billerProductId;
	}

	public String getPrefixValue() {
		return prefixValue;
	}

	public void setPrefixValue(String prefixValue) {
		this.prefixValue = prefixValue;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Boolean getIsNew() {
		return isNew;
	}

	public void setIsNew(Boolean isNew) {
		this.isNew = isNew;
	}

}
