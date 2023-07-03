package com.valuequest.dao.impl;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.valuequest.dao.GenericDao;
import com.valuequest.entity.K2CTransactionTypeLookup;
import com.valuequest.entity.Lookup;
import com.valuequest.entity.Module;
import com.valuequest.entity.ParamConfig;
import com.valuequest.entity.StructureProductCategory;
import com.valuequest.entity.StructureProvider;
import com.valuequest.entity.ViewProductCategory;
import com.valuequest.entity.ProductTypeEntity;
import com.valuequest.entity.StructureBillerProduct;
import com.valuequest.entity.StructureLoadProduct;

@Repository
public class GenericDaoImpl implements GenericDao {

	@Autowired
	protected SessionFactory sessionFactory;

	@SuppressWarnings("unchecked")
	@Override
	public List<Lookup> lookup(String type) {
		return sessionFactory.getCurrentSession()
				.createQuery("from Lookup where type = :type and activeFlag='Y'")
				.setParameter("type", type).list();
	}

	@Override
	public Lookup getLookupByValue(String type, String value) {
		return (Lookup) sessionFactory.getCurrentSession()
				.createQuery("from Lookup where type = :type and value= :value and activeFlag='Y'")
				.setParameter("type", type)
				.setParameter("value", value).uniqueResult();
	}

	@Override
	public String getLookupDesc(String type, String value) {
		Lookup lookup = (Lookup) sessionFactory.getCurrentSession()
				.createQuery("from Lookup where type = :type and value= :value and activeFlag='Y'")
				.setParameter("type", type)
				.setParameter("value", value).uniqueResult();

		if (lookup != null)
			return lookup.getDescription();

		return null;
	}

	@Override
	public ParamConfig getConfigByName(String name) {
		return (ParamConfig) sessionFactory.getCurrentSession()
				.createQuery("from ParamConfig where name = :name")
				.setParameter("name", name).uniqueResult();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Module> module() {
		return sessionFactory.getCurrentSession()
				.createQuery("from Module").list();
	}

	@Override
	public Module getModule(Long id) {
		return (Module) sessionFactory.getCurrentSession().get(Module.class, id);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<StructureProductCategory> productCategory() {
		return sessionFactory.getCurrentSession()
				.createQuery("from StructureProductCategory").list();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ViewProductCategory> productCategoryLoad() {
		return sessionFactory.getCurrentSession()
				.createQuery("from ViewProductCategory where productTypeName='E-load'").list();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ViewProductCategory> productCategoryBiller() {
		return sessionFactory.getCurrentSession()
				.createQuery("from ViewProductCategory where productTypeName='Bills Payment'").list();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<StructureProductCategory> category() {
		return sessionFactory.getCurrentSession()
				.createQuery("from StructureProductCategory").list();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<StructureBillerProduct> billerProduct() {
		return sessionFactory.getCurrentSession()
				.createQuery("from StructureBillerProduct").list();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<StructureLoadProduct> loadProduct() {
		return sessionFactory.getCurrentSession()
				.createQuery("from StructureLoadProduct").list();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<StructureProvider> provider() {
		return sessionFactory.getCurrentSession()
				.createQuery("from StructureProvider").list();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ProductTypeEntity> productType() {
		return sessionFactory.getCurrentSession()
				.createQuery("from ProductTypeEntity").list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<K2CTransactionTypeLookup> k2cTransactionTypeLookup(String type) {
		return sessionFactory.getCurrentSession()
				.createQuery("from K2CTransactionTypeLookup").list();
	}
}