package com.valuequest.entity.security;

import java.io.Serializable;
import java.util.Date;

import com.valuequest.entity.Entity;

public class SecUserrole implements Serializable, Entity {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4574678311322292520L;
	
	private Long id;
	private SecUser user;
	private SecRole role;
	private Long roleId;
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

	public SecUser getUser() {
		return user;
	}

	public void setUser(SecUser user) {
		this.user = user;
	}

	public SecRole getRole() {
		return role;
	}

	public void setRole(SecRole role) {
		this.role = role;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
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

	public boolean equals(SecUserrole secRole) {
		return getId().equals(secRole.getId());
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (obj instanceof SecUserrole) {
			SecUserrole secRole = (SecUserrole) obj;
			return equals(secRole);
		}

		return false;
	}
}
