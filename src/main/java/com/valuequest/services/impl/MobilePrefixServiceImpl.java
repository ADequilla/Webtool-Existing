package com.valuequest.services.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.valuequest.common.DataTables;
import com.valuequest.controller.maintenance.model.MobilePrefixModel;
import com.valuequest.dao.MobilePrefixDao;
import com.valuequest.entity.StructureMobilePrefix;
import com.valuequest.entity.security.SecUser;
import com.valuequest.services.MobilePrefixService;

@Service
@Transactional(readOnly = true)
public class MobilePrefixServiceImpl extends SimpleServiceImpl<StructureMobilePrefix> implements MobilePrefixService {

	@Autowired
	private MobilePrefixDao mobilePrefixDao;
	
	@Override
	public Class<StructureMobilePrefix> getRealClass() {
		return StructureMobilePrefix.class;
	}

	@Override
	public StructureMobilePrefix findById(Long id) {
		return mobilePrefixDao.findById(id);
	}
	
	@Override
	public StructureMobilePrefix findByPrefixValue(String prefixValue) {
		return mobilePrefixDao.findByPrefixValue(prefixValue);
	}
	
	@Override
	public boolean isExist(String prefixValue) {
		StructureMobilePrefix mobilePrefix = findByPrefixValue(prefixValue);
		if (mobilePrefix == null) {
			return false;
		}
		
		return true;
	}
	
	@Override
	@Modifying
    @Transactional(propagation=Propagation.REQUIRED)
    public void save(MobilePrefixModel model, SecUser user) {
		StructureMobilePrefix  mobilePrefix = findById(model.getId());
		if (mobilePrefix == null){
			mobilePrefix = new StructureMobilePrefix();
			mobilePrefix.setProductCategoryId(model.getProductCategoryId());
			mobilePrefix.setPrefixValue(model.getPrefixValue());
			mobilePrefix.setProductName(model.getProductName());
			mobilePrefix.setCreatedDate(new Date());
			mobilePrefix.setCreatedBy(user.getId());
			mobilePrefix.setUpdatedDate(new Date());
			mobilePrefix.setUpdatedBy(user.getId());
		}else{
			mobilePrefix.setUpdatedDate(new Date());
			mobilePrefix.setUpdatedBy(user.getId());
			mobilePrefix.setProductCategoryId(model.getProductCategoryId());
			mobilePrefix.setPrefixValue(model.getPrefixValue());
			mobilePrefix.setProductName(model.getProductName());
		}
		
		this.saveOrUpdate(mobilePrefix);
	}

	@Override
	@Modifying
    @Transactional(propagation=Propagation.REQUIRED)
	public void delete(List<MobilePrefixModel> states) {
		List<Long> ids = new ArrayList<Long>();
		for (MobilePrefixModel model : states) {
			ids.add(model.getId());
		}

		if (ids.size() > 0) {
			mobilePrefixDao.delete(ids);
		}
	}


	@Override
	public DataTables searchByMapCriteria(DataTables dataTables, HashMap<String, Object> searchMap) {
		String prefixValue   = (String) searchMap.get("prefixValue");
		String productName = (String) searchMap.get("productName");
		Criteria criteria 	= this.getSessionFactory().getCurrentSession().createCriteria(StructureMobilePrefix.class);
		
		if (StringUtils.isNotBlank(prefixValue))
			criteria.add(Restrictions.ilike("prefixValue", prefixValue, MatchMode.ANYWHERE));

		if (StringUtils.isNotBlank(productName))
			criteria.add(Restrictions.ilike("productName", productName, MatchMode.ANYWHERE));

		return this.getDataTablesFromCriteria(criteria, dataTables);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<StructureMobilePrefix> getAll() {
		Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(StructureMobilePrefix.class);
		return criteria.list();
	}
	
}
