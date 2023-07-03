package com.valuequest.services;

import java.util.HashMap;
import java.util.List;

import com.valuequest.common.DataTables;
import com.valuequest.controller.administration.model.RoleModel;
import com.valuequest.entity.security.SecRole;
import com.valuequest.entity.security.SecRoledetail;
import com.valuequest.entity.security.SecUser;

public interface RoleService {

	public SecRole findById(Long id);

	public List<SecRole> list();
	
	public List<SecRoledetail> detailList(Long roleId);

	public void save(RoleModel model, SecUser userLogin);
	
	public DataTables searchByMapCriteria(DataTables dataTables, HashMap<String, Object> searchMap);
}
