package com.valuequest.services.impl;

import java.util.HashMap;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.valuequest.common.DataTables;
import com.valuequest.entity.ViewK2CTransactions;
import com.valuequest.services.K2CTransactionService;

@Service
public class K2CTransactionServiceImpl extends SimpleServiceImpl<ViewK2CTransactions> implements   K2CTransactionService{

	@SuppressWarnings("unchecked")
	@Override
	public List<ViewK2CTransactions> getBySessionId(String sessionId) {
		Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(ViewK2CTransactions.class);
		criteria.add(Restrictions.eq("sessionId", sessionId));
		return criteria.list();
	}

	@Override
	public Class<ViewK2CTransactions> getRealClass() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DataTables searchByMapCriteria(DataTables dataTables, HashMap<String, Object> searchMap) {
		// TODO Auto-generated method stub
		return null;
	}

	

}
