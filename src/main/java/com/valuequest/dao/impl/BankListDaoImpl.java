package com.valuequest.dao.impl;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.valuequest.dao.BankListDao;
import com.valuequest.entity.StructureBankList;

@Repository
public class BankListDaoImpl extends BaseDao<StructureBankList> implements BankListDao {

	@Autowired
	SessionFactory sessionFactory;

	@Override
	public StructureBankList findByBankCode(String bankCode) {
		return (StructureBankList) sessionFactory.getCurrentSession().createQuery("from StructureBankList where bankCode = :bankCode")
				.setParameter("bankCode", bankCode).uniqueResult();
	}

	@Override
	public void delete(List<Long> ids) {
		sessionFactory.getCurrentSession().createQuery("delete from StructureBankList where id in :ids")
				.setParameterList("ids", ids).executeUpdate();
	}

}
