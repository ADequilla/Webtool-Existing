package com.valuequest.services.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.valuequest.common.DataTables;
import com.valuequest.controller.administration.model.StateModel;
import com.valuequest.entity.ProductAndService;
import com.valuequest.entity.security.SecUser;
import com.valuequest.services.ProductAndServiceService;

@Service
@Transactional(readOnly = true)
public class ProductAndServiceServiceImpl extends SimpleServiceImpl<ProductAndService> implements ProductAndServiceService {

	@Override
	public Class<ProductAndService> getRealClass() {
		return ProductAndService.class;
	}

	@Override
	@Modifying
	@Transactional(propagation = Propagation.REQUIRED)
	public void save(ProductAndService model, SecUser userLogin) {
		ProductAndService product = null;
		if (model.getId() != null) {
			product = this.findById(model.getId());
			product.setUpdatedBy(userLogin.getId());
			product.setUpdatedDate(new Date());
		} else {
			product = new ProductAndService();
			product.setCreatedBy(userLogin.getId());
			product.setCreatedDate(new Date());
		}
		product.setName(model.getName());
		product.setDescription(model.getDescription());
		product.setBanner(model.getBanner());
		product.setShow(model.getShow());
		
		this.saveOrUpdate(product);
		model.setId(product.getId());
	}

	@Override
	@Modifying
    @Transactional(propagation=Propagation.REQUIRED)
	public void delete(List<StateModel> states) {
		List<Long> ids = new ArrayList<Long>();
		for (StateModel model : states) {
			ids.add(model.getId());
		}

		if (ids.size() > 0) {
			this.getSessionFactory().getCurrentSession().createQuery("delete from ProductAndService where id in :ids").setParameterList("ids", ids).executeUpdate();
		}
	}

	@Override
	public DataTables searchByMapCriteria(DataTables dataTables, HashMap<String, Object> searchMap) {
		String name 		= (String) searchMap.get("name");
		Criteria criteria 	= this.getSessionFactory().getCurrentSession().createCriteria(getRealClass());

		if (StringUtils.isNotBlank(name))
			criteria.add(Restrictions.ilike("name", name, MatchMode.ANYWHERE));

		return this.getDataTablesFromCriteria(criteria, dataTables);
	}
}
