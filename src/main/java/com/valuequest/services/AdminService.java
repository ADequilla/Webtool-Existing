package com.valuequest.services;

import java.util.HashMap;
import java.util.List;

import com.valuequest.common.DataTables;
import com.valuequest.controller.administration.model.MenuModel;
import com.valuequest.controller.administration.model.UserModel;
import com.valuequest.entity.ViewUser;
import com.valuequest.entity.security.SecUser;
import com.valuequest.entity.security.SecUserrole;

public interface AdminService {
	
	public SecUser getSecUser(String username);

	public SecUser getSecUserName(String username);

	public ViewUser getCheckStatusByName(String username);
	
	public void updateCekStatus(SecUser userLogin, String sesionId);
	
	public SecUser findById(Long id);

	public String encodePassword(String password);

	public void save(UserModel model, SecUser userLogin);

	public void resetFailAttempts(SecUser userLogin);

	public void updateFailAttempts(SecUser userLogin, int maxRetry);

	public void resetUserPassword(UserModel model, SecUser userLogin);

	public List<String> getAllRights();

	public List<String> getRightsByUser(SecUser userLogin);

	public List<MenuModel> getAllMenuModel();

	public List<String> getComponent(Long userId, String priviledge);

	public List<SecUserrole> listSecUserrole(Long userId);

	public boolean getPriviledge(Long userId, String priviledge);

	public boolean getPriviledge(Long userId, String menu, String component);

	public DataTables searchByMapCriteria(DataTables dataTables, HashMap<String, Object> searchMap);

	public void saveOrUpdate(SecUser secUser);
}


