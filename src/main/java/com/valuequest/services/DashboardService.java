package com.valuequest.services;

import java.util.HashMap;
import com.valuequest.entity.ViewClient;
import java.util.List;

public interface DashboardService {

    public List<ViewClient> searchByMapCriteria(HashMap<String, Object> searchMap);
    public List<ViewClient> findByCriteria(String startDate, String endDate, String insti, String branch, String unit, String center);
    
}
