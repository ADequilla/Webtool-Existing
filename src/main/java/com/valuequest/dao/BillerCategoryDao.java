package com.valuequest.dao;

import java.util.List;

import com.valuequest.entity.StructureBillerCategory;

public interface BillerCategoryDao extends InterfaceBaseDao<StructureBillerCategory> {

	public StructureBillerCategory findById(Long id);

	public void delete(List<Long> ids);

}
