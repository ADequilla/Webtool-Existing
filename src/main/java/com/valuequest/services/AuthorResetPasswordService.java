package com.valuequest.services;

import java.util.HashMap;
import java.util.List;

import com.valuequest.common.DataTables;
import com.valuequest.controller.monitoring.model.AuthorResetPasswordModel;
import com.valuequest.entity.AuthorResetPassword;
import com.valuequest.entity.ViewAuthorResetPassword;
import com.valuequest.entity.security.SecUser;

public interface AuthorResetPasswordService {

	public ViewAuthorResetPassword findByCid(String cid);

	public ViewAuthorResetPassword findByParam(Long id);

	public boolean isExist(String cid);

	public void save(AuthorResetPasswordModel model, SecUser user);

	public void delete(List<AuthorResetPasswordModel> states);

	public DataTables searchByMapCriteria(DataTables dataTables, HashMap<String, Object> searchMap);
	
	public List<AuthorResetPassword> getAll();
}
