package com.valuequest.services;

import java.util.HashMap;

import com.valuequest.common.DataTables;
import com.valuequest.entity.UploadFile;

public interface UploadFileService {

	public UploadFile findById(Long id);
	
	public void saveOrUpdate(UploadFile uploadFile);
	
	public DataTables searchByMapCriteria(DataTables dataTables, HashMap<String, Object> searchMap);
	
	public void saveDetail(Object entity);
}
