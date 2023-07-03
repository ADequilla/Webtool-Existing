package com.valuequest.services;

import java.util.HashMap;
import java.util.List;

import com.valuequest.common.DataTables;
import com.valuequest.controller.monitoring.model.SuspiciousModel;
import com.valuequest.entity.Suspicious;
import com.valuequest.entity.ViewCashinSuspicious;
import com.valuequest.entity.ViewTransSuspicious;
import com.valuequest.entity.security.SecUser;

public interface SuspiciousService {

	public Suspicious findById(Long id);
	
	public void saveOrUpdate(Suspicious suspicious);

	public void save(SuspiciousModel model, SecUser userLogin);

	public List<ViewTransSuspicious> transList();

	public List<ViewCashinSuspicious> cashinList();

	public DataTables searchByMapCriteria(DataTables dataTables, HashMap<String, Object> searchMap);
}