package com.valuequest.services;

import java.util.List;

import com.valuequest.controller.maintenance.model.ProductDenomModel;
import com.valuequest.entity.StructureProviderDenom;

public interface ProviderDenomService {

	public StructureProviderDenom findByCode(Long code);

	public boolean isExist(Long code);

	public void delete(List<ProductDenomModel> states);

}
