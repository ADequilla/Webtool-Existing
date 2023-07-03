package com.valuequest.services.impl;

import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.valuequest.common.DataTables;
import com.valuequest.dao.TransactionDao;
import com.valuequest.entity.ViewTransaction;
import com.valuequest.services.TransactionService;

@Service
@Transactional(readOnly = true)
public class TransactionServiceImpl extends SimpleServiceImpl<ViewTransaction> implements TransactionService{

	
	@Autowired
	private TransactionDao transactionDao;
	
	@Override
	public ViewTransaction findByTrnType(String trnType) {
		return transactionDao.findByTrnType(trnType);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<ViewTransaction> getAll() {
		Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(ViewTransaction.class);
		return criteria.list();
	}

	@Override
	public Class<ViewTransaction> getRealClass() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DataTables searchByMapCriteria(DataTables dataTables, HashMap<String, Object> searchMap) {
		String trnType   = (String) searchMap.get("trnType");
		String cid   = (String) searchMap.get("cid");
		String clientName   = (String) searchMap.get("clientName");
		
		Criteria criteria 	= this.getSessionFactory().getCurrentSession().createCriteria(ViewTransaction.class);
		
		if (StringUtils.isNotBlank(trnType))
			criteria.add(Restrictions.eq("trnType", trnType));
		
		if (StringUtils.isNotBlank(cid))
			criteria.add(Restrictions.eq("cid", cid));
		
		if (StringUtils.isNotBlank(clientName))
			criteria.add(Restrictions.like("clientName", clientName));

		return this.getDataTablesFromCriteria(criteria, dataTables);
	}

	@Override
	public ViewTransaction findByCid(String cid) {
		return transactionDao.findByCid(cid);
	}

	@Override
	public ViewTransaction findByClientName(String clientName) {
		return transactionDao.findByClientName(clientName);
	}

}
