package com.valuequest.controller.api.request.model;

import java.io.Serializable;

import javax.persistence.Entity;

@Entity
public class CustomerLoanListRequestModel implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -357747473465928269L;
	private String cid;
	

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}
	  
}
