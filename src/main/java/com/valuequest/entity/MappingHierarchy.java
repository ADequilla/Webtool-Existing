package com.valuequest.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "mfs.t_mapping_hierarchy")
public class 	MappingHierarchy implements Serializable {

	private static final long serialVersionUID = -4766966230697392291L;
	
	public static final String STRUCTURE_INST 			= "INSTITUTION";
	public static final String STRUCTURE_BRANCH 		= "BRANCH";
	public static final String STRUCTURE_UNIT 			= "UNIT";
	public static final String STRUCTURE_CENTER 		= "CENTER";

	@Id
	@SequenceGenerator(name = "sequence", sequenceName = "mfs.seq_t_mapping_hierarchy", initialValue = 1000, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence")
	@Column(name = "hierarchy_id")
	private Long id;

	@ManyToOne
	@JoinColumn(name = "inst_code")
	private StructureInstitution institution;

	@ManyToOne
	@JoinColumn(name = "branch_code")
	private StructureBranch branch;

	@ManyToOne
	@JoinColumn(name = "unit_code")
	private StructureUnit unit;

	@ManyToOne
	@JoinColumn(name = "center_code")
	private StructureCenter center;

	@Column(name = "created_date")
	@Temporal(TemporalType.DATE)
	private Date createdDate;
	@Column(name = "created_by")
	private Long createdBy;
	@Column(name = "last_updated_date")
	@Temporal(TemporalType.DATE)
	private Date lastUpdatedDate;
	@Column(name = "last_updated_by")
	private Long lastUpdatedBy;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public StructureInstitution getInstitution() {
		return institution;
	}

	public void setInstitution(StructureInstitution institution) {
		this.institution = institution;
	}

	public StructureBranch getBranch() {
		return branch;
	}

	public void setBranch(StructureBranch branch) {
		this.branch = branch;
	}

	public StructureUnit getUnit() {
		return unit;
	}

	public void setUnit(StructureUnit unit) {
		this.unit = unit;
	}

	public StructureCenter getCenter() {
		return center;
	}

	public void setCenter(StructureCenter center) {
		this.center = center;
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

	public Date getLastUpdatedDate() {
		return lastUpdatedDate;
	}

	public void setLastUpdatedDate(Date lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}

	public Long getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdatedBy(Long lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}
}
