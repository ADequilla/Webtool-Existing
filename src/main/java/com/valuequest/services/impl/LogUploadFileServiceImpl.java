package com.valuequest.services.impl;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.valuequest.common.DataTables;
import com.valuequest.entity.LogUploadFile;
import com.valuequest.entity.ViewLogUploadFile;
import com.valuequest.services.LogUploadFileService;
import com.valuequest.util.Constantas;
import com.valuequest.util.DateUtil;

@Service
@Transactional(readOnly = true)
public class LogUploadFileServiceImpl extends SimpleServiceImpl<LogUploadFile> implements LogUploadFileService{

	@Override
	public Class<LogUploadFile> getRealClass() {
		return LogUploadFile.class;
	}

	@Override
	public DataTables searchByMapCriteria(DataTables dataTables, HashMap<String, Object> searchMap) {
		String submitedDateStart = (String) searchMap.get("submitedDateStart");
		String submitedDateEnd = (String) searchMap.get("submitedDateEnd");
		String userUploader = (String) searchMap.get("userUploader");
		String reportId = (String) searchMap.get("reportId");
		Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(ViewLogUploadFile.class);
		criteria.createAlias("uploadBy", "uploadBy", JoinType.LEFT_OUTER_JOIN);
		
		if(StringUtils.isNotBlank(submitedDateStart)){
			try {
				Date date = Constantas.fdate.parse(submitedDateStart);
				criteria.add(Restrictions.ge("uploadDate", DateUtil.truncate(date)));
			} catch (ParseException e) {
				e.printStackTrace();
			}	
		}
		
		if(StringUtils.isNotBlank(submitedDateEnd)){
			try {
				Date date = Constantas.fdate.parse(submitedDateEnd);
				criteria.add(Restrictions.le("uploadDate", DateUtil.endOfDay(date)));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}

		if (StringUtils.isNotBlank(reportId))
			criteria.add(Restrictions.eq("id", Long.valueOf(reportId)));

		if (StringUtils.isNotBlank(userUploader)) {
			String[] name = userUploader.split(" ", 3);
			if(name.length == 3) {
				criteria.add(Restrictions.and(Restrictions.ilike("uploadBy.givenName", name[0], MatchMode.ANYWHERE),
						Restrictions.ilike("uploadBy.middleName", name[1], MatchMode.ANYWHERE),
						Restrictions.ilike("uploadBy.lastName", name[2], MatchMode.ANYWHERE)));
			}else {
				criteria.add(Restrictions.or(Restrictions.ilike("uploadBy.givenName", userUploader, MatchMode.ANYWHERE),
						Restrictions.ilike("uploadBy.middleName", userUploader, MatchMode.ANYWHERE),
						Restrictions.ilike("uploadBy.lastName", userUploader, MatchMode.ANYWHERE)));
			}
		}
			


		return this.getDataTablesFromCriteria(criteria, dataTables);
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public void save(LogUploadFile logUploadFile) {
		this.saveOrUpdate(logUploadFile);
	}

}
