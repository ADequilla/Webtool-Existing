package com.valuequest.services;

import java.util.HashMap;
import java.util.List;

import com.valuequest.common.DataTables;
import com.valuequest.controller.monitoring.model.ListUsedDeviceModel;
import com.valuequest.entity.ListUsedDevice;
import com.valuequest.entity.security.SecUser;

public interface ListAgentService {

	public DataTables searchByMapCriteriaAgent(DataTables dataTables, HashMap<String, Object> searchMap);
}
