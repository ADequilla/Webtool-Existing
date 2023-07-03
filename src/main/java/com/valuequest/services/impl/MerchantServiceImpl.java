package com.valuequest.services.impl;

import java.util.Date;
import java.util.HashMap;

import org.hibernate.Criteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.valuequest.common.DataTables;
import com.valuequest.dao.MerchantDao;
import com.valuequest.entity.MerchantEntity;
import com.valuequest.entity.ViewClient;
import com.valuequest.entity.security.SecUser;
import com.valuequest.services.MerchantService;

@Service
@Transactional(readOnly = true)
public class MerchantServiceImpl extends SimpleServiceImpl<MerchantEntity> implements MerchantService{

	@Autowired
	MerchantDao merchantDao;
	
	@Override
	public Class<MerchantEntity> getRealClass() {
		return MerchantEntity.class;
	}
	
	@Modifying
    @Transactional(propagation=Propagation.REQUIRED)
	@Override
	public void save(MerchantEntity merchant, SecUser userLogin) {
		merchant.setLastUpdatedBy(userLogin.getId());
		merchant.setLastUpdatedDate(new Date());
		this.saveOrUpdate(merchant);
	}
	
	@Override
	public DataTables searchByMapCriteria(DataTables dataTables, HashMap<String, Object> searchMap) {
		Criteria criteria 		= this.getSessionFactory().getCurrentSession().createCriteria(ViewClient.class);
		
		return this.getDataTablesFromCriteria(criteria, dataTables);
	}

	@Override
	public Boolean ifExist(Long id, String businessName) {
		return merchantDao.ifExist(id, businessName);
	}
}