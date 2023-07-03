package com.valuequest.services.impl;

import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.valuequest.common.DataTables;
import com.valuequest.dao.RoutesDao;
import com.valuequest.entity.StructureUnit;
import com.valuequest.entity.ViewRoutes;
import com.valuequest.services.RoutesService;
@Service
@Transactional(readOnly = true)
public class RoutesServiceImpl extends SimpleServiceImpl<ViewRoutes> implements RoutesService {

	
	@Autowired
	private RoutesDao routesDao;
	
	@Override
	public ViewRoutes findByTrnCode(String id) {
		return routesDao.findByTrnCode(id);
	}
	
	@SuppressWarnings("unchecked")
	public List<ViewRoutes> getAll() {
		Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(ViewRoutes.class);
		return criteria.list();
	}
	
	
	
	public DataTables searchByMapCriteria(DataTables dataTables, HashMap<String, Object> searchMap) {
		String trnCode   = (String) searchMap.get("trnCode");
		Criteria criteria 	= this.getSessionFactory().getCurrentSession().createCriteria(ViewRoutes.class);
		
		if (StringUtils.isNotBlank(trnCode))
			criteria.add(Restrictions.eq("trnCode", trnCode));

		return this.getDataTablesFromCriteria(criteria, dataTables);
	}

	@Override
	public Class<ViewRoutes> getRealClass() {
		return ViewRoutes.class;
	}
	
}
