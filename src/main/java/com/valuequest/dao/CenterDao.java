package com.valuequest.dao;

import java.util.List;

import com.valuequest.entity.StructureCenter;

public interface CenterDao extends InterfaceBaseDao<StructureCenter> {

	public StructureCenter findByCode(String code);

	public void delete(List<String> ids);

	public void deleteUserMappedBy(Long userId);
}
