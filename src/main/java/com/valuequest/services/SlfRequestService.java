package com.valuequest.services;

import java.util.HashMap;
import java.util.List;

import com.valuequest.common.DataTables;
import com.valuequest.controller.administration.model.StateModel;
import com.valuequest.entity.security.SecUser;

public interface SlfRequestService {

	public void reject(List<StateModel> models, SecUser userLogin);

	public void confirm(List<StateModel> models, SecUser userLogin);

	public DataTables searchByMapCriteria(DataTables dataTables, HashMap<String, Object> searchMap);
}