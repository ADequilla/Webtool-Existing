package com.valuequest.services.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.valuequest.common.DataTables;
import com.valuequest.controller.administration.model.HierarchyModel;
import com.valuequest.controller.administration.model.StateModel;
import com.valuequest.dao.BranchDao;
import com.valuequest.dao.CenterDao;
import com.valuequest.dao.InstitutionDao;
import com.valuequest.dao.UnitDao;
import com.valuequest.entity.MappingHierarchy;
import com.valuequest.entity.security.SecUser;
import com.valuequest.services.HierarchyService;

@Service
@Transactional(readOnly = true)
public class HierarchyServiceImpl extends SimpleServiceImpl<MappingHierarchy> implements HierarchyService {

	@Autowired
	protected InstitutionDao institutionDao;

	@Autowired
	protected BranchDao branchDao;

	@Autowired
	protected UnitDao unitDao;

	@Autowired
	protected CenterDao centerDao;

	@Override
	public Class<MappingHierarchy> getRealClass() {
		return MappingHierarchy.class;
	}

	@Override
	@Modifying
	@Transactional(propagation = Propagation.REQUIRED)
	public void save(HierarchyModel model, SecUser userLogin) {
		MappingHierarchy hierarchy = null;
		if (model.getId() == null) {
			hierarchy = new MappingHierarchy();
			hierarchy.setCreatedBy(userLogin.getId());
			hierarchy.setCreatedDate(new Date());
		} else {
			hierarchy = this.findById(model.getId());
			hierarchy.setLastUpdatedBy(userLogin.getId());
			hierarchy.setLastUpdatedDate(new Date());
		}
		hierarchy.setInstitution(institutionDao.findByCode(model.getInstitutionCode()));
		hierarchy.setBranch(branchDao.findByCode(model.getBranchCode()));
		hierarchy.setUnit(unitDao.findByCode(model.getUnitCode()));
		hierarchy.setCenter(centerDao.findByCode(model.getCenterCode()));

		this.saveOrUpdate(hierarchy);
		model.setId(hierarchy.getId());
	}

	@SuppressWarnings("rawtypes")
	@Override
	public boolean isDuplicate(HierarchyModel model) {
		Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(MappingHierarchy.class);
		criteria.createAlias("institution", "institution", JoinType.LEFT_OUTER_JOIN);
		criteria.createAlias("branch", "branch", JoinType.LEFT_OUTER_JOIN);
		criteria.createAlias("unit", "unit", JoinType.LEFT_OUTER_JOIN);
		criteria.createAlias("center", "center", JoinType.LEFT_OUTER_JOIN);

		if (StringUtils.isBlank(model.getInstitutionCode())) {
			criteria.add(Restrictions.isNull("institution.code"));
		} else {
			criteria.add(Restrictions.eq("institution.code", model.getInstitutionCode()));
		}

		if (StringUtils.isBlank(model.getBranchCode())) {
			criteria.add(Restrictions.isNull("branch.code"));
		} else {
			criteria.add(Restrictions.eq("branch.code", model.getBranchCode()));
		}

		if (StringUtils.isBlank(model.getUnitCode())) {
			criteria.add(Restrictions.isNull("unit.code"));
		} else {
			criteria.add(Restrictions.eq("unit.code", model.getUnitCode()));
		}

		if (StringUtils.isBlank(model.getCenterCode())) {
			criteria.add(Restrictions.isNull("center.code"));
		} else {
			criteria.add(Restrictions.eq("center.code", model.getCenterCode()));
		}

		List list = criteria.list();
		if (list.size() > 0) {
			MappingHierarchy mapping = (MappingHierarchy) list.get(0);
			if (model.getId() != mapping.getId())
				return true;
		}

		return false;
	}

	@Override
	public DataTables searchByMapCriteria(DataTables dataTables, HashMap<String, Object> searchMap) {
		String institution	= (String) searchMap.get("institution");
		String branch 		= (String) searchMap.get("branch");
		String unit 		= (String) searchMap.get("unit");
		String center 		= (String) searchMap.get("center");
		Criteria criteria 	= this.getSessionFactory().getCurrentSession().createCriteria(getRealClass());
		criteria.createAlias("institution", "institution", JoinType.LEFT_OUTER_JOIN);
		criteria.createAlias("branch", "branch", JoinType.LEFT_OUTER_JOIN);
		criteria.createAlias("unit", "unit", JoinType.LEFT_OUTER_JOIN);
		criteria.createAlias("center", "center", JoinType.LEFT_OUTER_JOIN);

		if (StringUtils.isNotBlank(institution))
			criteria.add(Restrictions.eq("institution.code", institution));

		if (StringUtils.isNotBlank(branch))
			criteria.add(Restrictions.eq("branch.code", branch));

		if (StringUtils.isNotBlank(unit))
			criteria.add(Restrictions.eq("unit.code", unit));

		if (StringUtils.isNotBlank(center))
			criteria.add(Restrictions.eq("center.code", center));

		return getDataTablesFromCriteria(criteria, dataTables);
	}

	@Override
	@Modifying
    @Transactional(propagation=Propagation.REQUIRED)
	public void delete(List<StateModel> states) {
		List<Long> ids = new ArrayList<Long>();
		for (StateModel model : states) {
			ids.add(model.getId());
		}

		if (ids.size() > 0) {
			this.getSessionFactory().getCurrentSession().createQuery("delete from MappingHierarchy where id in :ids").setParameterList("ids", ids).executeUpdate();
		}
	}
}