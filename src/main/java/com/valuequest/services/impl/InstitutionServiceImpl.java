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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.valuequest.common.DataTables;
import com.valuequest.controller.administration.model.StateModel;
import com.valuequest.controller.maintenance.model.InstitutionModel;
import com.valuequest.dao.InstitutionDao;
import com.valuequest.entity.MappingHierarchy;
import com.valuequest.entity.StructureInstitution;
import com.valuequest.entity.UserInstitution;
import com.valuequest.entity.security.SecUser;
import com.valuequest.services.InstitutionService;

@Service
@Transactional(readOnly = true)
public class InstitutionServiceImpl extends SimpleServiceImpl<StructureInstitution> implements InstitutionService {

	@Autowired
	private InstitutionDao institutionDao;

	@Override
	public Class<StructureInstitution> getRealClass() {
		return StructureInstitution.class;
	}

	@Override
	public StructureInstitution findByCode(String code) {
		return institutionDao.findByCode(code);
	}

	@Override
	public boolean isExist(String code) {
		StructureInstitution institution = findByCode(code);
		if (institution == null) {
			return false;
		}
		
		return true;
	}

	@Override
	@Modifying
    @Transactional(propagation=Propagation.REQUIRED)
    public void save(InstitutionModel model, SecUser user) {
		StructureInstitution institution = findByCode(model.getCode());
		if (institution == null){
			institution = new StructureInstitution();
			institution.setCode(model.getCode());
			institution.setCreatedDate(new Date());
			institution.setCreatedBy(user.getId());
		}else{
			institution.setUpdatedDate(new Date());
			institution.setUpdatedBy(user.getId());
		}
		institution.setDescription(model.getDescription());
		
		this.saveOrUpdate(institution);
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
			institutionDao.delete(ids);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<StructureInstitution> mappedList() {
		Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(StructureInstitution.class);
		criteria.add(Subqueries.propertyIn("code", criteriaByMapping()));

		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<StructureInstitution> mappedListBy(Long userId) {
		Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(StructureInstitution.class);
		criteria.add(Subqueries.propertyIn("code", criteriaBy(userId)));

		return criteria.list();
	}

	@Override
	public DataTables searchByMapCriteria(DataTables dataTables, HashMap<String, Object> searchMap) {
		Long loginId 		= (Long) searchMap.get("loginId");
		String code 		= (String) searchMap.get("code");
		String description 	= (String) searchMap.get("description");
		Criteria criteria	= this.getSessionFactory().getCurrentSession().createCriteria(getRealClass());
		System.out.print("#######Login ID: "+loginId);
		if (loginId != null)
			criteria.add(Subqueries.propertyIn("code", criteriaBy(loginId.longValue())));
		
		if (StringUtils.isNotBlank(code))
			criteria.add(Restrictions.eq("code", code));
		
		if (StringUtils.isNotBlank(description))
			criteria.add(Restrictions.ilike("description", description, MatchMode.ANYWHERE));
		
		if (searchMap.containsKey("isFiltered"))
			criteria.add(Subqueries.propertyIn("code", criteriaByMapping()));
		
		return this.getDataTablesFromCriteria(criteria, dataTables);
	}
	
	
	
	//-- Private method
	private DetachedCriteria criteriaByMapping() {
		DetachedCriteria criteria = DetachedCriteria.forClass(MappingHierarchy.class);
		criteria.createAlias("institution", "institution");
		criteria.setProjection(Projections.projectionList().add(Projections.groupProperty("institution.code")));

		return criteria;
	}
	
	private DetachedCriteria criteriaBy(Long userId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(UserInstitution.class);
		criteria.createAlias("user", "user");
		criteria.createAlias("institution", "institution");
		criteria.add(Restrictions.eq("user.id", userId));
		criteria.setProjection(Projections.projectionList().add(Projections.groupProperty("institution.code")));

		return criteria;
	}
}