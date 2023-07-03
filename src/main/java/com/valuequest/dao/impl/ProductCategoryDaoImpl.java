package com.valuequest.dao.impl;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.valuequest.dao.ProductCategoryDao;
import com.valuequest.entity.StructureProductCategory;

@Repository
public class ProductCategoryDaoImpl extends BaseDao<StructureProductCategory> implements ProductCategoryDao {

	@Autowired
	SessionFactory sessionFactory;

	@Override
	public StructureProductCategory findById(Long id) {
		return (StructureProductCategory) sessionFactory.getCurrentSession().createQuery("from StructureProductCategory where productCategoryId = :id")
				.setParameter("id", id).uniqueResult();
	}

	@Override
	public void delete(List<Long> ids) {
		sessionFactory.getCurrentSession().createQuery("delete from StructureProductCategory where productCategoryId in :ids")
				.setParameterList("ids", ids).executeUpdate();
	}

}
