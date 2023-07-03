package com.valuequest.dao.impl;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.valuequest.dao.BillerPayDao;
import com.valuequest.entity.StructureBillerPay;

@Repository
public class BillerPayDaoImpl extends BaseDao<StructureBillerPay> implements BillerPayDao {

	@Autowired
	SessionFactory sessionFactory;

	@Override
	public StructureBillerPay findByCode(String code) {
		return (StructureBillerPay) sessionFactory.getCurrentSession().createQuery("from StructureBillerPay where billerCode = :code")
				.setParameter("code", code).uniqueResult();
	}

	@Override
	public void delete(List<String> ids) {
		sessionFactory.getCurrentSession().createQuery("delete from StructureBillerPay where billerCode in :ids")
				.setParameterList("ids", ids).executeUpdate();
	}

}
