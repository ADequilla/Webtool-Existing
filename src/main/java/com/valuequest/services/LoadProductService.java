package com.valuequest.services;

import java.util.HashMap;
import java.util.List;

import com.valuequest.common.DataTables;
import com.valuequest.controller.maintenance.model.LoadProductModel;
import com.valuequest.entity.StructureLoadProduct;
import com.valuequest.entity.security.SecUser;

public interface LoadProductService {

	public StructureLoadProduct findById(Long id);

	public boolean isExist(Long code);

	public void save(LoadProductModel model, SecUser user);

	public void delete(List<LoadProductModel> states);

	public DataTables searchByMapCriteria(DataTables dataTables, HashMap<String, Object> searchMap);
	
	public List<StructureLoadProduct> getAll();
}
