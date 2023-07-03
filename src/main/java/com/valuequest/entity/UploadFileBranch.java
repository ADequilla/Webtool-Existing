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

@Entity
@Table(name="mfs.t_upload_branch")
public class UploadFileBranch implements Serializable {

	private static final long serialVersionUID = -4766966230697392291L;
	
	@Id
	@SequenceGenerator(name="sequence", sequenceName="mfs.seq_t_upload_branch", initialValue=1000, allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="sequence")
	@Column(name="upload_branch_id")
	private Long id;
	
	@ManyToOne
	@JoinColumn(name="upload_id")
	private UploadFile uploadFile;
	
	@ManyToOne
	@JoinColumn(name="branch_code")
	private StructureBranch branch;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public UploadFile getUploadFile() {
		return uploadFile;
	}

	public void setUploadFile(UploadFile uploadFile) {
		this.uploadFile = uploadFile;
	}

	public StructureBranch getBranch() {
		return branch;
	}

	public void setBranch(StructureBranch branch) {
		this.branch = branch;
	}
}
