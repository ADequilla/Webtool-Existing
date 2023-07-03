package com.valuequest.services.impl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.valuequest.common.DataTables;
import com.valuequest.controller.monitoring.model.ListUsedDeviceModel;
import com.valuequest.dao.ListUsedDeviceDao;
import com.valuequest.entity.ListUsedDevice;
import com.valuequest.entity.security.SecUser;
import com.valuequest.services.ListUsedDeviceService;
import com.valuequest.util.Constantas;
import com.valuequest.util.DateUtil;

@Service
@Transactional(readOnly = true)
public class ListUsedDeviceServiceImpl extends SimpleServiceImpl<ListUsedDevice> implements ListUsedDeviceService {

	@Autowired
	private ListUsedDeviceDao listUsedDeviceDao;
	
	@Override
	public Class<ListUsedDevice> getRealClass() {
		return ListUsedDevice.class;
	}

	@Override
	public ListUsedDevice findById(Long id) {
		return listUsedDeviceDao.findById(id);
	}

	@Override
	public List<ListUsedDevice> findByCid(String cid) {
		return listUsedDeviceDao.findByCid(cid);
	}
	
	@Override
	public boolean dataCid(String cid) {
		List<ListUsedDevice> usedDevice = findByCid(cid);
		System.out.println("### size CID in device: "+usedDevice.size());
		if (usedDevice.size()> 1) {
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	public boolean isExist(Long id) {
		ListUsedDevice usedDevice = findById(id);
		if (usedDevice == null) {
			return false;
		}
		
		return true;
	}
	
	@Override
	@Modifying
    @Transactional(propagation=Propagation.REQUIRED)
    public void save(ListUsedDeviceModel model, SecUser user) {
		ListUsedDevice  usedDevice = findById(model.getId());
		if (usedDevice == null){
			usedDevice = new ListUsedDevice();
			usedDevice.setId(model.getId());
			usedDevice.setDeviceId(model.getDeviceId());
			usedDevice.setDeviceModel(model.getDeviceModel());
			usedDevice.setCid(model.getCid());
			usedDevice.setBranchCode(model.getBranchCode());
			usedDevice.setMobileNumber(model.getMobileNumber());
			usedDevice.setClientName(model.getClientName());
			usedDevice.setClientType(model.getClientType());
			usedDevice.setDeviceStatus(model.getDeviceStatus());
			usedDevice.setCreatedDate(new Date());
			usedDevice.setCreatedBy(user.getId());
			usedDevice.setUpdatedDate(new Date());
			usedDevice.setUpdatedBy(user.getId());
		}else{
			usedDevice.setUpdatedDate(new Date());
			usedDevice.setUpdatedBy(user.getId());
			usedDevice.setDeviceStatus(model.getDeviceStatus());
		}
		
		this.saveOrUpdate(usedDevice);
	}

	@Override
	@Modifying
    @Transactional(propagation=Propagation.REQUIRED)
	public void delete(List<ListUsedDeviceModel> states) {
		List<Long> ids = new ArrayList<Long>();
		for (ListUsedDeviceModel model : states) {
			ids.add(model.getId());
		}

		if (ids.size() > 0) {
			listUsedDeviceDao.delete(ids);
		}
	}


	@Override
	public DataTables searchByMapCriteria(DataTables dataTables, HashMap<String, Object> searchMap) {
		String searchDateStart 	= (String) searchMap.get("searchDateStart");
		String searchDateEnd 	= (String) searchMap.get("searchDateEnd");
		String deviceId   = (String) searchMap.get("deviceId");
		String cid = (String) searchMap.get("cid");
		String mobileNumber = (String) searchMap.get("mobileNumber");
		String clientType = (String) searchMap.get("clientType");
		String deviceStatus = (String) searchMap.get("deviceStatus");
		Criteria criteria 	= this.getSessionFactory().getCurrentSession().createCriteria(ListUsedDevice.class);
		
		if(StringUtils.isNotBlank(searchDateStart)){
			try {
				Date date = Constantas.fdate.parse(searchDateStart);
				criteria.add(Restrictions.ge("createdDate", DateUtil.truncate(date)));
			} catch (ParseException e) {
				e.printStackTrace();
			}	
		}
		
		if(StringUtils.isNotBlank(searchDateEnd)){
			try {
				Date date = Constantas.fdate.parse(searchDateEnd);
				criteria.add(Restrictions.le("createdDate", DateUtil.endOfDay(date)));
			} catch (ParseException e) {
				e.printStackTrace();
			}	
		}
		
		if (StringUtils.isNotBlank(deviceId))
			criteria.add(Restrictions.ilike("deviceId", deviceId, MatchMode.ANYWHERE));

		if (StringUtils.isNotBlank(deviceStatus))
			criteria.add(Restrictions.eq("deviceStatus", Long.parseLong(deviceStatus)));
		
		if (StringUtils.isNotBlank(mobileNumber))
			criteria.add(Restrictions.eq("mobileNumber", mobileNumber));

		if (StringUtils.isNotBlank(clientType))
			criteria.add(Restrictions.eq("clientType", clientType));
		
		if (StringUtils.isNotBlank(cid))
			criteria.add(Restrictions.eq("cid", cid));

		criteria.addOrder(Order.desc("createdDate"));
		
		return this.getDataTablesFromCriteria(criteria, dataTables);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ListUsedDevice> getAll() {
		Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(ListUsedDevice.class);
		return criteria.list();
	}
	
}
