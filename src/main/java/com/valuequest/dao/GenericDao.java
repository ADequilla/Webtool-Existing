package com.valuequest.dao;

import java.util.List;

import com.valuequest.entity.K2CTransactionTypeLookup;
import com.valuequest.entity.Lookup;
import com.valuequest.entity.Module;
import com.valuequest.entity.ParamConfig;
import com.valuequest.entity.StructureProductCategory;
import com.valuequest.entity.StructureProvider;
import com.valuequest.entity.ViewProductCategory;
import com.valuequest.entity.ProductTypeEntity;
import com.valuequest.entity.StructureBillerProduct;
import com.valuequest.entity.StructureLoadProduct;

public interface GenericDao {

	public List<Lookup> lookup(String type);
	
	public List<K2CTransactionTypeLookup> k2cTransactionTypeLookup(String type);

	public Lookup getLookupByValue(String type, String value);

	public String getLookupDesc(String type, String value);
	
	public ParamConfig getConfigByName(String name);
	
	public List<Module> module();

	public Module getModule(Long id);
	
	public List<StructureProductCategory> productCategory();
	
	public List<ViewProductCategory> productCategoryBiller();
	
	public List<ViewProductCategory> productCategoryLoad();
	
	public List<StructureProvider> provider();
	
	public List<ProductTypeEntity> productType();
	
	public List<StructureProductCategory> category();
	
	public List<StructureBillerProduct> billerProduct();
	
	public List<StructureLoadProduct> loadProduct();
	
}
