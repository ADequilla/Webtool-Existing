package com.valuequest.dao;

import java.util.List;

import com.valuequest.entity.ProductTypeEntity;
import com.valuequest.entity.ViewRoutes;

public interface RoutesDao extends InterfaceBaseDao<ViewRoutes> {

	public ViewRoutes findByTrnCode(String trnCode);

	public void delete(List<String> ids);
}
