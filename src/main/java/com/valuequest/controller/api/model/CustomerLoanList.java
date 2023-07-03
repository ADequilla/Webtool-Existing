package com.valuequest.controller.api.model;

import java.util.ArrayList;


//import com.valuequest.controller.report.JSONException;


public class CustomerLoanList{
	/**
	 * 
	 */
	//private static final long serialVersionUID = 7815642825546966221L;
	private String acc;
	private String status;
	private String dateRelease;
	private String acctType;
	private int principal;
	private int interest;
	private int oth;
	private int balance;
	private int term;
	private int paidTerm;
	
	
	
	
	public String getAcc() {
		return acc;
	}




	public String getStatus() {
		return status;
	}




	public String getDateRelease() {
		return dateRelease;
	}




	public String getAcctType() {
		return acctType;
	}




	public int getPrincipal() {
		return principal;
	}




	public int getInterest() {
		return interest;
	}




	public int getOth() {
		return oth;
	}




	public int getBalance() {
		return balance;
	}




	public int getTerm() {
		return term;
	}




	public int getPaidTerm() {
		return paidTerm;
	}

	



	
}
