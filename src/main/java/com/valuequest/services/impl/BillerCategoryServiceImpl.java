package com.valuequest.services.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.valuequest.common.DataTables;
import com.valuequest.controller.maintenance.model.BillerCategoryModel;
import com.valuequest.dao.BillerCategoryDao;
import com.valuequest.entity.StructureBillerCategory;
import com.valuequest.entity.security.SecUser;
import com.valuequest.services.BillerCategoryService;

@Service
@Transactional(readOnly = true)
public class BillerCategoryServiceImpl extends SimpleServiceImpl<StructureBillerCategory> implements BillerCategoryService {

	@Autowired
	private BillerCategoryDao billerCategoryDao;
	
	@Override
	public Class<StructureBillerCategory> getRealClass() {
		return StructureBillerCategory.class;
	}

	@Override
	public StructureBillerCategory findByCode(Long id) {
		return billerCategoryDao.findById(id);
	}

	@Override
	public boolean isExist(Long id) {
		StructureBillerCategory provider = findByCode(id);
		if (provider == null) {
			return false;
		}
		
		return true;
	}
	
	@Override
	@Modifying
    @Transactional(propagation=Propagation.REQUIRED)
    public void save(BillerCategoryModel model, SecUser user) {
		StructureBillerCategory  billerCategory = findByCode(model.getId());
		if (billerCategory == null){
			billerCategory = new StructureBillerCategory();
			billerCategory.setBillerCategoryName(model.getBillerCategoryName());
			billerCategory.setStatus(model.getStatus());
			billerCategory.setCreatedDate(new Date());
			billerCategory.setCreatedBy(user.getId());
			billerCategory.setUpdatedDate(new Date());
			billerCategory.setUpdatedBy(user.getId());
		}else{
			billerCategory.setUpdatedDate(new Date());
			billerCategory.setUpdatedBy(user.getId());
			billerCategory.setBillerCategoryName(model.getBillerCategoryName());
			billerCategory.setStatus(model.getStatus());
		}
		
		this.saveOrUpdate(billerCategory);
	}

	@Override
	@Modifying
    @Transactional(propagation=Propagation.REQUIRED)
	public void delete(List<BillerCategoryModel> states) {
		List<Long> ids = new ArrayList<Long>();
		for (BillerCategoryModel model : states) {
			ids.add(model.getId());
		}

		if (ids.size() > 0) {
			billerCategoryDao.delete(ids);
		}
	}


	@Override
	public DataTables searchByMapCriteria(DataTables dataTables, HashMap<String, Object> searchMap) {
		String billerCategoryName = (String) searchMap.get("billerCategoryName");
		Criteria criteria 	= this.getSessionFactory().getCurrentSession().createCriteria(getRealClass());
		
		if (StringUtils.isNotBlank(billerCategoryName))
			criteria.add(Restrictions.eq("billerCategoryName", billerCategoryName));

		return this.getDataTablesFromCriteria(criteria, dataTables);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<StructureBillerCategory> getAll() {
		Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(StructureBillerCategory.class);
		return criteria.list();
	}
	
}
