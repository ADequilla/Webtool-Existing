package com.valuequest.dao;

import java.util.List;

import com.valuequest.entity.StructureCommissionSetup;

public interface CommissionSetupDao extends InterfaceBaseDao<StructureCommissionSetup> {

	public StructureCommissionSetup findById(Long id);

	public void delete(List<Long> ids);

}
