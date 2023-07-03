package com.valuequest.services;

import java.util.HashMap;
import java.util.List;

import com.valuequest.common.DataTables;
import com.valuequest.controller.administration.model.StateModel;
import com.valuequest.entity.ServiceDownTime;
import com.valuequest.entity.security.SecUser;

public interface DownTimeService {

	public ServiceDownTime findById(Long id);

	public void save(ServiceDownTime model, SecUser user);

	public void delete(List<StateModel> states);

	public DataTables searchByMapCriteria(DataTables dataTables, HashMap<String, Object> searchMap);

	public DataTables searchByMapCriteria(DataTables dataTables, HashMap<String, Object> searchMap,
			String lookupDesignation);
}
