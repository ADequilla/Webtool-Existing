package com.valuequest.dao;

import com.valuequest.entity.ParamConfig;

public interface ParamConfigDao extends InterfaceBaseDao<ParamConfig> {

	Boolean checkingDuplicateParamName(String paramName, Long id);

	String getParamValue(String paramName);

}
