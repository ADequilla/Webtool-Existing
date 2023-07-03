package com.valuequest.dao;

import java.util.List;

import com.valuequest.entity.StructureLoadProduct;

public interface LoadProductDao extends InterfaceBaseDao<StructureLoadProduct> {

	public StructureLoadProduct findById(Long id);

	public void delete(List<Long> ids);

}
