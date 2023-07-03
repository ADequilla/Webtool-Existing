package com.valuequest.dao.impl;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.valuequest.dao.LoadProductDao;
import com.valuequest.entity.StructureLoadProduct;

@Repository
public class LoadProductDaoImpl extends BaseDao<StructureLoadProduct> implements LoadProductDao {

	@Autowired
	SessionFactory sessionFactory;

	@Override
	public StructureLoadProduct findById(Long id) {
		return (StructureLoadProduct) sessionFactory.getCurrentSession().createQuery("from StructureLoadProduct where loadProductId = :id")
				.setParameter("id", id).uniqueResult();
	}

	@Override
	public void delete(List<Long> ids) {
		sessionFactory.getCurrentSession().createQuery("delete from StructureLoadProduct where loadProductId in :ids")
				.setParameterList("ids", ids).executeUpdate();
	}

}
