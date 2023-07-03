package com.valuequest.services.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.valuequest.common.DataTables;
import com.valuequest.entity.LogPassword;
import com.valuequest.services.LogPasswordService;

@Service
@Transactional(readOnly = true)
public class LogPasswordServiceImpl extends SimpleServiceImpl<LogPassword> implements LogPasswordService{
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public Class<LogPassword> getRealClass() {
		return LogPassword.class;
	}
	
	@Override
	@Modifying
    @Transactional(propagation=Propagation.REQUIRED)
    public void save(String loginname, String password) {

		LogPassword log = new LogPassword();
		log.setUserLogin(loginname);
		log.setUserPassword(password);
		log.setCreatedDate(new Date(System.currentTimeMillis()));
		
    	saveOrUpdate(log);
    }

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List lastPasswordList(String loginname, int period) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(LogPassword.class);
		criteria.add(Restrictions.eq("userLogin", loginname));
		criteria.addOrder(Order.desc("createdDate"));
		criteria.setMaxResults(period);
		
		List<String> passwds	= new ArrayList<String>();
		List<LogPassword> list 	= criteria.list();
		for (LogPassword log : list) {
			passwds.add(log.getUserPassword());
		}
		
		return passwds;
	}

	@Override
	public DataTables searchByMapCriteria(DataTables dataTables, HashMap<String, Object> searchMap) {
		return null;
	}

}
