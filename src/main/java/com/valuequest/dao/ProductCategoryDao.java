package com.valuequest.dao;

import java.util.List;

import com.valuequest.entity.StructureProductCategory;

public interface ProductCategoryDao extends InterfaceBaseDao<StructureProductCategory> {

	public StructureProductCategory findById(Long id);

	public void delete(List<Long> ids);

}
