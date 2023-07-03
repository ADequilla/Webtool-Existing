package com.valuequest.dao;

import java.util.List;

import com.valuequest.entity.CSRHotlainView;

public interface CSRHotlainDao extends InterfaceBaseDao<CSRHotlainView> {

	public CSRHotlainView findByContactNumber(String contactNumber);
	
	public CSRHotlainView findById(Long id);
	
	public void delete(List<Long> ids);

}
