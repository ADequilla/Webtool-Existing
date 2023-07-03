package com.valuequest.controller.maintenance.model;

public class PartnerModel {
	private Long id;
	private String partnerId;
	private String partnerName;
	private String partnerDesc;
	private Long status;

	private String partnerAccount;
	private String myPartnerId;
	private String partnerApiUrl;
	private String myPartnerToken;
	private String secretKey;
	private Long mriGroup;
	private String merchantUrl;
	private String merchantPrefix;
	private String cashoutConfirmUrl;
	private Boolean isNew;

	public PartnerModel() {
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

	public String getPartnerId() {
		return partnerId;
	}

	public void setPartnerId(String partnerId) {
		this.partnerId = partnerId;
	}

	public String getPartnerName() {
		return partnerName;
	}

	public void setPartnerName(String partnerName) {
		this.partnerName = partnerName;
	}

	public String getPartnerDesc() {
		return partnerDesc;
	}

	public void setPartnerDesc(String partnerDesc) {
		this.partnerDesc = partnerDesc;
	}

	public Long getStatus() {
		return status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	public String getPartnerAccount() {
		return partnerAccount;
	}

	public void setPartnerAccount(String partnerAccount) {
		this.partnerAccount = partnerAccount;
	}

	public String getMyPartnerId() {
		return myPartnerId;
	}

	public void setMyPartnerId(String myPartnerId) {
		this.myPartnerId = myPartnerId;
	}

	public String getPartnerApiUrl() {
		return partnerApiUrl;
	}

	public void setPartnerApiUrl(String partnerApiUrl) {
		this.partnerApiUrl = partnerApiUrl;
	}

	public String getMyPartnerToken() {
		return myPartnerToken;
	}

	public void setMyPartnerToken(String myPartnerToken) {
		this.myPartnerToken = myPartnerToken;
	}

	public String getSecretKey() {
		return secretKey;
	}

	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}

	public Long getMriGroup() {
		return mriGroup;
	}

	public void setMriGroup(Long mriGroup) {
		this.mriGroup = mriGroup;
	}


	public String getMerchantUrl() {
		return merchantUrl;
	}


	public void setMerchantUrl(String merchantUrl) {
		this.merchantUrl = merchantUrl;
	}


	public String getMerchantPrefix() {
		return merchantPrefix;
	}


	public void setMerchantPrefix(String merchantPrefix) {
		this.merchantPrefix = merchantPrefix;
	}


	public String getCashoutConfirmUrl() {
		return cashoutConfirmUrl;
	}


	public void setCashoutConfirmUrl(String cashoutConfirmUrl) {
		this.cashoutConfirmUrl = cashoutConfirmUrl;
	}
}