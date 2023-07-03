package com.valuequest.services;

import java.util.HashMap;
import java.util.List;

import com.valuequest.common.DataTables;
import com.valuequest.controller.maintenance.model.BillerCategoryModel;
import com.valuequest.entity.StructureBillerCategory;
import com.valuequest.entity.security.SecUser;

public interface BillerCategoryService {

	public StructureBillerCategory findByCode(Long id);

	public boolean isExist(Long code);

	public void save(BillerCategoryModel model, SecUser user);

	public void delete(List<BillerCategoryModel> states);

	public DataTables searchByMapCriteria(DataTables dataTables, HashMap<String, Object> searchMap);
	
	public List<StructureBillerCategory> getAll();
}
