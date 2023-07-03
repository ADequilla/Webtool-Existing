package com.valuequest.services.impl;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.valuequest.common.DataTables;
import com.valuequest.common.SortField;
import com.valuequest.entity.AsynReport;
import com.valuequest.entity.ViewAsynReport;
import com.valuequest.services.AsynReportService;
import com.valuequest.util.Constantas;
import com.valuequest.util.DateUtil;

@Service
@Transactional(readOnly = true)
public class AsynReportServiceImpl extends SimpleServiceImpl<AsynReport> implements AsynReportService{

	@Override
	public Class<AsynReport> getRealClass() {
		return AsynReport.class;
	}

	public DataTables searchByMapCriteria(DataTables dataTables, HashMap<String, Object> searchMap) {
		Long loginId				= (Long) searchMap.get("loginId");
		String type					= (String) searchMap.get("type");
		String submitedDateStart	= (String) searchMap.get("submitedDateStart");
		String submitedDateEnd		= (String) searchMap.get("submitedDateEnd");
		String completedDateStart	= (String) searchMap.get("completedDateStart");
		String completedDateEnd		= (String) searchMap.get("completedDateEnd");
		String status				= (String) searchMap.get("status");
		String reportId				= (String) searchMap.get("reportId");
		String branch				= (String) searchMap.get("branch");
		String username				= (String) searchMap.get("username");

		Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(ViewAsynReport.class);
//		criteria.add(Restrictions.eq("submitedBy", loginId));
		criteria.add(Restrictions.eq("type", type));

		if(StringUtils.isNotBlank(submitedDateStart)){
			try {
				Date date = Constantas.fdate.parse(submitedDateStart);
				criteria.add(Restrictions.ge("submitedDate", DateUtil.truncate(date)));
			} catch (ParseException e) {
				e.printStackTrace();
			}	
		}
		
		if(StringUtils.isNotBlank(submitedDateEnd)){
			try {
				Date date = Constantas.fdate.parse(submitedDateEnd);
				criteria.add(Restrictions.le("submitedDate", DateUtil.endOfDay(date)));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		
		if(StringUtils.isNotBlank(completedDateStart)){
			try {
				Date date = Constantas.fdate.parse(completedDateStart);
				criteria.add(Restrictions.ge("completedDate", DateUtil.truncate(date)));
			} catch (ParseException e) {
				e.printStackTrace();
			}	
		}
		
		if(StringUtils.isNotBlank(completedDateEnd)){
			try {
				Date date = Constantas.fdate.parse(completedDateEnd);
				criteria.add(Restrictions.le("completedDate", DateUtil.endOfDay(date)));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		
		if(StringUtils.isNotBlank(status))
			criteria.add(Restrictions.eq("status", status));

		if(StringUtils.isNotBlank(reportId))
			criteria.add(Restrictions.eq("id", Long.valueOf(reportId)));

		if(StringUtils.isNotBlank(branch))
			criteria.add(Restrictions.ilike("branchCodes", branch, MatchMode.ANYWHERE));

		if (StringUtils.isNotBlank(username))
			criteria.add(Restrictions.ilike("username", username, MatchMode.ANYWHERE));

		List<SortField> sortFields = dataTables.generateSortFields();
		for (SortField sf : sortFields) {
			if ("asc".equalsIgnoreCase(sf.getDirection())) {
				criteria.addOrder(Order.asc(sf.getField()));
			} else {
				criteria.addOrder(Order.desc(sf.getField()));
			}
		}

		return this.getDataTablesFromCriteria(criteria, dataTables);
	}
}