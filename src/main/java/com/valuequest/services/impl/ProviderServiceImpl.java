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
import com.valuequest.controller.maintenance.model.ProviderModel;
import com.valuequest.dao.ProviderDao;
import com.valuequest.entity.StructureProvider;
import com.valuequest.entity.security.SecUser;
import com.valuequest.services.ProviderService;

@Service
@Transactional(readOnly = true)
public class ProviderServiceImpl extends SimpleServiceImpl<StructureProvider> implements ProviderService {

	@Autowired
	private ProviderDao providerDao;
	
	@Override
	public Class<StructureProvider> getRealClass() {
		return StructureProvider.class;
	}

	@Override
	public StructureProvider findById(Long id) {
		return providerDao.findById(id);
	}

	@Override
	public boolean isExist(Long id) {
		StructureProvider provider = findById(id);
		if (provider == null) {
			return false;
		}
		
		return true;
	}
	
	@Override
	@Modifying
    @Transactional(propagation=Propagation.REQUIRED)
    public void save(ProviderModel model, SecUser user) {
		StructureProvider  provider = findById(model.getId());
		if (provider == null){
			provider = new StructureProvider();
			provider.setProviderName(model.getProviderName());
			provider.setDescription(model.getDescription());
			provider.setProviderAlias(model.getProviderAlias());
			provider.setStatus(model.getStatus());
			provider.setCreatedDate(new Date());
			provider.setCreatedBy(user.getId());
			provider.setUpdatedDate(new Date());
			provider.setUpdatedBy(user.getId());
		}else{
			provider.setUpdatedDate(new Date());
			provider.setUpdatedBy(user.getId());
			provider.setProviderName(model.getProviderName());
			provider.setDescription(model.getDescription());
			provider.setProviderAlias(model.getProviderAlias());
			provider.setStatus(model.getStatus());
		}
		
		this.saveOrUpdate(provider);
	}

	@Override
	@Modifying
    @Transactional(propagation=Propagation.REQUIRED)
	public void delete(List<ProviderModel> states) {
		List<Long> ids = new ArrayList<Long>();
		for (ProviderModel model : states) {
			ids.add(model.getId());
		}

		if (ids.size() > 0) {
			providerDao.delete(ids);
		}
	}


	@Override
	public DataTables searchByMapCriteria(DataTables dataTables, HashMap<String, Object> searchMap) {
		String id = (String) searchMap.get("id");
		String providerName	= (String) searchMap.get("providerName");
		Criteria criteria 	= this.getSessionFactory().getCurrentSession().createCriteria(StructureProvider.class);
		
		if (StringUtils.isNotBlank(id))
			criteria.add(Restrictions.eq("id", Long.parseLong(id)));

		if (StringUtils.isNotBlank(providerName))
			criteria.add(Restrictions.ilike("providerName", providerName, MatchMode.ANYWHERE));

		return this.getDataTablesFromCriteria(criteria, dataTables);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<StructureProvider> getAll() {
		Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(StructureProvider.class);
		return criteria.list();
	}
	
}
