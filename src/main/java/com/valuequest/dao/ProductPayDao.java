package com.valuequest.dao;

import java.util.List;

import com.valuequest.entity.StructureProductPay;

public interface ProductPayDao extends InterfaceBaseDao<StructureProductPay> {

	public StructureProductPay findByCode(String code);

	public void delete(List<String> ids);

}
