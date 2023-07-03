package com.valuequest.services;

import java.util.HashMap;
import java.util.List;

import com.valuequest.common.DataTables;
import com.valuequest.controller.administration.model.StateModel;
import com.valuequest.controller.maintenance.model.InstitutionModel;
import com.valuequest.entity.StructureInstitution;
import com.valuequest.entity.security.SecUser;

public interface InstitutionService {

	public StructureInstitution findByCode(String code);

	public boolean isExist(String code);

	public void save(InstitutionModel model, SecUser user);

	public void delete(List<StateModel> states);

	public List<StructureInstitution> mappedList();

	public List<StructureInstitution> mappedListBy(Long userId);

	public DataTables searchByMapCriteria(DataTables dataTables, HashMap<String, Object> searchMap);
}
