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
import com.valuequest.entity.Atm;
import com.valuequest.entity.ViewAtm;
import com.valuequest.entity.security.SecUser;
import com.valuequest.services.AtmLocationService;

@Service
@Transactional(readOnly = true)
public class AtmLocationServiceImpl extends SimpleServiceImpl<Atm> implements AtmLocationService{

	@Override
	public Class<Atm> getRealClass() {
		return Atm.class;
	}
	
	@Override
	@Modifying
    @Transactional(propagation=Propagation.REQUIRED)
    public void save(Atm model, SecUser user) {
		Atm atm = null;
    	if (model.getId() != null){
    		atm = this.findById(model.getId());
    		atm.setLastUpdatedBy(user.getId());
    		atm.setLastUpdatedDate(new Date());
    	}else{
    		atm = new Atm();
    		atm.setCreatedBy(user.getId());
    		atm.setCreatedDate(new Date());
    	}
    	
    	atm.setType(model.getType());
		atm.setInst(model.getInst());
    	atm.setAddress(model.getAddress());
    	atm.setCity(model.getCity());
    	atm.setDescription(model.getDescription());
    	atm.setLongitude(model.getLongitude());
    	atm.setLatitude(model.getLatitude());
		// atm.setInstCode(model.getInstCode());
    	
    	this.saveOrUpdate(atm);
    	model.setId(atm.getId());
    }

	@Override
	@Modifying
    @Transactional(propagation=Propagation.REQUIRED)
	public void delete(List<StateModel> states) {
		List<Long> ids = new ArrayList<Long>();
		for (StateModel model : states) {
			ids.add(model.getId());
		}
		
		if(ids.size() > 0){
			this.getSessionFactory().getCurrentSession().createQuery("delete from Atm where id in :ids").setParameterList("ids", ids).executeUpdate();
		}
	}
	
	@Override
	public DataTables searchByMapCriteria(DataTables dataTables, HashMap<String, Object> searchMap) {
		String location		= (String) searchMap.get("location");
		String instCode		= (String) searchMap.get("inst");
		Criteria criteria 	= this.getSessionFactory().getCurrentSession().createCriteria(ViewAtm.class);

		if (StringUtils.isNotBlank(location)){
			criteria.add(Restrictions.disjunction()
			        .add(Restrictions.ilike("address", location, MatchMode.ANYWHERE))
			        .add(Restrictions.ilike("city", location, MatchMode.ANYWHERE))
			        .add(Restrictions.ilike("description", location, MatchMode.ANYWHERE)));
		}

		if (StringUtils.isNotBlank(instCode)){
			criteria.add(Restrictions.ilike("inst", instCode, MatchMode.ANYWHERE));
		}

		return this.getDataTablesFromCriteria(criteria, dataTables);
	}
}