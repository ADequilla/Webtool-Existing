package com.valuequest.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.valuequest.dao.GenericDao;
import com.valuequest.entity.K2CTransactionTypeLookup;
import com.valuequest.entity.Lookup;
import com.valuequest.entity.Module;
import com.valuequest.entity.ParamConfig;
import com.valuequest.services.GenericService;
import com.valuequest.entity.StructureProductCategory;
import com.valuequest.entity.StructureProvider;
import com.valuequest.entity.ViewProductCategory;
import com.valuequest.entity.ProductTypeEntity;
import com.valuequest.entity.StructureBillerProduct;
import com.valuequest.entity.StructureLoadProduct;

@Service
@Transactional(readOnly = true)
public class GenericServiceImpl implements GenericService {
	
	@Autowired
	private GenericDao genericDao;

	@Override
	public List<Lookup> lookup(String type) {
		return genericDao.lookup(type);
	}

	@Override
	public Lookup getLookupByValue(String type, String value) {
		return genericDao.getLookupByValue(type, value);
	}

	@Override
	public String getLookupDesc(String type, String value) {
		return genericDao.getLookupDesc(type, value);
	}

	@Override
	public ParamConfig getConfigByName(String name) {
		return genericDao.getConfigByName(name);
	}

	@Override
	public String getConfigValueByName(String name) {
		ParamConfig config = getConfigByName(name);
		if (config != null)
			return config.getValue();
		
		return null;
	}
	
	@Override
	public List<Module> module() {
		return genericDao.module();
	}

	@Override
	public Module getModule(Long id) {
		return genericDao.getModule(id);
	}
	
	@Override
	public List<StructureProductCategory> productCategory() {
		return genericDao.productCategory();
	}
	
	@Override
	public List<ViewProductCategory> productCategoryBiller() {
		return genericDao.productCategoryBiller();
	}
	
	@Override
	public List<ViewProductCategory> productCategoryLoad() {
		return genericDao.productCategoryLoad();
	}
	
	@Override
	public List<StructureProvider> provider() {
		return genericDao.provider();
	}
	
	@Override
	public List<ProductTypeEntity> productType() {
		return genericDao.productType();
	}
	
	@Override
	public List<StructureProductCategory> category() {
		return genericDao.category();
	}
	
	@Override
	public List<StructureBillerProduct> billerProduct() {
		return genericDao.billerProduct();
	}
	
	@Override
	public List<StructureLoadProduct> loadProduct() {
		return genericDao.loadProduct();
	}

	@Override
	public List<K2CTransactionTypeLookup> k2cTransactionTypeLookup(String type) {
		return genericDao.k2cTransactionTypeLookup(type);
	}
	
}
