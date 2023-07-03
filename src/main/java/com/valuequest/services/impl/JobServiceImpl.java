package com.valuequest.services.impl;

import java.util.HashMap;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.valuequest.common.DataTables;
import com.valuequest.entity.JobProcess;
import com.valuequest.services.JobService;

@Service
@Transactional(readOnly = true)
public class JobServiceImpl extends SimpleServiceImpl<JobProcess> implements JobService{

	@Override
	public Class<JobProcess> getRealClass() {
		return JobProcess.class;
	}

	@Override
	public DataTables searchByMapCriteria(DataTables dataTables, HashMap<String, Object> searchMap) {
		return null;
	}

}
