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
import com.valuequest.controller.maintenance.model.BankListModel;
import com.valuequest.dao.BankListDao;
import com.valuequest.entity.StructureBankList;
import com.valuequest.entity.security.SecUser;
import com.valuequest.services.BankListService;

@Service
@Transactional(readOnly = true)
public class BankListServiceImpl extends SimpleServiceImpl<StructureBankList> implements BankListService {

	@Autowired
	private BankListDao bankListDao;
	
	@Override
	public Class<StructureBankList> getRealClass() {
		return StructureBankList.class;
	}

	@Override
	public StructureBankList findByBankCode(String bankCode) {
		return bankListDao.findByBankCode(bankCode);
	}

	@Override
	public boolean isExist(String bankCode) {
		StructureBankList bankList = findByBankCode(bankCode);
		if (bankList == null) {
			return false;
		}
		
		return true;
	}
	
	@Override
	@Modifying
    @Transactional(propagation=Propagation.REQUIRED)
    public void save(BankListModel model, SecUser user) {
		StructureBankList  bankList = findById(model.getId());
		if (bankList == null){
			bankList = new StructureBankList();
			bankList.setId(model.getId());
			bankList.setBankCode(model.getBankCode());
			bankList.setBankName(model.getBankName());
			bankList.setShortName(model.getShortName());
			bankList.setBankBic(model.getBankBic());
			bankList.setCreatedDate(new Date());
			bankList.setCreatedBy(user.getId());
			bankList.setUpdatedDate(new Date());
			bankList.setUpdatedBy(user.getId());
		}else{
			bankList.setUpdatedDate(new Date());
			bankList.setUpdatedBy(user.getId());
			bankList.setBankCode(model.getBankCode());
			bankList.setBankName(model.getBankName());
			bankList.setShortName(model.getShortName());
			bankList.setBankBic(model.getBankBic());
		}
		
		this.saveOrUpdate(bankList);
	}

	@Override
	@Modifying
    @Transactional(propagation=Propagation.REQUIRED)
	public void delete(List<BankListModel> states) {
		List<Long> ids = new ArrayList<Long>();
		for (BankListModel model : states) {
			ids.add(model.getId());
		}

		if (ids.size() > 0) {
			bankListDao.delete(ids);
		}
	}


	@Override
	public DataTables searchByMapCriteria(DataTables dataTables, HashMap<String, Object> searchMap) {
		String bankCode = (String) searchMap.get("bankCode");
		String bankName = (String) searchMap.get("bankName");
		String shortName = (String) searchMap.get("shortName");
		
		Criteria criteria 	= this.getSessionFactory().getCurrentSession().createCriteria(StructureBankList.class);
		
		if (StringUtils.isNotBlank(bankCode))
			criteria.add(Restrictions.ilike("bankCode", bankCode));
		
		if (StringUtils.isNotBlank(bankName))
			criteria.add(Restrictions.ilike("bankName", bankName, MatchMode.ANYWHERE));
		
		if (StringUtils.isNotBlank(shortName))
			criteria.add(Restrictions.ilike("shortName", shortName, MatchMode.ANYWHERE));
		
		return this.getDataTablesFromCriteria(criteria, dataTables);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<StructureBankList> getAll() {
		Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(StructureBankList.class);
		return criteria.list();
	}
	
}
