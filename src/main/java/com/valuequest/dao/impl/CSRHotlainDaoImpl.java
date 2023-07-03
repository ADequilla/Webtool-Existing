package com.valuequest.dao.impl;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.valuequest.dao.CSRHotlainDao;
import com.valuequest.entity.CSRHotlainView;

@Repository
public class CSRHotlainDaoImpl extends BaseDao<CSRHotlainView> implements CSRHotlainDao {

	@Autowired
	SessionFactory sessionFactory;

	@Override
	public CSRHotlainView findByContactNumber(String contactNumber) {
		return (CSRHotlainView) sessionFactory.getCurrentSession().createQuery("from CSRHotlainView where contactNumber = :contactNumber")
				.setParameter("contactNumber", contactNumber).uniqueResult();
	}

	@Override
	public CSRHotlainView findById(Long id) {
		return (CSRHotlainView) sessionFactory.getCurrentSession().createQuery("from CSRHotlainView where id = :id")
				.setParameter("id", id).uniqueResult();
	}
	
	@Override
	public void delete(List<Long> ids) {
		sessionFactory.getCurrentSession().createQuery("delete from CSRHotlainView where id in :ids")
				.setParameterList("ids", ids).executeUpdate();
	}

}
