package com.valuequest.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "mfs.m_module")
public class Module implements Serializable {

	private static final long serialVersionUID = -4766966230697392291L;

	@Id
	@Column(name="module_id")
	private Long id;
	@Column(name="module_name", length=100)
	private String name;
	
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
}