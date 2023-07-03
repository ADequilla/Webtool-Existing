package com.valuequest.dao.impl;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.valuequest.dao.CenterDao;
import com.valuequest.entity.StructureCenter;

@Repository
public class CenterDaoImpl extends BaseDao<StructureCenter> implements CenterDao {

	@Autowired
	SessionFactory sessionFactory;

	@Override
	public StructureCenter findByCode(String code) {
		return (StructureCenter) sessionFactory.getCurrentSession()
				.createQuery("from StructureCenter where code = :code").setParameter("code", code).uniqueResult();
	}

	@Override
	public void delete(List<String> ids) {
		sessionFactory.getCurrentSession().createQuery("delete from StructureCenter where code in :ids")
				.setParameterList("ids", ids).executeUpdate();
	}

	@Override
	public void deleteUserMappedBy(Long userId) {
		sessionFactory.getCurrentSession().createQuery("delete from UserCenter where user.id = :userId")
				.setParameter("userId", userId).executeUpdate();
	}
}
