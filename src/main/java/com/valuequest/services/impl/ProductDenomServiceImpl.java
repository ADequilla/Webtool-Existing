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
import com.valuequest.controller.maintenance.model.ProductDenomModel;
import com.valuequest.dao.ProductDenomDao;
import com.valuequest.dao.ProductPayDao;
import com.valuequest.entity.StructureProductDenom;
import com.valuequest.entity.StructureProductPay;
import com.valuequest.entity.ViewProductDenom;
import com.valuequest.entity.security.SecUser;
import com.valuequest.services.ProductDenomService;

@Service
@Transactional(readOnly = true)
public class ProductDenomServiceImpl extends SimpleServiceImpl<StructureProductDenom> implements ProductDenomService {

	@Autowired
	private ProductDenomDao productDenomDao;
	
	@Autowired
	private ProductPayDao productPayDao;
	
	@Override
	public Class<StructureProductDenom> getRealClass() {
		return StructureProductDenom.class;
	}

	@Override
	public StructureProductDenom findByCode(Long id) {
		return productDenomDao.findByCode(id);
	}

	@Override
	public boolean isExist(Long id) {
		StructureProductDenom productDenom = findByCode(id);
		if (productDenom == null) {
			return false;
		}
		
		return true;
	}
	
	@Override
	@Modifying
    @Transactional(propagation=Propagation.REQUIRED)
    public void save(ProductDenomModel model, SecUser user) {
		StructureProductDenom  productDenom = findByCode(model.getId());
		
		if (productDenom == null){
			productDenom = new StructureProductDenom();
			System.out.println("############# model.getProductCode() = "+model.getProductCode());
			StructureProductPay pp = productPayDao.findByCode(model.getProductCode());
			System.out.println("############# StructureProductPay = "+pp);
			
			if(pp != null){
				productDenom.setProductId(pp.getId());
			}
			productDenom.setDescription(model.getDescription());
			productDenom.setDenom(model.getDenom());
			productDenom.setCreatedDate(new Date());
			productDenom.setCreatedBy(user.getId());
			productDenom.setUpdatedDate(new Date());
			productDenom.setUpdatedBy(user.getId());
			
			this.saveOrUpdate(productDenom);
		}else{
			productDenom.setUpdatedDate(new Date());
			productDenom.setUpdatedBy(user.getId());
			productDenom.setDenom(model.getDenom());
			productDenom.setDescription(model.getDescription());
			
			this.saveOrUpdate(productDenom);
		}
	}

	@Override
	@Modifying
    @Transactional(propagation=Propagation.REQUIRED)
	public void delete(List<ProductDenomModel> states) {
		List<Long> ids = new ArrayList<Long>();
		for (ProductDenomModel model : states) {
			ids.add(model.getId());
		}

		if (ids.size() > 0) {
			productDenomDao.delete(ids);
		}
	}


	@Override
	public DataTables searchByMapCriteria(DataTables dataTables, HashMap<String, Object> searchMap) {
		String productCode = (String) searchMap.get("productCode");
		String productName	= (String) searchMap.get("productName");
		Criteria criteria 	= this.getSessionFactory().getCurrentSession().createCriteria(ViewProductDenom.class);
		
		if (StringUtils.isNotBlank(productCode))
			criteria.add(Restrictions.eq("productCode", productCode));

		if (StringUtils.isNotBlank(productName))
			criteria.add(Restrictions.ilike("productName", productName, MatchMode.ANYWHERE));

		return this.getDataTablesFromCriteria(criteria, dataTables);
	}

	@Override
	public ViewProductDenom findProductProviderDenom(Long id) {
		return productDenomDao.findProductProviderDenom(id);
	}
	
}
