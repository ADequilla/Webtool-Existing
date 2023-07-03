package com.valuequest.services;

import java.util.HashMap;

import javax.servlet.http.HttpSession;

import com.valuequest.common.DataTables;
import com.valuequest.entity.ViewTask;

public interface TaskService {

    public ViewTask findById(Long id);
    
    public DataTables searchByMapCriteria(DataTables dataTables, HashMap<String, Object> searchMap);
}
