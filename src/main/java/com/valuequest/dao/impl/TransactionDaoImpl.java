package com.valuequest.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.valuequest.dao.TransactionDao;
import com.valuequest.entity.ViewTransaction;

@Repository
public class TransactionDaoImpl  extends BaseDao<ViewTransaction> implements TransactionDao {

	@Override
	public ViewTransaction findByTrnType(String trnType) {
		return (ViewTransaction) sessionFactory.getCurrentSession().createQuery("from ViewTransaction where trnType = :trnType")
				.setParameter("trnType", trnType).uniqueResult();
	}

	@Override
	public void delete(List<String> ids) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ViewTransaction findByCid(String cid) {
		return (ViewTransaction) sessionFactory.getCurrentSession().createQuery("from ViewTransaction where cid = :cid")
				.setParameter("cid", cid).uniqueResult();
	}

	@Override
	public ViewTransaction findByClientName(String clientName) {
		return (ViewTransaction) sessionFactory.getCurrentSession().createQuery("from ViewTransaction where clientName = :clientName")
				.setParameter("clientName", clientName).uniqueResult();
	}

}	
