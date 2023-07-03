package com.valuequest.services.impl;

import java.util.HashMap;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.valuequest.common.DataTables;
import com.valuequest.entity.TempAccountOfficer;
import com.valuequest.services.TempAccountOfficerService;

@Service
@Transactional(readOnly = true)
public class TempAccountOfficerServiceImpl extends SimpleServiceImpl<TempAccountOfficer> implements TempAccountOfficerService{

	@Override
	public Class<TempAccountOfficer> getRealClass() {
		return TempAccountOfficer.class;
	}

	@Override
	public DataTables searchByMapCriteria(DataTables dataTables, HashMap<String, Object> searchMap) {
		return null;
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public void save(TempAccountOfficer tempAccountOfficer) {
		this.saveOrUpdate(tempAccountOfficer);
	}

	@Override
	public List<TempAccountOfficer> searchByMapCriteria(HashMap<String, Object> map) {
		Long logUploadFileId = (Long) map.get("logUploadFileId");
		Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(TempAccountOfficer.class);

		if (logUploadFileId != null)
			criteria.add(Restrictions.eq("logUploadFileId", logUploadFileId));

		return criteria.list();
	}

}
