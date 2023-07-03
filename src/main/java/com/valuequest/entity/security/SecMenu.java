package com.valuequest.entity.security;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.valuequest.entity.Entity;

public class SecMenu implements Serializable, Entity {
	/**
	 * 
	 */
	private static final long serialVersionUID = -11061142604251222L;
	
	private Long id;
	private String name;
	private String url;
	private String icon;
	private String description;
	private Integer sequence;
	private Integer enabled;

	private SecMenu parent;
	private Set<SecMenu> childs = new HashSet<SecMenu>(0);
	private Set<SecComponent> components = new HashSet<SecComponent>(0);

	public boolean isNew() {
		return this.getId() == null;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getSequence() {
		return sequence;
	}

	public void setSequence(Integer sequence) {
		this.sequence = sequence;
	}

	public Integer getEnabled() {
		return enabled;
	}

	public void setEnabled(Integer enabled) {
		this.enabled = enabled;
	}

	public SecMenu getParent() {
		return parent;
	}

	public void setParent(SecMenu parent) {
		this.parent = parent;
	}

	public Set<SecMenu> getChilds() {
		return childs;
	}

	public void setChilds(Set<SecMenu> childs) {
		this.childs = childs;
	}

	public Set<SecComponent> getComponents() {
		return components;
	}

	public void setComponents(Set<SecComponent> components) {
		this.components = components;
	}

	public boolean equals(SecMenu secMenu) {
		return getId().equals(secMenu.getId());
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (obj instanceof SecMenu) {
			SecMenu secMenu = (SecMenu) obj;
			return equals(secMenu);
		}

		return false;
	}
}
