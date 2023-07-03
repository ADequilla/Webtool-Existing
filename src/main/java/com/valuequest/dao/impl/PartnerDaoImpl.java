package com.valuequest.dao.impl;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.valuequest.dao.PartnerDao;
import com.valuequest.entity.StructurePartner;

@Repository
public class PartnerDaoImpl extends BaseDao<StructurePartner> implements PartnerDao {

	@Autowired
	SessionFactory sessionFactory;

	@Override
	public StructurePartner findByPartnerId(String partnerId) {
		return (StructurePartner) sessionFactory.getCurrentSession().createQuery("from StructurePartner where partnerId = :partnerId")
				.setParameter("partnerId", partnerId).uniqueResult();
	}

	@Override
	public void delete(List<Long> ids) {
		sessionFactory.getCurrentSession().createQuery("delete from StructurePartner where id in :ids")
				.setParameterList("ids", ids).executeUpdate();
	}

}
