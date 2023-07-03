package com.valuequest.services;

import java.util.HashMap;
import java.util.List;

import com.valuequest.common.DataTables;
import com.valuequest.entity.ProductTypeEntity;
import com.valuequest.entity.ViewRoutes;

public interface RoutesService {
	
	public ViewRoutes findById(Long id);

	public ViewRoutes findByTrnCode(String trnCode);

	public DataTables searchByMapCriteria(DataTables dataTables, HashMap<String, Object> searchMap);
	
	public List<ViewRoutes> getAll();
}