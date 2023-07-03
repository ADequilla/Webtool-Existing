package com.valuequest.services.impl;

import java.util.Date;
import java.util.HashMap;

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
import com.valuequest.dao.ParamConfigDao;
import com.valuequest.entity.ParamConfig;
import com.valuequest.entity.security.SecUser;
import com.valuequest.services.ParamConfigService;

@Service
@Transactional(readOnly = true)
public class ParamConfigServiceImpl extends SimpleServiceImpl<ParamConfig> implements ParamConfigService{

	@Autowired
	ParamConfigDao paramConfigDao;
	
	@Override
	public Class<ParamConfig> getRealClass() {
		return ParamConfig.class;
	}

	@Override
	@Modifying
    @Transactional(propagation=Propagation.REQUIRED)
	public void save(ParamConfig model, SecUser user) {
		ParamConfig paramConf = null;
    	if (model.getId() != null){
    		paramConf = this.findById(model.getId());
    		paramConf.setLastUpdatedBy(user.getId());
    		paramConf.setLastUpdatedDate(new Date());
    	}else{
    		paramConf = new ParamConfig();
    		paramConf.setCreatedBy(user.getId());
    		paramConf.setCreatedDate(new Date());
    	}
		
    	paramConf.setValue(model.getValue());
    	paramConf.setType(model.getType());
    	paramConf.setName(model.getName());
    	paramConf.setStatus("ACTIVE");
    	paramConf.setDescription(model.getDescription());
		
    	this.saveOrUpdate(paramConf);
    	model.setId(paramConf.getId());
	}

	@Override
	public DataTables searchByMapCriteria(DataTables dataTables, HashMap<String, Object> searchMap) {
		String type			= (String) searchMap.get("type");
		String name			= (String) searchMap.get("name");
		Criteria criteria 	= this.getSessionFactory().getCurrentSession().createCriteria(getRealClass());

		if (StringUtils.isNotBlank(type))
			criteria.add(Restrictions.eq("type", type));

		if (StringUtils.isNotBlank(name))
			criteria.add(Restrictions.ilike("name", name, MatchMode.ANYWHERE));

		criteria.add(Restrictions.eq("status", "ACTIVE"));
		
		return this.getDataTablesFromCriteria(criteria, dataTables);
	}


	@Override
	public Boolean checkingDuplicateParamName(String paramName, Long id) {
		return paramConfigDao.checkingDuplicateParamName(paramName, id);
	}

	@Override
	public String getParamValue(String paramName) {
		
		return paramConfigDao.getParamValue(paramName);
	
	}
}
