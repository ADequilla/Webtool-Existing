package com.valuequest.services.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpSession;

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
import com.valuequest.dao.CSRHotlainDao;
import com.valuequest.entity.CSRHotlainView;
import com.valuequest.entity.StructureInstitution;
import com.valuequest.entity.UserInstitution;
import com.valuequest.entity.ViewCSRHotline;
import com.valuequest.entity.security.SecUser;
import com.valuequest.services.CSRHotlineService;

@Service
@Transactional(readOnly = true)
public class CSRHotlineServiceImpl extends SimpleServiceImpl<CSRHotlainView> implements CSRHotlineService {

	@Override
	public Class<CSRHotlainView> getRealClass() {
		return CSRHotlainView.class;
	}

	protected SecUser getLoginSecUser(HttpSession session) {
		return (SecUser) session.getAttribute("loginSecUser");
	}

	private DetachedCriteria criteriaBy(Long userId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(UserInstitution.class);
		criteria.createAlias("user", "user");
		criteria.createAlias("institution", "institution");
		criteria.add(Restrictions.eq("user.id", userId));
		criteria.setProjection(Projections.projectionList().add(Projections.groupProperty("institution.code")));

		return criteria;
	}
	
	List<String> instiList = new ArrayList<String>();
	
	public void getUserInsti(Long userId) {
		instiList.clear();
		Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(StructureInstitution.class);
		criteria.add(Subqueries.propertyIn("code", criteriaBy(userId)));
		List r = criteria.list();
		for (Iterator iterator = r.iterator(); iterator.hasNext();){
			StructureInstitution insti = (StructureInstitution) iterator.next();		
            instiList.add(insti.getCode());
		}
		
		System.out.println("#####Arrray of Insti#####\n "+  instiList.toString());
		
	}

	@Override
	public DataTables searchByMapCriteria(DataTables dataTables, HashMap<String, Object> searchMap, HttpSession session) {
		String contactNumber = (String) searchMap.get("contactNumber");
		String networkProvider = (String) searchMap.get("networkProvider");
		//String institutionCode = (String) searchMap.get("instCode");
		Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(ViewCSRHotline.class);
		criteria.add(Restrictions.in("instCode", instiList));
		SecUser user = this.getLoginSecUser(session);
		getUserInsti(user.getId());

		if (StringUtils.isNotBlank(contactNumber))
			criteria.add(Restrictions.eq("contactNumber", contactNumber));

		if (StringUtils.isNotBlank(networkProvider))
			criteria.add(Restrictions.ilike("networkProvider", networkProvider, MatchMode.ANYWHERE));

		

		return this.getDataTablesFromCriteria(criteria, dataTables);
	}


	@Override
	public boolean isExist(String code) {
		CSRHotlainView csrHotlain = findByContactNumber(code);
		if (csrHotlain == null) {
			return false;
		}

		return true;
	}

	@Override
	@Modifying
	@Transactional(propagation = Propagation.REQUIRED)
	public void update(CSRHotlainView model, SecUser user) {
		CSRHotlainView csrHotlain = new CSRHotlainView();
		csrHotlain.setCreatedDate(model.getCreatedDate());
		csrHotlain.setCreatedBy(model.getId());
		csrHotlain.setUpdatedDate(new Date());
		csrHotlain.setUpdatedBy(user.getId());
		csrHotlain.setNetworkProvider(model.getNetworkProvider());
		csrHotlain.setContactNumber(model.getContactNumber());
		csrHotlain.setId(model.getId());
		this.saveOrUpdate(csrHotlain);
	}

	@Override
	@Modifying
	@Transactional(propagation = Propagation.REQUIRED)
	public void save(CSRHotlainView model, SecUser user) {
		CSRHotlainView csrHotlain = null;
		if (model.getId() != null) {
			csrHotlain = this.findById(model.getId());
			csrHotlain.setUpdatedDate(new Date());
			csrHotlain.setUpdatedBy(user.getId());
		} else {
			csrHotlain = new CSRHotlainView();
			csrHotlain.setCreatedDate(new Date());
			csrHotlain.setCreatedBy(user.getId());
		}
		csrHotlain.setContactNumber(model.getContactNumber());
		csrHotlain.setNetworkProvider(model.getNetworkProvider());
		csrHotlain.setInstCode(model.getInstCode());
		
		this.saveOrUpdate(csrHotlain);
		model.setId(csrHotlain.getId());
	}

	@Override
	public CSRHotlainView findByContactNumber(String contactNumber) {
		// TODO Auto-generated method stub
		return null;
	}


	@Modifying
    @Transactional(propagation=Propagation.REQUIRED)
	@Override
	public void delete(List<StateModel> states) {
		List<Long> ids = new ArrayList<Long>();
		for (StateModel model : states) {
			ids.add(model.getId());
		}

		if (ids.size() > 0) {
			this.getSessionFactory().getCurrentSession().createQuery("delete from CSRHotlainView where id in :ids").setParameterList("ids", ids).executeUpdate();
		}
		
	}

	@Override
	public DataTables searchByMapCriteria(DataTables dataTables, HashMap<String, Object> searchMap) {
		// TODO Auto-generated method stub
		return null;
	}

}
