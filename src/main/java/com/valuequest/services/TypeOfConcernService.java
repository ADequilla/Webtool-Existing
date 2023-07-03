package com.valuequest.services;

import java.util.HashMap;
import java.util.List;

import com.valuequest.common.DataTables;
import com.valuequest.controller.administration.model.StateModel;
import com.valuequest.entity.Concern;
import com.valuequest.entity.security.SecUser;

public interface TypeOfConcernService {

	public Concern findById(Long id);

	public void save(Concern model, SecUser userLogin);

	public void delete(List<StateModel> states);

	public List<Concern> list();

	public DataTables searchByMapCriteria(DataTables dataTables, HashMap<String, Object> searchMap);
}
