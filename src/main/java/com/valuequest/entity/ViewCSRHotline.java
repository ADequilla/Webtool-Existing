package com.valuequest.entity;

import java.io.Serializable;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.valuequest.entity.util.CustomJsonDatetime2Serializer;

@Entity
@Table(name = "mfs.view_csr_hotline")
public class ViewCSRHotline implements Serializable {
    
    @Id
	@Column(name = "id")
	private Long id;

	@Column(name = "contact_number")
	private String contactNumber;

    @Column(name = "network_provider")
	private String networkProvider;

    @Column(name = "created_date")
    @Temporal(TemporalType.TIMESTAMP)
	@JsonSerialize(using = CustomJsonDatetime2Serializer.class)
	private Date createdDate;

    @Column(name = "created_by")
	private String createdBy;

    @Column(name = "last_updated_date")
    @Temporal(TemporalType.TIMESTAMP)
	@JsonSerialize(using = CustomJsonDatetime2Serializer.class)
	private Date lastUpdatedDate;

    @Column(name = "last_updated_by")
	private String lastUpdatedBy;

    @Column(name = "inst_code")
	private String instCode;

    @Column(name = "inst_desc")
	private String instDesc;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getNetworkProvider() {
        return networkProvider;
    }

    public void setNetworkProvider(String networkProvider) {
        this.networkProvider = networkProvider;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public void setLastUpdatedDate(Date lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }

    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public String getInstDesc() {
        return instDesc;
    }

    public void setInstDesc(String instDesc) {
        this.instDesc = instDesc;
    }

    
}
