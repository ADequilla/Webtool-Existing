/**
 * 
 */
package com.valuequest.controller.maintenance.model;

public class NewsModel {
	private Long id;
	private String subject;
	private String desc;
	private String periodStart;
	private String periodEnd;
	private String clientType;
	private String[] branch;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getPeriodStart() {
		return periodStart;
	}

	public void setPeriodStart(String periodStart) {
		this.periodStart = periodStart;
	}

	public String getPeriodEnd() {
		return periodEnd;
	}

	public void setPeriodEnd(String periodEnd) {
		this.periodEnd = periodEnd;
	}

	public String getClientType() {
		return clientType;
	}

	public void setClientType(String clientType) {
		this.clientType = clientType;
	}

	public String[] getBranch() {
		return branch;
	}

	public void setBranch(String[] branch) {
		this.branch = branch;
	}

}
