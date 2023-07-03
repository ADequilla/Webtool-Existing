package com.valuequest.services.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.valuequest.common.DataTables;
import com.valuequest.controller.mbo.model.MboOperationModel;
import com.valuequest.entity.MboOperation;
import com.valuequest.entity.security.SecUser;
import com.valuequest.services.OperationService;

@Service
@Transactional(readOnly = true)
public class OperationServiceImpl extends SimpleServiceImpl<MboOperation> implements OperationService {

	@Override
	public Class<MboOperation> getRealClass() {
		return MboOperation.class;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<MboOperation> list() {
		Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(getRealClass());
		criteria.addOrder(Order.asc("daysCode"));
		
		return criteria.list();
	}

	
	@Override
	@Modifying
    @Transactional(propagation=Propagation.REQUIRED)
	public void save(List<MboOperationModel> operations, SecUser userLogin) {
		MboOperation operation = null;
		for (MboOperationModel model : operations) {
			operation = this.findById(model.getId());
			operation.setEnabled(model.getEnabled());
			operation.setHoursStart(model.getHoursStart());
			operation.setHoursEnd(model.getHoursEnd());
			operation.setLastUpdatedDate(new Date());
			operation.setLastUpdatedBy(userLogin.getId());
			
			this.saveOrUpdate(operation);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public MboOperation getByDaysCode(int daysCode) {
		Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(getRealClass());
		criteria.add(Restrictions.eq("daysCode", daysCode));
		criteria.add(Restrictions.eq("enabled", 1));

		List<MboOperation> list = criteria.list();
		if (list.size() > 0)
			return list.get(0);

		return null;
	}
	
	@Override
	public DataTables searchByMapCriteria(DataTables dataTables, HashMap<String, Object> searchMap) {
		return null;
	}
}