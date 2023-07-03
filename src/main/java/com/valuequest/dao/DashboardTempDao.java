package com.valuequest.dao;

import com.valuequest.entity.DashboardTemp;

public interface DashboardTempDao extends InterfaceBaseDao<DashboardTemp> {

	public void deleteTruncate();
	
	public void saveData(String dateStart, String dateEnd, String branch);

}
