package com.valuequest.services;

import java.util.HashMap;
import java.util.List;

import com.valuequest.common.DataTables;
import com.valuequest.controller.maintenance.model.ProviderModel;
import com.valuequest.entity.StructureProvider;
import com.valuequest.entity.security.SecUser;

public interface ProviderService {

	public StructureProvider findById(Long id);

	public boolean isExist(Long code);

	public void save(ProviderModel model, SecUser user);

	public void delete(List<ProviderModel> states);

	public DataTables searchByMapCriteria(DataTables dataTables, HashMap<String, Object> searchMap);
	
	public List<StructureProvider> getAll();
}
