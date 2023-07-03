package com.valuequest.services;

import java.util.HashMap;
import java.util.List;

import com.valuequest.common.DataTables;
import com.valuequest.controller.maintenance.model.BankListModel;
import com.valuequest.entity.StructureBankList;
import com.valuequest.entity.security.SecUser;

public interface BankListService {

	public StructureBankList findByBankCode(String bankCode);

	public boolean isExist(String bankCode);

	public void save(BankListModel model, SecUser user);

	public void delete(List<BankListModel> states);

	public DataTables searchByMapCriteria(DataTables dataTables, HashMap<String, Object> searchMap);
	
	public List<StructureBankList> getAll();
}
