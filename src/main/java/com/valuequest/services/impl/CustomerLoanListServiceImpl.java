package com.valuequest.services.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.valuequest.common.DataTables;
import com.valuequest.controller.api.model.CustomerLoanList;
import com.valuequest.services.CustomerLoanListService;

@Service
@Transactional(readOnly = false)

public class CustomerLoanListServiceImpl extends SimpleServiceImpl<CustomerLoanList> implements CustomerLoanListService{

	
	public DataTables searchByMapCriteria1(DataTables dataTables, HashMap<String, Object> searchMap, CustomerLoanList[] obj) {
		String acc   = (String) searchMap.get("acc");
		//JSONParser parser = new JSONParser(); 
		//JSONObject jsonObject = (JSONObject) parser.parse(json);
		
		System.out.println("#########Customer Loan List Length ############ "+obj.length);
		Criteria criteria 	= this.getSessionFactory().getCurrentSession().createCriteria(obj.getClass());
		//System.out.println("#########Criteria List ############ "+criteria.list());
		if (StringUtils.isNotBlank(acc))
			criteria.add(Restrictions.eq("acc", acc));
		

		return this.getDataTablesFromCriteria(criteria,dataTables);
		
		
	}

	@Override
	public Class<CustomerLoanList> getRealClass() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DataTables searchByMapCriteria(DataTables dataTables, HashMap<String, Object> searchMap) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CustomerLoanList findByAcc(String acc) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String GetAll() {
		// TODO Auto-generated method stub
		return null;
	}


	

}

	
