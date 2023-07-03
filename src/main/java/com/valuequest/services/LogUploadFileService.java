package com.valuequest.services;

import java.util.HashMap;

import com.valuequest.common.DataTables;
import com.valuequest.entity.LogUploadFile;

public interface LogUploadFileService {

	public void save(LogUploadFile logUploadFile);

	public DataTables searchByMapCriteria(DataTables dataTables, HashMap<String, Object> searchMap);

}
