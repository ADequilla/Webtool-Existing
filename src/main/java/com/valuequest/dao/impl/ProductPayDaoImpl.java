package com.valuequest.dao.impl;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.valuequest.dao.ProductPayDao;
import com.valuequest.entity.StructureProductPay;

@Repository
public class ProductPayDaoImpl extends BaseDao<StructureProductPay> implements ProductPayDao {

	@Autowired
	SessionFactory sessionFactory;

	@Override
	public StructureProductPay findByCode(String code) {
		return (StructureProductPay) sessionFactory.getCurrentSession().createQuery("from StructureProductPay where productCode = :code")
				.setParameter("code", code).uniqueResult();
	}

	@Override
	public void delete(List<String> ids) {
		sessionFactory.getCurrentSession().createQuery("delete from StructureProductPay where productCode in :ids")
				.setParameterList("ids", ids).executeUpdate();
	}

}
