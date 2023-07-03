package com.valuequest.services;

import java.util.HashMap;
import java.util.List;

import com.valuequest.common.DataTables;
import com.valuequest.controller.maintenance.model.PartnerModel;
import com.valuequest.entity.StructurePartner;
import com.valuequest.entity.security.SecUser;

public interface PartnerService {

	public StructurePartner findByPartnerId(String partnerId);

	public boolean isExist(String partnerId);

	public void save(PartnerModel model, SecUser user);

	public void delete(List<PartnerModel> states);

	public DataTables searchByMapCriteria(DataTables dataTables, HashMap<String, Object> searchMap);
	
	public List<StructurePartner> getAll();
}
