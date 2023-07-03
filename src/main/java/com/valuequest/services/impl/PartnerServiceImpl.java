package com.valuequest.services.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.valuequest.common.DataTables;
import com.valuequest.controller.maintenance.model.PartnerModel;
import com.valuequest.dao.PartnerDao;
import com.valuequest.entity.StructurePartner;
import com.valuequest.entity.security.SecUser;
import com.valuequest.services.PartnerService;

@Service
@Transactional(readOnly = true)
public class PartnerServiceImpl extends SimpleServiceImpl<StructurePartner> implements PartnerService {

	@Autowired
	private PartnerDao partnerDao;
	
	@Override
	public Class<StructurePartner> getRealClass() {
		return StructurePartner.class;
	}

	@Override
	public StructurePartner findByPartnerId(String partnerId) {
		return partnerDao.findByPartnerId(partnerId);
	}

	@Override
	public boolean isExist(String partnerId) {
		StructurePartner partner = findByPartnerId(partnerId);
		if (partner == null) {
			return false;
		}
		
		return true;
	}
	
	@Override
	@Modifying
    @Transactional(propagation=Propagation.REQUIRED)
    public void save(PartnerModel model, SecUser user) {
		StructurePartner  partner = findById(model.getId());
		if (partner == null){
			partner = new StructurePartner();
			partner.setId(model.getId());
			partner.setPartnerId(model.getPartnerId());
			partner.setPartnerName(model.getPartnerName());
			partner.setPartnerDesc(model.getPartnerDesc());
			partner.setStatus(model.getStatus());
			partner.setPartnerAccount(model.getPartnerAccount());
			partner.setMyPartnerId(model.getMyPartnerId());
			partner.setPartnerApiUrl(model.getPartnerApiUrl());
			partner.setMyPartnerToken(model.getMyPartnerToken());
			partner.setSecretKey(model.getSecretKey());
			partner.setMriGroup(model.getMriGroup());
			partner.setMerchantUrl(model.getMerchantUrl());
			partner.setMerchantPrefix(model.getMerchantPrefix());
			partner.setCashoutConfirmUrl(model.getCashoutConfirmUrl());
			partner.setCreatedDate(new Date());
			partner.setCreatedBy(user.getId());
			partner.setUpdatedDate(new Date());
			partner.setUpdatedBy(user.getId());
		}else{
			partner.setUpdatedDate(new Date());
			partner.setUpdatedBy(user.getId());
			partner.setPartnerId(model.getPartnerId());
			partner.setPartnerName(model.getPartnerName());
			partner.setPartnerDesc(model.getPartnerDesc());
			partner.setStatus(model.getStatus());
			partner.setPartnerAccount(model.getPartnerAccount());
			partner.setMyPartnerId(model.getMyPartnerId());
			partner.setPartnerApiUrl(model.getPartnerApiUrl());
			partner.setMyPartnerToken(model.getMyPartnerToken());
			partner.setSecretKey(model.getSecretKey());
			partner.setMriGroup(model.getMriGroup());
			partner.setMerchantUrl(model.getMerchantUrl());
			partner.setMerchantPrefix(model.getMerchantPrefix());
			partner.setCashoutConfirmUrl(model.getCashoutConfirmUrl());
		}
		
		this.saveOrUpdate(partner);
	}

	@Override
	@Modifying
    @Transactional(propagation=Propagation.REQUIRED)
	public void delete(List<PartnerModel> states) {
		List<Long> ids = new ArrayList<Long>();
		for (PartnerModel model : states) {
			ids.add(model.getId());
		}

		if (ids.size() > 0) {
			partnerDao.delete(ids);
		}
	}


	@Override
	public DataTables searchByMapCriteria(DataTables dataTables, HashMap<String, Object> searchMap) {
		String partnerId = (String) searchMap.get("partnerId");
		String partnerName = (String) searchMap.get("partnerName");
		String partnerDesc = (String) searchMap.get("partnerDesc");
		String status = (String) searchMap.get("status");
		
		Criteria criteria 	= this.getSessionFactory().getCurrentSession().createCriteria(StructurePartner.class);
		
		if (StringUtils.isNotBlank(partnerId))
			criteria.add(Restrictions.ilike("partnerId", partnerId));
		
		if (StringUtils.isNotBlank(partnerName))
			criteria.add(Restrictions.ilike("partnerName", partnerName, MatchMode.ANYWHERE));
		
		if (StringUtils.isNotBlank(partnerDesc))
			criteria.add(Restrictions.ilike("partnerDesc", partnerDesc, MatchMode.ANYWHERE));
		
		if (StringUtils.isNotBlank(status))
			criteria.add(Restrictions.eq("status", Long.parseLong(status)));
		
		criteria.addOrder(Order.asc("partnerId"));
		
		return this.getDataTablesFromCriteria(criteria, dataTables);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<StructurePartner> getAll() {
		Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(StructurePartner.class);
		criteria.addOrder(Order.asc("partnerId"));
		return criteria.list();
	}	
}