package com.valuequest.services;

import java.util.HashMap;
import java.util.List;

import com.valuequest.entity.TempAccountOfficer;

public interface TempAccountOfficerService {

	public void save(TempAccountOfficer tempAccountOfficer);

	public List<TempAccountOfficer> searchByMapCriteria(HashMap<String, Object> map);

}
