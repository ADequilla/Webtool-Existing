package com.valuequest.services;

import java.util.List;

public interface LogPasswordService {
	
	public void save(String loginname, String password);
	
	public List lastPasswordList(String loginname, int period);
}
