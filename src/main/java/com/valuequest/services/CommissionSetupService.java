package com.valuequest.services;

import java.util.HashMap;
import java.util.List;

import com.valuequest.common.DataTables;
import com.valuequest.controller.maintenance.model.CommissionSetupModel;
import com.valuequest.entity.StructureCommissionSetup;
import com.valuequest.entity.security.SecUser;

public interface CommissionSetupService {

	public StructureCommissionSetup findById(Long id);

	public boolean isExist(Long code);

	public void save(CommissionSetupModel model, SecUser user);

	public void delete(List<CommissionSetupModel> states);

	public DataTables searchByMapCriteria(DataTables dataTables, HashMap<String, Object> searchMap);
	
	public List<StructureCommissionSetup> getAll();
}
