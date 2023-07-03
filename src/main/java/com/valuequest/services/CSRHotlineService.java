package com.valuequest.services;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpSession;

import com.valuequest.common.DataTables;
import com.valuequest.controller.administration.model.StateModel;
import com.valuequest.entity.CSRHotlainView;
import com.valuequest.entity.security.SecUser;

public interface CSRHotlineService {

	public CSRHotlainView findByContactNumber(String contactNumber);

	public CSRHotlainView findById(Long id);

	public DataTables searchByMapCriteria(DataTables dataTables, HashMap<String, Object> searchMap, HttpSession session);

	public void delete(List<StateModel> states);

	public boolean isExist(String code);

	public void save(CSRHotlainView model, SecUser user);

	public void update(CSRHotlainView model, SecUser user);

}
