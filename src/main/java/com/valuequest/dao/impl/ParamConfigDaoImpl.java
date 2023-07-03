package com.valuequest.dao.impl;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.valuequest.dao.ParamConfigDao;
import com.valuequest.entity.ParamConfig;

@Repository
public class ParamConfigDaoImpl extends BaseDao<ParamConfig> implements ParamConfigDao {
	@Autowired
	SessionFactory sessionFactory;

	@Override
	public Boolean checkingDuplicateParamName(String paramName, Long id) {
		Long count;
		if(id == null) {
			count = (Long) sessionFactory.getCurrentSession().createQuery(
					"select count(id) from ParamConfig where name = :paramName ")
					.setString("paramName", paramName)
					.uniqueResult();
		}else {
			count = (Long) sessionFactory.getCurrentSession().createQuery(
					"select count(id) from ParamConfig where name = :paramName and id <> :paramId")
					.setString("paramName", paramName).setLong("paramId", id)
					.uniqueResult();
		}
		if(count > 0) {
			return true;
		}
		return false;
	}

	@Override
	public String getParamValue(String paramName) {
		String paramValue;
		
			paramValue = (String) sessionFactory.getCurrentSession().createQuery(
					"select value from ParamConfig where name = :paramName ")
					.setString("paramName", paramName)
					.uniqueResult();
	
		return paramValue;
	}
	
}
