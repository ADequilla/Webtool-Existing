package com.valuequest.services;

import java.util.HashMap;
import java.util.List;

import com.valuequest.common.DataTables;
import com.valuequest.controller.administration.model.StateModel;
import com.valuequest.entity.BillerTemp;
import com.valuequest.entity.security.SecUser;

public interface BillerService {
	
	public BillerTemp findById(Long id);

	public void save(BillerTemp model, SecUser userLogin);
	
	public void update(List<StateModel> states, String status, SecUser userLogin);

	public DataTables searchByMapCriteria(DataTables dataTables, HashMap<String, Object> searchMap);
}
