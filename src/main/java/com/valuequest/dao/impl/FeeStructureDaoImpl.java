package com.valuequest.dao.impl;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.valuequest.controller.utilities.model.OldFeeStructure;
import com.valuequest.dao.FeeStructureDao;
import com.valuequest.entity.FeeStructure;
import org.hibernate.Criteria;

import java.util.List;

@Repository
public class FeeStructureDaoImpl extends BaseDao<FeeStructure> implements FeeStructureDao {
	@Autowired
	SessionFactory sessionFactory;

	@Override
	public Boolean checkingDuplicateRange(Long startRange, Long endRange, String transType, Long id) {
		Long count;
		
		if(id == null) {
			count = (Long) sessionFactory.getCurrentSession().createQuery(
					"select count(id) from FeeStructure where ((startRange <= :startRange and endRange >= :startRange) "
					+ " or (startRange <= :endRange and endRange >= :endRange)) and transType = :transType")
					.setLong("startRange", startRange).setLong("endRange", endRange).setParameter("transType", transType)
					.uniqueResult();
		}else {
			count = (Long) sessionFactory.getCurrentSession().createQuery(
					"select count(id) from FeeStructure where ((startRange <= :startRange and endRange >= :startRange) "
					+ " or (startRange <= :endRange and endRange >= :endRange)) and transType = :transType and id <> :feeId")
					.setLong("startRange", startRange).setLong("endRange", endRange).setParameter("transType", transType).setLong("feeId", id)
					.uniqueResult();
		}

		if(count > 0) {
			
			return true;
		}
		return false;
	}
}
