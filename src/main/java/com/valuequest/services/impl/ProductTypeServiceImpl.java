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
import com.valuequest.controller.maintenance.model.ProductTypeModel;
import com.valuequest.dao.ProductTypeDao;
import com.valuequest.entity.ProductTypeEntity;
import com.valuequest.entity.ViewProductType;
import com.valuequest.entity.security.SecUser;
import com.valuequest.services.ProductTypeService;
import com.valuequest.util.ObjectUtil;


@Service
@Transactional(readOnly = true)
public class ProductTypeServiceImpl extends SimpleServiceImpl<ProductTypeEntity> implements ProductTypeService {

	
	@Autowired
	private ProductTypeDao productTypeDao;
	
	@Override
	public Class<ProductTypeEntity> getRealClass() {
		return ProductTypeEntity.class;
	}
	
	@Override
	public ProductTypeEntity findById(Long id) {
		return productTypeDao.findById(id);
	}

	@Override
	public boolean isExist(Long id) {
		ProductTypeEntity productType = findById(id);
		if (productType == null) {
			return false;
		}
		
		return true;
	}

	@Override
	@Modifying
    @Transactional(propagation=Propagation.REQUIRED)
	public void save(ProductTypeModel model, SecUser user) {
		
		ProductTypeEntity  productType = findById(model.getProductTypeId());
		if (productType == null){
			productType = new ProductTypeEntity();
			productType.setProviderId(model.getProviderId());
			productType.setProductTypeId(model.getProductTypeId());
			productType.setProductTypeName(model.getProductTypeName()); 
			productType.setDescription(model.getDescription());
			productType.setStatus(model.getStatus());
			productType.setLastSync(new Date());
			productType.setCreatedDate(new Date());
			productType.setCreatedBy(user.getId());
			productType.setUpdatedDate(new Date());
			productType.setUpdatedBy(user.getId());
		}else{
			productType.setLastSync(new Date());
			productType.setUpdatedDate(new Date());
			productType.setUpdatedBy(user.getId());
			productType.setProviderId(model.getProviderId());
			productType.setProductTypeId(model.getProductTypeId());
			productType.setProductTypeName(model.getProductTypeName());
			productType.setDescription(model.getDescription());
			productType.setStatus(model.getStatus());
		}
		
		this.saveOrUpdate(productType);		
	}

	@Override
	@Modifying
    @Transactional(propagation=Propagation.REQUIRED)
	public void delete(List<ProductTypeModel> states) {
		List<Long> ids = new ArrayList<Long>();
		for (ProductTypeModel model : states) {
			ids.add(model.getProductTypeId());
		}

		if (ids.size() > 0) {
			productTypeDao.delete(ids);
		}
		
	}


	@Override
	public DataTables searchByMapCriteria(DataTables dataTables, HashMap<String, Object> searchMap) {
		String productTypeId   = (String) searchMap.get("productTypeId");
		String productTypeName = (String) searchMap.get("productTypeName");
		String providerName = (String) searchMap.get("providerName");
		Criteria criteria 	= this.getSessionFactory().getCurrentSession().createCriteria(ViewProductType.class);
		
		if (StringUtils.isNotBlank(productTypeId))
			criteria.add(Restrictions.eq("productTypeId", Long.parseLong(productTypeId)));

		if (StringUtils.isNotBlank(productTypeName))
			criteria.add(Restrictions.ilike("productTypeName", productTypeName, MatchMode.ANYWHERE));
		
		if (StringUtils.isNotBlank(providerName))
			criteria.add(Restrictions.eq("providerName", providerName));

		return this.getDataTablesFromCriteria(criteria, dataTables);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ProductTypeEntity> getAll() {
		Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(ProductTypeEntity.class);
		return criteria.list();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public ProductTypeEntity findByProductTypeId (String productTypeId) {
		Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(ProductTypeEntity.class);
		criteria.add(Restrictions.eq("productTypeId", productTypeId));
				
		List<ProductTypeEntity> list = criteria.list();
		if (ObjectUtil.isNotEmpty(list))
			return list.get(0);

		return null;
	}
	
}
