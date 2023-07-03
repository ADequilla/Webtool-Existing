package com.valuequest.dao;

import java.util.List;

import com.valuequest.entity.StructureProvider;

public interface ProviderDao extends InterfaceBaseDao<StructureProvider> {

	public StructureProvider findById(Long id);

	public void delete(List<Long> ids);

}
