package com.valuequest.dao.impl;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.valuequest.dao.RoutesDao;
import com.valuequest.entity.ProductTypeEntity;
import com.valuequest.entity.ViewRoutes;

	@Repository
public class RoutesDaoImpl extends BaseDao<ViewRoutes> implements RoutesDao {

	@Override
	public ViewRoutes findByTrnCode(String trnCode) {
		return (ViewRoutes) sessionFactory.getCurrentSession().createQuery("from ViewRoutes where trnCode = :trnCode")
				.setParameter("trnCode", trnCode).uniqueResult();
	}

	@Override
	public void delete(List<String> ids) {
		// TODO Auto-generated method stub
		
	}


}
