package com.valuequest.services.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.expression.ParseException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ResponseBody;

import com.valuequest.common.DataTables;
import com.valuequest.entity.ViewClient;
import com.valuequest.services.DashboardService;
import com.valuequest.util.Constantas;
import com.valuequest.util.DateUtil;

@Service
@Transactional(readOnly = true)
public class DashboardImpl extends SimpleServiceImpl<ViewClient> implements DashboardService {

	@Override
	public List<ViewClient> searchByMapCriteria(HashMap<String, Object> searchMap) {
		String startDate	= (String) searchMap.get("startDate");
		String endDate		= (String) searchMap.get("endDate");
		String insti		= (String) searchMap.get("insti");
		String branch 		= (String) searchMap.get("branch");
		String unit 		= (String) searchMap.get("unit");
		String center 		= (String) searchMap.get("center");

		System.out.println("Start Date"+ startDate);
		System.out.println("End Date"+ endDate);
		System.out.println("Insti"+ insti);
		System.out.println("Branch"+ branch);
		System.out.println("Unit"+ unit);
		System.out.println("Center"+ center);

		Criteria criteria;
		
		criteria = this.getSessionFactory().getCurrentSession().createCriteria(ViewClient.class);
		
		if(StringUtils.isNotBlank(startDate)){
			try {
				Date date = Constantas.fdate.parse(startDate);
				System.out.println("Date Start:" + DateUtil.truncate(date));
				criteria.add(Restrictions.ge("registered", DateUtil.truncate(date)));
			} catch (ParseException e) {
				e.printStackTrace();
			} catch (java.text.ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		}
		
		if(StringUtils.isNotBlank(endDate)){
			try {
				Date date = Constantas.fdate.parse(endDate);
				System.out.println("Date End:" + DateUtil.endOfDay(date));
				criteria.add(Restrictions.le("registered", DateUtil.endOfDay(date)));
			} catch (ParseException e) {
				e.printStackTrace();
			} catch (java.text.ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		}

		if (StringUtils.isNotBlank(insti))
			criteria.add(Restrictions.eq("instCode", insti));

		if (StringUtils.isNotBlank(branch))
			criteria.add(Restrictions.eq("branchCode", branch));

		if (StringUtils.isNotBlank(unit))
			criteria.add(Restrictions.eq("unitCode", unit));

		if (StringUtils.isNotBlank(center))
			criteria.add(Restrictions.eq("centerCode", center));

		List<ViewClient> list = criteria.list();

		return list;
		// return this.getDataTablesFromCriteria(criteria, dataTables);
	}

	@Override
	public Class<ViewClient> getRealClass() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DataTables searchByMapCriteria(DataTables dataTables, HashMap<String, Object> searchMap) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ViewClient> findByCriteria(String startDate, String endDate, String insti, String branch, String unit, String center) {
				System.out.println("#########FindByCriteria######");
				System.out.println("Start Date"+ startDate);
				System.out.println("End Date"+ endDate);
				System.out.println("Insti"+ insti);
				System.out.println("Branch"+ branch);
				System.out.println("Unit"+ unit);
				System.out.println("Center"+ center);
		
				Criteria criteria;
				
				criteria = this.getSessionFactory().getCurrentSession().createCriteria(ViewClient.class);
				
				
		
				if (StringUtils.isNotBlank(insti))
					criteria.add(Restrictions.eq("instCode", insti));
		
				if (StringUtils.isNotBlank(branch))
					criteria.add(Restrictions.eq("branchCode", branch));
		
				if (StringUtils.isNotBlank(unit))
					criteria.add(Restrictions.eq("unitCode", unit));
		
				if (StringUtils.isNotBlank(center))
					criteria.add(Restrictions.eq("centerCode", center));

				if(StringUtils.isNotBlank(startDate)){
					try {
						Date date = Constantas.fdate.parse(startDate);
						System.out.println("Date Start:" + DateUtil.truncate(date));
						criteria.add(Restrictions.ge("registered", DateUtil.truncate(date)));
					} catch (ParseException e) {
						e.printStackTrace();
					} catch (java.text.ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}	
				}
				
				if(StringUtils.isNotBlank(endDate)){
					try {
						Date date = Constantas.fdate.parse(endDate);
						System.out.println("Date End:" + DateUtil.endOfDay(date));
						criteria.add(Restrictions.le("registered", DateUtil.endOfDay(date)));
					} catch (ParseException e) {
						e.printStackTrace();
					} catch (java.text.ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}	
				}
		
				List<ViewClient> list = criteria.list();
				System.out.println("Client List\n" + list);
				return list;
	}

  
    
}
