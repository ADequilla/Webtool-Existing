package com.valuequest.services;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpSession;

import com.valuequest.common.DataTables;
import com.valuequest.controller.administration.model.StateModel;
import com.valuequest.controller.maintenance.model.NewsModel;
import com.valuequest.entity.Inbox;
import com.valuequest.entity.security.SecUser;

public interface NewsService {

	public Inbox findById(Long id);

	public DataTables searchByMapCriteria(DataTables dataTables, HashMap<String, Object> searchMap, HttpSession session);

	public void save(NewsModel model, SecUser userLogin);

	public void delete(List<StateModel> states);


}
