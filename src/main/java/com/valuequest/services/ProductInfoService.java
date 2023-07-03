package com.valuequest.services;

import java.util.HashMap;
import java.util.List;

import com.valuequest.common.DataTables;
import com.valuequest.controller.administration.model.StateModel;
import com.valuequest.entity.Product;
import com.valuequest.entity.security.SecUser;

public interface ProductInfoService {

	public Product findById(Long id);

	public void save(Product model, SecUser user);

	public void delete(List<StateModel> states);

	public DataTables searchByMapCriteria(DataTables dataTables, HashMap<String, Object> searchMap);
}
