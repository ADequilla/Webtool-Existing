package com.valuequest.services;

import java.util.HashMap;

import com.valuequest.common.DataTables;

public interface ProductAndServicesService {
	

	public DataTables searchByMapCriteria(DataTables dataTables, HashMap<String, Object> searchMap);
	

}
