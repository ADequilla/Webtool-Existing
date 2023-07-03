package com.valuequest.services.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.valuequest.common.DataTables;
import com.valuequest.controller.maintenance.model.ProductDenomModel;
import com.valuequest.dao.ProviderDenomDao;
import com.valuequest.entity.StructureProviderDenom;
import com.valuequest.services.ProviderDenomService;

@Service
@Transactional(readOnly = true)
public class ProviderDenomServiceImpl extends SimpleServiceImpl<StructureProviderDenom> implements ProviderDenomService {

	@Autowired
	private ProviderDenomDao providerDenomDao;
	
	@Override
	public Class<StructureProviderDenom> getRealClass() {
		return StructureProviderDenom.class;
	}

	@Override
	public StructureProviderDenom findByCode(Long id) {
		return providerDenomDao.findByCode(id);
	}

	@Override
	public boolean isExist(Long id) {
		StructureProviderDenom providerDenom = findByCode(id);
		if (providerDenom == null) {
			return false;
		}
		
		return true;
	}
	

	@Override
	@Modifying
    @Transactional(propagation=Propagation.REQUIRED)
	public void delete(List<ProductDenomModel> states) {
		List<Long> ids = new ArrayList<Long>();
		for (ProductDenomModel model : states) {
			ids.add(model.getId());
		}

		if (ids.size() > 0) {
			providerDenomDao.delete(ids);
		}
	}

	@Override
	public DataTables searchByMapCriteria(DataTables dataTables, HashMap<String, Object> searchMap) {
		return null;
	}
	
}
