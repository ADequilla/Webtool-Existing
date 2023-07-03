package com.valuequest.services;

import java.util.HashMap;
import java.util.List;

import com.valuequest.common.DataTables;
import com.valuequest.controller.administration.model.StateModel;
import com.valuequest.entity.FeeStructure;
import com.valuequest.entity.security.SecUser;

public interface FeeStructureService {

	public FeeStructure findById(Long id);

	public void save(FeeStructure model, SecUser user);

	public void delete(List<StateModel> states);

	public DataTables searchByMapCriteria(DataTables dataTables, HashMap<String, Object> searchMap);

	public Boolean checkingDuplicateRange(Long startRange, Long endRange, String transType, Long id);
}
