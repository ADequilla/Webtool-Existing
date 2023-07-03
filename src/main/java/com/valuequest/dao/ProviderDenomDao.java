package com.valuequest.dao;

import java.util.List;

import com.valuequest.entity.StructureProviderDenom;

public interface ProviderDenomDao extends InterfaceBaseDao<StructureProviderDenom> {

	public StructureProviderDenom findByCode(Long id);
	
	public void delete(List<Long> ids);
	
	public StructureProviderDenom findByIdProductDenom(Long productDenomId);

}
