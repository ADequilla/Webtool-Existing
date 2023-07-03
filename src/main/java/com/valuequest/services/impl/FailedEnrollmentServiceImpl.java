package com.valuequest.services.impl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.valuequest.common.DataTables;
import com.valuequest.controller.monitoring.model.FailedEnrollmentModel;
import com.valuequest.dao.FailedEnrollmentDao;
import com.valuequest.entity.ListFailedEnrollment;
import com.valuequest.entity.security.SecUser;
import com.valuequest.services.FailedEnrollmentService;
import com.valuequest.util.Constantas;
import com.valuequest.util.DateUtil;

@Service
@Transactional(readOnly = true)
public class FailedEnrollmentServiceImpl extends SimpleServiceImpl<ListFailedEnrollment> implements FailedEnrollmentService {

	@Autowired
	private FailedEnrollmentDao failedEnrollmentDao;
	
	@Override
	public Class<ListFailedEnrollment> getRealClass() {
		return ListFailedEnrollment.class;
	}

	@Override
	public ListFailedEnrollment findById(Long id) {
		return failedEnrollmentDao.findById(id);
	}

	@Override
	public boolean isExist(Long id) {
		ListFailedEnrollment productCategory = findById(id);
		if (productCategory == null) {
			return false;
		}
		
		return true;
	}
	
	@Override
	@Modifying
    @Transactional(propagation=Propagation.REQUIRED)
    public void save(FailedEnrollmentModel model, SecUser user) {
		ListFailedEnrollment  productCategory = findById(model.getId());
		/*if (productCategory == null){
			productCategory = new ListFailedEnrollment();
			productCategory.setFailedEnrollmentId(model.getFailedEnrollmentId());
			productCategory.setProductTypeId(model.getProductTypeId());
			productCategory.setFailedEnrollmentName(model.getFailedEnrollmentName());
			productCategory.setDescription(model.getDescription());
			productCategory.setStatus(model.getStatus());
			productCategory.setLastSync(new Date());
			productCategory.setCreatedDate(new Date());
			productCategory.setCreatedBy(user.getId());
			productCategory.setUpdatedDate(new Date());
			productCategory.setUpdatedBy(user.getId());
		}else{
			productCategory.setLastSync(new Date());
			productCategory.setUpdatedDate(new Date());
			productCategory.setUpdatedBy(user.getId());
			productCategory.setFailedEnrollmentName(model.getFailedEnrollmentName());
			productCategory.setDescription(model.getDescription());
			productCategory.setStatus(model.getStatus());
		}*/
		
		this.saveOrUpdate(productCategory);
	}

	@Override
	@Modifying
    @Transactional(propagation=Propagation.REQUIRED)
	public void delete(List<FailedEnrollmentModel> states) {
		List<Long> ids = new ArrayList<Long>();
		for (FailedEnrollmentModel model : states) {
			ids.add(model.getId());
		}

		if (ids.size() > 0) {
			failedEnrollmentDao.delete(ids);
		}
	}


	@Override
	public DataTables searchByMapCriteria(DataTables dataTables, HashMap<String, Object> searchMap) {
		String searchDateStart 	= (String) searchMap.get("searchDateStart");
		String searchDateEnd 	= (String) searchMap.get("searchDateEnd");
		String accountNumber   = (String) searchMap.get("accountNumber");
		String dateBirth = (String) searchMap.get("dateBirth");
		String mobileNumber = (String) searchMap.get("mobileNumber");
		String clientType = (String) searchMap.get("clientType");
		String errorMessage = (String) searchMap.get("errorMessage");
		Criteria criteria 	= this.getSessionFactory().getCurrentSession().createCriteria(ListFailedEnrollment.class);
		
		if(StringUtils.isNotBlank(searchDateStart)){
			try {
				Date date = Constantas.fdate.parse(searchDateStart);
				criteria.add(Restrictions.ge("createdDate", DateUtil.truncate(date)));
			} catch (ParseException e) {
				e.printStackTrace();
			}	
		}
		
		if(StringUtils.isNotBlank(searchDateEnd)){
			try {
				Date date = Constantas.fdate.parse(searchDateEnd);
				criteria.add(Restrictions.le("createdDate", DateUtil.endOfDay(date)));
			} catch (ParseException e) {
				e.printStackTrace();
			}	
		}
		
		if (StringUtils.isNotBlank(accountNumber))
			criteria.add(Restrictions.eq("accountNumber", accountNumber));

		if (StringUtils.isNotBlank(dateBirth)) {
			try {
				Date date = Constantas.fdate.parse(dateBirth);
				criteria.add(Restrictions.ge("dateBirth", DateUtil.truncate(date)));
				criteria.add(Restrictions.le("dateBirth", DateUtil.endOfDay(date)));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		
		if (StringUtils.isNotBlank(mobileNumber))
			criteria.add(Restrictions.eq("mobileNumber", mobileNumber));

		if (StringUtils.isNotBlank(clientType))
			criteria.add(Restrictions.eq("clientType", clientType));
		
		if (StringUtils.isNotBlank(errorMessage))
			criteria.add(Restrictions.ilike("errorMessage", errorMessage, MatchMode.ANYWHERE));

		criteria.addOrder(Order.desc("createdDate"));
		
		return this.getDataTablesFromCriteria(criteria, dataTables);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ListFailedEnrollment> getAll() {
		Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(ListFailedEnrollment.class);
		return criteria.list();
	}
	
}
