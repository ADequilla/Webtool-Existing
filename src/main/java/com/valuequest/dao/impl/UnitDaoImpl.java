package com.valuequest.dao.impl;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.valuequest.dao.UnitDao;
import com.valuequest.entity.StructureUnit;

@Repository
public class UnitDaoImpl extends BaseDao<StructureUnit> implements UnitDao {

	@Autowired
	SessionFactory sessionFactory;

	@Override
	public StructureUnit findByCode(String code) {
		return (StructureUnit) sessionFactory.getCurrentSession().createQuery("from StructureUnit where code = :code")
				.setParameter("code", code).uniqueResult();
	}

	@Override
	public void delete(List<String> ids) {
		sessionFactory.getCurrentSession().createQuery("delete from StructureUnit where code in :ids")
				.setParameterList("ids", ids).executeUpdate();
	}

	@Override
	public void deleteUserMappedBy(Long userId) {
		sessionFactory.getCurrentSession().createQuery("delete from UserUnit where user.id = :userId")
				.setParameter("userId", userId).executeUpdate();
	}
}
