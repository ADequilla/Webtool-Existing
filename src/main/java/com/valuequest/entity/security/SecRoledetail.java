package com.valuequest.entity.security;

import java.io.Serializable;
import java.util.Date;

import com.valuequest.entity.Entity;

public class SecRoledetail implements Serializable, Entity {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4574678311322292520L;
	
	private Long id;
	private SecRole role;
	private SecComponent component;
	private Long componentId;
	private Date createdDate;
	private Long createdBy;
	private Long lastUpdatedBy;
	private Date lastUpdatedDate;

	public boolean isNew() {
		return getId() == null;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public SecRole getRole() {
		return role;
	}

	public void setRole(SecRole role) {
		this.role = role;
	}

	public SecComponent getComponent() {
		return component;
	}

	public void setComponent(SecComponent component) {
		this.component = component;
	}

	public Long getComponentId() {
		return componentId;
	}

	public void setComponentId(Long componentId) {
		this.componentId = componentId;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	public Long getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdatedBy(Long lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	public Date getLastUpdatedDate() {
		return lastUpdatedDate;
	}

	public void setLastUpdatedDate(Date lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}

	public boolean equals(SecRoledetail secRole) {
		return getId().equals(secRole.getId());
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (obj instanceof SecRoledetail) {
			SecRoledetail secRole = (SecRoledetail) obj;
			return equals(secRole);
		}

		return false;
	}
}
