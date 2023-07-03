package com.valuequest.dao.impl;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.valuequest.dao.CommissionSetupDao;
import com.valuequest.entity.StructureCommissionSetup;

@Repository
public class CommissionSetupDaoImpl extends BaseDao<StructureCommissionSetup> implements CommissionSetupDao {

	@Autowired
	SessionFactory sessionFactory;

	@Override
	public StructureCommissionSetup findById(Long id) {
		return (StructureCommissionSetup) sessionFactory.getCurrentSession().createQuery("from StructureCommissionSetup where id = :id")
				.setParameter("id", id).uniqueResult();
	}

	@Override
	public void delete(List<Long> ids) {
		sessionFactory.getCurrentSession().createQuery("delete from StructureCommissionSetup where id in :ids")
				.setParameterList("ids", ids).executeUpdate();
	}

}
