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
import com.valuequest.entity.SplashScreen;
import com.valuequest.entity.security.SecUser;
import com.valuequest.services.SplashScreenService;

@Service
@Transactional(readOnly = true)
public class SplashScreenServiceImpl extends SimpleServiceImpl<SplashScreen> implements SplashScreenService {

	@Override
	public Class<SplashScreen> getRealClass() {
		return SplashScreen.class;
	}

	@Override
	@Modifying
	@Transactional(propagation = Propagation.REQUIRED)
	public void save(SplashScreen model, SecUser userLogin) {
		SplashScreen splash = null;
		if (model.getId() != null) {
			splash = this.findById(model.getId());
			splash.setUpdatedBy(userLogin.getId());
			splash.setUpdatedDate(new Date());
		} else {
			splash = new SplashScreen();
			splash.setCreatedBy(userLogin.getId());
			splash.setCreatedDate(new Date());
		}
		splash.setAction(model.getAction());
		splash.setTitle(model.getTitle());
		splash.setMessage(model.getMessage());
		splash.setImageURL(model.getImageURL());
		splash.setRedirectLink(model.getRedirectLink());
		splash.setSubMessage(model.getSubMessage());
		splash.setShow(model.getShow());
		
		this.saveOrUpdate(splash);
		model.setId(splash.getId());
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
			this.getSessionFactory().getCurrentSession().createQuery("delete from SplashScreen where id in :ids").setParameterList("ids", ids).executeUpdate();
		}
	}

	@Override
	public DataTables searchByMapCriteria(DataTables dataTables, HashMap<String, Object> searchMap) {
		String action 		= (String) searchMap.get("action");
		String title 		= (String) searchMap.get("title");
		Criteria criteria 	= this.getSessionFactory().getCurrentSession().createCriteria(getRealClass());

		if (StringUtils.isNotBlank(action))
			criteria.add(Restrictions.ilike("action", action, MatchMode.ANYWHERE));

		if (StringUtils.isNotBlank(title))
			criteria.add(Restrictions.ilike("title", title, MatchMode.ANYWHERE));

		return this.getDataTablesFromCriteria(criteria, dataTables);
	}
}
