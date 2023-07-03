package com.valuequest.services;

import java.util.HashMap;
import java.util.List;

import com.valuequest.common.DataTables;
import com.valuequest.controller.maintenance.model.ProductCategoryModel;
import com.valuequest.entity.StructureProductCategory;
import com.valuequest.entity.security.SecUser;

public interface ProductCategoryService {

	public StructureProductCategory findById(Long id);

	public boolean isExist(Long code);

	public void save(ProductCategoryModel model, SecUser user);

	public void delete(List<ProductCategoryModel> states);

	public DataTables searchByMapCriteria(DataTables dataTables, HashMap<String, Object> searchMap);
	
	public List<StructureProductCategory> getAll();
}
