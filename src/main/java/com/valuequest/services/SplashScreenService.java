package com.valuequest.services;

import java.util.HashMap;
import java.util.List;

import com.valuequest.common.DataTables;
import com.valuequest.controller.administration.model.StateModel;
import com.valuequest.entity.ProductAndService;
import com.valuequest.entity.SplashScreen;
import com.valuequest.entity.security.SecUser;

public interface SplashScreenService {

	public SplashScreen findById(Long id);

	public void save(SplashScreen model, SecUser userLogin);

	public void delete(List<StateModel> states);

	public DataTables searchByMapCriteria(DataTables dataTables, HashMap<String, Object> searchMap);
}
