package com.valuequest.services.impl;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.valuequest.common.DataTables;
import com.valuequest.controller.administration.model.StateModel;
import com.valuequest.controller.mobilecollection.model.McUserModel;
import com.valuequest.dao.LogAuditTrailAoDao;
import com.valuequest.entity.LogAuditTrailAo;
import com.valuequest.entity.LogUploadFile;
import com.valuequest.entity.MCUser;
import com.valuequest.entity.MCUserMapper;
import com.valuequest.entity.StructureBranch;
import com.valuequest.entity.StructureUnit;
import com.valuequest.entity.TempAccountOfficer;
import com.valuequest.entity.UserBranch;
import com.valuequest.entity.UserUnit;
import com.valuequest.entity.security.SecUser;
import com.valuequest.services.GenericService;
import com.valuequest.services.LogAuditTrailAoService;
import com.valuequest.services.LogUploadFileService;
import com.valuequest.services.MCUserService;
import com.valuequest.services.TempAccountOfficerService;
import com.valuequest.util.Constantas;
import com.valuequest.util.DateUtil;
import com.valuequest.util.GenerateNumber;
import com.valuequest.util.HttpClientMcu;
import com.valuequest.util.ShaGenerator;
import com.valuequest.util.PojoJsonMapper;
import com.valuequest.util.StatusConstantas;

/**
 * @author Eki Nurhadi
 *
 */

@Service
@Transactional(readOnly = true)
public class MCUserServiceImpl extends SimpleServiceImpl<MCUser> implements MCUserService {

	//protected BCryptPasswordEncoder bcryptEncoder = new BCryptPasswordEncoder();
	
	@Autowired
	private LogUploadFileService logUploadFileService;

	@Autowired
	private TempAccountOfficerService tempAccountOfficerService;

	@Autowired
	private GenericService genericService;

	@Autowired
	private LogAuditTrailAoService logAuditTrailAoService;

	@Autowired
	private LogAuditTrailAoDao logAuditTrailAoDao;
	
	@Override
	public Class<MCUser> getRealClass() {
		return MCUser.class;
	}

	public static String hashPasswordBcrypt(String value) {
        return BCrypt.hashpw(value, BCrypt.gensalt(11));
    }
	
	@Override
	public DataTables searchByMapCriteria(DataTables dataTables, HashMap<String, Object> searchMap, Boolean isPendingData) {
		String mcuId = (String) searchMap.get("mcuId");
		String staffId = (String) searchMap.get("staffId");
		String mobileNumber = (String) searchMap.get("mobileNumber");
		String internalAccount = (String) searchMap.get("internalAccount");
		String branchCode = (String) searchMap.get("branchCode");
		String unitCode = (String) searchMap.get("unitCode");
		String designation = (String) searchMap.get("designation");
		String aoStatus = (String) searchMap.get("aoStatus");
		String dateStart = (String) searchMap.get("dateStart");
		String dateEnd = (String) searchMap.get("dateEnd");
		Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(getRealClass());
		criteria.createAlias("approvedBy", "approvedBy", JoinType.LEFT_OUTER_JOIN);
		criteria.createAlias("createdBy", "createdBy", JoinType.LEFT_OUTER_JOIN);
		criteria.createAlias("updatedBy", "updatedBy", JoinType.LEFT_OUTER_JOIN);
		criteria.createAlias("unit", "unit");
		criteria.createAlias("branch", "branch");

		if (StringUtils.isNotBlank(dateStart) && StringUtils.isNotBlank(dateEnd)) {
			try {
				Date start = Constantas.fdate.parse(dateStart);
				Date end = Constantas.fdate.parse(dateEnd);
				criteria.add(Restrictions.and(Restrictions.ge("createdOn", Constantas.atStartOfDay(start)), Restrictions.le("createdOn",Constantas.atEndOfDay(end))));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}

		if (!StringUtils.isNotBlank(dateStart) && StringUtils.isNotBlank(dateEnd)) {
			try {
				Date end = Constantas.fdate.parse(dateEnd);
				criteria.add(Restrictions.and(Restrictions.ge("createdOn", Constantas.atStartOfDay(end)), Restrictions.le("createdOn", Constantas.atEndOfDay(end))));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}

		if (StringUtils.isNotBlank(dateStart) && !StringUtils.isNotBlank(dateEnd)) {
			try {
				Date start = Constantas.fdate.parse(dateStart);
				criteria.add(
						Restrictions.and(Restrictions.ge("createdOn", Constantas.atStartOfDay(start)), Restrictions.le("createdOn", Constantas.atStartOfDay(start))));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}

		if (StringUtils.isNotBlank(mcuId))
			criteria.add(Restrictions.ilike("mcuId", mcuId, MatchMode.ANYWHERE));

		if (StringUtils.isNotBlank(staffId))
			criteria.add(Restrictions.ilike("staffId", staffId, MatchMode.ANYWHERE));

		if (StringUtils.isNotBlank(mobileNumber))
			criteria.add(Restrictions.ilike("mobileNumber", mobileNumber, MatchMode.ANYWHERE));

		if (StringUtils.isNotBlank(internalAccount))
			criteria.add(Restrictions.ilike("internalAccount", internalAccount, MatchMode.ANYWHERE));

		if (StringUtils.isNotBlank(branchCode))
			criteria.add(Restrictions.eq("branch.code", branchCode));

		if (StringUtils.isNotBlank(unitCode))
			criteria.add(Restrictions.eq("unit.code", unitCode));

		if (StringUtils.isNotBlank(designation))
			criteria.add(Restrictions.ilike("designation", designation, MatchMode.ANYWHERE));

		if (StringUtils.isNotBlank(aoStatus)) {
			if(StatusConstantas.WAIT_AUTHORIZATION.equals(aoStatus)) {
				criteria.add(Restrictions.eq("modified", Boolean.TRUE));
			}else {
				criteria.add(Restrictions.eq("aoStatus", aoStatus));
			}
		}
			
		if(!isPendingData) {
			criteria.add(Restrictions.isNotNull("approvedBy"));
		}else {
			criteria.add(Restrictions.eq("approvedStatus", StatusConstantas.PENDING));
		}
		
		criteria.addOrder(Order.asc("mcuId"));
		return this.getDataTablesFromCriteria(criteria, dataTables);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void resetMCUserPassword(String mcuid, SecUser loginSecUser) {
		MCUser mcUser = this.getMCUserByMcuId(mcuid);
		String number = GenerateNumber.GenerateRandomNumber(8);
		mcUser.setAoPassword(hashPasswordBcrypt(number));
		System.out.println("##############number: "+number);
		System.out.println("###############bcrypt number: "+hashPasswordBcrypt(number));
		
		System.out.println("###########checkPW: "+BCrypt.checkpw(number, hashPasswordBcrypt(number)));
		
		mcUser.setResetPassword(1);
		mcUser.setAoStatus("ACTIVE");
		mcUser.setLoginRetry(0);
		this.saveOrUpdate(mcUser);

		HttpClientMcu httpClientMcu = new HttpClientMcu();
		String msg = genericService.getConfigValueByName("SMS_RESET_PASSWORD");
		String url = genericService.getConfigValueByName("URL_SEND_SMS_MOBCOL");
		try {
			url = url + "customerId=" + mcUser.getId() + "&activity=RESET_PASSWORD" + "&message="
					+ URLEncoder.encode(String.format(msg, number), StandardCharsets.UTF_8.toString());
			System.out.println("##### url " + url);
			httpClientMcu.callMbo(url);

			LogAuditTrailAo auditTrailAo = new LogAuditTrailAo(mcUser.getId(), StatusConstantas.RESET_PASSWORD, null,
					StatusConstantas.WEB, loginSecUser.getId(), loginSecUser.getId(), new Date(), null);
			logAuditTrailAoService.save(auditTrailAo);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void resetMCUserPin(String mcuid, SecUser loginSecUser) {
		MCUser mcUser = this.getMCUserByMcuId(mcuid);
		String number = GenerateNumber.GenerateRandomNumber(6);
		mcUser.setAoPin(ShaGenerator.hashSha512(number + mcUser.getDeviceId()));
		mcUser.setResetPin(1);
		mcUser.setAoStatus("ACTIVE");
		mcUser.setPinRetry(0);
		this.saveOrUpdate(mcUser);

		HttpClientMcu httpClientMcu = new HttpClientMcu();
		String msg = genericService.getConfigValueByName("SMS_RESET_MPIN");
		String url = genericService.getConfigValueByName("URL_SEND_SMS_MOBCOL");
		try {
			url = url + "customerId=" + mcUser.getId() + "&activity=RESET_MPIN" + "&message="
					+ URLEncoder.encode(String.format(msg, number), StandardCharsets.UTF_8.toString());
			System.out.println("##### url " + url);
			httpClientMcu.callMbo(url);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		LogAuditTrailAo auditTrailAo = new LogAuditTrailAo(mcUser.getId(), StatusConstantas.RESET_MPIN, null,
				StatusConstantas.WEB, loginSecUser.getId(), loginSecUser.getId(), new Date(), null);
		logAuditTrailAoService.save(auditTrailAo);
	}

	@Override
	public MCUser getMCUserByMcuId(String mcuId) {
		Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(MCUser.class);
		criteria.createAlias("approvedBy", "approvedBy", JoinType.LEFT_OUTER_JOIN);
		criteria.createAlias("createdBy", "createdBy", JoinType.LEFT_OUTER_JOIN);
		criteria.createAlias("updatedBy", "updatedBy", JoinType.LEFT_OUTER_JOIN);
		criteria.add(Restrictions.eq("mcuId", mcuId));
		return (MCUser) criteria.uniqueResult();
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void save(McUserModel model, SecUser loginSecUser) {
		MCUser mcUser = new MCUser();
		String changes = null; 
		if (model.getId() != null) {
			mcUser = this.findById(model.getId());
			changes = getChanges(mcUser, model);
			mcUser.setUpdatedBy(loginSecUser);
			mcUser.setUpdatedOn(new Date());
			mcUser.setModified(Boolean.TRUE);
			String json = PojoJsonMapper.toJson(model);
			mcUser.setFreeData(json);
		} else {
			mcUser.setCreatedBy(loginSecUser);
			mcUser.setMcuId(model.getMcuId());
			mcUser.setCreatedOn(new Date());
			mcUser.setAoStatus(StatusConstantas.INACTIVE);
			mcUser.setStaffId(model.getStaffId());
			mcUser.setGivenName(model.getGivenName());
			mcUser.setMiddleName(model.getMiddleName());
			mcUser.setSurname(model.getSurname());
			mcUser.setDesignation(model.getDesignation());

			mcUser.setInternalAccount(model.getInternalAccount());
			mcUser.setEmail(model.getEmail());
			mcUser.setMobileNumber(model.getMobileNumber());
			mcUser.setModified(Boolean.FALSE);
			mcUser.setEnabled(1);
			
			String[] branchs = model.getBranch();
			if (branchs != null && branchs.length > 0) {
				StructureBranch structureBranch = null;
				for (String branch : branchs) {
					structureBranch = new StructureBranch();
					structureBranch.setCode(branch);
					mcUser.setBranch(structureBranch);
				}
			}

			String[] units = model.getUnit();
			if (units != null && units.length > 0) {
				StructureUnit structureUnit = null;
				for (String unit : units) {
					structureUnit = new StructureUnit();
					structureUnit.setCode(unit);
					mcUser.setUnit(structureUnit);
				}
			}
		}
		mcUser.setApprovedStatus(StatusConstantas.PENDING);
		this.saveOrUpdate(mcUser);
		
		if (mcUser.getId() != null) {
			LogAuditTrailAo auditTrailAo = new LogAuditTrailAo(mcUser.getId(), StatusConstantas.UPDATE, changes,
					StatusConstantas.WEB, loginSecUser.getId(), loginSecUser.getId(), new Date(), null);
			logAuditTrailAoService.save(auditTrailAo);
		}
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void saveUpload(MCUser model, SecUser loginSecUser) {
		MCUser mcUser = new MCUser();
		String changes = null; 
		if (model.getId() == null) {
			mcUser.setCreatedBy(loginSecUser);
			mcUser.setMcuId(model.getMcuId());
			mcUser.setCreatedOn(new Date());
			mcUser.setAoStatus(StatusConstantas.INACTIVE);
			mcUser.setStaffId(model.getStaffId());
			mcUser.setGivenName(model.getGivenName());
			mcUser.setMiddleName(model.getMiddleName());
			mcUser.setSurname(model.getSurname());
			mcUser.setDesignation(model.getDesignation());
			mcUser.setBranch(model.getBranch());
			mcUser.setUnit(model.getUnit());
			mcUser.setInternalAccount(model.getInternalAccount());
			mcUser.setEmail(model.getEmail());
			mcUser.setMobileNumber(model.getMobileNumber());
			mcUser.setModified(Boolean.FALSE);
			mcUser.setEnabled(1);
		}
		mcUser.setApprovedStatus(StatusConstantas.PENDING);
		this.saveOrUpdate(mcUser);
		
		if (mcUser.getId() != null) {
			LogAuditTrailAo auditTrailAo = new LogAuditTrailAo(mcUser.getId(), StatusConstantas.UPDATE, changes,
					StatusConstantas.WEB, loginSecUser.getId(), loginSecUser.getId(), new Date(), null);
			logAuditTrailAoService.save(auditTrailAo);
		}
	}

	@Override
	public MCUser getMCUserByStaffId(String staffId) {
		Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(MCUser.class);
		criteria.createAlias("approvedBy", "approvedBy", JoinType.LEFT_OUTER_JOIN);
		criteria.createAlias("createdBy", "createdBy", JoinType.LEFT_OUTER_JOIN);
		criteria.createAlias("updatedBy", "updatedBy", JoinType.LEFT_OUTER_JOIN);
		criteria.add(Restrictions.eq("staffId", staffId));
		return (MCUser) criteria.uniqueResult();
	}

	@Override
	public List<MCUserMapper> searchByMapCriteria(HashMap<String, Object> map) {
		String startPeriod = (String) map.get("START_PERIOD");
		String endPeriod = (String) map.get("END_PERIOD");
		String createdBy = (String) map.get("CREATED_BY");
		Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(MCUserMapper.class);

		if(StringUtils.isNotBlank(startPeriod)){
			try {
				Date date = Constantas.fdate.parse(startPeriod);
				criteria.add(Restrictions.ge("createdOn", DateUtil.truncate(date)));
			} catch (ParseException e) {
				e.printStackTrace();
			}	
		}
		
		if(StringUtils.isNotBlank(endPeriod)){
			try {
				Date date = Constantas.fdate.parse(endPeriod);
				criteria.add(Restrictions.le("createdOn", DateUtil.endOfDay(date)));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		
		if (StringUtils.isNotBlank(createdBy))
			criteria.add(Restrictions.like("createdBy", createdBy, MatchMode.ANYWHERE));

		criteria.addOrder(Order.asc("createdOn"));
		return criteria.list();
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void update(List<StateModel> states, String status, SecUser loginSecUser) {
		for (StateModel state : states) {
			MCUser mcUser = this.findById(state.getId());
			if (mcUser != null) {
				if (StatusConstantas.PENDING.equals(mcUser.getApprovedStatus())) {
					if(StatusConstantas.REJECTED.equals(status) && mcUser.getApprovedBy() == null) {
						this.getSessionFactory().getCurrentSession().createQuery("delete from MCUser where id = :id").setLong("id", mcUser.getId()).executeUpdate();
					}else {
						mcUser.setApprovedStatus(status);
						mcUser.setApprovedBy(loginSecUser);
						mcUser.setApprovedDate(new Date());
						mcUser.setModified(Boolean.FALSE);
						
						if(mcUser.getFreeData() != null && StatusConstantas.APPROVED.equals(status)) {
							McUserModel fromJson = PojoJsonMapper.fromJson(mcUser.getFreeData(), McUserModel.class);
							mcUser.setStaffId(fromJson.getStaffId());
							mcUser.setGivenName(fromJson.getGivenName());
							mcUser.setMiddleName(fromJson.getMiddleName());
							mcUser.setSurname(fromJson.getSurname());
							mcUser.setDesignation(fromJson.getDesignation());
							mcUser.setInternalAccount(fromJson.getInternalAccount());
							mcUser.setEmail(fromJson.getEmail());
							mcUser.setMobileNumber(fromJson.getMobileNumber());
							mcUser.setFreeData(null);
							
							String[] branchs = fromJson.getBranch();
							if (branchs != null && branchs.length > 0) {
								StructureBranch structureBranch = null;
								for (String branch : branchs) {
									structureBranch = new StructureBranch();
									structureBranch.setCode(branch);
									mcUser.setBranch(structureBranch);
								}
							}

							String[] units = fromJson.getUnit();
							if (units != null && units.length > 0) {
								StructureUnit structureUnit = null;
								for (String unit : units) {
									structureUnit = new StructureUnit();
									structureUnit.setCode(unit);
									mcUser.setUnit(structureUnit);
								}
							}
						}
						
						this.saveOrUpdate(mcUser);
						
					}
					
					if (mcUser.getId() != null) {
						List<LogAuditTrailAo> logAuditTrailAo = logAuditTrailAoDao.findByAoId(mcUser.getId());
						
						
						logAuditTrailAo.get(0).setLastUpdateBy(loginSecUser.getId());
						logAuditTrailAo.get(0).setLastUpdateDate(new Date());
						
						logAuditTrailAoService.save(logAuditTrailAo.get(0));
					}
				}
			}
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void deactiveOrReactive(String mcuid, Integer deactive, SecUser loginSecUser) {
		MCUser mcUser = this.getMCUserByMcuId(mcuid);
		mcUser.setEnabled(deactive);
		this.saveOrUpdate(mcUser);

		LogAuditTrailAo auditTrailAo;
		if (StatusConstantas.DEACTIVE.equals(deactive)) {
			auditTrailAo = new LogAuditTrailAo(mcUser.getId(), StatusConstantas.DEACTIVATE, null, StatusConstantas.WEB,
					loginSecUser.getId(), loginSecUser.getId(), new Date(), null);
		} else {
			auditTrailAo = new LogAuditTrailAo(mcUser.getId(), StatusConstantas.REACTIVATE, null, StatusConstantas.WEB,
					loginSecUser.getId(), loginSecUser.getId(), new Date(), null);
		}

		logAuditTrailAoService.save(auditTrailAo);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void changeAccStatusByMcuId(String mcuid, String inactive, SecUser loginSecUser) {
		MCUser mcUser = this.getMCUserByMcuId(mcuid);
		mcUser.setAoStatus(inactive);
		this.saveOrUpdate(mcUser);

		LogAuditTrailAo auditTrailAo = new LogAuditTrailAo(mcUser.getId(), StatusConstantas.UNBLOCKED, null,
				StatusConstantas.WEB, loginSecUser.getId(), loginSecUser.getId(), new Date(), null);
		logAuditTrailAoService.save(auditTrailAo);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void uploadFile(List<MCUser> mcUsers, List<TempAccountOfficer> tempAccountOfficers,
		LogUploadFile logUploadFile, SecUser loginSecUser) {
			if(tempAccountOfficers.size() > 0) {
				for (TempAccountOfficer tempAccountOfficer : tempAccountOfficers) {
					tempAccountOfficer.setLogUploadFileId(logUploadFile.getId());
					tempAccountOfficerService.save(tempAccountOfficer);
				}
			}

			if(mcUsers.size() > 0) {
				for (MCUser mcUser : mcUsers) {
					this.saveUpload(mcUser, loginSecUser);
				}
			}
	}

	@Override
	public MCUser getMCUserByInternalAccount(String internalAccount) {
		Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(MCUser.class);
		criteria.createAlias("approvedBy", "approvedBy", JoinType.LEFT_OUTER_JOIN);
		criteria.createAlias("createdBy", "createdBy", JoinType.LEFT_OUTER_JOIN);
		criteria.createAlias("updatedBy", "updatedBy", JoinType.LEFT_OUTER_JOIN);
		criteria.add(Restrictions.eq("internalAccount", internalAccount));
		criteria.add(Restrictions.eq("enabled", StatusConstantas.REACTIVE));
		return (MCUser) criteria.uniqueResult();
	}

	@Override
	public MCUser getMCUserByMobileNumber(String mobileNumber) {
		Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(MCUser.class);
		criteria.createAlias("approvedBy", "approvedBy", JoinType.LEFT_OUTER_JOIN);
		criteria.createAlias("createdBy", "createdBy", JoinType.LEFT_OUTER_JOIN);
		criteria.createAlias("updatedBy", "updatedBy", JoinType.LEFT_OUTER_JOIN);
		criteria.add(Restrictions.eq("mobileNumber", mobileNumber));
		return (MCUser) criteria.uniqueResult();
	}

	public String getChanges(MCUser inDb, McUserModel toBeUpdate) {
		List<String> changes = new ArrayList<>();

		if (!inDb.getStaffId().equals(toBeUpdate.getStaffId()))
			changes.add(toBeUpdate.getStaffId());

		if (!inDb.getGivenName().equals(toBeUpdate.getGivenName())
				|| !inDb.getMiddleName().equals(toBeUpdate.getMiddleName())
				|| !inDb.getSurname().equals(toBeUpdate.getSurname()))
			changes.add(toBeUpdate.getGivenName()+" "+toBeUpdate.getMiddleName()+" "+toBeUpdate.getSurname());

		if (!inDb.getDesignation().equals(toBeUpdate.getDesignation()))
			changes.add(toBeUpdate.getDesignation());

		if (!inDb.getMobileNumber().equals(toBeUpdate.getMobileNumber()))
			changes.add(toBeUpdate.getMobileNumber());

		String[] branchs = toBeUpdate.getBranch();
		if (branchs != null && branchs.length > 0) {
			for (String branch : branchs) {
				if (!inDb.getBranch().getCode().equals(branch))
					changes.add(branch);
			}
		}

		String[] units = toBeUpdate.getUnit();
		if (units != null && units.length > 0) {
			for (String unit : units) {
				if (!inDb.getUnit().getCode().equals(unit))
					changes.add(unit);
			}
		}

		if (!inDb.getInternalAccount().equals(toBeUpdate.getInternalAccount()))
			changes.add(toBeUpdate.getInternalAccount());

		if (!inDb.getEmail().equals(toBeUpdate.getEmail()))
			changes.add(toBeUpdate.getEmail());

		return StringUtils.join(changes, "|");
	}

	@Override
	public DataTables searchByMapCriteria(DataTables dataTables, HashMap<String, Object> searchMap) {
		String mcuId = (String) searchMap.get("mcuId");
		String staffId = (String) searchMap.get("staffId");
		String mobileNumber = (String) searchMap.get("mobileNumber");
		String internalAccount = (String) searchMap.get("internalAccount");
		String branchCode = (String) searchMap.get("branchCode");
		String unitCode = (String) searchMap.get("unitCode");
		String designation = (String) searchMap.get("designation");
		String aoStatus = (String) searchMap.get("aoStatus");
		String dateStart = (String) searchMap.get("dateStart");
		String dateEnd = (String) searchMap.get("dateEnd");
		Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(getRealClass());
		criteria.createAlias("approvedBy", "approvedBy", JoinType.LEFT_OUTER_JOIN);
		criteria.createAlias("createdBy", "createdBy", JoinType.LEFT_OUTER_JOIN);
		criteria.createAlias("updatedBy", "updatedBy", JoinType.LEFT_OUTER_JOIN);

		if (StringUtils.isNotBlank(dateStart) && StringUtils.isNotBlank(dateEnd)) {
			try {
				Date start = Constantas.fdate.parse(dateStart);
				Date end = Constantas.fdate.parse(dateEnd);
				criteria.add(Restrictions.and(Restrictions.ge("createdOn", start), Restrictions.le("createdOn", end)));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}

		if (!StringUtils.isNotBlank(dateStart) && StringUtils.isNotBlank(dateEnd)) {
			try {
				Date end = Constantas.fdate.parse(dateEnd);
				criteria.add(Restrictions.and(Restrictions.ge("createdOn", end), Restrictions.le("createdOn", end)));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}

		if (StringUtils.isNotBlank(dateStart) && !StringUtils.isNotBlank(dateEnd)) {
			try {
				Date start = Constantas.fdate.parse(dateStart);
				criteria.add(
						Restrictions.and(Restrictions.ge("createdOn", start), Restrictions.le("createdOn", start)));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}

		if (StringUtils.isNotBlank(mcuId))
			criteria.add(Restrictions.ilike("mcuId", mcuId, MatchMode.ANYWHERE));

		if (StringUtils.isNotBlank(staffId))
			criteria.add(Restrictions.ilike("staffId", staffId, MatchMode.ANYWHERE));

		if (StringUtils.isNotBlank(mobileNumber))
			criteria.add(Restrictions.ilike("mobileNumber", mobileNumber, MatchMode.ANYWHERE));

		if (StringUtils.isNotBlank(internalAccount))
			criteria.add(Restrictions.ilike("internalAccount", internalAccount, MatchMode.ANYWHERE));

		if (StringUtils.isNotBlank(branchCode))
			criteria.add(Restrictions.eq("branchCode", branchCode));

		if (StringUtils.isNotBlank(unitCode))
			criteria.add(Restrictions.eq("unitCode", unitCode));

		if (StringUtils.isNotBlank(designation))
			criteria.add(Restrictions.ilike("designation", designation, MatchMode.ANYWHERE));

		if (StringUtils.isNotBlank(aoStatus))
			criteria.add(Restrictions.eq("aoStatus", aoStatus));
		
		return this.getDataTablesFromCriteria(criteria, dataTables);
	}

}
