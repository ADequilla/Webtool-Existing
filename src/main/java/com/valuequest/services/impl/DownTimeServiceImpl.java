package com.valuequest.services.impl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.valuequest.common.DataTables;
import com.valuequest.controller.administration.model.StateModel;
import com.valuequest.entity.Lookup;
import com.valuequest.entity.ServiceDownTime;
import com.valuequest.entity.security.SecUser;
import com.valuequest.services.DownTimeService;
import com.valuequest.services.GenericService;
import com.valuequest.util.Constantas;
import com.valuequest.util.DateUtil;

@Service
@Transactional(readOnly = true)
public class DownTimeServiceImpl extends SimpleServiceImpl<ServiceDownTime> implements DownTimeService {

	@Autowired
	private GenericService genericService;
	
	@Override
	public Class<ServiceDownTime> getRealClass() {
		return ServiceDownTime.class;
	}

	@Override
	@Modifying
    @Transactional(propagation=Propagation.REQUIRED)
    public void save(ServiceDownTime model, SecUser user) {
		ServiceDownTime serviceDownTime = null;
		if (model.getId() != null) {
			serviceDownTime = this.findById(model.getId());
			serviceDownTime.setUpdatedBy(user.getId());
			serviceDownTime.setUpdatedDate(new Date(System.currentTimeMillis()));
		} else {
			serviceDownTime = new ServiceDownTime();
			serviceDownTime.setCreatedBy(user.getId());
			serviceDownTime.setDate(new Date(System.currentTimeMillis()));
		}
		serviceDownTime.setDesc(model.getDesc());
		serviceDownTime.setStartDate(model.getStartDate());
		serviceDownTime.setEndDate(model.getEndDate());
		serviceDownTime.setType(model.getType());
		
		this.saveOrUpdate(serviceDownTime);
		model.setId(serviceDownTime.getId());
	}

	@Override
	@Modifying
    @Transactional(propagation=Propagation.REQUIRED)
	public void delete(List<StateModel> states) {
		List<Long> ids = new ArrayList<Long>();
		for (StateModel model : states) {
			ids.add(model.getId());
		}

		if (ids.size() > 0) {
			this.getSessionFactory().getCurrentSession().createQuery("delete from ServiceDownTime where id in :ids").setParameterList("ids", ids).executeUpdate();
		}
	}

	@Override
	public DataTables searchByMapCriteria(DataTables dataTables, HashMap<String, Object> searchMap) {
		String startDate	= (String) searchMap.get("startDate");
		String endDate		= (String) searchMap.get("endDate");
		String desc			= (String) searchMap.get("desc");
		Criteria criteria 	= this.getSessionFactory().getCurrentSession().createCriteria(getRealClass());

		if (StringUtils.isNotBlank(desc))
			criteria.add(Restrictions.ilike("desc", desc, MatchMode.ANYWHERE));
		
		if(StringUtils.isNotBlank(startDate)){
			try {
				Date date = Constantas.fdate.parse(startDate);
				criteria.add(Restrictions.ge("startDate", DateUtil.truncate(date)));
			} catch (ParseException e) {
				e.printStackTrace();
			}	
		}
		
		if(StringUtils.isNotBlank(endDate)){
			try {
				Date date = Constantas.fdate.parse(endDate);
				criteria.add(Restrictions.le("endDate", DateUtil.endOfDay(date)));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}

		return this.getDataTablesFromCriteria(criteria, dataTables);
	}

	@Override
	public DataTables searchByMapCriteria(DataTables dataTables, HashMap<String, Object> searchMap,
			String lookupDesignation) {
		String startDate	= (String) searchMap.get("startDate");
		String endDate		= (String) searchMap.get("endDate");
		String desc			= (String) searchMap.get("desc");
		Criteria criteria 	= this.getSessionFactory().getCurrentSession().createCriteria(getRealClass());

		if (StringUtils.isNotBlank(desc))
			criteria.add(Restrictions.ilike("desc", desc, MatchMode.ANYWHERE));
		
		if(StringUtils.isNotBlank(startDate)){
			try {
				Date date = Constantas.fdate.parse(startDate);
				criteria.add(Restrictions.ge("startDate", DateUtil.truncate(date)));
			} catch (ParseException e) {
				e.printStackTrace();
			}	
		}
		
		if(StringUtils.isNotBlank(endDate)){
			try {
				Date date = Constantas.fdate.parse(endDate);
				criteria.add(Restrictions.le("endDate", DateUtil.endOfDay(date)));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		
		if(Lookup.LOOKUP_DESIGNATION.equals(lookupDesignation)) {
			List<String> list = new ArrayList<>();
			List<Lookup> lookups = genericService.lookup(lookupDesignation);
			for (Lookup l : lookups) {
				list.add(l.getValue());
			}
			criteria.add(Restrictions.in("type", list));
		}else {
			List<String> list = new ArrayList<>();
			List<Lookup> lookups = genericService.lookup(Lookup.LOOKUP_DESIGNATION);
			for (Lookup l : lookups) {
				list.add(l.getValue());
			}
			criteria.add(Restrictions.not(Restrictions.in("type", list)));
		}
		
		
		return this.getDataTablesFromCriteria(criteria, dataTables);
	}
}