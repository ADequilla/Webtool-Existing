package com.valuequest.services;

import java.util.List;

import com.valuequest.entity.ViewK2CTransactions;


public interface K2CTransactionService {
	public List<ViewK2CTransactions> getBySessionId(String sessionId);
}
