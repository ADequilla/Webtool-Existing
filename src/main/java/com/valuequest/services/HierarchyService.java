package com.valuequest.services;

import java.util.HashMap;
import java.util.List;

import com.valuequest.common.DataTables;
import com.valuequest.controller.administration.model.HierarchyModel;
import com.valuequest.controller.administration.model.StateModel;
import com.valuequest.entity.MappingHierarchy;
import com.valuequest.entity.security.SecUser;
import javax.servlet.http.HttpSession;

public interface HierarchyService {

	public MappingHierarchy findById(Long id);

	public void save(HierarchyModel model, SecUser userLogin);

	public boolean isDuplicate(HierarchyModel model);

	public DataTables searchByMapCriteria(DataTables dataTables, HashMap<String, Object> searchMap, HttpSession session);
	
	public DataTables searchByMapCriteria(DataTables dataTables, HashMap<String, Object> searchMap);

	public void delete(List<StateModel> states);
}
