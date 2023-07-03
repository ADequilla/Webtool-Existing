package com.valuequest.controller.monitoring.model;

public class TransFinanceModel {
	private Long id;
	private String coreRefNo;
	private String mobileRefNo;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public void setCoreRefNo(String coreRefNo){
		this.coreRefNo=coreRefNo;
	}
	public String getCoreRefNo(){
		return coreRefNo;
	}
	public void setmobileRefNo(String mobileRefNo){
		this.mobileRefNo=mobileRefNo;
	}
	public String getMobileRefNo(){
		return mobileRefNo;
	}

}
