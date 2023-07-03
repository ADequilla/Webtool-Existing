package com.valuequest.services.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.valuequest.common.DataTables;
import com.valuequest.controller.administration.model.RoleModel;
import com.valuequest.controller.administration.model.StateModel;
import com.valuequest.entity.security.SecRole;
import com.valuequest.entity.security.SecRoledetail;
import com.valuequest.entity.security.SecUser;
import com.valuequest.services.RoleService;

@Service
@Transactional(readOnly = true)
public class RoleServiceImpl extends SimpleServiceImpl<SecRole> implements RoleService {

	@Override
	public Class<SecRole> getRealClass() {
		return SecRole.class;
	}

	@Modifying
	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public void save(RoleModel model, SecUser userLogin) {
		List<Long> unchecked 	= new ArrayList<Long>();
		List<Long> checked 		= new ArrayList<Long>();
		for (StateModel stateModel : model.getStates()) {
			if (stateModel.getId() != null) {
				if ("1".equals(stateModel.getState())) {
					checked.add(stateModel.getId());
				} else if ("0".equals(stateModel.getState())) {
					unchecked.add(stateModel.getId());
				}
			}
		}

		SecRole role = null;
		if (model.getId() != null) {
			// delete any unchecked mapping for the group
			List<Long> compIds = new ArrayList<Long>();
			compIds.addAll(checked);
			compIds.addAll(unchecked);
			if (compIds.size() > 0) {
				this.getSessionFactory().getCurrentSession().createQuery("delete from SecRoledetail where role.id = :roleId and component.id in :compIds")
						.setParameter("roleId", model.getId()).setParameterList("compIds", compIds).executeUpdate();
			}

			role = findById(model.getId());
			role.setLastUpdatedBy(userLogin.getId());
			role.setLastUpdatedDate(new Date(System.currentTimeMillis()));
		} else {
			role = new SecRole();
			role.setCreatedBy(userLogin.getId());
			role.setCreatedDate(new Date(System.currentTimeMillis()));
		}
		role.setName(model.getName());
		role.setDescription(model.getDescription());

		this.saveOrUpdate(role);
		model.setId(role.getId());

		
		if (!checked.isEmpty()) {
			SecRoledetail roledetail = null;
			for (Long compId : checked) {
				roledetail = new SecRoledetail();
				roledetail.setRole(role);
				roledetail.setComponentId(compId);
				roledetail.setCreatedBy(userLogin.getId());
				roledetail.setCreatedDate(new Date(System.currentTimeMillis()));

				this.getSessionFactory().getCurrentSession().save(roledetail);
			}
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SecRoledetail> detailList(Long roleId) {
		return this.getSessionFactory().getCurrentSession().createQuery("from SecRoledetail where role.id = :roleId")
				.setParameter("roleId", roleId).list();
	}
	
	@Override
	public DataTables searchByMapCriteria(DataTables dataTables, HashMap<String, Object> searchMap) {
		String name 		= (String) searchMap.get("name");
		Criteria criteria 	= this.getSessionFactory().getCurrentSession().createCriteria(getRealClass());

		if (StringUtils.isNotBlank(name))
			criteria.add(Restrictions.ilike("name", name, MatchMode.ANYWHERE));

		return this.getDataTablesFromCriteria(criteria, dataTables);
	}
}