package com.valuequest.dao;

import java.util.List;

import com.valuequest.entity.StructureBankList;

public interface BankListDao extends InterfaceBaseDao<StructureBankList> {

	public StructureBankList findByBankCode(String bankCode);

	public void delete(List<Long> ids);

}
