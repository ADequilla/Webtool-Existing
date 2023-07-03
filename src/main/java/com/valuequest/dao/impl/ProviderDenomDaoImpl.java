package com.valuequest.dao.impl;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.valuequest.dao.ProviderDenomDao;
import com.valuequest.entity.StructureProviderDenom;

@Repository
public class ProviderDenomDaoImpl extends BaseDao<StructureProviderDenom> implements ProviderDenomDao {

	@Autowired
	SessionFactory sessionFactory;

	@Override
	public StructureProviderDenom findByCode(Long id) {
		return (StructureProviderDenom) sessionFactory.getCurrentSession().createQuery("from StructureProviderDenom where id = :id")
				.setParameter("id", id).uniqueResult();
	}

	@Override
	public void delete(List<Long> ids) {
		sessionFactory.getCurrentSession().createQuery("delete from StructureProviderDenom where productDenomId in :ids")
				.setParameterList("ids", ids).executeUpdate();
	}
	
	@Override
	public StructureProviderDenom findByIdProductDenom(Long productDenomId) {
		return (StructureProviderDenom) sessionFactory.getCurrentSession().createQuery("from StructureProviderDenom where productDenomId = :productDenomId")
				.setParameter("productDenomId", productDenomId).uniqueResult();
	}


}
