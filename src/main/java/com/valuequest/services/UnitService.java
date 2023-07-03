package com.valuequest.services;

import java.util.HashMap;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import com.valuequest.common.DataTables;
import com.valuequest.controller.administration.model.StateModel;
import com.valuequest.controller.maintenance.model.UnitModel;
import com.valuequest.entity.StructureUnit;
import com.valuequest.entity.security.SecUser;

public interface UnitService {

	public StructureUnit findByCode(String code);

	public boolean isExist(String code);

	public void save(UnitModel model, SecUser user);

	public void delete(List<StateModel> states);

	public List<StructureUnit> mappedListBy(String[] branchs);

	public List<StructureUnit> mappedListBy(Long userId);

	public DetachedCriteria criteriaBy(Long userId);

	public DataTables searchByMapCriteria(DataTables dataTables, HashMap<String, Object> searchMap);
	
	public List<StructureUnit> mappedListByMcUser(Long mcUserId);

	public DetachedCriteria criteriaByMcUser(Long mcUserId);
}