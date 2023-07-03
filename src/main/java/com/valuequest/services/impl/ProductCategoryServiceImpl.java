package com.valuequest.services.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.valuequest.common.DataTables;
import com.valuequest.controller.maintenance.model.ProductCategoryModel;
import com.valuequest.dao.ProductCategoryDao;
import com.valuequest.entity.StructureProductCategory;
import com.valuequest.entity.ViewProductCategory;
import com.valuequest.entity.security.SecUser;
import com.valuequest.services.ProductCategoryService;

@Service
@Transactional(readOnly = true)
public class ProductCategoryServiceImpl extends SimpleServiceImpl<StructureProductCategory> implements ProductCategoryService {

	@Autowired
	private ProductCategoryDao productCategoryDao;
	
	@Override
	public Class<StructureProductCategory> getRealClass() {
		return StructureProductCategory.class;
	}

	@Override
	public StructureProductCategory findById(Long id) {
		return productCategoryDao.findById(id);
	}

	@Override
	public boolean isExist(Long id) {
		StructureProductCategory productCategory = findById(id);
		if (productCategory == null) {
			return false;
		}
		
		return true;
	}
	
	@Override
	@Modifying
    @Transactional(propagation=Propagation.REQUIRED)
    public void save(ProductCategoryModel model, SecUser user) {
		StructureProductCategory  productCategory = findById(model.getProductCategoryId());
		if (productCategory == null){
			productCategory = new StructureProductCategory();
			productCategory.setProductCategoryId(model.getProductCategoryId());
			productCategory.setProductTypeId(model.getProductTypeId());
			productCategory.setProductCategoryName(model.getProductCategoryName());
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
			productCategory.setProductCategoryName(model.getProductCategoryName());
			productCategory.setDescription(model.getDescription());
			productCategory.setStatus(model.getStatus());
		}
		
		this.saveOrUpdate(productCategory);
	}

	@Override
	@Modifying
    @Transactional(propagation=Propagation.REQUIRED)
	public void delete(List<ProductCategoryModel> states) {
		List<Long> ids = new ArrayList<Long>();
		for (ProductCategoryModel model : states) {
			ids.add(model.getProductCategoryId());
		}

		if (ids.size() > 0) {
			productCategoryDao.delete(ids);
		}
	}


	@Override
	public DataTables searchByMapCriteria(DataTables dataTables, HashMap<String, Object> searchMap) {
		String productCategoryId   = (String) searchMap.get("productCategoryId");
		String productCategoryName = (String) searchMap.get("productCategoryName");
		String productTypeName = (String) searchMap.get("productTypeName");
		String providerName = (String) searchMap.get("providerName");
		Criteria criteria 	= this.getSessionFactory().getCurrentSession().createCriteria(ViewProductCategory.class);
		
		if (StringUtils.isNotBlank(productCategoryId))
			criteria.add(Restrictions.eq("productCategoryId", Long.parseLong(productCategoryId)));

		if (StringUtils.isNotBlank(productCategoryName))
			criteria.add(Restrictions.ilike("productCategoryName", productCategoryName, MatchMode.ANYWHERE));
		
		if (StringUtils.isNotBlank(productTypeName))
			criteria.add(Restrictions.eq("productTypeName", productTypeName));

		if (StringUtils.isNotBlank(providerName))
			criteria.add(Restrictions.eq("providerName", providerName));
		
		return this.getDataTablesFromCriteria(criteria, dataTables);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<StructureProductCategory> getAll() {
		Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(StructureProductCategory.class);
		return criteria.list();
	}
	
}
