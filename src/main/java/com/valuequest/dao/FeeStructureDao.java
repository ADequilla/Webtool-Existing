package com.valuequest.dao;

import com.valuequest.entity.FeeStructure;

public interface FeeStructureDao extends InterfaceBaseDao<FeeStructure> {

	Boolean checkingDuplicateRange(Long startRange, Long endRange, String transType, Long id);

}
