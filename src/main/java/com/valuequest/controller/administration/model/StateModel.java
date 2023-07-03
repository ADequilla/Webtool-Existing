package com.valuequest.controller.administration.model;

public class StateModel {
	
	private Long id;
	private String code;
	private String state;
	
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * @return the state
	 */
	public String getState() {
		return state;
	}
	/**
	 * @param state the state to set
	 */
	public void setState(String state) {
		this.state = state;
	}
	/**
	 * @return the id
	 */
	public String getCode() {
		return code;
	}
	/**
	 * @param id the id to set
	 */
	public void setCode(String code) {
		this.code = code;
	}
}
