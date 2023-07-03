package com.valuequest.services;

import java.util.HashMap;
import java.util.List;

import com.valuequest.common.DataTables;
//import com.valuequest.controller.maintenance.model.RemittanceModel;
//import com.valuequest.entity.StructureRemittance;
import com.valuequest.entity.ViewRemittance;
//import com.valuequest.entity.security.SecUser;

public interface RemittanceService {
	
	public ViewRemittance findById(Long id);

	public boolean isExist(Long code);

	//public void save(RemittanceModel model, SecUser user);

	//public void delete(List<RemittanceModel> states);

	public DataTables searchByMapCriteria(DataTables dataTables, HashMap<String, Object> searchMap);
	
	public DataTables searchCancelledByMapCriteria(DataTables dataTables, HashMap<String, Object> searchMap);
	
	public DataTables searchClaimedByMapCriteria(DataTables dataTables, HashMap<String, Object> searchMap);
	
	public DataTables searchCriteria(DataTables dataTables, HashMap<String, Object> searchMap);
	
//	public DataTables searchCriteriaPending(DataTables dataTables, HashMap<String, Object> searchMap);
	
	public DataTables searchMonitoring(DataTables dataTables, HashMap<String, Object> searchMap);
	
	public Long countStatus0();
	
	public Long countStatus1();
	
	public Long countStatus2();
	
	public Long countStatus3();
	
	public Long countStatusAll();
	
	public List<ViewRemittance> getAll();
}

