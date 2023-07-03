package com.valuequest.services.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;
import org.hibernate.sql.JoinType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.valuequest.common.DataTables;
import com.valuequest.controller.administration.model.StateModel;
import com.valuequest.controller.maintenance.model.UnitModel;
import com.valuequest.dao.UnitDao;
import com.valuequest.entity.MCUser;
import com.valuequest.entity.MappingHierarchy;
import com.valuequest.entity.StructureUnit;
import com.valuequest.entity.UserUnit;
import com.valuequest.entity.security.SecUser;
import com.valuequest.services.UnitService;

@Service
@Transactional(readOnly = true)
public class UnitServiceImpl extends SimpleServiceImpl<StructureUnit> implements UnitService {

	@Autowired
	private UnitDao unitDao;
	
	@Override
	public Class<StructureUnit> getRealClass() {
		return StructureUnit.class;
	}

	@Override
	public StructureUnit findByCode(String code) {
		return unitDao.findByCode(code);
	}

	@Override
	public boolean isExist(String code) {
		StructureUnit unit = findByCode(code);
		if (unit == null) {
			return false;
		}
		
		return true;
	}
	
	@Override
	@Modifying
    @Transactional(propagation=Propagation.REQUIRED)
    public void save(UnitModel model, SecUser user) {
		StructureUnit  unit = findByCode(model.getCode());
		if (unit == null){
			unit = new StructureUnit();
			unit.setCode(model.getCode());
			unit.setCreatedDate(new Date());
			unit.setCreatedBy(user.getId());
		}else{
			unit.setUpdatedDate(new Date());
			unit.setUpdatedBy(user.getId());
		}
		unit.setDescription(model.getDescription());
		
		this.saveOrUpdate(unit);
	}

	@Override
	@Modifying
    @Transactional(propagation=Propagation.REQUIRED)
	public void delete(List<StateModel> states) {
		List<String> ids = new ArrayList<String>();
		for (StateModel model : states) {
			ids.add(model.getCode());
		}

		if (ids.size() > 0) {
			unitDao.delete(ids);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<StructureUnit> mappedListBy(String[] branchs) {
		DetachedCriteria criteriaMapping = DetachedCriteria.forClass(MappingHierarchy.class);
		criteriaMapping.createAlias("branch", "branch");
		criteriaMapping.createAlias("unit", "unit");
		criteriaMapping.add(Restrictions.in("branch.code", branchs));
		criteriaMapping.setProjection(Projections.projectionList().add(Projections.groupProperty("unit.code")));
		
		Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(StructureUnit.class);
		criteria.add(Subqueries.propertyIn("code", criteriaMapping));
		
		return criteria.list();
	}
	
	@SuppressWarnings({"unchecked"})
	@Override
	public List<StructureUnit> mappedListBy(Long userId) {
		Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(StructureUnit.class);
		criteria.add(Subqueries.propertyIn("code", criteriaBy(userId)));
		
		return criteria.list();
	}

	@Override
	public DetachedCriteria criteriaBy(Long userId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(UserUnit.class);
		criteria.createAlias("user", "user");
		criteria.createAlias("unit", "unit");
		criteria.add(Restrictions.eq("user.id", userId));
		criteria.setProjection(Projections.projectionList().add(Projections.groupProperty("unit.code")));
		
		return criteria;
	}

	@Override
	public DataTables searchByMapCriteria(DataTables dataTables, HashMap<String, Object> searchMap) {
		String code 		= (String) searchMap.get("code");
		String description 	= (String) searchMap.get("description");
		Criteria criteria 	= this.getSessionFactory().getCurrentSession().createCriteria(getRealClass());
		
		/*
		if (loginId != null)
			criteria.add(Subqueries.propertyIn("code", criteriaBy(loginId)));*/
		
		if (StringUtils.isNotBlank(code))
			criteria.add(Restrictions.eq("code", code));

		if (StringUtils.isNotBlank(description))
			criteria.add(Restrictions.ilike("description", description, MatchMode.ANYWHERE));

		if(searchMap.containsKey("isFiltered"))
			criteria.add(Subqueries.propertyIn("code", criteriaByMapping(searchMap)));
		
		return this.getDataTablesFromCriteria(criteria, dataTables);
	}
	
	//-- Private method
	private DetachedCriteria criteriaByMapping(HashMap<String, Object> searchMap){
		String institution 			= (String) searchMap.get("institution");
		String branch 				= (String) searchMap.get("branch");
		DetachedCriteria criteria 	= DetachedCriteria.forClass(MappingHierarchy.class);
		criteria.createAlias("institution", "institution", JoinType.LEFT_OUTER_JOIN);
		criteria.createAlias("branch", "branch", JoinType.LEFT_OUTER_JOIN);
		criteria.createAlias("unit", "unit");
		
		if (StringUtils.isNotBlank(institution))
			criteria.add(Restrictions.eq("institution.code", institution));

		if (StringUtils.isNotBlank(branch))
			criteria.add(Restrictions.eq("branch.code", branch));
		
		criteria.setProjection(Projections.projectionList().add(Projections.groupProperty("unit.code")));
		
		return criteria;
	}
	
	//MCUser
	@SuppressWarnings({"unchecked"})
	@Override
	public List<StructureUnit> mappedListByMcUser(Long mcUserId) {
		Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(StructureUnit.class);
		criteria.add(Subqueries.propertyIn("code", criteriaByMcUser(mcUserId)));
		
		return criteria.list();
	}

	@Override
	public DetachedCriteria criteriaByMcUser(Long mcUserId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(MCUser.class);
		criteria.createAlias("unit", "unit");
		criteria.add(Restrictions.eq("id", mcUserId));
		criteria.setProjection(Projections.projectionList().add(Projections.groupProperty("unit.code")));
		
		return criteria;
	}
}