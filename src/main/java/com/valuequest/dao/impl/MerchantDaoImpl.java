package com.valuequest.dao.impl;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.valuequest.dao.MerchantDao;
import com.valuequest.entity.MerchantEntity;

@Repository
public class MerchantDaoImpl extends BaseDao<MerchantEntity> implements MerchantDao {
	@Autowired
	SessionFactory sessionFactory;

	@Override
	public Boolean ifExist(Long id, String businessName) {
		Long count;
		count = (Long) sessionFactory.getCurrentSession().createQuery(
				"select count(id) from MerchantEntity where merchantName = :merchantName and clientId <> :clientId")
				.setParameter("merchantName", businessName).setLong("clientId", id)
				.uniqueResult();
		if(count > 0) {
			return true;
		}
		return false;
	}
}
