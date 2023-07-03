package com.valuequest.services.impl;

import java.text.ParseException;
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
import com.valuequest.controller.maintenance.model.BillerProductModel;
import com.valuequest.dao.BillerProductDao;
import com.valuequest.entity.MCUserMapper;
import com.valuequest.entity.StructureBillerProduct;
import com.valuequest.entity.ViewBillerProduct;
import com.valuequest.entity.security.SecUser;
import com.valuequest.services.BillerProductService;
import com.valuequest.util.Constantas;

@Service
@Transactional(readOnly = true)
public class BillerProductServiceImpl extends SimpleServiceImpl<StructureBillerProduct> implements BillerProductService {

	@Autowired
	private BillerProductDao billerProductDao;
	
	@Override
	public Class<StructureBillerProduct> getRealClass() {
		return StructureBillerProduct.class;
	}

	@Override
	public StructureBillerProduct findById(Long id) {
		return billerProductDao.findById(id);
	}

	@Override
	public boolean isExist(Long id) {
		StructureBillerProduct billerProduct = findById(id);
		if (billerProduct == null) {
			return false;
		}
		
		return true;
	}
	
	@Override
	@Modifying
    @Transactional(propagation=Propagation.REQUIRED)
    public void save(BillerProductModel model, SecUser user) {
		StructureBillerProduct  billerProduct = findById(model.getBillerProductId());
		if (billerProduct == null){
			billerProduct = new StructureBillerProduct();
			billerProduct.setProductCategoryId(model.getProductCategoryId());
			billerProduct.setBillerProductId(model.getBillerProductId());
			billerProduct.setBillerProductName(model.getBillerProductName());
			billerProduct.setDescription(model.getDescription());
			billerProduct.setBankCommission(model.getBankCommission());
			billerProduct.setServiceFee(model.getServiceFee());
			billerProduct.setStatus(model.getStatus());
			billerProduct.setCreatedDate(new Date());
			billerProduct.setCreatedBy(user.getId());
			billerProduct.setUpdatedDate(new Date());
			billerProduct.setUpdatedBy(user.getId());
		}else{
			billerProduct.setUpdatedDate(new Date());
			billerProduct.setUpdatedBy(user.getId());
			billerProduct.setBillerProductName(model.getBillerProductName());
			billerProduct.setDescription(model.getDescription());
			billerProduct.setBankCommission(model.getBankCommission());
			billerProduct.setServiceFee(model.getServiceFee());
			billerProduct.setStatus(model.getStatus());
		}
		
		this.saveOrUpdate(billerProduct);
	}

	@Override
	@Modifying
    @Transactional(propagation=Propagation.REQUIRED)
	public void delete(List<BillerProductModel> states) {
		List<Long> ids = new ArrayList<Long>();
		for (BillerProductModel model : states) {
			ids.add(model.getBillerProductId());
		}

		if (ids.size() > 0) {
			billerProductDao.delete(ids);
		}
	}


	@Override
	public List<ViewBillerProduct> searchByMapCriteria(HashMap<String, Object> map) {
		String billerProductId   = (String) map.get("billerProductId");
		String billerProductName = (String) map.get("billerProductName");
		String productCategoryName = (String) map.get("productCategoryName");
		String providerName = (String) map.get("providerName");
		
		Criteria criteria 	= this.getSessionFactory().getCurrentSession().createCriteria(ViewBillerProduct.class);

		if (StringUtils.isNotBlank(billerProductId))
			criteria.add(Restrictions.eq("billerProductId", Long.parseLong(billerProductId)));

		if (StringUtils.isNotBlank(billerProductName))
			criteria.add(Restrictions.ilike("billerProductName", billerProductName, MatchMode.ANYWHERE));
		
		if (StringUtils.isNotBlank(productCategoryName))
			criteria.add(Restrictions.eq("productCategoryName", productCategoryName));
		
		if (StringUtils.isNotBlank(providerName))
			criteria.add(Restrictions.eq("providerName", providerName));

		return criteria.list();
	}
	
	@Override
	public DataTables searchByMapCriteria(DataTables dataTables, HashMap<String, Object> searchMap) {
		String billerProductId   = (String) searchMap.get("billerProductId");
		String billerProductName = (String) searchMap.get("billerProductName");
		String productCategoryName = (String) searchMap.get("productCategoryName");
		String providerName = (String) searchMap.get("providerName");
		
		Criteria criteria 	= this.getSessionFactory().getCurrentSession().createCriteria(ViewBillerProduct.class);
		
		if (StringUtils.isNotBlank(billerProductId))
			criteria.add(Restrictions.eq("billerProductId", Long.parseLong(billerProductId)));

		if (StringUtils.isNotBlank(billerProductName))
			criteria.add(Restrictions.ilike("billerProductName", billerProductName, MatchMode.ANYWHERE));
		
		if (StringUtils.isNotBlank(productCategoryName))
			criteria.add(Restrictions.eq("productCategoryName", productCategoryName));
		
		if (StringUtils.isNotBlank(providerName))
			criteria.add(Restrictions.eq("providerName", providerName));
		
		return this.getDataTablesFromCriteria(criteria, dataTables);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<StructureBillerProduct> getAll() {
		Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(StructureBillerProduct.class);
		return criteria.list();
	}
	
}
