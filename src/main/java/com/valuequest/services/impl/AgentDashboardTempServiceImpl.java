package com.valuequest.services.impl;

import java.util.HashMap;

import org.hibernate.Criteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.valuequest.common.DataTables;
import com.valuequest.dao.AgentDashboardTempDao;
import com.valuequest.entity.AgentDashboardTemp;
import com.valuequest.entity.ClientDashboardTemp;
import com.valuequest.services.AgentDashboardTempService;

@Service
@Transactional(readOnly = false)
public class AgentDashboardTempServiceImpl extends SimpleServiceImpl<AgentDashboardTemp> implements AgentDashboardTempService{

	@Autowired
	private AgentDashboardTempDao agentDashboardTempDao;
	
	@Override
	public Class<AgentDashboardTemp> getRealClass() {
		return AgentDashboardTemp.class;
	}
	
	@Override
	public DataTables searchByMapCriteria(DataTables dataTables, HashMap<String, Object> searchMap) {
		
		String branch = (String) searchMap.get("branch");
		
		/*dashboardTempDeleteAndSave(branch);*/
		
		Criteria criteria 	= this.getSessionFactory().getCurrentSession().createCriteria(AgentDashboardTemp.class);

		return this.getDataTablesFromCriteria(criteria, dataTables);
	}
	
	public void dashboardTempDeleteAndSave(String branch){
		agentDashboardTempDao.deleteTruncate();
		agentDashboardTempDao.saveData( branch);
	}

	@Override
	public DataTables searchByMapCriteriaAgent(DataTables dataTables, HashMap<String, Object> searchMap) {
		
		String branch = (String) searchMap.get("branch");
		
		dashboardTempClientDeleteAndSave(branch);
		
		Criteria criteria 	= this.getSessionFactory().getCurrentSession().createCriteria(ClientDashboardTemp.class);

		return this.getDataTablesFromCriteria(criteria, dataTables);
	}
	
	public void dashboardTempClientDeleteAndSave(String branch){
		agentDashboardTempDao.deleteTruncateClient();
		agentDashboardTempDao.saveDataClient(branch);
	}

}