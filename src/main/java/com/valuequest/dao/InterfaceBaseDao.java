package com.valuequest.dao;

import java.util.Collection;
import java.util.List;

import org.springframework.dao.DataAccessException;

public interface InterfaceBaseDao<T> {

	public T merge(T entity) throws DataAccessException;
	
	public void persist(T entity) throws DataAccessException;

	public void refresh(T entity) throws DataAccessException;

	public void save(T entity) throws DataAccessException;

	public void saveOrUpdate(T entity) throws DataAccessException;

	public void saveOrUpdateAll(Collection<T> entities) throws DataAccessException;

	public void update(T entity) throws DataAccessException;

	public void delete(T entity) throws DataAccessException;

	public void deleteAll(Collection<T> entities) throws DataAccessException;

	public T get(Class<T> entityClass, Long id) throws DataAccessException;

	public List<T> loadAll(Class<T> clazz) throws DataAccessException;
}
