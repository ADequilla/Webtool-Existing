package com.valuequest.dao;

import java.util.List;

import com.valuequest.entity.StructureBillerPay;

public interface BillerPayDao extends InterfaceBaseDao<StructureBillerPay> {

	public StructureBillerPay findByCode(String code);

	public void delete(List<String> ids);

}
