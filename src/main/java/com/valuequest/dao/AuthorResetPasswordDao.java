package com.valuequest.dao;

import java.util.List;

import com.valuequest.entity.AuthorResetPassword;
import com.valuequest.entity.ViewAuthorResetPassword;

public interface AuthorResetPasswordDao extends InterfaceBaseDao<AuthorResetPassword> {

	public ViewAuthorResetPassword findByCid(String cid);

	public ViewAuthorResetPassword findByParam(Long id);

	public void delete(List<Long> ids);

}
