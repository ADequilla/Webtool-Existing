package com.valuequest.services;

import java.util.HashMap;
import java.util.List;

import com.valuequest.common.DataTables;
import com.valuequest.controller.maintenance.model.MobilePrefixModel;
import com.valuequest.entity.StructureMobilePrefix;
import com.valuequest.entity.security.SecUser;

public interface MobilePrefixService {

	public StructureMobilePrefix findById(Long id);
	
	public StructureMobilePrefix findByPrefixValue(String PrefixValue);

	public boolean isExist(String code);

	public void save(MobilePrefixModel model, SecUser user);

	public void delete(List<MobilePrefixModel> states);

	public DataTables searchByMapCriteria(DataTables dataTables, HashMap<String, Object> searchMap);
	
	public List<StructureMobilePrefix> getAll();
}
