package com.valuequest.services;

import java.util.HashMap;

import com.valuequest.common.DataTables;
import com.valuequest.entity.AsynReport;

public interface AsynReportService { 

	public AsynReport findById(Long id);
	
	public void saveOrUpdate(AsynReport asynReport);
	
	public DataTables searchByMapCriteria(DataTables dataTables, HashMap<String, Object> searchMap);
}
