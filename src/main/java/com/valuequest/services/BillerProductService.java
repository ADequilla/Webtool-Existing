package com.valuequest.services;

import java.util.HashMap;
import java.util.List;

import com.valuequest.common.DataTables;
import com.valuequest.controller.maintenance.model.BillerProductModel;
import com.valuequest.entity.StructureBillerProduct;
import com.valuequest.entity.ViewBillerProduct;
import com.valuequest.entity.security.SecUser;

public interface BillerProductService {

	public StructureBillerProduct findById(Long id);

	public boolean isExist(Long code);

	public void save(BillerProductModel model, SecUser user);

	public void delete(List<BillerProductModel> states);

	public DataTables searchByMapCriteria(DataTables dataTables, HashMap<String, Object> searchMap);
	
	public List<ViewBillerProduct> searchByMapCriteria(HashMap<String, Object> searchMap);
	
	public List<StructureBillerProduct> getAll();
}
