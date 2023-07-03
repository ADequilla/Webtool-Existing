package com.valuequest.dao;

import java.util.List;

import com.valuequest.entity.ViewUser;
import com.valuequest.entity.security.SecComponent;
import com.valuequest.entity.security.SecMenu;
import com.valuequest.entity.security.SecUser;
import com.valuequest.entity.security.SecUserrole;

public interface AdminDao extends InterfaceBaseDao<SecUser> {

	public SecUser getSecUser(String username);

	public SecUser getSecUserName(String username);
	
	public ViewUser getCheckStatusByName(String username);

	public List<String> getAllRights();

	public List<String> getRightsByUser(SecUser userLogin);

	public List<SecMenu> getAllParentMenu();

	public List<SecMenu> getChildMenuByParent(SecMenu parent);

	public List<SecComponent> getComponentByMenu(SecMenu menu);

	public List<SecUserrole> listSecUserrole(Long userId);

	public List<String> getComponent(Long userId, String priviledge);

	public boolean getPriviledge(Long userId, String priviledge);

	public boolean getPriviledge(Long userId, String menu, String component);

}
