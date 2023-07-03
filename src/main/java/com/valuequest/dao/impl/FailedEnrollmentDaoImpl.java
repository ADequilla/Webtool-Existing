package com.valuequest.dao.impl;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.valuequest.dao.FailedEnrollmentDao;
import com.valuequest.entity.ListFailedEnrollment;

@Repository
public class FailedEnrollmentDaoImpl extends BaseDao<ListFailedEnrollment> implements FailedEnrollmentDao {

	@Autowired
	SessionFactory sessionFactory;

	@Override
	public ListFailedEnrollment findById(Long id) {
		return (ListFailedEnrollment) sessionFactory.getCurrentSession().createQuery("from ListFailedEnrollment where id = :id")
				.setParameter("id", id).uniqueResult();
	}

	@Override
	public void delete(List<Long> ids) {
		sessionFactory.getCurrentSession().createQuery("delete from ListFailedEnrollment where id in :ids")
				.setParameterList("ids", ids).executeUpdate();
	}

}
