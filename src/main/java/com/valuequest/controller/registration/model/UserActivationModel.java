/**
 * 
 */
package com.valuequest.controller.registration.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.valuequest.controller.administration.model.StateModel;

public class UserActivationModel {

	@JsonProperty("users")
	private List<StateModel> states;

	public List<StateModel> getStates() {
		return states;
	}

	public void setStates(List<StateModel> states) {
		this.states = states;
	}
	
}