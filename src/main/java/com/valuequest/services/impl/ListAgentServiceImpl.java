package com.valuequest.services.impl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.valuequest.common.DataTables;
import com.valuequest.controller.monitoring.model.ListUsedDeviceModel;
import com.valuequest.dao.ListUsedDeviceDao;
import com.valuequest.entity.ListUsedDevice;
import com.valuequest.entity.ViewClient;
import com.valuequest.entity.security.SecUser;
import com.valuequest.services.ListAgentService;
import com.valuequest.services.ListUsedDeviceService;
import com.valuequest.util.Constantas;
import com.valuequest.util.DateUtil;

@Service
@Transactional(readOnly = true)
public class ListAgentServiceImpl extends SimpleServiceImpl<ViewClient> implements ListAgentService {

	@Override
	public Class<ViewClient> getRealClass() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public DataTables searchByMapCriteriaAgent(DataTables dataTables, HashMap<String, Object> searchMap) {
		String searchDateStart 	= (String) searchMap.get("searchDateStart");
		String searchDateEnd 	= (String) searchMap.get("searchDateEnd");
		String mobileNumber = (String) searchMap.get("mobileNumber");
		String cid = (String) searchMap.get("cid");
		String instiCode = (String) searchMap.get("instiCode");
		String branchCode = (String) searchMap.get("branchCode");
		String unitCode = (String) searchMap.get("unitCode");
		String centerCode = (String) searchMap.get("centerCode");
		Criteria criteria 	= this.getSessionFactory().getCurrentSession().createCriteria(ViewClient.class);

		criteria.add(Restrictions.eq("agentFeature", 1));

		if(StringUtils.isNotBlank(searchDateStart)){
			try {
				Date date = Constantas.fdate.parse(searchDateStart);
				criteria.add(Restrictions.ge("enableAgentFeatures", DateUtil.truncate(date)));
			} catch (ParseException e) {
				e.printStackTrace();
			}	
		}
		
		if(StringUtils.isNotBlank(searchDateEnd)){
			try {
				Date date = Constantas.fdate.parse(searchDateEnd);
				criteria.add(Restrictions.le("enableAgentFeatures", DateUtil.endOfDay(date)));
			} catch (ParseException e) {
				e.printStackTrace();
			}	
		}

		if (StringUtils.isNotBlank(cid))
			criteria.add(Restrictions.eq("cid", cid));

		if (StringUtils.isNotBlank(mobileNumber))
			criteria.add(Restrictions.eq("mobileNo", mobileNumber));

		if (StringUtils.isNotBlank(instiCode))
			criteria.add(Restrictions.eq("instCode", instiCode));
		
		if (StringUtils.isNotBlank(branchCode))
			criteria.add(Restrictions.eq("branchCode", branchCode));

		if (StringUtils.isNotBlank(unitCode))
			criteria.add(Restrictions.eq("unitCode", unitCode));

		if (StringUtils.isNotBlank(centerCode))
			criteria.add(Restrictions.eq("centerCode", centerCode));

		return this.getDataTablesFromCriteria(criteria, dataTables);

	}


	@Override
	public DataTables searchByMapCriteria(DataTables dataTables, HashMap<String, Object> searchMap) {
		// TODO Auto-generated method stub
		return null;
	}

	
	
}
