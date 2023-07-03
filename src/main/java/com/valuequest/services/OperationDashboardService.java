package com.valuequest.services;

import java.util.HashMap;
import java.util.List;

import com.valuequest.common.DataTables;
import com.valuequest.entity.Client;
import com.valuequest.entity.ViewDashboardTransaction;

public interface OperationDashboardService {

	public Client findById(Long id);

	public Long countAgent();
	
	public Long countMember();
	
	public Long countNonMember();
	
	public List<ViewDashboardTransaction> getAll();
	
	public List<ViewDashboardTransaction> getAllByParam(String dateStart, String dateEnd, String branch);
	
	public DataTables searchByMapCriteria(DataTables dataTables, HashMap<String, Object> searchMap);
}