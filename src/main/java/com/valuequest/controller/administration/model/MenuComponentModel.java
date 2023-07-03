/**
 * 
 */
package com.valuequest.controller.administration.model;

import java.util.List;

import com.valuequest.entity.security.SecComponent;
import com.valuequest.entity.security.SecMenu;


public class MenuComponentModel {
	private SecMenu menu;
	private List<SecComponent> components;
	
	public SecMenu getMenu() {
		return menu;
	}
	public void setMenu(SecMenu menu) {
		this.menu = menu;
	}
	public List<SecComponent> getComponents() {
		return components;
	}
	public void setComponents(List<SecComponent> components) {
		this.components = components;
	}
}
