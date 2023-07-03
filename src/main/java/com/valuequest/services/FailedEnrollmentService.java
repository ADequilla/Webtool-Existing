package com.valuequest.services;

import java.util.HashMap;
import java.util.List;

import com.valuequest.common.DataTables;
import com.valuequest.controller.monitoring.model.FailedEnrollmentModel;
import com.valuequest.entity.ListFailedEnrollment;
import com.valuequest.entity.security.SecUser;

public interface FailedEnrollmentService {

	public ListFailedEnrollment findById(Long id);

	public boolean isExist(Long code);

	public void save(FailedEnrollmentModel model, SecUser user);

	public void delete(List<FailedEnrollmentModel> states);

	public DataTables searchByMapCriteria(DataTables dataTables, HashMap<String, Object> searchMap);
	
	public List<ListFailedEnrollment> getAll();
}
