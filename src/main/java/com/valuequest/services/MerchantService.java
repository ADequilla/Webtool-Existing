package com.valuequest.services;

import java.util.HashMap;

import com.valuequest.common.DataTables;
import com.valuequest.entity.MerchantEntity;
import com.valuequest.entity.security.SecUser;

public interface MerchantService {

	public MerchantEntity findById(Long id);
	public MerchantEntity findByClientId(Long clientId);
	public void save(MerchantEntity merchant, SecUser userLogin);
	public DataTables searchByMapCriteria(DataTables dataTables, HashMap<String, Object> searchMap);
	public Boolean ifExist(Long id, String businessName);
}