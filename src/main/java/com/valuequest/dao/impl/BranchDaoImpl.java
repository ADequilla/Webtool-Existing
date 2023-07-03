package com.valuequest.dao.impl;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.valuequest.dao.BranchDao;
import com.valuequest.entity.StructureBranch;

@Repository
public class BranchDaoImpl extends BaseDao<StructureBranch> implements BranchDao {

	@Autowired
	SessionFactory sessionFactory;

	@Override
	public StructureBranch findByCode(String code) {
		return (StructureBranch) sessionFactory.getCurrentSession()
				.createQuery("from StructureBranch where code = :code").setParameter("code", code).uniqueResult();
	}

	@Override
	public void delete(List<String> ids) {
		sessionFactory.getCurrentSession().createQuery("delete from StructureBranch where code in :ids")
				.setParameterList("ids", ids).executeUpdate();
	}

	@Override
	public void deleteUserMappedBy(Long userId) {
		sessionFactory.getCurrentSession().createQuery("delete from UserBranch where user.id = :userId")
				.setParameter("userId", userId).executeUpdate();
	}
}
