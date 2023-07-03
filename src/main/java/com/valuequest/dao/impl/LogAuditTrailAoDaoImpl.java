package com.valuequest.dao.impl;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.valuequest.dao.LogAuditTrailAoDao;
import com.valuequest.entity.LogAuditTrailAo;

@Repository
public class LogAuditTrailAoDaoImpl extends BaseDao<LogAuditTrailAo> implements LogAuditTrailAoDao {

	@Autowired
	SessionFactory sessionFactory;

	@Override
	public LogAuditTrailAo findById(Long id) {
		return (LogAuditTrailAo) sessionFactory.getCurrentSession().createQuery("from LogAuditTrailAo where id = :id")
				.setParameter("id", id).uniqueResult();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<LogAuditTrailAo> findByAoId(Long aoId) {
		return sessionFactory.getCurrentSession().createQuery("from LogAuditTrailAo where aoId = :aoId and app_type='WEB' ORDER BY id DESC")
				.setParameter("aoId", aoId).list();
	}
	
	@Override
	public void delete(List<Long> ids) {
		sessionFactory.getCurrentSession().createQuery("delete from LogAuditTrailAo where id in :ids")
				.setParameterList("ids", ids).executeUpdate();
	}

}
