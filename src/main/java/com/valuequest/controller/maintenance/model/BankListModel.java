package com.valuequest.controller.maintenance.model;

import java.math.BigDecimal;

public class BankListModel {
	private Long id;
	private String bankCode;
	private String bankName;
	private String shortName;
	private String bankBic;
	private Boolean isNew;

	public BankListModel() {
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


	public String getBankCode() {
		return bankCode;
	}


	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}


	public String getBankName() {
		return bankName;
	}


	public void setBankName(String bankName) {
		this.bankName = bankName;
	}


	public String getShortName() {
		return shortName;
	}


	public void setShortName(String shortName) {
		this.shortName = shortName;
	}


	public String getBankBic() {
		return bankBic;
	}


	public void setBankBic(String bankBic) {
		this.bankBic = bankBic;
	}

}
