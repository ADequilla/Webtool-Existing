package com.valuequest.services;

import java.util.HashMap;
import java.util.List;

import com.valuequest.common.DataTables;
import com.valuequest.entity.ViewTransaction;

public interface TransactionService {

	public ViewTransaction findByTrnType(String trnType);
	public ViewTransaction findByCid(String cid);
	public ViewTransaction findByClientName(String clientName);

	public DataTables searchByMapCriteria(DataTables dataTables, HashMap<String, Object> searchMap);
	
	public List<ViewTransaction> getAll();
	
}
