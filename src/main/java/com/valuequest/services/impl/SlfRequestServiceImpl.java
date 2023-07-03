package com.valuequest.services.impl;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.valuequest.common.DataTables;
import com.valuequest.controller.administration.model.StateModel;
import com.valuequest.entity.Lookup;
import com.valuequest.entity.TransFinance;
import com.valuequest.entity.ViewSlfRequest;
import com.valuequest.entity.security.SecUser;
import com.valuequest.services.BranchService;
import com.valuequest.services.SlfRequestService;
import com.valuequest.util.Constantas;
import com.valuequest.util.DateUtil;

@Service
@Transactional(readOnly = true)
public class SlfRequestServiceImpl extends SimpleServiceImpl<TransFinance> implements SlfRequestService {

	@Autowired
	private BranchService branchService;

	@Override
	public Class<TransFinance> getRealClass() {
		return TransFinance.class;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Modifying
	@Override
	public void reject(List<StateModel> models, SecUser userLogin) {
		TransFinance trans = null;
		for (StateModel model : models) {
			trans = this.findById(model.getId());
			if (trans != null) {
				trans.setStatus(Lookup.LOOKUP_SLF_REQUEST_REJECTED);
				trans.setUpdatedBy(userLogin.getId());
				trans.setUpdatedDate(new Date());

				this.saveOrUpdate(trans);
			}
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Modifying
	@Override
	public void confirm(List<StateModel> models, SecUser userLogin) {
		TransFinance trans = null;
		for (StateModel model : models) {
			trans = this.findById(model.getId());
			if (trans != null) {
				trans.setStatus(Lookup.LOOKUP_SLF_REQUEST_CONFIRMED);
				trans.setAmountApproved(Double.valueOf(model.getCode()));
				trans.setUpdatedBy(userLogin.getId());
				trans.setUpdatedDate(new Date());

				this.saveOrUpdate(trans);
			}
		}
	}

	public DataTables searchByMapCriteria(DataTables dataTables, HashMap<String, Object> searchMap) {
		Long loginId 		= (Long) searchMap.get("loginId");
		String cid 			= (String) searchMap.get("cid");
		String branch 		= (String) searchMap.get("branch");
		String transDate 	= (String) searchMap.get("transDate");
		Criteria criteria 	= this.getSessionFactory().getCurrentSession().createCriteria(ViewSlfRequest.class);

		if (loginId != null)
			criteria.add(Subqueries.propertyIn("branchCode", branchService.criteriaBy(loginId)));

		if (StringUtils.isNotBlank(cid))
			criteria.add(Restrictions.ilike("cid", cid, MatchMode.ANYWHERE));

		if (StringUtils.isNotBlank(branch))
			criteria.add(Restrictions.eq("branchCode", branch));

		if (StringUtils.isNotBlank(transDate)) {
			try {
				Date date = Constantas.fdate.parse(transDate);
				criteria.add(Restrictions.ge("transDate", DateUtil.truncate(date)));
				criteria.add(Restrictions.le("transDate", DateUtil.endOfDay(date)));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}

		return this.getDataTablesFromCriteria(criteria, dataTables);
	}
}