package com.valuequest.controller.configuration.model;

public class TransactionModel {
	private Long id;
	private String url;
	private String cid;
	private String source;
	
	
	public TransactionModel () {
		super();
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getUrl() {
		return url;
	}


	public void setUrl(String url) {
		this.url = url;
	}


	public String getCid() {
		return cid;
	}


	public void setCid(String cid) {
		this.cid = cid;
	}


	public String getSource() {
		return source;
	}


	public void setSource(String source) {
		this.source = source;
	}
	
	
	
}
