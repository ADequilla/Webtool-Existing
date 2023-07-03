package com.valuequest.services;

import java.util.HashMap;
import java.util.List;

import com.valuequest.common.DataTables;
import com.valuequest.controller.maintenance.model.ProductTypeModel;
import com.valuequest.entity.ProductTypeEntity;
import com.valuequest.entity.security.SecUser;

public interface ProductTypeService {
	public ProductTypeEntity findById(Long id);
	
	public ProductTypeEntity findByProductTypeId(String productTypeId);

	public boolean isExist(Long code);

	public void save(ProductTypeModel model, SecUser user);

	public void delete(List<ProductTypeModel> states);

	public DataTables searchByMapCriteria(DataTables dataTables, HashMap<String, Object> searchMap);
	
	public List<ProductTypeEntity> getAll();
}
