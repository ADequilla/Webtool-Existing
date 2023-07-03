package com.valuequest.services.impl;

import java.util.ArrayList;
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
import com.valuequest.controller.maintenance.model.CommissionSetupModel;
import com.valuequest.dao.CommissionSetupDao;
import com.valuequest.entity.StructureCommissionSetup;
import com.valuequest.entity.security.SecUser;
import com.valuequest.services.CommissionSetupService;

@Service
@Transactional(readOnly = true)
public class CommissionSetupServiceImpl extends SimpleServiceImpl<StructureCommissionSetup> implements CommissionSetupService {

	@Autowired
	private CommissionSetupDao commissionSetupDao;
	
	@Override
	public Class<StructureCommissionSetup> getRealClass() {
		return StructureCommissionSetup.class;
	}

	@Override
	public StructureCommissionSetup findById(Long id) {
		return commissionSetupDao.findById(id);
	}

	@Override
	public boolean isExist(Long id) {
		StructureCommissionSetup commissionSetup = findById(id);
		if (commissionSetup == null) {
			return false;
		}
		
		return true;
	}
	
	@Override
	@Modifying
    @Transactional(propagation=Propagation.REQUIRED)
    public void save(CommissionSetupModel model, SecUser user) {
		StructureCommissionSetup commissionSetup = findById(model.getId());
		if (commissionSetup == null){
			commissionSetup = new StructureCommissionSetup();
			commissionSetup.setTransType(model.getTransType());
			commissionSetup.setCommissionType(model.getCommissionType());
			commissionSetup.setCustomerIncome(model.getCustomerIncome());
			commissionSetup.setAgentIncome(model.getAgentIncome());
			commissionSetup.setBankIncome(model.getBankIncome());
			commissionSetup.setBankPartnerIncome(model.getBankPartnerIncome());
			commissionSetup.setCreatedDate(new Date());
			commissionSetup.setCreatedBy(user.getId());
			commissionSetup.setUpdatedDate(new Date());
			commissionSetup.setUpdatedBy(user.getId());
		}else{
			commissionSetup.setUpdatedDate(new Date());
			commissionSetup.setUpdatedBy(user.getId());
			commissionSetup.setTransType(model.getTransType());
			commissionSetup.setCommissionType(model.getCommissionType());
			commissionSetup.setCustomerIncome(model.getCustomerIncome());
			commissionSetup.setAgentIncome(model.getAgentIncome());
			commissionSetup.setBankIncome(model.getBankIncome());
			commissionSetup.setBankPartnerIncome(model.getBankPartnerIncome());
		}
		
		this.saveOrUpdate(commissionSetup);
	}

	@Override
	@Modifying
    @Transactional(propagation=Propagation.REQUIRED)
	public void delete(List<CommissionSetupModel> states) {
		List<Long> ids = new ArrayList<Long>();
		for (CommissionSetupModel model : states) {
			ids.add(model.getId());
		}

		if (ids.size() > 0) {
			commissionSetupDao.delete(ids);
		}
	}


	@Override
	public DataTables searchByMapCriteria(DataTables dataTables, HashMap<String, Object> searchMap) {
		String transType   = (String) searchMap.get("transType");
		String commissionType = (String) searchMap.get("commissionType");
		Criteria criteria 	= this.getSessionFactory().getCurrentSession().createCriteria(StructureCommissionSetup.class);
		
		if (StringUtils.isNotBlank(transType))
			criteria.add(Restrictions.ilike("transType", transType, MatchMode.ANYWHERE));

		if (StringUtils.isNotBlank(commissionType))
			criteria.add(Restrictions.ilike("commissionType", commissionType, MatchMode.ANYWHERE));

		return this.getDataTablesFromCriteria(criteria, dataTables);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<StructureCommissionSetup> getAll() {
		Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(StructureCommissionSetup.class);
		return criteria.list();
	}
	
}
