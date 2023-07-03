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
import com.valuequest.controller.maintenance.model.ProductTypeModel;
import com.valuequest.dao.ProductTypeDao;
import com.valuequest.entity.ProductAndServicesEntity;
import com.valuequest.entity.ProductTypeEntity;
import com.valuequest.entity.ViewProductAndServices;
import com.valuequest.entity.ViewProductType;
import com.valuequest.entity.security.SecUser;
import com.valuequest.services.ProductAndServicesService;
import com.valuequest.util.Constantas;
import com.valuequest.util.DateUtil;
import com.valuequest.util.ObjectUtil;


@Service
@Transactional(readOnly = true)
public class ProductAndServicesServiceImpl extends SimpleServiceImpl<ProductAndServicesEntity> implements ProductAndServicesService {


	@Override
	public DataTables searchByMapCriteria(DataTables dataTables, HashMap<String, Object> searchMap) {
		String serviceName   = (String) searchMap.get("serviceName");
		String serviceBanner = (String) searchMap.get("serviceBanner");
		String createdDate = (String) searchMap.get("createdDate");
		Criteria criteria 	= this.getSessionFactory().getCurrentSession().createCriteria(ViewProductAndServices.class);
		
		if (StringUtils.isNotBlank(serviceName))
			criteria.add(Restrictions.ilike("serviceName", serviceName, MatchMode.ANYWHERE));

		if (StringUtils.isNotBlank(serviceBanner))
			criteria.add(Restrictions.ilike("serviceBanner", serviceBanner, MatchMode.ANYWHERE));

		if(StringUtils.isNotBlank(createdDate)){
			try {
				Date date = Constantas.fdate.parse(createdDate);
				criteria.add(Restrictions.ge("createdDate", DateUtil.truncate(date)));
			} catch (ParseException e) {
				e.printStackTrace();
			}	
		}

		return this.getDataTablesFromCriteria(criteria, dataTables);
	}

	@Override
	public Class<ProductAndServicesEntity> getRealClass() {
		// TODO Auto-generated method stub
		return null;
	}


	
}
