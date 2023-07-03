package com.valuequest.dao.impl;

import java.util.Collection;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;

import com.valuequest.dao.InterfaceBaseDao;

@SuppressWarnings("unchecked")
public class BaseDao<T> implements InterfaceBaseDao<T> {

	@Autowired
	protected SessionFactory sessionFactory;

	@Override
	public T merge(T entity) throws DataAccessException {
		return (T) sessionFactory.getCurrentSession().merge(entity);
	}

	@Override
	public void persist(T entity) throws DataAccessException {
		sessionFactory.getCurrentSession().persist(entity);
	}

	@Override
	public void refresh(T entity) throws DataAccessException {
		sessionFactory.getCurrentSession().refresh(entity);
	}

	@Override
	public void save(T entity) throws DataAccessException {
		sessionFactory.getCurrentSession().save(entity);
	}

	@Override
	public void saveOrUpdate(T entity) throws DataAccessException {
		sessionFactory.getCurrentSession().saveOrUpdate(entity);
	}

	@Override
	public void saveOrUpdateAll(Collection<T> entities) throws DataAccessException {
		for (T entity : entities) {
			saveOrUpdate(entity);
		}
	}

	@Override
	public void update(T entity) throws DataAccessException {
		sessionFactory.getCurrentSession().update(entity);
	}

	@Override
	public void delete(T entity) throws DataAccessException {
		sessionFactory.getCurrentSession().delete(entity);
	}

	@Override
	public void deleteAll(Collection<T> entities) throws DataAccessException {
		for (T entity : entities) {
			delete(entity);
		}
	}

	@Override
	public T get(Class<T> entityClass, Long id) throws DataAccessException {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(entityClass);
		criteria.add(Restrictions.eq("id", id));
		return (T) criteria.uniqueResult();
	}

	@Override
	public List<T> loadAll(Class<T> clazz) {
		return sessionFactory.getCurrentSession().createCriteria(clazz).list();
	}
}
