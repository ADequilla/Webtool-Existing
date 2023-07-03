package com.valuequest.dao;

import java.util.List;

import com.valuequest.entity.StructureProductDenom;
import com.valuequest.entity.ViewProductDenom;

public interface ProductDenomDao extends InterfaceBaseDao<StructureProductDenom> {

	public StructureProductDenom findByCode(Long code);

	public StructureProductDenom findByParam(Long productId, Long denom, String description);

	public void delete(List<Long> ids);

	public ViewProductDenom findProductProviderDenom(Long id);

}
