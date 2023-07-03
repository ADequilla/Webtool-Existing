package com.valuequest.dao;

import java.util.List;

import com.valuequest.entity.StructureBranch;

public interface BranchDao extends InterfaceBaseDao<StructureBranch> {

	public StructureBranch findByCode(String code);

	public void delete(List<String> ids);

	public void deleteUserMappedBy(Long userId);
}
