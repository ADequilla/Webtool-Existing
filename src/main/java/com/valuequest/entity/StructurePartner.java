package com.valuequest.entity;

import java.io.Serializable;
import java.math.BigDecimal;
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

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.valuequest.entity.util.CustomJsonDateSerializer;

@Entity
@Table(name = "mfs.m_partner")
public class StructurePartner implements Serializable {

	private static final long serialVersionUID = 3968242135510516883L;
	
	@Id
	@SequenceGenerator(name = "sequence", sequenceName = "mfs.m_partner_id_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence")
	@Column(name = "id")
	private Long id;
	@Column(name = "partner_id")
	private String partnerId;
	@Column(name = "partner_name")
	private String partnerName;
	@Column(name = "partner_desc")
	private String partnerDesc;
	@Column(name = "status")
	private Long status;
	
	@Column(name = "created_date")
	@Temporal(TemporalType.DATE)
	@JsonSerialize(using = CustomJsonDateSerializer.class)
	private Date createdDate;
	@Column(name = "created_by")
	private Long createdBy;
	@Column(name = "last_updated_date")
	@Temporal(TemporalType.DATE)
	private Date updatedDate;
	@Column(name = "last_updated_by")
	private Long updatedBy;

	@Column(name = "partner_account")
	private String partnerAccount;
	@Column(name = "my_partner_id")
	private String myPartnerId;
	@Column(name = "partner_api_url")
	private String partnerApiUrl;
	@Column(name = "my_partner_token")
	private String myPartnerToken;
	@Column(name = "my_partner_secret_key")
	private String secretKey;
	@Column(name = "mri_group")
	private Long mriGroup;
	@Column(name = "merchant_payment_callback_url")
	private String merchantUrl;
	@Column(name = "merchant_id_prefix")
	private String merchantPrefix;
	@Column(name = "cashout_confirm_url")
	private String cashoutConfirmUrl;
	
	public StructurePartner() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public Long getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(Long updatedBy) {
		this.updatedBy = updatedBy;
	}

	public String getPartnerId() {
		return partnerId;
	}

	public void setPartnerId(String partnerId) {
		this.partnerId = partnerId;
	}

	public String getPartnerName() {
		return partnerName;
	}

	public void setPartnerName(String partnerName) {
		this.partnerName = partnerName;
	}

	public String getPartnerDesc() {
		return partnerDesc;
	}

	public void setPartnerDesc(String partnerDesc) {
		this.partnerDesc = partnerDesc;
	}

	public Long getStatus() {
		return status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	public String getPartnerAccount() {
		return partnerAccount;
	}

	public void setPartnerAccount(String partnerAccount) {
		this.partnerAccount = partnerAccount;
	}

	public String getMyPartnerId() {
		return myPartnerId;
	}

	public void setMyPartnerId(String myPartnerId) {
		this.myPartnerId = myPartnerId;
	}

	public String getPartnerApiUrl() {
		return partnerApiUrl;
	}

	public void setPartnerApiUrl(String partnerApiUrl) {
		this.partnerApiUrl = partnerApiUrl;
	}

	public String getMyPartnerToken() {
		return myPartnerToken;
	}

	public void setMyPartnerToken(String myPartnerToken) {
		this.myPartnerToken = myPartnerToken;
	}

	public String getSecretKey() {
		return secretKey;
	}

	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}

	public Long getMriGroup() {
		return mriGroup;
	}

	public void setMriGroup(Long mriGroup) {
		this.mriGroup = mriGroup;
	}

	public String getMerchantUrl() {
		return merchantUrl;
	}

	public void setMerchantUrl(String merchantUrl) {
		this.merchantUrl = merchantUrl;
	}

	public String getMerchantPrefix() {
		return merchantPrefix;
	}

	public void setMerchantPrefix(String merchantPrefix) {
		this.merchantPrefix = merchantPrefix;
	}

	public String getCashoutConfirmUrl() {
		return cashoutConfirmUrl;
	}

	public void setCashoutConfirmUrl(String cashoutConfirmUrl) {
		this.cashoutConfirmUrl = cashoutConfirmUrl;
	}
}