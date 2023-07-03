package com.valuequest.services.impl;

import java.util.HashMap;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.valuequest.common.DataTables;
import com.valuequest.entity.LogAuditTrailAo;
import com.valuequest.services.LogAuditTrailAoService;

@Service
@Transactional(readOnly = true)
public class LogAuditTrailAoServiceImpl extends SimpleServiceImpl<LogAuditTrailAo> implements LogAuditTrailAoService{

	@Override
	public Class<LogAuditTrailAo> getRealClass() {
		return LogAuditTrailAo.class;
	}

	@Override
	public DataTables searchByMapCriteria(DataTables dataTables, HashMap<String, Object> searchMap) {
		return null;
	}

	@Override
	@Modifying
    @Transactional(propagation=Propagation.REQUIRED)
	public void save(LogAuditTrailAo auditTrailAo) {
		this.saveOrUpdate(auditTrailAo);
	}

}
