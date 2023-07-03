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
import com.valuequest.controller.maintenance.model.ProductPayModel;
import com.valuequest.dao.ProductPayDao;
import com.valuequest.entity.StructureBillerPay;
import com.valuequest.entity.StructureProductPay;
import com.valuequest.entity.ViewProductPay;
import com.valuequest.entity.security.SecUser;
import com.valuequest.services.BillerPayService;
import com.valuequest.services.ProductPayService;

@Service
@Transactional(readOnly = true)
public class ProductPayServiceImpl extends SimpleServiceImpl<StructureProductPay> implements ProductPayService {

	@Autowired
	private ProductPayDao productPayDao;
	
	@Autowired
	private BillerPayService billerPayService;
	
	@Override
	public Class<StructureProductPay> getRealClass() {
		return StructureProductPay.class;
	}

	@Override
	public StructureProductPay findByCode(String code) {
		return productPayDao.findByCode(code);
	}

	@Override
	public boolean isExist(String code) {
		StructureProductPay productPay = findByCode(code);
		if (productPay == null) {
			return false;
		}
		
		return true;
	}
	
	@Override
	@Modifying
    @Transactional(propagation=Propagation.REQUIRED)
    public void save(ProductPayModel model, SecUser user) {
		StructureProductPay  productPay = findByCode(model.getProductCode());
		if (productPay == null){
			productPay = new StructureProductPay();
			System.out.println("############# model.getBillerCode() = "+model.getBillerCode());
			StructureBillerPay bp = billerPayService.findByCode(model.getBillerCode());
			System.out.println("############# StructureBillerPay = "+bp);
			
			if(bp != null){
				productPay.setBillerId(bp.getId());
			}
			productPay.setProductCode(model.getProductCode());
			productPay.setProductName(model.getProductName());
			productPay.setDescription(model.getDescription());
			productPay.setStatus(model.getStatus());
			productPay.setCreatedDate(new Date());
			productPay.setCreatedBy(user.getId());
			productPay.setUpdatedDate(new Date());
			productPay.setUpdatedBy(user.getId());
		}else{
			productPay.setUpdatedDate(new Date());
			productPay.setUpdatedBy(user.getId());
			productPay.setProductName(model.getProductName());
			productPay.setDescription(model.getDescription());
			productPay.setStatus(model.getStatus());
		}
		
		this.saveOrUpdate(productPay);
	}

	@Override
	@Modifying
    @Transactional(propagation=Propagation.REQUIRED)
	public void delete(List<ProductPayModel> states) {
		List<String> ids = new ArrayList<String>();
		for (ProductPayModel model : states) {
			ids.add(model.getProductCode());
		}

		if (ids.size() > 0) {
			productPayDao.delete(ids);
		}
	}


	@Override
	public DataTables searchByMapCriteria(DataTables dataTables, HashMap<String, Object> searchMap) {
		String productCode = (String) searchMap.get("productCode");
		String productName	= (String) searchMap.get("productName");
		Criteria criteria 	= this.getSessionFactory().getCurrentSession().createCriteria(ViewProductPay.class);
		
		if (StringUtils.isNotBlank(productCode))
			criteria.add(Restrictions.eq("productCode", productCode));

		if (StringUtils.isNotBlank(productName))
			criteria.add(Restrictions.ilike("productName", productName, MatchMode.ANYWHERE));

		return this.getDataTablesFromCriteria(criteria, dataTables);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<StructureProductPay> getAll() {
		Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(StructureProductPay.class);
		return criteria.list();
	}
	
}
