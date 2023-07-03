package com.valuequest.services;

import javax.servlet.http.HttpServletRequest;

import com.valuequest.entity.AuditTrail;
import com.valuequest.entity.security.SecUser;

public interface AuditTrailService {

	public void saveOrUpdate(AuditTrail auditTrail);

	public void save(HttpServletRequest request, long moduleId, String activity, String dataBefore, String dataAfter, String cid, SecUser userLogin);
}
