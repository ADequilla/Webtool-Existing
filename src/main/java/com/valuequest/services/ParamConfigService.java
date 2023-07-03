package com.valuequest.services;

import java.util.HashMap;

import com.valuequest.common.DataTables;
import com.valuequest.controller.utilities.model.ParamConfigModel;
import com.valuequest.entity.ParamConfig;
import com.valuequest.entity.security.SecUser;

public interface ParamConfigService {

	public ParamConfig findById(Long id);

	public void save(ParamConfig model, SecUser user);

	public DataTables searchByMapCriteria(DataTables dataTables, HashMap<String, Object> searchMap);
	
	public Boolean checkingDuplicateParamName(String paramName, Long id);

	public String getParamValue(String paramName);
}
