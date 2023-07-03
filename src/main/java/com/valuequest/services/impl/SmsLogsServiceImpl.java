package com.valuequest.services.impl;


import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.valuequest.common.DataTables;
import com.valuequest.entity.SmsLogs;
import com.valuequest.entity.StructureInstitution;
import com.valuequest.entity.UserInstitution;
import com.valuequest.entity.ViewSmsLog;
import com.valuequest.entity.security.SecUser;
import com.valuequest.services.SmsLogsService;
import com.valuequest.util.Constantas;
import com.valuequest.util.DateUtil;

@Service
@Transactional(readOnly = true)
public class SmsLogsServiceImpl extends SimpleServiceImpl<SmsLogs> implements SmsLogsService {

	@Override
	public Class<SmsLogs> getRealClass() {
		return SmsLogs.class;
	}
	
	protected SecUser getLoginSecUser(HttpSession session) {
		return (SecUser) session.getAttribute("loginSecUser");
	}
	
	final Logger log = Logger.getLogger("SMS Logs");
	
	private DetachedCriteria criteriaBy(Long userId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(UserInstitution.class);
		criteria.createAlias("user", "user");
		criteria.createAlias("institution", "institution");
		criteria.add(Restrictions.eq("user.id", userId));
		criteria.setProjection(Projections.projectionList().add(Projections.groupProperty("institution.code")));

		return criteria;
	}
	
	List<String> instiList = new ArrayList<String>();
	
	public void getUserInsti(Long userId) {
		instiList.clear();
		Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(StructureInstitution.class);
		criteria.add(Subqueries.propertyIn("code", criteriaBy(userId)));
		List r = criteria.list();
		for (Iterator iterator = r.iterator(); iterator.hasNext();){
			StructureInstitution insti = (StructureInstitution) iterator.next();		
            instiList.add(insti.getCode());
		}
		
		System.out.println("#####Arrray of Insti#####\n "+  instiList.toString());
		
	}
	
	@Override
	public DataTables searchByMapCriteria(DataTables dataTables, HashMap<String, Object> searchMap, HttpSession session) {
		String cid 	= (String) searchMap.get("cid");
		String searchDateStart 	= (String) searchMap.get("searchDateStart");
		String searchDateEnd 	= (String) searchMap.get("searchDateEnd");
		String mobileNo 	= (String) searchMap.get("mobileNo");
		String msgStatus 	= (String) searchMap.get("msgStatus");
		String searchMt 	= (String) searchMap.get("searchMt");
		
		SecUser user = this.getLoginSecUser(session);
		getUserInsti(user.getId());
		
		Criteria criteria 	= this.getSessionFactory().getCurrentSession().createCriteria(ViewSmsLog.class);
		criteria.add(Restrictions.in("instCode", instiList));
		
		if (StringUtils.isNotBlank(mobileNo))
			criteria.add(Restrictions.ilike("mobileNumber", mobileNo, MatchMode.ANYWHERE));

		if (StringUtils.isNotBlank(msgStatus))
			criteria.add(Restrictions.eq("status", msgStatus));
		
		if (StringUtils.isNotBlank(cid))
			criteria.add(Restrictions.eq("cid", cid));
		
		if (StringUtils.isNotBlank(searchMt))
			criteria.add(Restrictions.eq("messageType", searchMt));
		
		if(StringUtils.isNotBlank(searchDateStart)){
			try {
				Date date = Constantas.fdate.parse(searchDateStart);
				criteria.add(Restrictions.ge("sendDate", DateUtil.truncate(date)));
			} catch (ParseException e) {
				e.printStackTrace();
			}	
		}
		
		if(StringUtils.isNotBlank(searchDateEnd)){
			try {
				Date date = Constantas.fdate.parse(searchDateEnd);
				criteria.add(Restrictions.le("sendDate", DateUtil.endOfDay(date)));
			} catch (ParseException e) {
				e.printStackTrace();
			}	
		}
		
		
		// List r = criteria.list();
		
		// log.info("###############Start#############");
		//  for (Iterator iterator = r.iterator(); iterator.hasNext();){
	    //         ViewSmsLog smslog = (ViewSmsLog) iterator.next();
	    //         log.info("------------------------");
	    //         log.info("User Name: " + user.getUsrLoginname());
	    //         log.info("Message ID: " + smslog.getMsgId()); 
	    //         log.info("Transaction ID: " + smslog.getTransId()); 
	    //         log.info("Message Status: " + smslog.getStatus());
	    //         log.info("Sent Date: " + smslog.getSendDate());
	    //         log.info("Transaction ID: " + smslog.getTransId());
	    //         log.info("Transaction ID MCPRO: " + smslog.getTransIdMcpro());
	    //         log.info("Mobile Number: " + smslog.getMobileNumber());
	    //         log.info("Message Command: " + smslog.getMessageCommand()); 
	    //         log.info("Activity: " + smslog.getMessageType());
	             
	    //   }
		 log.info("###############End#############");
		
//		
		 
		 
		 System.out.println("#####Insti List######" + instiList);
		
		return getDataTablesFromCriteria(criteria, dataTables);
	}
	
	int arr[] = {};
	
	public static int[] addX(int n, int arr[], int x)
	{
		int i;

		// create a new array of size n+1
		int newarr[] = new int[n + 1];

		// insert the elements from
		// the old array into the new array
		// insert all elements till n
		// then insert x at n+1
		for (i = 0; i < n; i++)
			newarr[i] = arr[i];

		newarr[n] = x;

		return newarr;
	}

	@Override
	public DataTables searchByMapCriteria(DataTables dataTables, HashMap<String, Object> searchMap) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
