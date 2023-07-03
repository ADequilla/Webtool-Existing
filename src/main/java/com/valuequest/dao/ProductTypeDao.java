package com.valuequest.dao;

import java.util.List;

import com.valuequest.entity.ProductTypeEntity;

public interface ProductTypeDao extends InterfaceBaseDao<ProductTypeEntity>{
	
	public ProductTypeEntity findById(Long id);

	public void delete(List<Long> ids);
	
}
