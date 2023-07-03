package com.valuequest.services;

import java.util.HashMap;
import java.util.List;

import com.valuequest.common.DataTables;
import com.valuequest.controller.maintenance.model.ProductDenomModel;
import com.valuequest.entity.StructureProductDenom;
import com.valuequest.entity.ViewProductDenom;
import com.valuequest.entity.security.SecUser;

public interface ProductDenomService {

	public StructureProductDenom findByCode(Long code);

	public boolean isExist(Long code);

	public void save(ProductDenomModel model, SecUser user);

	public void delete(List<ProductDenomModel> states);

	public DataTables searchByMapCriteria(DataTables dataTables, HashMap<String, Object> searchMap);
	
	public ViewProductDenom findProductProviderDenom(Long id);
}
