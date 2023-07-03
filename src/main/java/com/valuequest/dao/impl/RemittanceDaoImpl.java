package com.valuequest.dao.impl;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.valuequest.dao.RemittanceDao;
import com.valuequest.entity.ViewRemittance;
//import com.valuequest.entity.StructureRemittance;

@Repository
public class RemittanceDaoImpl extends BaseDao<ViewRemittance> implements RemittanceDao {

	@Autowired
	SessionFactory sessionFactory;

	@Override
	public ViewRemittance findById(Long id) {
		return (ViewRemittance) sessionFactory.getCurrentSession().createQuery("from ViewRemittance where id = :id")
				.setParameter("id", id).uniqueResult();
	}

	@Override
	public void delete(List<Long> ids) {
		sessionFactory.getCurrentSession().createQuery("delete from ViewRemittance where id in :ids")
				.setParameterList("ids", ids).executeUpdate();
	}

}
