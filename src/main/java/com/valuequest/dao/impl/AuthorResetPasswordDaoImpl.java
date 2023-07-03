package com.valuequest.dao.impl;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.valuequest.dao.AuthorResetPasswordDao;
import com.valuequest.entity.AuthorResetPassword;
import com.valuequest.entity.ViewAuthorResetPassword;

@Repository
public class AuthorResetPasswordDaoImpl extends BaseDao<AuthorResetPassword> implements AuthorResetPasswordDao {

	@Autowired
	SessionFactory sessionFactory;

	@Override
	public ViewAuthorResetPassword findByCid(String cid) {
		return (ViewAuthorResetPassword) sessionFactory.getCurrentSession().createQuery("from ViewAuthorResetPassword where cid = :cid")
				.setParameter("cid", cid).uniqueResult();
	}
	
	@Override
	public ViewAuthorResetPassword findByParam(Long id) {
		return (ViewAuthorResetPassword) sessionFactory.getCurrentSession().createQuery("from ViewAuthorResetPassword where id = :id")
				.setParameter("id", id).uniqueResult();
	}

	@Override
	public void delete(List<Long> ids) {
		sessionFactory.getCurrentSession().createQuery("delete from AuthorResetPassword where id in :ids")
				.setParameterList("ids", ids).executeUpdate();
	}

}
