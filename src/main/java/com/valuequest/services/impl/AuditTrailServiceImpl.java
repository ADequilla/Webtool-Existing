package com.valuequest.services.impl;

import java.util.Date;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;


import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.valuequest.common.DataTables;
import com.valuequest.entity.AuditTrail;
import com.valuequest.entity.security.SecUser;
import com.valuequest.services.AuditTrailService;

@Service
@Transactional(readOnly = true)
public class AuditTrailServiceImpl extends SimpleServiceImpl<AuditTrail> implements AuditTrailService{

	

	@Override
	public Class<AuditTrail> getRealClass() {
		return AuditTrail.class;
	}

	@Modifying
	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public void save(HttpServletRequest request, long moduleId, String activity, String dataBefore, String dataAfter, String cid, SecUser userLogin) {
		AuditTrail auditTrail = new AuditTrail();
		if (activity.contains("|")) {
			String[] data = activity.split("\\|");
			auditTrail.setActivity(data[0]);
			auditTrail.setResetId(Long.parseLong(data[1]));
		} else {
			auditTrail.setActivity(activity);
		}
		auditTrail.setModule(getGenericService().getModule(moduleId));
			
		auditTrail.setDataBefore(dataBefore);
		auditTrail.setDataAfter(dataAfter);
		auditTrail.setCid(cid);
		auditTrail.setAppType("WEB");
		auditTrail.setUserType("WEB");
		auditTrail.setIpAddress(request.getRemoteAddr());
		auditTrail.setCreatedBy(userLogin.getId());
		auditTrail.setCreatedDate(new Date(System.currentTimeMillis()));

		this.saveOrUpdate(auditTrail);
		
	}

	@Override
	public DataTables searchByMapCriteria(DataTables dataTables, HashMap<String, Object> searchMap) {
		// TODO Auto-generated method stub
		return null;
	}
}
