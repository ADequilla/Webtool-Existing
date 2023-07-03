package com.valuequest.dao;

import java.util.List;

import com.valuequest.entity.ListUsedDevice;

public interface ListUsedDeviceDao extends InterfaceBaseDao<ListUsedDevice> {

	public ListUsedDevice findById(Long id);

	public List<ListUsedDevice> findByCid(String cid);
	
	public void delete(List<Long> ids);

}
