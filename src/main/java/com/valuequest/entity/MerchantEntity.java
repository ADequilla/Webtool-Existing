package com.valuequest.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "mfs.m_merchant")
public class MerchantEntity implements Serializable {

	private static final long serialVersionUID = -7088884612118602086L;

	@Id
	@SequenceGenerator(name = "sequence", sequenceName = "mfs.m_merchant_id_seq", initialValue = 1000, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence")
	@Column(name = "id")
	private Long id;

	@Column(name = "client_id")
	private Long clientId;
	
	@Column(name = "merchant_id")
	private String merchantId;
	
	@Column(name = "merchant_name")
	private String merchantName;
	
	@Column(name = "merchant_city")
	private String merchantCity;
	
	@Column(name = "merchant_account_number")
	private String merchantAccountNumber;
	
	@Column(name = "merchant_activated_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date merchantActivatedDate;
	
	@Column(name = "merchant_activated_by")
	private Long merchantActivatedBy;
	
	@Column(name = "merchant_expired_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date merchantExpiredDate;
	
	@Column(name = "merchant_deactivated_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date merchantDeactivatedDate;
	
	@Column(name = "merchant_deactivated_by")
	private Long merchantDeactivatedBy;
	
	@Column(name = "merchant_deactivated_remarks")
	private String merchantDeactivatedRemarks;
	
	@Column(name = "merchant_status")
	private Integer merchantStatus;
	
	@Column(name = "merchant_qr_code")
	private String merchantQrCode;
	
	@Column(name = "created_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate;

	@Column(name = "created_by")
	private Long createdBy;

	@Column(name = "last_updated_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastUpdatedDate;

	@Column(name = "last_updated_by")
	private Long lastUpdatedBy;

	@Column(name = "merchant_postal_code")
	private String merchantPostalCode;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getClientId() {
		return clientId;
	}

	public void setClientId(Long clientId) {
		this.clientId = clientId;
	}
	
	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}
	
	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}
	
	public String getMerchantCity() {
		return merchantCity;
	}

	public void setMerchantCity(String merchantCity) {
		this.merchantCity = merchantCity;
	}
	
	public String getMerchantAccountNumber() {
		return merchantAccountNumber;
	}

	public void setMerchantAccountNumber(String merchantAccountNumber) {
		this.merchantAccountNumber = merchantAccountNumber;
	}
	
	public Date getMerchantActivatedDate() {
		return merchantActivatedDate;
	}

	public void setMerchantActivatedDate(Date merchantActivatedDate) {
		this.merchantActivatedDate = merchantActivatedDate;
	}
	
	public Long getMerchantActivatedBy() {
		return merchantActivatedBy;
	}

	public void setMerchantActivatedBy(Long merchantActivatedBy) {
		this.merchantActivatedBy = merchantActivatedBy;
	}
	
	public Date getMerchantExpiredDate() {
		return merchantExpiredDate;
	}

	public void setMerchantExpiredDate(Date merchantExpiredDate) {
		this.merchantExpiredDate = merchantExpiredDate;
	}
	
	public Date getMerchantDeactivatedDate() {
		return merchantDeactivatedDate;
	}

	public void setMerchantDeactivatedDate(Date merchantDeactivatedDate) {
		this.merchantDeactivatedDate = merchantDeactivatedDate;
	}
	
	public Long getMerchantDeactivatedBy() {
		return merchantDeactivatedBy;
	}

	public void setMerchantDeactivatedBy(Long merchantDeactivatedBy) {
		this.merchantDeactivatedBy = merchantDeactivatedBy;
	}
	
	public String getMerchantDeactivatedRemarks() {
		return merchantDeactivatedRemarks;
	}

	public void setMerchantDeactivatedRemarks(String merchantDeactivatedRemarks) {
		this.merchantDeactivatedRemarks = merchantDeactivatedRemarks;
	}
	
	public Integer getMerchantStatus() {
		return merchantStatus;
	}

	public void setMerchantStatus(Integer merchantStatus) {
		this.merchantStatus = merchantStatus;
	}
	
	public String getMerchantQrCode() {
		return merchantQrCode;
	}

	public void setMerchantQrCode(String merchantQrCode) {
		this.merchantQrCode = merchantQrCode;
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
	
	public String getMerchantPostalCode() {
		return merchantPostalCode;
	}

	public void setMerchantPostalCode(String merchantPostalCode) {
		this.merchantPostalCode = merchantPostalCode;
	}
}