package com.valuequest.dao;

import com.valuequest.entity.StructureUser;

public interface LastLoginDao  extends InterfaceBaseDao<StructureUser> {
	
	public void update(Long userID);

}
