package com.valuequest.dao;

import java.util.List;

import com.valuequest.entity.StructureBillerProduct;

public interface BillerProductDao extends InterfaceBaseDao<StructureBillerProduct> {

	public StructureBillerProduct findById(Long id);

	public void delete(List<Long> ids);

}
