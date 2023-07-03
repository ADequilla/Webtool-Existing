package com.valuequest.dao;

import java.util.List;

import com.valuequest.entity.LogAuditTrailAo;

public interface LogAuditTrailAoDao extends InterfaceBaseDao<LogAuditTrailAo> {

	public LogAuditTrailAo findById(Long id);

	public  List<LogAuditTrailAo> findByAoId(Long aoId);
	
	public void delete(List<Long> ids);

}
