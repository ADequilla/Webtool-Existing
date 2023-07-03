/**
 * 
 */
package com.valuequest.controller.monitoring.model;

import java.io.Serializable;

import org.springframework.ui.ExtendedModelMap;

public class DashboardOperationModel extends ExtendedModelMap implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1123132L;
	private String dateStart;
	private String dateEnd;
	private String branch;

	public String getDateStart() {
		return dateStart;
	}

	public void setDateStart(String dateStart) {
		this.dateStart = dateStart;
	}

	public String getDateEnd() {
		return dateEnd;
	}

	public void setDateEnd(String dateEnd) {
		this.dateEnd = dateEnd;
	}

	public String getBranch() {
		return branch;
	}

	public void setBranch(String branch) {
		this.branch = branch;
	}

}