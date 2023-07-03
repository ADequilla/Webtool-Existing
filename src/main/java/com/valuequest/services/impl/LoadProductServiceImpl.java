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
import com.valuequest.controller.maintenance.model.LoadProductModel;
import com.valuequest.dao.LoadProductDao;
import com.valuequest.entity.StructureLoadProduct;
import com.valuequest.entity.ViewLoadProduct;
import com.valuequest.entity.security.SecUser;
import com.valuequest.services.LoadProductService;

@Service
@Transactional(readOnly = true)
public class LoadProductServiceImpl extends SimpleServiceImpl<StructureLoadProduct> implements LoadProductService {

	@Autowired
	private LoadProductDao loadProductDao;
	
	@Override
	public Class<StructureLoadProduct> getRealClass() {
		return StructureLoadProduct.class;
	}

	@Override
	public StructureLoadProduct findById(Long id) {
		return loadProductDao.findById(id);
	}

	@Override
	public boolean isExist(Long id) {
		StructureLoadProduct loadProduct = findById(id);
		if (loadProduct == null) {
			return false;
		}
		
		return true;
	}
	
	@Override
	@Modifying
    @Transactional(propagation=Propagation.REQUIRED)
    public void save(LoadProductModel model, SecUser user) {
		StructureLoadProduct  loadProduct = findById(model.getLoadProductId());
		if (loadProduct == null){
			loadProduct = new StructureLoadProduct();
			loadProduct.setProductCategoryId(model.getProductCategoryId());
			loadProduct.setLoadProductId(model.getLoadProductId());
			loadProduct.setLoadProductName(model.getLoadProductName());
			loadProduct.setDescription(model.getDescription());
			loadProduct.setStatus(model.getStatus());
			loadProduct.setCreatedDate(new Date());
			loadProduct.setCreatedBy(user.getId());
			loadProduct.setUpdatedDate(new Date());
			loadProduct.setUpdatedBy(user.getId());
		}else{
			loadProduct.setUpdatedDate(new Date());
			loadProduct.setUpdatedBy(user.getId());
			loadProduct.setLoadProductName(model.getLoadProductName());
			loadProduct.setDescription(model.getDescription());
			loadProduct.setStatus(model.getStatus());
		}
		
		this.saveOrUpdate(loadProduct);
	}

	@Override
	@Modifying
    @Transactional(propagation=Propagation.REQUIRED)
	public void delete(List<LoadProductModel> states) {
		List<Long> ids = new ArrayList<Long>();
		for (LoadProductModel model : states) {
			ids.add(model.getLoadProductId());
		}

		if (ids.size() > 0) {
			loadProductDao.delete(ids);
		}
	}


	@Override
	public DataTables searchByMapCriteria(DataTables dataTables, HashMap<String, Object> searchMap) {
		String loadProductId   = (String) searchMap.get("loadProductId");
		String loadProductName = (String) searchMap.get("loadProductName");
		String productCategoryName = (String) searchMap.get("productCategoryName");
		Criteria criteria 	= this.getSessionFactory().getCurrentSession().createCriteria(ViewLoadProduct.class);
		
		if (StringUtils.isNotBlank(loadProductId))
			criteria.add(Restrictions.eq("loadProductId", Long.parseLong(loadProductId)));

		if (StringUtils.isNotBlank(loadProductName))
			criteria.add(Restrictions.ilike("loadProductName", loadProductName, MatchMode.ANYWHERE));
		
		if (StringUtils.isNotBlank(productCategoryName))
			criteria.add(Restrictions.eq("productCategoryName", productCategoryName));
		
		return this.getDataTablesFromCriteria(criteria, dataTables);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<StructureLoadProduct> getAll() {
		Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(StructureLoadProduct.class);
		return criteria.list();
	}
	
}
