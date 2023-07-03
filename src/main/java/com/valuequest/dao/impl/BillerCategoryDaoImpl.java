package com.valuequest.dao.impl;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.valuequest.dao.BillerCategoryDao;
import com.valuequest.entity.StructureBillerCategory;

@Repository
public class BillerCategoryDaoImpl extends BaseDao<StructureBillerCategory> implements BillerCategoryDao {

	@Autowired
	SessionFactory sessionFactory;

	@Override
	public StructureBillerCategory findById(Long id) {
		return (StructureBillerCategory) sessionFactory.getCurrentSession().createQuery("from StructureBillerCategory where id = :id")
				.setParameter("id", id).uniqueResult();
	}

	@Override
	public void delete(List<Long> ids) {
		sessionFactory.getCurrentSession().createQuery("delete from StructureBillerCategory where id in :ids")
				.setParameterList("ids", ids).executeUpdate();
	}

}
