package com.valuequest.services;

import java.util.HashMap;
import java.util.List;

import com.valuequest.common.DataTables;
import com.valuequest.controller.maintenance.model.ProductPayModel;
import com.valuequest.entity.StructureProductPay;
import com.valuequest.entity.security.SecUser;

public interface ProductPayService {

	public StructureProductPay findByCode(String code);

	public boolean isExist(String code);

	public void save(ProductPayModel model, SecUser user);

	public void delete(List<ProductPayModel> states);

	public DataTables searchByMapCriteria(DataTables dataTables, HashMap<String, Object> searchMap);
	
	public List<StructureProductPay> getAll();
}
