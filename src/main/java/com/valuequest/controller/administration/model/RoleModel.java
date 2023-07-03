/**
 * 
 */
package com.valuequest.controller.administration.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;


public class RoleModel {
	private Long id;
    private String name;
    private String description;
    
    @JsonProperty("menus")
    private List<StateModel> states = new ArrayList<StateModel>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<StateModel> getStates() {
		return states;
	}

	public void setStates(List<StateModel> states) {
		this.states = states;
	}
}
