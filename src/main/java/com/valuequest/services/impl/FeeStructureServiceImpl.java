package com.valuequest.services.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.valuequest.common.DataTables;
import com.valuequest.controller.administration.model.StateModel;
import com.valuequest.dao.FeeStructureDao;
import com.valuequest.entity.FeeStructure;
import com.valuequest.entity.security.SecUser;
import com.valuequest.services.FeeStructureService;

@Service
@Transactional(readOnly = true)
public class FeeStructureServiceImpl extends SimpleServiceImpl<FeeStructure> implements FeeStructureService{

	@Autowired
	FeeStructureDao feeStructureDao;
	
	@Override
	public Class<FeeStructure> getRealClass() {
		return FeeStructure.class;
	}

	@Override
	@Modifying
    @Transactional(propagation=Propagation.REQUIRED)
    public void save(FeeStructure model, SecUser user) {
		FeeStructure feeStructure = null;
    	if (model.getId() != null){
    		feeStructure = this.findById(model.getId());
    		feeStructure.setLastUpdatedBy(user.getId());
    		feeStructure.setLastUpdatedDate(new Date());
    	}else{
    		feeStructure = new FeeStructure();
    		feeStructure.setCreatedBy(user.getId());
    		feeStructure.setCreatedDate(new Date());
    	}
    	
    	feeStructure.setClientType(model.getClientType());
    	feeStructure.setTransType(model.getTransType());
    	feeStructure.setStartRange(model.getStartRange());
    	feeStructure.setEndRange(model.getEndRange());
    	feeStructure.setTotalCharge(model.getTotalCharge());
    	feeStructure.setAgentIncome(model.getAgentIncome());
    	feeStructure.setFdsFee(model.getFdsFee());
    	feeStructure.setCmitFee(model.getCmitFee());
    	feeStructure.setBankIncome(model.getBankIncome());
    	feeStructure.setAgentTargetIncome(model.getAgentTargetIncome());
    	feeStructure.setBancnetIncome(model.getBancnetIncome());
    	this.saveOrUpdate(feeStructure);
    	model.setId(feeStructure.getId());

		
    }

	@Override
	@Modifying
    @Transactional(propagation=Propagation.REQUIRED)
	public void delete(List<StateModel> states) {
		List<Long> ids = new ArrayList<Long>();
		for (StateModel model : states) {
			ids.add(model.getId());
		}
		
		if(ids.size() > 0){
			this.getSessionFactory().getCurrentSession().createQuery("delete from FeeStructure where id in :ids").setParameterList("ids", ids).executeUpdate();
		}
	}

	@Override
	public DataTables searchByMapCriteria(DataTables dataTables, HashMap<String, Object> searchMap) {
		String transType	= (String) searchMap.get("transType");
		Criteria criteria 	= this.getSessionFactory().getCurrentSession().createCriteria(getRealClass());

		if (StringUtils.isNotBlank(transType))
			criteria.add(Restrictions.eq("transType", transType));
		
		return this.getDataTablesFromCriteria(criteria, dataTables);
	}

	@Override
	public Boolean checkingDuplicateRange(Long startRange, Long endRange, String transType, Long id) {
		return feeStructureDao.checkingDuplicateRange(startRange, endRange, transType, id);
	}
}
