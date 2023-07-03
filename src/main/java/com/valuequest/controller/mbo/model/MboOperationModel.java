package com.valuequest.controller.mbo.model;

public class MboOperationModel {
	private Long id;
	private Integer enabled;
	private String hoursStart;
	private String hoursEnd;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getEnabled() {
		return enabled;
	}

	public void setEnabled(Integer enabled) {
		this.enabled = enabled;
	}

	public String getHoursStart() {
		return hoursStart;
	}

	public void setHoursStart(String hoursStart) {
		this.hoursStart = hoursStart;
	}

	public String getHoursEnd() {
		return hoursEnd;
	}

	public void setHoursEnd(String hoursEnd) {
		this.hoursEnd = hoursEnd;
	}
}