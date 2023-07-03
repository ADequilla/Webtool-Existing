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
import com.valuequest.controller.administration.model.StateModel;
import com.valuequest.entity.Concern;
import com.valuequest.entity.security.SecUser;
import com.valuequest.services.TypeOfConcernService;

@Service
@Transactional(readOnly = true)
public class TypeOfConcernServiceImpl extends SimpleServiceImpl<Concern> implements TypeOfConcernService {

	@Override
	public Class<Concern> getRealClass() {
		return Concern.class;
	}

	@Modifying
	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public void save(Concern model, SecUser user) {
		Concern concern = null;
		if (model.getId() != null) {
			concern = this.findById(model.getId());
			concern.setUpdatedBy(user.getId());
			concern.setUpdatedDate(new Date());
		} else {
			concern = new Concern();
			concern.setDate(new Date());
			concern.setCreatedBy(user.getId());
		}
		concern.setDesc(model.getDesc());
		concern.setName(model.getName());
		concern.setLevel(model.getLevel());
		concern.setTime(model.getTime());

		this.saveOrUpdate(concern);
		model.setId(concern.getId());
	}

	@Modifying
	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public void delete(List<StateModel> states) {
		List<Long> ids = new ArrayList<Long>();
		for (StateModel model : states) {
			ids.add(model.getId());
		}

		if (ids.size() > 0) {
			this.getSessionFactory().getCurrentSession().createQuery("delete from Concern where id in :ids").setParameterList("ids", ids).executeUpdate();
		}
	}

	public DataTables searchByMapCriteria(DataTables dataTables, HashMap<String, Object> searchMap) {
		String name 		= (String) searchMap.get("name");
		String level 		= (String) searchMap.get("level");
		Criteria criteria 	= this.getSessionFactory().getCurrentSession().createCriteria(getRealClass());

		if (StringUtils.isNotBlank(name))
			criteria.add(Restrictions.ilike("name", name, MatchMode.ANYWHERE));

		if (StringUtils.isNotBlank(level))
			criteria.add(Restrictions.eq("level", level));

		return getDataTablesFromCriteria(criteria, dataTables);
	}
}
