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
@Table(name = "mfs.m_user_unit")
public class UserUnit implements Serializable {

	private static final long serialVersionUID = -4766966230697392291L;
	
	@Id
	@SequenceGenerator(name = "sequence", sequenceName = "mfs.seq_m_user_unit", initialValue = 1000, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence")
	@Column(name = "user_unit_id")
	private Long id;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private SecUser user;

	@ManyToOne
	@JoinColumn(name = "unit_code", insertable = false, updatable = false)
	private StructureUnit unit;

	@Column(name = "unit_code", length = 15)
	private String unitCode;

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

	public StructureUnit getUnit() {
		return unit;
	}

	public void setUnit(StructureUnit unit) {
		this.unit = unit;
	}

	public String getUnitCode() {
		return unitCode;
	}

	public void setUnitCode(String unitCode) {
		this.unitCode = unitCode;
	}
}
