package com.valuequest.dao;

import java.util.List;

import com.valuequest.entity.StructureMobilePrefix;

public interface MobilePrefixDao extends InterfaceBaseDao<StructureMobilePrefix> {

	public StructureMobilePrefix findById(Long id);
	
	public StructureMobilePrefix findByPrefixValue(String prefixValue);

	public void delete(List<Long> ids);

}
