package com.valuequest.services.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.valuequest.common.DataTables;
import com.valuequest.controller.monitoring.model.AuthorResetPasswordModel;
import com.valuequest.dao.AuthorResetPasswordDao;
import com.valuequest.entity.AuthorResetPassword;
import com.valuequest.entity.ViewAuthorResetPassword;
import com.valuequest.entity.security.SecUser;
import com.valuequest.services.AuthorResetPasswordService;
import com.valuequest.services.BranchService;

@Service
@Transactional(readOnly = true)
public class AuthorResetPasswordServiceImpl extends SimpleServiceImpl<AuthorResetPassword> implements AuthorResetPasswordService {

	@Autowired
	private AuthorResetPasswordDao authorResetPasswordDao;
	
	@Override
	public Class<AuthorResetPassword> getRealClass() {
		return AuthorResetPassword.class;
	}

	@Autowired
	private BranchService branchService;
	
	@Override
	public ViewAuthorResetPassword findByCid(String cid) {
		return authorResetPasswordDao.findByCid(cid);
	}
	
	@Override
	public ViewAuthorResetPassword findByParam(Long id) {
		return authorResetPasswordDao.findByParam(id);
	}

	@Override
	public boolean isExist(String cid) {
		ViewAuthorResetPassword viewAuthorResetPassword = findByCid(cid);
		if (viewAuthorResetPassword == null) {
			return false;
		}
		
		return true;
	}
	
	@Override
	@Modifying
    @Transactional(propagation=Propagation.REQUIRED)
    public void save(AuthorResetPasswordModel model, SecUser user) {
		AuthorResetPassword  authorResetPassword = findById(model.getId());
		if (authorResetPassword == null){
			authorResetPassword = new AuthorResetPassword();
			authorResetPassword.setClientId(model.getClientId());
			authorResetPassword.setStatus(model.getStatus());
			authorResetPassword.setType(model.getType());
			authorResetPassword.setCreatedDate(new Date());
			authorResetPassword.setCreatedBy(user.getId());
			authorResetPassword.setUpdatedDate(new Date());
			authorResetPassword.setUpdatedBy(user.getId());
		}else{
			authorResetPassword.setUpdatedDate(new Date());
			authorResetPassword.setUpdatedBy(user.getId());
			authorResetPassword.setStatus(model.getStatus());
		}
		
		this.saveOrUpdate(authorResetPassword);
	}

	@Override
	@Modifying
    @Transactional(propagation=Propagation.REQUIRED)
	public void delete(List<AuthorResetPasswordModel> states) {
		List<Long> ids = new ArrayList<Long>();
		for (AuthorResetPasswordModel model : states) {
			ids.add(model.getId());
		}

		if (ids.size() > 0) {
			authorResetPasswordDao.delete(ids);
		}
	}


	@Override
	public DataTables searchByMapCriteria(DataTables dataTables, HashMap<String, Object> searchMap) {
		Long loginId 			= (Long) searchMap.get("loginId");
		String cid 				= (String) searchMap.get("cid");
		Criteria criteria 	= this.getSessionFactory().getCurrentSession().createCriteria(ViewAuthorResetPassword.class);
		
		if (loginId != null) {
//			Criterion cr1 = Restrictions.and(Restrictions.isNotNull("centerCode"), Subqueries.propertyIn("centerCode", centerService.criteriaBy(loginId)));
			Criterion cr2 = Restrictions.and(Subqueries.propertyIn("branchCode", branchService.criteriaBy(loginId)));
			criteria.add(Restrictions.or(cr2));
		}
		
		if (StringUtils.isNotBlank(cid)){
			criteria.add(Restrictions.eq("cid", cid));
		}
		
		criteria.add(Restrictions.eq("status", "P"));

		return this.getDataTablesFromCriteria(criteria, dataTables);
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AuthorResetPassword> getAll() {
		Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(AuthorResetPassword.class);
		return criteria.list();
	}
	
}
