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
import com.valuequest.controller.maintenance.model.BranchModel;
import com.valuequest.dao.BranchDao;
import com.valuequest.entity.MCUser;
import com.valuequest.entity.MappingHierarchy;
import com.valuequest.entity.StructureBranch;
import com.valuequest.entity.StructureInstitution;
import com.valuequest.entity.UserBranch;
import com.valuequest.entity.security.SecUser;
import com.valuequest.services.BranchService;

@Service
@Transactional(readOnly = true)
public class BranchServiceImpl extends SimpleServiceImpl<StructureBranch> implements BranchService {

	@Autowired
	private BranchDao branchDao;

	@Override
	public Class<StructureBranch> getRealClass() {
		return StructureBranch.class;
	}

	@Override
	public StructureBranch findByCode(String code) {
		return branchDao.findByCode(code);
	}

	@Override
	public boolean isExist(String code) {
		StructureBranch branch = findByCode(code);
		if (branch == null) {
			return false;
		}

		return true;
	}

	@Override
	@Modifying
	@Transactional(propagation = Propagation.REQUIRED)
	public void save(BranchModel model, SecUser user) {
		StructureBranch branch = findByCode(model.getCode());
		if (branch == null) {
			branch = new StructureBranch();
			branch.setInsti(model.getInsti());
			branch.setCode(model.getCode());
			branch.setCreatedDate(new Date());
			branch.setCreatedBy(user.getId());
		} else {
			branch.setUpdatedDate(new Date());
			branch.setUpdatedBy(user.getId());
		}
		branch.setDescription(model.getDescription());

		this.saveOrUpdate(branch);
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
			branchDao.delete(ids);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<StructureBranch> mappedListBy(String[] institutions) {
		DetachedCriteria criteriaMapping = DetachedCriteria.forClass(MappingHierarchy.class);
		criteriaMapping.createAlias("institution", "institution");
		criteriaMapping.createAlias("branch", "branch");
		criteriaMapping.add(Restrictions.in("institution.code", institutions));
		criteriaMapping.setProjection(Projections.projectionList().add(Projections.groupProperty("branch.code")));

		Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(StructureBranch.class);
		criteria.add(Subqueries.propertyIn("code", criteriaMapping));

		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<StructureBranch> mappedList() {
		Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(StructureBranch.class);
		criteria.add(Subqueries.propertyIn("code", criteriaByMapping()));

		return criteria.list();
	}
	
	//-- Private method
	private DetachedCriteria criteriaByMapping() {
		DetachedCriteria criteria = DetachedCriteria.forClass(MappingHierarchy.class);
		criteria.createAlias("branch", "branch");
		criteria.setProjection(Projections.projectionList().add(Projections.groupProperty("branch.code")));
		return criteria;
	}
		
	@SuppressWarnings("unchecked")
	@Override
	public List<StructureBranch> mappedListBy(Long userId) {
		Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(StructureBranch.class);
		criteria.add(Subqueries.propertyIn("code", criteriaBy(userId)));

		return criteria.list();
	}

	@Override
	public DetachedCriteria criteriaBy(Long userId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(UserBranch.class);
		criteria.createAlias("user", "user");
		criteria.createAlias("branch", "branch");
		criteria.add(Restrictions.eq("user.id", userId));
		criteria.setProjection(Projections.projectionList().add(Projections.groupProperty("branch.code")));

		return criteria;
	}

	@Override
	public DataTables searchByMapCriteria(DataTables dataTables, HashMap<String, Object> searchMap) {
		Long loginId 		= (Long) searchMap.get("loginId");
		String insti 		= (String) searchMap.get("insti");
		String code 		= (String) searchMap.get("code");
		String description 	= (String) searchMap.get("description");
		Criteria criteria 	= this.getSessionFactory().getCurrentSession().createCriteria(getRealClass());

		if (loginId != null)
			criteria.add(Subqueries.propertyIn("code", criteriaBy(loginId)));

		if (StringUtils.isNotBlank(insti))
			criteria.add(Restrictions.ilike("insti", insti, MatchMode.ANYWHERE));

		if (StringUtils.isNotBlank(code))
			criteria.add(Restrictions.eq("code", code));

		if (StringUtils.isNotBlank(description))
			criteria.add(Restrictions.ilike("description", description, MatchMode.ANYWHERE));

		if (searchMap.containsKey("isFiltered"))
			criteria.add(Subqueries.propertyIn("code", criteriaByMapping(searchMap)));

		return this.getDataTablesFromCriteria(criteria, dataTables);
	}

	
	
	//-- Private method
	private DetachedCriteria criteriaByMapping(HashMap<String, Object> searchMap){
		String institution 			= (String) searchMap.get("institution");
		DetachedCriteria criteria 	= DetachedCriteria.forClass(MappingHierarchy.class);
		criteria.createAlias("institution", "institution", JoinType.LEFT_OUTER_JOIN);
		criteria.createAlias("branch", "branch");
		
		if (StringUtils.isNotBlank(institution))
			criteria.add(Restrictions.eq("institution.code", institution));

		criteria.setProjection(Projections.projectionList().add(Projections.groupProperty("branch.code")));
		
		return criteria;
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<UserBranch> getUserBranchList(Long userId){
		Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(UserBranch.class);
		criteria.add(Restrictions.eq("user.id", userId));
		return criteria.list();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<StructureBranch> getAllBranch(){
		Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(StructureBranch.class);
		return criteria.list();
	}
	
	
	//MCUser
	@SuppressWarnings("unchecked")
	@Override
	public List<StructureBranch> mappedListByMcUser(Long mcUserId) {
		Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(StructureBranch.class);
		criteria.add(Subqueries.propertyIn("code", criteriaByMcUser(mcUserId)));

		return criteria.list();
	}

	@Override
	public DetachedCriteria criteriaByMcUser(Long mcUserId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(MCUser.class);
		criteria.createAlias("branch", "branch");
		criteria.add(Restrictions.eq("id", mcUserId));
		criteria.setProjection(Projections.projectionList().add(Projections.groupProperty("branch.code")));

		return criteria;
	}
}