package com.valuequest.dao;

import java.util.List;

import com.valuequest.entity.StructureInstitution;

public interface InstitutionDao extends InterfaceBaseDao<StructureInstitution> {

	public StructureInstitution findByCode(String code);

	public void delete(List<String> ids);

	public void deleteUserMappedBy(Long userId);
}
