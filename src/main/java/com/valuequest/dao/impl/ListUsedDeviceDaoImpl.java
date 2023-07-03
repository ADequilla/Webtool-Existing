package com.valuequest.dao.impl;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.valuequest.dao.ListUsedDeviceDao;
import com.valuequest.entity.ListUsedDevice;

@Repository
public class ListUsedDeviceDaoImpl extends BaseDao<ListUsedDevice> implements ListUsedDeviceDao {

	@Autowired
	SessionFactory sessionFactory;

	@Override
	public ListUsedDevice findById(Long id) {
		return (ListUsedDevice) sessionFactory.getCurrentSession().createQuery("from ListUsedDevice where id = :id")
				.setParameter("id", id).uniqueResult();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ListUsedDevice> findByCid(String cid) {
		return sessionFactory.getCurrentSession().createQuery("from ListUsedDevice where cid = :cid")
				.setParameter("cid", cid).list();
	}
	
	@Override
	public void delete(List<Long> ids) {
		sessionFactory.getCurrentSession().createQuery("delete from ListUsedDevice where id in :ids")
				.setParameterList("ids", ids).executeUpdate();
	}

}
