package com.valuequest.dao;

import java.util.List;

import com.valuequest.entity.ViewTransaction;

public interface TransactionDao  extends InterfaceBaseDao<ViewTransaction> {
	
	public ViewTransaction findByTrnType(String trnType);
	public ViewTransaction findByCid(String cid);
	public ViewTransaction findByClientName(String clientName);

	public void delete(List<String> ids);

}
