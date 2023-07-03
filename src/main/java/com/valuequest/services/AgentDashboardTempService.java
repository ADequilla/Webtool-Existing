package com.valuequest.services;

import java.util.HashMap;

import com.valuequest.common.DataTables;

public interface AgentDashboardTempService {
	
	public DataTables searchByMapCriteria(DataTables dataTables, HashMap<String, Object> searchMap);

	public DataTables searchByMapCriteriaAgent(DataTables dataTables, HashMap<String, Object> searchMap);

}