package com.valuequest.services;

import java.util.HashMap;
import java.util.List;

import com.valuequest.common.DataTables;
import com.valuequest.controller.monitoring.model.ListUsedDeviceModel;
import com.valuequest.entity.ListUsedDevice;
import com.valuequest.entity.security.SecUser;

public interface ListUsedDeviceService {

	public ListUsedDevice findById(Long id);
	
	public List<ListUsedDevice> findByCid(String cid);

	public boolean dataCid(String cid);
	
	public boolean isExist(Long code);

	public void save(ListUsedDeviceModel model, SecUser user);

	public void delete(List<ListUsedDeviceModel> states);

	public DataTables searchByMapCriteria(DataTables dataTables, HashMap<String, Object> searchMap);
	
	public List<ListUsedDevice> getAll();
}
