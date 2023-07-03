package com.valuequest.dao.impl;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.valuequest.dao.ProductTypeDao;
import com.valuequest.entity.ProductTypeEntity;

@Repository
public class ProductTypeDaoImpl extends BaseDao<ProductTypeEntity> implements ProductTypeDao {

	@Autowired
	SessionFactory sessionFactory;
	
	@Override
	public ProductTypeEntity findById(Long id) {
		return (ProductTypeEntity) sessionFactory.getCurrentSession().createQuery("from ProductTypeEntity where productTypeId = :id")
				.setParameter("id", id).uniqueResult();
	}

	@Override
	public void delete(List<Long> ids) {
		sessionFactory.getCurrentSession().createQuery("delete from ProductTypeEntity where productTypeId in :ids")
				.setParameterList("ids", ids).executeUpdate();
		
	}

}
