package com.valuequest.services.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.valuequest.common.DataTables;
import com.valuequest.controller.administration.model.StateModel;
import com.valuequest.entity.Product;
import com.valuequest.entity.security.SecUser;
import com.valuequest.services.ProductInfoService;

@Service
@Transactional(readOnly = true)
public class ProductInfoServiceImpl extends SimpleServiceImpl<Product> implements ProductInfoService {

	@Override
	public Class<Product> getRealClass() {
		return Product.class;
	}

	@Override
	@Modifying
	@Transactional(propagation = Propagation.REQUIRED)
	public void save(Product model, SecUser user) {
		Product product = null;
		if (model.getId() != null) {
			product = this.findById(model.getId());
			product.setUpdatedBy(user.getId());
			product.setLastUpdate(new Date());
		} else {
			product = new Product();
			product.setCreatedBy(user.getId());
			product.setCreatedDate(new Date());
			product.setProductDate(new Date());
		}
		product.setProductName(model.getProductName());
		product.setProductDesc(model.getProductDesc());
		product.setProductPeriodStart(model.getProductPeriodStart());
		product.setProductPeriodEnd(model.getProductPeriodEnd());
		product.setProductImg(model.getProductImg());

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
			this.getSessionFactory().getCurrentSession().createQuery("delete from Product where id in :ids").setParameterList("ids", ids).executeUpdate();
		}
	}

	@Override
	public DataTables searchByMapCriteria(DataTables dataTables, HashMap<String, Object> searchMap) {
		String topic 		= (String) searchMap.get("topic");
		Criteria criteria 	= this.getSessionFactory().getCurrentSession().createCriteria(getRealClass());
		criteria.createAlias("user", "user", JoinType.INNER_JOIN);

		if (StringUtils.isNotBlank(topic))
			criteria.add(Restrictions.ilike("productName", topic, MatchMode.ANYWHERE));

		return this.getDataTablesFromCriteria(criteria, dataTables);
	}
}
