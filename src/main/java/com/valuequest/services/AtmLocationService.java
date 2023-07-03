package com.valuequest.services;

import java.util.HashMap;
import java.util.List;

import com.valuequest.common.DataTables;
import com.valuequest.controller.administration.model.StateModel;
import com.valuequest.entity.Atm;
import com.valuequest.entity.security.SecUser;

public interface AtmLocationService {
	
	public Atm findById(Long id);
	
	public void save(Atm model, SecUser user);
	
	public void delete(List<StateModel> states);

	public DataTables searchByMapCriteria(DataTables dataTables, HashMap<String, Object> searchMap);
}
