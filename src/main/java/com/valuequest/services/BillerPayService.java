package com.valuequest.services;

import java.util.HashMap;
import java.util.List;

import com.valuequest.common.DataTables;
import com.valuequest.controller.maintenance.model.BillerPayModel;
import com.valuequest.entity.StructureBillerPay;
import com.valuequest.entity.security.SecUser;

public interface BillerPayService {

	public StructureBillerPay findByCode(String code);

	public boolean isExist(String code);

	public void save(BillerPayModel model, SecUser user);

	public void delete(List<BillerPayModel> states);

	public DataTables searchByMapCriteria(DataTables dataTables, HashMap<String, Object> searchMap);
	
	public List<StructureBillerPay> getAll();
}
