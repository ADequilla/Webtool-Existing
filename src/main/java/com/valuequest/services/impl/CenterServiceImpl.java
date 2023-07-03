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
import com.valuequest.controller.maintenance.model.CenterModel;
import com.valuequest.dao.CenterDao;
import com.valuequest.entity.MappingHierarchy;
import com.valuequest.entity.StructureCenter;
import com.valuequest.entity.UserCenter;
import com.valuequest.entity.security.SecUser;
import com.valuequest.services.CenterService;

@Service
@Transactional(readOnly = true)
public class CenterServiceImpl extends SimpleServiceImpl<StructureCenter> implements CenterService {

	@Autowired
	private CenterDao centerDao;

	@Override
	public Class<StructureCenter> getRealClass() {
		return StructureCenter.class;
	}

	@Override
	public StructureCenter findByCode(String code) {
		return centerDao.findByCode(code);
	}

	@Override
	public boolean isExist(String code) {
		StructureCenter center = findByCode(code);
		if (center == null) {
			return false;
		}

		return true;
	}

	@Override
	@Modifying
	@Transactional(propagation = Propagation.REQUIRED)
	public void save(CenterModel model, SecUser user) {
		StructureCenter center = findByCode(model.getCode());
		if (center == null) {
			center = new StructureCenter();
			center.setCode(model.getCode());
			center.setCreatedDate(new Date());
			center.setCreatedBy(user.getId());
		} else {
			center.setUpdatedDate(new Date());
			center.setUpdatedBy(user.getId());
		}
		center.setDescription(model.getDescription());

		this.saveOrUpdate(center);
	}

	@Override
	@Modifying
	@Transactional(propagation = Propagation.REQUIRED)
	public void delete(List<StateModel> states) {
		List<String> ids = new ArrayList<String>();
		for (StateModel model : states) {
			ids.add(model.getCode());
		}

		if (ids.size() > 0) {
			centerDao.delete(ids);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<StructureCenter> mappedListBy(String[] units) {
		DetachedCriteria criteriaMapping = DetachedCriteria.forClass(MappingHierarchy.class);
		criteriaMapping.createAlias("unit", "unit");
		criteriaMapping.createAlias("center", "center");
		criteriaMapping.add(Restrictions.in("unit.code", units));
		criteriaMapping.setProjection(Projections.projectionList().add(Projections.groupProperty("center.code")));

		Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(StructureCenter.class);
		criteria.add(Subqueries.propertyIn("code", criteriaMapping));

		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<StructureCenter> mappedUserBy(Long userId) {
		Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(StructureCenter.class);
		criteria.add(Subqueries.propertyIn("code", criteriaBy(userId)));

		return criteria.list();
	}

	@Override
	public DetachedCriteria criteriaBy(Long userId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(UserCenter.class);
		criteria.createAlias("user", "user");
		criteria.createAlias("center", "center");
		criteria.add(Restrictions.eq("user.id", userId));
		criteria.setProjection(Projections.projectionList().add(Projections.groupProperty("center.code")));

		return criteria;
	}

	@Override
	public DataTables searchByMapCriteria(DataTables dataTables, HashMap<String, Object> searchMap) {
		Long loginId 		= (Long) searchMap.get("loginId");
		String code 		= (String) searchMap.get("code");
		String description 	= (String) searchMap.get("description");
		Criteria criteria 	= this.getSessionFactory().getCurrentSession().createCriteria(getRealClass());

		if (loginId != null)
			criteria.add(Subqueries.propertyIn("code", criteriaBy(loginId)));

		if (StringUtils.isNotBlank(code))
			criteria.add(Restrictions.eq("code", code));

		if (StringUtils.isNotBlank(description))
			criteria.add(Restrictions.ilike("description", description, MatchMode.ANYWHERE));

		if (searchMap.containsKey("isFiltered"))
			criteria.add(Subqueries.propertyIn("code", criteriaByMapping(searchMap)));

		return this.getDataTablesFromCriteria(criteria, dataTables);
	}

	
	
	// -- Private method
	private DetachedCriteria criteriaByMapping(HashMap<String, Object> searchMap) {
		String institution 			= (String) searchMap.get("institution");
		String branch 				= (String) searchMap.get("branch");
		String unit 				= (String) searchMap.get("unit");
		DetachedCriteria criteria 	= DetachedCriteria.forClass(MappingHierarchy.class);
		criteria.createAlias("institution", "institution", JoinType.LEFT_OUTER_JOIN);
		criteria.createAlias("branch", "branch", JoinType.LEFT_OUTER_JOIN);
		criteria.createAlias("unit", "unit", JoinType.LEFT_OUTER_JOIN);
		criteria.createAlias("center", "center");

		if (StringUtils.isNotBlank(institution))
			criteria.add(Restrictions.eq("institution.code", institution));

		if (StringUtils.isNotBlank(branch))
			criteria.add(Restrictions.eq("branch.code", branch));

		if (StringUtils.isNotBlank(unit))
			criteria.add(Restrictions.eq("unit.code", unit));

		criteria.setProjection(Projections.projectionList().add(Projections.groupProperty("center.code")));

		return criteria;
	}
}