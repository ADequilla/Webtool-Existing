package com.valuequest.dao.impl;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.valuequest.dao.ProductDenomDao;
import com.valuequest.entity.StructureProductDenom;
import com.valuequest.entity.ViewProductDenom;

@Repository
public class ProductDenomDaoImpl extends BaseDao<StructureProductDenom> implements ProductDenomDao {

	@Autowired
	SessionFactory sessionFactory;

	@Override
	public StructureProductDenom findByCode(Long id) {
		return (StructureProductDenom) sessionFactory.getCurrentSession().createQuery("from StructureProductDenom where id = :id")
				.setParameter("id", id).uniqueResult();
	}

	@Override
	public void delete(List<Long> ids) {
		sessionFactory.getCurrentSession().createQuery("delete from StructureProductDenom where id in :ids")
				.setParameterList("ids", ids).executeUpdate();
	}

	@Override
	public StructureProductDenom findByParam(Long productId, Long denom, String description) {
		return (StructureProductDenom) sessionFactory.getCurrentSession().createQuery("from StructureProductDenom where "
				+ " productId = :productId "
				+ " and denom = :denom "
				+ " and description = :description")
				.setParameter("productId", productId)
				.setParameter("denom", denom)
				.setParameter("description", description).uniqueResult();
	}
	
	@Override
	public ViewProductDenom findProductProviderDenom(Long id) {
		return (ViewProductDenom) sessionFactory.getCurrentSession().createQuery("from ViewProductDenom where id = :id")
				.setParameter("id", id).uniqueResult();
	}

}
