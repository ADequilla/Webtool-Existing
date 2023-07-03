package com.valuequest.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="mfs.view_k2c_transaction_type")
public class K2CTransactionTypeLookup implements Serializable {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1465629474462340310L;
	
	public static final String LOOKUP_K2C_TRANSACTION_LOG_TYPE	= "K2C_TRAN_LOG_TYPE";

	@Id
	@Column(name="tran_url", length=100)
	private String tranURL;
	
	public String getTranURL() {
		return tranURL;
	}

	public void setTranType(String tranURL) {
		this.tranURL = tranURL;
	}

	
	


}
	