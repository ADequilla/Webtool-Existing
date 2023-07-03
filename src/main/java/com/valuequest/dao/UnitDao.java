package com.valuequest.dao;

import java.util.List;

import com.valuequest.entity.StructureUnit;

public interface UnitDao extends InterfaceBaseDao<StructureUnit> {

	public StructureUnit findByCode(String code);

	public void delete(List<String> ids);

	public void deleteUserMappedBy(Long userId);
}
