package com.valuequest.services.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.valuequest.common.DataTables;
import com.valuequest.controller.administration.model.StateModel;
import com.valuequest.entity.Biller;
import com.valuequest.entity.BillerTemp;
import com.valuequest.entity.security.SecUser;
import com.valuequest.services.BillerService;

@Service
@Transactional(readOnly = true)
public class BillerServiceImpl extends SimpleServiceImpl<BillerTemp> implements BillerService {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public Class<BillerTemp> getRealClass() {
		return BillerTemp.class;
	}

	@Override
	@Modifying
	@Transactional(propagation = Propagation.REQUIRED)
	public void save(BillerTemp model, SecUser userLogin) {
		BillerTemp biller = null;
		if (model.getId() != null) {
			biller = this.findById(model.getId());
			biller.setUpdatedBy(userLogin.getId());
			biller.setUpdatedDate(new Date());
		} else {
			biller = new BillerTemp();
			biller.setCreatedBy(userLogin.getId());
			biller.setCreatedDate(new Date());
		}

		biller.setName(model.getName());
		biller.setAccount(model.getAccount());
		biller.setEnabled(model.getEnabled());
		biller.setStatus(BillerTemp.BILLER_STATUS_PENDING);
		biller.setType(model.getType());

		this.saveOrUpdate(biller);
		model.setId(biller.getId());
	}

	@Override
	@Modifying
	@Transactional(propagation = Propagation.REQUIRED)
	public void update(List<StateModel> states, String status, SecUser userLogin) {
		BillerTemp temp = null;
		for (StateModel model : states) {
			temp = this.findById(model.getId());
			temp.setStatus(status);
			temp.setApproveBy(userLogin);
			temp.setUpdatedBy(userLogin.getId());
			temp.setUpdatedDate(new Date());

			this.saveOrUpdate(temp);

			// Save to ACTUAL
			if (BillerTemp.BILLER_STATUS_APPROVED.equals(status)) {
				Biller biller = get(temp.getId());
				if (biller == null) {
					biller = new Biller();
					biller.setId(temp.getId());
					biller.setCreatedBy(temp.getCreatedBy());
					biller.setCreatedDate(temp.getCreatedDate());
				}
				biller.setName(temp.getName());
				biller.setAccount(temp.getAccount());
				biller.setEnabled(temp.getEnabled());
				biller.setUpdatedBy(temp.getUpdatedBy());
				biller.setType(temp.getType());
				biller.setUpdatedDate(temp.getUpdatedDate());

				saveOrUpdate(biller);
			}
		}
	}

	@Override
	public DataTables searchByMapCriteria(DataTables dataTables, HashMap<String, Object> searchMap) {
		String name 		= (String) searchMap.get("name");
		String account 		= (String) searchMap.get("account");
		Criteria criteria 	= sessionFactory.getCurrentSession().createCriteria(getRealClass());
		criteria.createAlias("approveBy", "approveBy", JoinType.LEFT_OUTER_JOIN);

		if (StringUtils.isNotBlank(name))
			criteria.add(Restrictions.ilike("name", name, MatchMode.ANYWHERE));

		if (StringUtils.isNotBlank(account))
			criteria.add(Restrictions.ilike("account", account, MatchMode.ANYWHERE));

		return this.getDataTablesFromCriteria(criteria, dataTables);
	}

	private Biller get(Long id) {
		return ((Biller) sessionFactory.getCurrentSession().get(Biller.class, id));
	}

	@Modifying
	@Transactional(propagation = Propagation.REQUIRED)
	private void saveOrUpdate(Biller biller) {
		sessionFactory.getCurrentSession().saveOrUpdate(biller);
	}
}
