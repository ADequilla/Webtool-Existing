package com.valuequest.dao.impl;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.valuequest.dao.BillerProductDao;
import com.valuequest.entity.StructureBillerProduct;

@Repository
public class BillerProductDaoImpl extends BaseDao<StructureBillerProduct> implements BillerProductDao {

	@Autowired
	SessionFactory sessionFactory;

	@Override
	public StructureBillerProduct findById(Long id) {
		return (StructureBillerProduct) sessionFactory.getCurrentSession().createQuery("from StructureBillerProduct where billerProductId = :id")
				.setParameter("id", id).uniqueResult();
	}

	@Override
	public void delete(List<Long> ids) {
		sessionFactory.getCurrentSession().createQuery("delete from StructureBillerProduct where billerProductId in :ids")
				.setParameterList("ids", ids).executeUpdate();
	}

}
