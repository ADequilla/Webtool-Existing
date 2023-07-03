package com.valuequest.dao;

import com.valuequest.entity.AgentDashboardTemp;

public interface AgentDashboardTempDao extends InterfaceBaseDao<AgentDashboardTemp> {

	public void deleteTruncate();
	
	public void saveData(String branch);
	
	public void deleteTruncateClient();
	
	public void saveDataClient(String branch);

}
