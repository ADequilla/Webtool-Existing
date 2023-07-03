package com.valuequest.services.impl;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.valuequest.common.DataTables;
//import com.valuequest.controller.maintenance.model.RemittanceModel;
import com.valuequest.dao.RemittanceDao;
//import com.valuequest.entity.StructureRemittance;
import com.valuequest.entity.ViewRemittance;
import com.valuequest.services.RemittanceService;
import com.valuequest.util.Constantas;
import com.valuequest.util.DateUtil;

@Service
@Transactional(readOnly = true)
public class RemittanceServiceImpl extends SimpleServiceImpl<ViewRemittance> implements RemittanceService {

	@Autowired
	private RemittanceDao remittanceDao;
	
	@Override
	public Class<ViewRemittance> getRealClass() {
		return ViewRemittance.class;
	}

	@Override
	public ViewRemittance findById(Long id) {
		return remittanceDao.findById(id);
	}
	
	@Override
	public boolean isExist(Long id) {
		ViewRemittance remittance = findById(id);
		if (remittance == null) {
			return false;
		}
		
		return true;
	}

	@Override
	public DataTables searchByMapCriteria(DataTables dataTables, HashMap<String, Object> searchMap) {
		String startSendDate   = (String) searchMap.get("startSendDate");
		String endSendDate = (String) searchMap.get("endSendDate");
		Long status = (Long) searchMap.get("status");
		Criteria criteria 	= this.getSessionFactory().getCurrentSession().createCriteria(ViewRemittance.class);
		
		System.out.println("############# startSendDate = "+startSendDate);
		System.out.println("############# endSendDate = "+endSendDate);
		
		if(StringUtils.isNotBlank(startSendDate)){
			try {
				Date date = Constantas.fdate.parse(startSendDate);
				criteria.add(Restrictions.ge("createdDate", DateUtil.truncate(date)));
			} catch (ParseException e) {
				e.printStackTrace();
			}	
		}
		
		if(StringUtils.isNotBlank(endSendDate)){
			try {
				Date date = Constantas.fdate.parse(endSendDate);
				criteria.add(Restrictions.le("createdDate", DateUtil.endOfDay(date)));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		criteria.add(Restrictions.eq("status", status));
		return this.getDataTablesFromCriteria(criteria, dataTables);
	}
	
	@Override
	public DataTables searchCancelledByMapCriteria(DataTables dataTables, HashMap<String, Object> searchMap) {
		String startCancelledDate   = (String) searchMap.get("startCancelledDate");
		String endCancelledDate = (String) searchMap.get("endCancelledDate");
		Long status = (Long) searchMap.get("status");
		Criteria criteria 	= this.getSessionFactory().getCurrentSession().createCriteria(ViewRemittance.class);
		
		System.out.println("############# startCancelledDate = "+startCancelledDate);
		System.out.println("############# endCancelledDate = "+endCancelledDate);
		
		if(StringUtils.isNotBlank(startCancelledDate)){
			try {
				Date date = Constantas.fdate.parse(startCancelledDate);
				criteria.add(Restrictions.ge("cancelledDate", DateUtil.truncate(date)));
			} catch (ParseException e) {
				e.printStackTrace();
			}	
		}
		
		if(StringUtils.isNotBlank(endCancelledDate)){
			try {
				Date date = Constantas.fdate.parse(endCancelledDate);
				criteria.add(Restrictions.le("cancelledDate", DateUtil.endOfDay(date)));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		criteria.add(Restrictions.eq("status", status));
		return this.getDataTablesFromCriteria(criteria, dataTables);
	}
	
	@Override
	public DataTables searchClaimedByMapCriteria(DataTables dataTables, HashMap<String, Object> searchMap) {
		String startClaimedDate   = (String) searchMap.get("startClaimedDate");
		String endClaimedDate = (String) searchMap.get("endClaimedDate");
		Long status = (Long) searchMap.get("status");
		Criteria criteria 	= this.getSessionFactory().getCurrentSession().createCriteria(ViewRemittance.class);
		
		System.out.println("############# startClaimedDate = "+startClaimedDate);
		System.out.println("############# endClaimedDate = "+endClaimedDate);
		
		if(StringUtils.isNotBlank(startClaimedDate)){
			try {
				Date date = Constantas.fdate.parse(startClaimedDate);
				criteria.add(Restrictions.ge("updatedDate", DateUtil.truncate(date)));
			} catch (ParseException e) {
				e.printStackTrace();
			}	
		}
		
		if(StringUtils.isNotBlank(endClaimedDate)){
			try {
				Date date = Constantas.fdate.parse(endClaimedDate);
				criteria.add(Restrictions.le("updatedDate", DateUtil.endOfDay(date)));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		criteria.add(Restrictions.eq("status", status));
		return this.getDataTablesFromCriteria(criteria, dataTables);
	}
	
	/*@Override
	public DataTables searchCriteriaPending(DataTables dataTables, HashMap<String, Object> searchMap) {
		String startSendDate   = (String) searchMap.get("startSendDate");
		String endSendDate = (String) searchMap.get("endSendDate");
		Long updatedDate = (Long) searchMap.get("updatedDate");
		Long status = (Long) searchMap.get("status");
		Criteria criteria 	= this.getSessionFactory().getCurrentSession().createCriteria(ViewRemittance.class);
		
		System.out.println("############# startSendDate = "+startSendDate);
		System.out.println("############# endSendDate = "+endSendDate);
		
		if(StringUtils.isNotBlank(startSendDate)){
			try {
				Date date = Constantas.fdate.parse(startSendDate);
				criteria.add(Restrictions.ge("createdDate", DateUtil.truncate(date)));
			} catch (ParseException e) {
				e.printStackTrace();
			}	
		}
		
		if(StringUtils.isNotBlank(endSendDate)){
			try {
				Date date = Constantas.fdate.parse(endSendDate);
				criteria.add(Restrictions.le("createdDate", DateUtil.endOfDay(date)));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		criteria.add(Restrictions.eq("status", status));
		criteria.add(Restrictions.eq("updatedDate", updatedDate));
		return this.getDataTablesFromCriteria(criteria, dataTables);
	}*/
	
	@Override
	public DataTables searchCriteria(DataTables dataTables, HashMap<String, Object> searchMap) {
		String startSendDate   = (String) searchMap.get("startSendDate");
		String endSendDate = (String) searchMap.get("endSendDate");
		Criteria criteria 	= this.getSessionFactory().getCurrentSession().createCriteria(ViewRemittance.class);
		
		System.out.println("############# startSendDate = "+startSendDate);
		System.out.println("############# endSendDate = "+endSendDate);
		
		if(StringUtils.isNotBlank(startSendDate)){
			try {
				Date date = Constantas.fdate.parse(startSendDate);
				criteria.add(Restrictions.ge("createdDate", DateUtil.truncate(date)));
			} catch (ParseException e) {
				e.printStackTrace();
			}	
		}
		
		if(StringUtils.isNotBlank(endSendDate)){
			try {
				Date date = Constantas.fdate.parse(endSendDate);
				criteria.add(Restrictions.le("createdDate", DateUtil.endOfDay(date)));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return this.getDataTablesFromCriteria(criteria, dataTables);
	}
	
	@Override
	public DataTables searchMonitoring(DataTables dataTables, HashMap<String, Object> searchMap) {
		String startSendDate   = (String) searchMap.get("startSendDate");
		String endSendDate = (String) searchMap.get("endSendDate");
		String senderMobileNumber = (String) searchMap.get("senderMobileNumber");
		String mobileReference = (String) searchMap.get("mobileReference");
		String sourceBranch = (String) searchMap.get("sourceBranch");
		String targetBranch = (String) searchMap.get("targetBranch");
		String status = (String) searchMap.get("status");
		
		System.out.println("############# startSendDate = "+startSendDate);
		System.out.println("############# endSendDate = "+endSendDate);
		
		Criteria criteria 	= this.getSessionFactory().getCurrentSession().createCriteria(ViewRemittance.class);
		
		if(StringUtils.isNotBlank(startSendDate)){
			try {
				Date date = Constantas.fdate.parse(startSendDate);
				criteria.add(Restrictions.ge("createdDate", DateUtil.truncate(date)));
			} catch (ParseException e) {
				e.printStackTrace();
			}	
		}
		
		if(StringUtils.isNotBlank(endSendDate)){
			try {
				Date date = Constantas.fdate.parse(endSendDate);
				criteria.add(Restrictions.le("createdDate", DateUtil.endOfDay(date)));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		
		if (StringUtils.isNotBlank(senderMobileNumber))
			criteria.add(Restrictions.eq("senderMobileNumber", senderMobileNumber));
		
		if (StringUtils.isNotBlank(mobileReference))
			criteria.add(Restrictions.ilike("sentMobileRefId", mobileReference, MatchMode.ANYWHERE));
		
		if (StringUtils.isNotBlank(sourceBranch))
			criteria.add(Restrictions.eq("sourceBranch", sourceBranch));
		
		if (StringUtils.isNotBlank(targetBranch))
			criteria.add(Restrictions.eq("targetBranch", targetBranch));
		
		if (StringUtils.isNotBlank(status))
			criteria.add(Restrictions.eq("status", Long.parseLong(status)));
		
		return this.getDataTablesFromCriteria(criteria, dataTables);
	}
	
	@Override
	public Long countStatus0() { //sent
		Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(ViewRemittance.class).setProjection(Projections.rowCount());
		return (Long) criteria.uniqueResult();
	}
	
	@Override
	public Long countStatus1() { //claimed
		Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(ViewRemittance.class).setProjection(Projections.rowCount());
		criteria.add(Restrictions.eq("status", 1L));
		return (Long) criteria.uniqueResult();
	}
	
	@Override
	public Long countStatus2() { //cancelled
		Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(ViewRemittance.class).setProjection(Projections.rowCount());
		criteria.add(Restrictions.eq("status", 2L));
		return (Long) criteria.uniqueResult();
	}
	
	@Override
	public Long countStatus3() { //pending
		Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(ViewRemittance.class).setProjection(Projections.rowCount());
		criteria.add(Restrictions.eq("status", 0L));
		return (Long) criteria.uniqueResult();
	}
	
	@Override
	public Long countStatusAll() {
		Criteria criteria1 = this.getSessionFactory().getCurrentSession().createCriteria(ViewRemittance.class).setProjection(Projections.rowCount());
		criteria1.add(Restrictions.ne("status", 0L));
		
		Criteria criteria2 = this.getSessionFactory().getCurrentSession().createCriteria(ViewRemittance.class).setProjection(Projections.rowCount());
		
		Long crit1 = (Long) criteria1.uniqueResult();
		Long crit2 = (Long) criteria2.uniqueResult();
		
		crit2 = crit1+crit2;
		
		return crit2;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ViewRemittance> getAll() {
		Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(ViewRemittance.class);
		return criteria.list();
	}
	
}
