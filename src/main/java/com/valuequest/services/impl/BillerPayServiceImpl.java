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
import com.valuequest.controller.maintenance.model.BillerPayModel;
import com.valuequest.dao.BillerPayDao;
import com.valuequest.entity.StructureBillerPay;
import com.valuequest.entity.security.SecUser;
import com.valuequest.services.BillerPayService;

@Service
@Transactional(readOnly = true)
public class BillerPayServiceImpl extends SimpleServiceImpl<StructureBillerPay> implements BillerPayService {

	@Autowired
	private BillerPayDao billerPayDao;
	
	@Override
	public Class<StructureBillerPay> getRealClass() {
		return StructureBillerPay.class;
	}

	@Override
	public StructureBillerPay findByCode(String code) {
		return billerPayDao.findByCode(code);
	}

	@Override
	public boolean isExist(String code) {
		StructureBillerPay billerPay = findByCode(code);
		if (billerPay == null) {
			return false;
		}
		
		return true;
	}
	
	@Override
	@Modifying
    @Transactional(propagation=Propagation.REQUIRED)
    public void save(BillerPayModel model, SecUser user) {
		StructureBillerPay  billerPay = findByCode(model.getBillerCode());
		if (billerPay == null){
			billerPay = new StructureBillerPay();
			billerPay.setTransactionId(1L);
			billerPay.setProviderId(1L);
			billerPay.setBillerCode(model.getBillerCode());
			billerPay.setBillerName(model.getBillerName());
			billerPay.setDescription(model.getDescription());
			billerPay.setTotalRebates(model.getTotalRebates());
			billerPay.setCustomerRebates(model.getCustomerRebates());
			billerPay.setBankRebates(model.getBankRebates());
			billerPay.setStatus(model.getStatus());
			billerPay.setCreatedDate(new Date());
			billerPay.setCreatedBy(user.getId());
			billerPay.setUpdatedDate(new Date());
			billerPay.setUpdatedBy(user.getId());
		}else{
			billerPay.setUpdatedDate(new Date());
			billerPay.setUpdatedBy(user.getId());
			billerPay.setBillerName(model.getBillerName());
			billerPay.setDescription(model.getDescription());
			billerPay.setTotalRebates(model.getTotalRebates());
			billerPay.setCustomerRebates(model.getCustomerRebates());
			billerPay.setBankRebates(model.getBankRebates());
			billerPay.setStatus(model.getStatus());
		}
		
		this.saveOrUpdate(billerPay);
	}

	@Override
	@Modifying
    @Transactional(propagation=Propagation.REQUIRED)
	public void delete(List<BillerPayModel> states) {
		List<String> ids = new ArrayList<String>();
		for (BillerPayModel model : states) {
			ids.add(model.getBillerCode());
		}

		if (ids.size() > 0) {
			billerPayDao.delete(ids);
		}
	}


	@Override
	public DataTables searchByMapCriteria(DataTables dataTables, HashMap<String, Object> searchMap) {
		String billerCode = (String) searchMap.get("billerCode");
		String billerName	= (String) searchMap.get("billerName");
		Criteria criteria 	= this.getSessionFactory().getCurrentSession().createCriteria(StructureBillerPay.class);
		
		if (StringUtils.isNotBlank(billerCode))
			criteria.add(Restrictions.eq("billerCode", billerCode));

		if (StringUtils.isNotBlank(billerName))
			criteria.add(Restrictions.ilike("billerName", billerName, MatchMode.ANYWHERE));

		return this.getDataTablesFromCriteria(criteria, dataTables);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<StructureBillerPay> getAll() {
		Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(StructureBillerPay.class);
		return criteria.list();
	}
	
}
