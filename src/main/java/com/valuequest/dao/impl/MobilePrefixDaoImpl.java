package com.valuequest.dao.impl;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.valuequest.dao.MobilePrefixDao;
import com.valuequest.entity.StructureMobilePrefix;

@Repository
public class MobilePrefixDaoImpl extends BaseDao<StructureMobilePrefix> implements MobilePrefixDao {

	@Autowired
	SessionFactory sessionFactory;

	@Override
	public StructureMobilePrefix findById(Long id) {
		return (StructureMobilePrefix) sessionFactory.getCurrentSession().createQuery("from StructureMobilePrefix where id = :id")
				.setParameter("id", id).uniqueResult();
	}

	@Override
	public StructureMobilePrefix findByPrefixValue(String prefixValue) {
		return (StructureMobilePrefix) sessionFactory.getCurrentSession().createQuery("from StructureMobilePrefix where prefixValue = :prefixValue")
				.setParameter("prefixValue", prefixValue).uniqueResult();
	}
	
	@Override
	public void delete(List<Long> ids) {
		sessionFactory.getCurrentSession().createQuery("delete from StructureMobilePrefix where id in :ids")
				.setParameterList("ids", ids).executeUpdate();
	}

}
