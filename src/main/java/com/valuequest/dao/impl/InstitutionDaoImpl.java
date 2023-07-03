package com.valuequest.dao.impl;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.valuequest.dao.InstitutionDao;
import com.valuequest.entity.StructureInstitution;

@Repository
public class InstitutionDaoImpl extends BaseDao<StructureInstitution> implements InstitutionDao {

	@Autowired
	SessionFactory sessionFactory;

	@Override
	public StructureInstitution findByCode(String code) {
		return (StructureInstitution) sessionFactory.getCurrentSession()
				.createQuery("from StructureInstitution where code = :code").setParameter("code", code).uniqueResult();
	}

	@Override
	public void delete(List<String> ids) {
		sessionFactory.getCurrentSession().createQuery("delete from StructureInstitution where code in :ids")
				.setParameterList("ids", ids).executeUpdate();
	}

	@Override
	public void deleteUserMappedBy(Long userId) {
		sessionFactory.getCurrentSession().createQuery("delete from UserInstitution where user.id = :userId")
				.setParameter("userId", userId).executeUpdate();
	}
}
