package com.valuequest.dao.impl;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.valuequest.dao.ProviderDao;
import com.valuequest.entity.StructureProvider;

@Repository
public class ProviderDaoImpl extends BaseDao<StructureProvider> implements ProviderDao {

	@Autowired
	SessionFactory sessionFactory;

	@Override
	public StructureProvider findById(Long id) {
		return (StructureProvider) sessionFactory.getCurrentSession().createQuery("from StructureProvider where id = :id")
				.setParameter("id", id).uniqueResult();
	}

	@Override
	public void delete(List<Long> ids) {
		sessionFactory.getCurrentSession().createQuery("delete from StructureProvider where id in :ids")
				.setParameterList("ids", ids).executeUpdate();
	}

}
