package com.valuequest.services.impl;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.valuequest.common.DataTables;
import com.valuequest.controller.monitoring.model.SuspiciousModel;
import com.valuequest.entity.Suspicious;
import com.valuequest.entity.UserBranch;
import com.valuequest.entity.ViewCashinSuspicious;
import com.valuequest.entity.ViewTransConfirmation;
import com.valuequest.entity.ViewTransSuspicious;
import com.valuequest.entity.security.SecUser;
import com.valuequest.services.BranchService;
import com.valuequest.services.SuspiciousService;
import com.valuequest.util.Constantas;
import com.valuequest.util.DateUtil;

@Service
@Transactional(readOnly = true)
public class SuspiciousServiceImpl extends SimpleServiceImpl<Suspicious> implements SuspiciousService {

	@Autowired
	private BranchService branchService;

	@Override
	public Class<Suspicious> getRealClass() {
		return Suspicious.class;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Modifying
	@Override
	public void save(SuspiciousModel model, SecUser userLogin) {
		Suspicious suspicious = this.findById(model.getId());
		suspicious.setNote(model.getNote());
		suspicious.setStatus(model.getStatus());
		suspicious.setCreatedBy(userLogin.getId());
		suspicious.setCreatedDate(new Date());

		this.saveOrUpdate(suspicious);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ViewTransSuspicious> transList() {
		Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(ViewTransSuspicious.class);
		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ViewCashinSuspicious> cashinList() {
		Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(ViewCashinSuspicious.class);
		return criteria.list();
	}

	@Override
	public DataTables searchByMapCriteria(DataTables dataTables, HashMap<String, Object> searchMap) {
		Long loginId = (Long) searchMap.get("loginId");
		String cid = (String) searchMap.get("cid");
		String transType = (String) searchMap.get("transType");
		String status = (String) searchMap.get("status");
		String branch = (String) searchMap.get("branch");
		String dateStart = (String) searchMap.get("dateStart");
		String dateEnd = (String) searchMap.get("dateEnd");

		Criteria criteria;

		criteria = this.getSessionFactory().getCurrentSession().createCriteria(ViewTransConfirmation.class);

		if (loginId != null) {
			List<UserBranch> listUserBranch = branchService.getUserBranchList(loginId);
			if(listUserBranch != null ){
				String[] branchCode = new String[listUserBranch.size()];
				for(int i = 0; i < listUserBranch.size() ; i++){
					branchCode[i] = listUserBranch.get(i).getBranchCode();
				}
				criteria.add(Restrictions.in("branchCode", branchCode));
			}
		}

		if (StringUtils.isNotBlank(cid))
			criteria.add(Restrictions.ilike("clientCid", cid, MatchMode.ANYWHERE));

		if (StringUtils.isNotBlank(transType))
			criteria.add(Restrictions.eq("transDesc", transType));

		if (StringUtils.isNotBlank(status))
			criteria.add(Restrictions.eq("status", status));

		if (StringUtils.isNotBlank(branch))
			criteria.add(Restrictions.eq("branchCode", branch));

		if (StringUtils.isNotBlank(dateStart)) {
			try {
				Date date = Constantas.fdate.parse(dateStart);
				criteria.add(Restrictions.ge("transDate", DateUtil.truncate(date)));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}

		if (StringUtils.isNotBlank(dateEnd)) {
			try {
				Date date = Constantas.fdate.parse(dateEnd);
				criteria.add(Restrictions.le("transDate", DateUtil.endOfDay(date)));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		criteria.add(Restrictions.ne("transType", "WRONG_PIN"));
		criteria.add(Restrictions.ne("transType", "WRONG_PASS"));
		
		return this.getDataTablesFromCriteria(criteria, dataTables);

	}
}