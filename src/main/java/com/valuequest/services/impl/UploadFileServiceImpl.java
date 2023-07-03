package com.valuequest.services.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.RowCountProjection;
import org.hibernate.criterion.Subqueries;
import org.hibernate.internal.CriteriaImpl;
import org.hibernate.internal.CriteriaImpl.OrderEntry;
import org.hibernate.sql.JoinType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.valuequest.common.DataTables;
import com.valuequest.common.SortField;
import com.valuequest.entity.UploadFile;
import com.valuequest.entity.UploadFileBranch;
import com.valuequest.entity.UploadFileUnit;
import com.valuequest.services.UnitService;
import com.valuequest.services.UploadFileService;
import com.valuequest.util.DateUtil;

@Service
@Transactional(readOnly = true)
public class UploadFileServiceImpl extends SimpleServiceImpl<UploadFile> implements UploadFileService {

	@Autowired
	private SessionFactory sessionFactory;
	@Autowired
	private UnitService unitService;
	
	@Override
	public Class<UploadFile> getRealClass() {
		return UploadFile.class;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public DataTables searchByMapCriteria(DataTables dataTables, HashMap<String, Object> searchMap) {
		Long loginId		= (Long) searchMap.get("loginId");
		String branch		= (String) searchMap.get("branch");
		String unit			= (String) searchMap.get("unit");
		String uploadStatus	= (String) searchMap.get("uploadStatus");
		Long uploadBy		= (Long) searchMap.get("uploadBy");
		Date uploadDate		= (Date) searchMap.get("uploadDate");

		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(UploadFile.class);
		criteria.createAlias("user", "user", JoinType.LEFT_OUTER_JOIN);
		
		if (loginId != null)
			criteria.add(Subqueries.propertyIn("id", criteriaByUser(loginId)));
		
		if (StringUtils.isNotBlank(branch))
			criteria.add(Subqueries.propertyIn("id", criteriaByBranch(branch)));

		if (StringUtils.isNotBlank(unit))
			criteria.add(Subqueries.propertyIn("id", criteriaByUnit(unit)));
		
		if (StringUtils.isNotBlank(uploadStatus))
			criteria.add(Restrictions.eq("status", uploadStatus));
		
		if(uploadBy != null){
			criteria.add(Restrictions.eq("user.id", uploadBy));
		}
		
		if(uploadDate != null){
			criteria.add(Restrictions.ge("uploadDate", DateUtil.truncate(uploadDate)));
			criteria.add(Restrictions.le("uploadDate", DateUtil.endOfDay(uploadDate)));
		}
		
		List<SortField> sortFields = dataTables.generateSortFields();
		for (SortField sf : sortFields) {
			if ("asc".equalsIgnoreCase(sf.getDirection())) {
				criteria.addOrder(Order.asc(sf.getField()));
			} else {
				criteria.addOrder(Order.desc(sf.getField()));
			}
		}

		List data = criteria.setFirstResult(dataTables.getiDisplayStart()).setMaxResults(dataTables.getiDisplayLength()).list();
		for (Object object : data) {
			UploadFile upload = (UploadFile) object;
			String branchs		= getBranchsBy(upload.getId());
			String units		= getUnitsBy(upload.getId());
			upload.setBranch(branchs);
			upload.setUnit(units);
			data.set(data.indexOf(object), upload);
		}
		
		Long total = 0l;
		if (data != null) {
			Iterator<OrderEntry> orderIter = ((CriteriaImpl) criteria).iterateOrderings();
			while (orderIter.hasNext()) {
				orderIter.next();
				orderIter.remove();
			}
			total = (Long) criteria.setProjection(new RowCountProjection()).setFirstResult(0).uniqueResult();
		}

		return dataTables.extract(data, total);
	}

	@Modifying
    @Transactional(propagation=Propagation.REQUIRED)
	@Override
	public void saveDetail(Object entity) {
		sessionFactory.getCurrentSession().save(entity);
	}
	
	private DetachedCriteria criteriaByUser(Long userId){
		DetachedCriteria criteriaUnit = unitService.criteriaBy(userId);
		
		DetachedCriteria criteria = DetachedCriteria.forClass(UploadFileUnit.class);
		criteria.createAlias("uploadFile", "uploadFile");
		criteria.createAlias("unit", "unit");
		criteria.add(Subqueries.propertyIn("unit.code", criteriaUnit));
		criteria.setProjection(Projections.projectionList().add(Projections.groupProperty("uploadFile.id")));
		
		return criteria;
	}
	
	private DetachedCriteria criteriaByBranch(String branch) {
		DetachedCriteria criteria = DetachedCriteria.forClass(UploadFileBranch.class);
		criteria.createAlias("uploadFile", "uploadFile");
		criteria.createAlias("branch", "branch");
		criteria.add(Restrictions.eq("branch.code", branch));
		criteria.setProjection(Projections.projectionList().add(Projections.groupProperty("uploadFile.id")));
		
		return criteria;
	}
	
	private DetachedCriteria criteriaByUnit(String unit) {
		DetachedCriteria criteria = DetachedCriteria.forClass(UploadFileUnit.class);
		criteria.createAlias("uploadFile", "uploadFile");
		criteria.createAlias("unit", "unit");
		criteria.add(Restrictions.eq("unit.code", unit));
		criteria.setProjection(Projections.projectionList().add(Projections.groupProperty("uploadFile.id")));
		
		return criteria;
	}
	
	@SuppressWarnings("rawtypes")
	private String getBranchsBy(Long uploadId) {
		String SQL	= "SELECT string_agg(DISTINCT b.branch_desc, ' - ') " +
						"FROM mfs.t_upload_branch ub " +
						"INNER JOIN mfs.m_branch b ON b.branch_code=ub.branch_code " +
						"WHERE ub.upload_id=:uploadId ";

		try {
			List list = sessionFactory.getCurrentSession().createSQLQuery(SQL).setParameter("uploadId", uploadId).list();
			if (list.size() > 0)
				return list.get(0).toString();
			
		} catch (Exception e) { }
		
		return "";
	}
	
	@SuppressWarnings("rawtypes")
	private String getUnitsBy(Long uploadId) {
		String SQL	= "SELECT string_agg(DISTINCT u.unit_desc, ' - ') " +
				"FROM mfs.t_upload_unit uu " +
				"INNER JOIN mfs.m_unit u ON u.unit_code=uu.unit_code " +
				"WHERE uu.upload_id=:uploadId ";
		
		try {
			List list = sessionFactory.getCurrentSession().createSQLQuery(SQL).setParameter("uploadId", uploadId).list();
			if (list.size() > 0)
				return list.get(0).toString();
			
		} catch (Exception e) { }
		
		return "";
	}
}