package com.valuequest.services;

import java.util.HashMap;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import com.valuequest.common.DataTables;
import com.valuequest.controller.administration.model.StateModel;
import com.valuequest.controller.maintenance.model.CenterModel;
import com.valuequest.entity.StructureCenter;
import com.valuequest.entity.security.SecUser;

public interface CenterService {

	public StructureCenter findByCode(String code);

	public boolean isExist(String code);

	public void save(CenterModel model, SecUser user);

	public void delete(List<StateModel> states);

	public List<StructureCenter> mappedListBy(String[] units);

	public List<StructureCenter> mappedUserBy(Long userId);

	public DetachedCriteria criteriaBy(Long userId);

	public DataTables searchByMapCriteria(DataTables dataTables, HashMap<String, Object> searchMap);
}
