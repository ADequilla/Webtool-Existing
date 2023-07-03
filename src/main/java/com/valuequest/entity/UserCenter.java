package com.valuequest.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.valuequest.entity.security.SecUser;

@Entity
@Table(name = "mfs.m_user_center")
public class UserCenter implements Serializable {

	private static final long serialVersionUID = -4766966230697392291L;
	
	@Id
	@SequenceGenerator(name = "sequence", sequenceName = "mfs.seq_m_user_center", initialValue = 1000, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence")
	@Column(name = "user_center_id")
	private Long id;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private SecUser user;

	@ManyToOne
	@JoinColumn(name = "center_code", insertable = false, updatable = false)
	private StructureCenter center;

	@Column(name = "center_code", length = 15)
	private String centerCode;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public SecUser getUser() {
		return user;
	}

	public void setUser(SecUser user) {
		this.user = user;
	}

	public StructureCenter getCenter() {
		return center;
	}

	public void setCenter(StructureCenter center) {
		this.center = center;
	}

	public String getCenterCode() {
		return centerCode;
	}

	public void setCenterCode(String centerCode) {
		this.centerCode = centerCode;
	}
}
