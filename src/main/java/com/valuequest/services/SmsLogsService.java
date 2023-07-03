package com.valuequest.services;

import java.util.HashMap;

import javax.servlet.http.HttpSession;

import com.valuequest.common.DataTables;

public interface SmsLogsService {

	public DataTables searchByMapCriteria(DataTables dataTables, HashMap<String, Object> searchMap, HttpSession session);
}
