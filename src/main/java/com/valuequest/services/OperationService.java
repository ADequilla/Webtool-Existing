package com.valuequest.services;

import java.util.List;

import com.valuequest.controller.mbo.model.MboOperationModel;
import com.valuequest.entity.MboOperation;
import com.valuequest.entity.security.SecUser;

public interface OperationService {

	public List<MboOperation> list();
	
	public MboOperation getByDaysCode(int daysCode);
	
	public void save(List<MboOperationModel> operations, SecUser userLogin);
}