package com.valuequest.services.impl;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;
import org.hibernate.sql.JoinType;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.valuequest.common.DataTables;
import com.valuequest.controller.administration.model.StateModel;
import com.valuequest.controller.mbo.model.MboModel;
import com.valuequest.controller.monitoring.model.ProfileModel;
import com.valuequest.entity.Client;
import com.valuequest.entity.JobProcess;
import com.valuequest.entity.Lookup;
import com.valuequest.entity.ParamConfig;
import com.valuequest.entity.TransFinanceUnion;
import com.valuequest.entity.ViewClient;
import com.valuequest.entity.security.SecUser;
import com.valuequest.services.BranchService;
import com.valuequest.services.CenterService;
import com.valuequest.services.ClientService;
import com.valuequest.services.JobService;
import com.valuequest.util.Constantas;
import com.valuequest.util.DateUtil;
import com.valuequest.util.GenerateNumber;
import com.valuequest.util.ShaGenerator;
import com.valuequest.util.MD5Generator;
import com.valuequest.util.ObjectUtil;

@Service
@Transactional(readOnly = true)
public class ClientServiceImpl extends SimpleServiceImpl<Client> implements ClientService{

	
	
	@Autowired
	private BranchService branchService;
	
	@Autowired
	private JobService jobService;
	
	@Override
	public Class<Client> getRealClass() {
		return Client.class;
	}

	//protected BCryptPasswordEncoder bcryptEncoder = new BCryptPasswordEncoder();
	
	private GenerateNumber generator;

	private HashMap<String, Object> getProductName;
	
	public static String hashPasswordBcrypt(String value) {
        return BCrypt.hashpw(value, BCrypt.gensalt(11));
    }
	
	@SuppressWarnings("unchecked")
	@Override
	public ViewClient findByCid(String cid) {
		Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(ViewClient.class);
		criteria.add(Restrictions.eq("cid", cid));
		
		List<ViewClient> list = criteria.list();
		if (ObjectUtil.isNotEmpty(list))
			return list.get(0);

		return null;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public ViewClient findByCidAndTypeCodeClientType(String cid) {
		Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(ViewClient.class);
		criteria.add(Restrictions.eq("cid", cid));
		String[] tc = new String[]{"MEMBER","NON MEMBER"};
		criteria.add(Restrictions.in("typeCode", tc));
		
		List<ViewClient> list = criteria.list();
		if (ObjectUtil.isNotEmpty(list))
			return list.get(0);

		return null;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public ViewClient findByCidAndTypeCode(String cid) {
		Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(ViewClient.class);
		criteria.add(Restrictions.eq("cid", cid));
		String[] tc = new String[]{"AO","UM"};
		criteria.add(Restrictions.in("typeCode", tc));
		criteria.add(Restrictions.ne("approveStatusCode", Lookup.LOOKUP_APPROVAL_STATUS_REJECTED));
		
		List<ViewClient> list = criteria.list();
		if (ObjectUtil.isNotEmpty(list))
			return list.get(0);

		return null;
	}

	public String getSavingName(String input){
		if("1001".equals(input)){
			return "CA Interest Bearing";
		}else if("1002".equals(input)){
			return "CA Non Interest Bearing";
		}else if("6001".equals(input)){
			return "Matapat";
		}else if("6002".equals(input)){
			return "Kayang Kaya";
		}else if("6003".equals(input)){
			return "Pledge";
		}else if("6004".equals(input)){
			return "Maagap";
		}else if("6005".equals(input)){
			return "FCDU";
		}else if("6006".equals(input)){
			return "AO ACCOUNT";
		}else if("6601".equals(input)){
			return "Tiwala";
		}else if("6602".equals(input)){
			return "Tagumpay Member";
		}else if("6603".equals(input)){
			return "Tagumpay Non Member";
		}else if("6604".equals(input)){
			return "Tagumpay Employee";
		}
		return "-";
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public ViewClient profile(ProfileModel model) {
		System.out.println("##############Masuk1");
		Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(ViewClient.class);
		System.out.println("##############View Client: "+ criteria.list());
		System.out.println("##############Masuk2");
		//List<ViewClient> list1 = criteria.list();
//		criteria.add(Restrictions.isNotNull("registered"));
		
		if (model.getNumber() != null) {
			criteria.add(Restrictions.eq(model.getField(), model.getNumber()));
		} else if (StringUtils.isNotBlank(model.getString())) {
			criteria.add(Restrictions.eq(model.getField(), model.getString()));
		} else {
			criteria.add(Restrictions.eq("id", Long.valueOf(-1)));
		}
		
		String[] tc = new String[]{"MEMBER","AGENT","NON MEMBER"};
		criteria.add(Restrictions.in("typeCode", tc));

		List<ViewClient> list = criteria.list();
		
		System.out.println("##########list: "+list);
		if (ObjectUtil.isNotEmpty(list)){
			ViewClient vc = list.get(0);
			try{
				vc.setAccountType(getSavingName(vc.getAccountType()));
			}catch (Exception e) {
				System.out.println("############## ERROR = getSavingName");
				e.printStackTrace();
			}
			return vc;
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public ViewClient profileMcu(ProfileModel model) {
		Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(ViewClient.class);

		if (model.getNumber() != null) {
			criteria.add(Restrictions.eq(model.getField(), model.getNumber()));
		} else if (StringUtils.isNotBlank(model.getString())) {
			criteria.add(Restrictions.eq(model.getField(), model.getString()));
		} else {
			criteria.add(Restrictions.eq("id", Long.valueOf(-1)));
		}

		List<ViewClient> list = criteria.list();
		if (ObjectUtil.isNotEmpty(list))
			return list.get(0);

		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ViewClient> list(String name, int size) {
		Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(ViewClient.class);
		criteria.add(Restrictions.isNotNull("registered"));
		criteria.add(Restrictions.eq("approveStatusCode", Lookup.LOOKUP_APPROVAL_STATUS_APPROVED));
		criteria.add(Restrictions.ilike("fullname", name, MatchMode.ANYWHERE));
		return criteria.setMaxResults(size).list();
	}

	@Modifying
    @Transactional(propagation=Propagation.REQUIRED)
	@Override
	public void save(Client client, SecUser userLogin) {
		client.setLastUpdatedBy(userLogin.getId());
		client.setLastUpdatedDate(new Date());
		this.saveOrUpdate(client);
	}
	
	@Modifying
    @Transactional(propagation=Propagation.REQUIRED)
	@Override
	public void save(MboModel model, SecUser userLogin) {
		Client client = null;
		if (model.getId() != null) {
			client = this.findById(model.getId());
			client.setLastUpdatedBy(userLogin.getId());
			client.setLastUpdatedDate(new Date());
		} else {
			client = new Client();
			client.setCreatedBy(userLogin.getId());
			client.setCreatedDate(new Date());
			client.setType(Client.CLIENT_TYPE_MBO);
			client.setEnabled(1);
			client.setResetPasswdFlag(0);
			client.setResetPinFlag(0);
			client.setLoginRetry(0);
			client.setPinRetry(0);
			client.setFailTokenAttempts(0);
			client.setApproverStatus(Lookup.LOOKUP_APPROVAL_STATUS_APPROVED);
			client.setStatus(Lookup.LOOKUP_ACTIVATION_STATUS_ACTIVE);
			client.setCid("");
		}
		client.setAccount(model.getAccount());
		client.setBranchCode(model.getBranchCode());
		client.setName(model.getName());
		client.setAddress(model.getAddress());
		client.setActCode(model.getActcode());
		
		this.saveOrUpdate(client);
		model.setId(client.getId());
	}
	
	@Modifying
    @Transactional(propagation=Propagation.REQUIRED)
	@Override
	public void saveMcu(MboModel model, SecUser userLogin) {
		Client client = null;
		if (model.getId() != null) {
			client = this.findById(model.getId());
			client.setLastUpdatedBy(userLogin.getId());
			client.setLastUpdatedDate(new Date());
		} else {
			client = new Client();
			client.setCreatedBy(userLogin.getId());
			client.setCreatedDate(new Date());
			client.setType(Client.CLIENT_TYPE_MBO);
			client.setEnabled(1);
			client.setResetPasswdFlag(0);
			client.setResetPinFlag(0);
			client.setLoginRetry(0);
			client.setPinRetry(0);
			client.setFailTokenAttempts(0);
			client.setApproverStatus(Lookup.LOOKUP_APPROVAL_STATUS_APPROVED);
			client.setStatus(Lookup.LOOKUP_ACTIVATION_STATUS_ACTIVE);
			client.setCid("");
		}
		client.setAccount(model.getAccount());
		client.setBranchCode(model.getBranchCode());
		client.setName(model.getName());
		client.setAddress(model.getAddress());
		client.setActCode(model.getActcode());
		
		this.saveOrUpdate(client);
		model.setId(client.getId());
	}

	@Modifying
    @Transactional(propagation=Propagation.REQUIRED)
	@Override
	public void update(List<StateModel> states, String status, SecUser userLogin) {
		Client client = null;
		for (StateModel state : states) {
			client = this.findById(state.getId());
			if ( client != null) {
				if(Lookup.LOOKUP_APPROVAL_STATUS_PENDING.equals(client.getApproverStatus())){
					if (Lookup.LOOKUP_APPROVAL_STATUS_APPROVED.equals(status)) {
						
						int actCodelifetime		= getActCodeLifetime(client.getType());
						int activationlifetime	= getActivationLifetime(client.getType());
						
						Calendar expActCal = Calendar.getInstance();
						expActCal.setTime(new Date(System.currentTimeMillis()));
						expActCal.add(Calendar.HOUR, actCodelifetime);
						
						Calendar expCal = Calendar.getInstance();
						expCal.setTime(new Date(System.currentTimeMillis()));
						expCal.add(Calendar.MONTH, activationlifetime);

						client.setActCode(generateActCode(client.getCid(), userLogin.getId()));
						client.setExpiredActcode(expActCal.getTime());
						client.setExpiredDate(expCal.getTime());
						client.setEnabled(1);
						client.setStatus(Lookup.LOOKUP_ACTIVATION_STATUS_WAIT);
						client.setBroadcastStatus(Lookup.LOOKUP_SMS_STATUS_UNSENT);
						
						if (client.getApproveDate() == null) {
							client.setApproveDate(new Date());
							client.setApprover(userLogin);
						} else {
							client.setReapproveDate(new Date());
							client.setReapprover(userLogin);
						}
						
						// Send Activation Code Message
						JobProcess job 	= new JobProcess();
						job.setStatus(JobProcess.JOB_STATUS_WAITING);
						job.setFlag(1);
						job.setDate(new Date());
						job.setRefId(client.getId());
						job.setUserType(client.getType());
						job.setName(JobProcess.JOB_SENT_SMS);
						jobService.saveOrUpdate(job);
					}

					client.setApproverStatus(status);
					client.setLastUpdatedBy(userLogin.getId());
					client.setLastUpdatedDate(new Date());
					
					this.saveOrUpdate(client);
				}
			}
		}
	}

	@Modifying
    @Transactional(propagation=Propagation.REQUIRED)
	@Override
	public void resendActcode(Client client, SecUser userLogin) {
		if (client != null) {
			int actCodelifetime = getActCodeLifetime(client.getType());
			Calendar cal 		= Calendar.getInstance();
			cal.setTime(new Date(System.currentTimeMillis()));
			cal.add(Calendar.HOUR, actCodelifetime);
			
			client.setExpiredActcode(cal.getTime());
			client.setLastUpdatedBy(userLogin.getId());
			client.setLastUpdatedDate(new Date());
			
			this.saveOrUpdate(client);
			
			JobProcess job 	= new JobProcess();
			job.setStatus(JobProcess.JOB_STATUS_WAITING);
			job.setFlag(1);
			job.setDate(new Date());
			job.setRefId(client.getId());
			job.setUserType(client.getType());
			job.setName(JobProcess.JOB_SENT_SMS);
			jobService.saveOrUpdate(job);
		}
	}

	//mobcol
	@Modifying
	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public void resetPassword(Client client, SecUser userLogin) {
		if(client != null){
			SimpleDateFormat f 		= new SimpleDateFormat("ddMMyy");
			String login			= client.getLogin();
			String defaultPassword	= "123456";
			if(client.getDob() != null){
				defaultPassword		= f.format(client.getDob());
			}
			String hashPassword	= null;
			//if	( Client.CLIENT_TYPE_AGENT.equals(client.getType())){
				hashPassword 	= MD5Generator.generate(defaultPassword + login.substring(login.length()-2) + login.substring(0,2));
			/*} else {
				hashPassword 	= MD5Generator.generate(defaultPassword + login.substring(login.length()-2) + login.substring(0,2));
			}*/

			client.setPassword(hashPassword);
			client.setStatus(Lookup.LOOKUP_ACTIVATION_STATUS_ACTIVE);
			client.setResetPasswdFlag(1);
			client.setLoginRetry(0);
			client.setEnabled(1);
			client.setLastUpdatedBy(userLogin.getId());
			client.setLastUpdatedDate(new Date());
			
			this.saveOrUpdate(client);
		}
	}
	
	//k2c
	@Modifying
	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public String resetPasswordRandom(Client client, SecUser userLogin) {
		if(client != null){
			String login			= client.getLogin();
			String defaultPassword = generator.generate(generator.GenerateRandomNumber(1), 6);
			String hashPassword	= null;
			hashPassword = hashPasswordBcrypt(defaultPassword + login.substring(login.length() - 2) + login.substring(0, 2));


			client.setPassword(hashPassword);
			client.setStatus(Lookup.LOOKUP_ACTIVATION_STATUS_ACTIVE);
			client.setResetPasswdFlag(1);
			client.setLoginRetry(0);
			client.setEnabled(1);
			client.setLastUpdatedBy(userLogin.getId());
			client.setLastUpdatedDate(new Date());
			
			this.saveOrUpdate(client);
			
			return defaultPassword;
		}
		return null;
	}

	//mobcol
	@Modifying
	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public void resetPin(Client client, SecUser userLogin) {
		if(client != null){
			SimpleDateFormat f 	= new SimpleDateFormat("ddMMyy");
			Long id				= client.getId();
			String cid			= client.getCid();
			String lastTokenId	= client.getLastTokenId();
			String defaultPin	= "123456";
			if(client.getDob() != null){
				defaultPin		= f.format(client.getDob());
			}
			String hashPin 	= null;
			if	( Client.CLIENT_TYPE_AGENT.equals(client.getType())){
				hashPin 	= MD5Generator.generate(id+defaultPin+cid);
			} else {
				hashPin		= MD5Generator.generate(defaultPin + lastTokenId);
			}

			client.setPin(hashPin);
			client.setStatus(Lookup.LOOKUP_ACTIVATION_STATUS_ACTIVE);
			client.setResetPinFlag(1);
			client.setPinRetry(0);
			client.setEnabled(1);
			client.setFailTokenAttempts(0);
			client.setLastUpdatedBy(userLogin.getId());
			client.setLastUpdatedDate(new Date());
			
			this.saveOrUpdate(client);
		}
	}
	
	//k2c
	@Modifying
	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public String resetPinAuthor(Client client, SecUser userLogin) {
		if(client != null){
			String lastTokenId	= client.getLastTokenId();
			String defaultPin = generator.generate(generator.GenerateRandomNumber(1), 6);
			String hashPin 	= null;
			hashPin		= ShaGenerator.hashSha512(defaultPin + lastTokenId);

			client.setPin(hashPin);
			client.setStatus(Lookup.LOOKUP_ACTIVATION_STATUS_ACTIVE);
			client.setResetPinFlag(1);
			client.setPinRetry(0);
			client.setEnabled(1);
			client.setFailTokenAttempts(0);
			client.setLastUpdatedBy(userLogin.getId());
			client.setLastUpdatedDate(new Date());
			
			this.saveOrUpdate(client);
			
			return defaultPin;
		}
		return null;
	}

	@Modifying
	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public void deactivate(Client client, SecUser userLogin) {
		if(client != null){
			client.setStatus(Lookup.LOOKUP_ACTIVATION_STATUS_INACTIVE);
			client.setEnabled(0);
			client.setBroadcastStatus("UNSENT");
			client.setLoginRetry(0);
			client.setPinRetry(0);
			client.setFailTokenAttempts(0);
			client.setLastUpdatedBy(userLogin.getId());
			client.setLastUpdatedDate(new Date());
			
			this.saveOrUpdate(client);
		}
	}
	
	@SuppressWarnings("unchecked")
	@Modifying
	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public void deactivateMbo() {
		Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(Client.class);
		criteria.add(Restrictions.eq("type", Client.CLIENT_TYPE_MBO));
		
		List<Client> list = criteria.list();
		for (Client client : list) {
			client.setStatus(Lookup.LOOKUP_ACTIVATION_STATUS_INACTIVE);
			client.setEnabled(0);
			client.setLoginStatus(null);//will be deleted
			client.setLogin(null);
			client.setPassword(null);
			client.setPin(null);
			client.setBroadcastStatus("UNSENT");
			client.setLoginRetry(0);
			client.setPinRetry(0);
			client.setFailTokenAttempts(0);
			client.setLastUpdatedBy(null);
			client.setLastUpdatedDate(new Date());
			
			this.saveOrUpdate(client);
			
			JobProcess job 	= new JobProcess();
			job.setStatus(JobProcess.JOB_STATUS_WAITING);
			job.setFlag(1);
			job.setDate(new Date());
			job.setRefId(client.getId());
			job.setUserType(client.getType());
			job.setName(JobProcess.JOB_REACCT_CODE);
			jobService.saveOrUpdate(job);
		}
	}

	@SuppressWarnings("unchecked")
	@Modifying
	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public void sendActCodeMbo() {
		Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(Client.class);
		criteria.add(Restrictions.eq("type", Client.CLIENT_TYPE_MBO));
		
		List<Client> list = criteria.list();
		for (Client client : list) {
			JobProcess job 	= new JobProcess();
			job.setStatus(JobProcess.JOB_STATUS_WAITING);
			job.setFlag(1);
			job.setDate(new Date());
			job.setRefId(client.getId());
			job.setUserType(client.getType());
			job.setName(JobProcess.JOB_SENT_SMS);
			jobService.saveOrUpdate(job);
		}
	}
	
	@Modifying
	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public void resetStatus(Client client, SecUser userLogin) {
		if (client != null) {
			client.setStatus(Lookup.LOOKUP_ACTIVATION_STATUS_WAIT);
			client.setLoginRetry(0);
			client.setEnabled(1);
			
			this.saveOrUpdate(client);
		}
	}
	
	@Override
	public DataTables searchByMapCriteria(DataTables dataTables, HashMap<String, Object> searchMap) {
		Long loginId 			= (Long) searchMap.get("loginId");
		String cid 				= (String) searchMap.get("cid");
		String name 			= (String) searchMap.get("name");
		String mobileNo 		= (String) searchMap.get("mobileNo");
		String type 			= (String) searchMap.get("type");
		String branch 			= (String) searchMap.get("branch");
		String unit 			= (String) searchMap.get("unit");
		String center 			= (String) searchMap.get("center");
		String smsStatus 		= (String) searchMap.get("smsStatus");
		String accStatus 		= (String) searchMap.get("accStatus");
		String enrolDateStart 	= (String) searchMap.get("enrolDateStart");
		String enrolDateEnd 	= (String) searchMap.get("enrolDateEnd");
		String approveStatus 	= (String) searchMap.get("approveStatus");
		String module			= (String) searchMap.get("module");
		String accountNumber	= (String) searchMap.get("accountNumber");
		String activatedDateStart	= (String) searchMap.get("activatedDateStart");
		String activatedDateEnd		= (String) searchMap.get("activatedDateEnd");
		
		
		Criteria criteria 		= this.getSessionFactory().getCurrentSession().createCriteria(ViewClient.class);
		criteria.createAlias("approver", "approver", JoinType.LEFT_OUTER_JOIN);
		criteria.createAlias("reApprover", "reApprover", JoinType.LEFT_OUTER_JOIN);

//		if (loginId != null) {
//			Criterion cr1 = Restrictions.and(Restrictions.isNotNull("centerCode"), Subqueries.propertyIn("centerCode", centerService.criteriaBy(loginId)));
//			Criterion cr2 = Restrictions.and(Restrictions.isNull("centerCode"), Subqueries.propertyIn("branchCode", branchService.criteriaBy(loginId)));
//			criteria.add(Restrictions.or(cr1, cr2));
//		}
		
		if (loginId != null) {
//			Criterion cr1 = Restrictions.and(Restrictions.isNotNull("centerCode"), Subqueries.propertyIn("centerCode", centerService.criteriaBy(loginId)));
			Criterion cr2 = Restrictions.and(Subqueries.propertyIn("branchCode", branchService.criteriaBy(loginId)));
			criteria.add(Restrictions.or(cr2));
		}
		
		
		if ("REGISTRATION".equals(module)) { 
			criteria.add(Restrictions.ne("accStatusCode", Lookup.LOOKUP_ACTIVATION_STATUS_ACTIVE));
		} else if ("PENDINGTASK".equals(module)) {
			String[] status = new String[] { Lookup.LOOKUP_APPROVAL_STATUS_PENDING, Lookup.LOOKUP_APPROVAL_STATUS_REJECTED };
			criteria.add(Restrictions.in("approveStatusCode", status));
			
			String[] typeUser = new String[] {"AO","UM"};
			criteria.add(Restrictions.in("typeCode", typeUser));
		}
		
		
		if (StringUtils.isNotBlank(name))
			criteria.add(Restrictions.ilike("fullname", name, MatchMode.ANYWHERE));
		
		if (StringUtils.isNotBlank(cid))
			criteria.add(Restrictions.eq("cid", cid));

		if (StringUtils.isNotBlank(mobileNo))
			criteria.add(Restrictions.eq("mobileNo", mobileNo));
		
		if (StringUtils.isNotBlank(type))
			criteria.add(Restrictions.eq("typeCode", type));
		
		if (StringUtils.isNotBlank(branch))
			criteria.add(Restrictions.eq("branchCode", branch));

		if (StringUtils.isNotBlank(unit))
			criteria.add(Restrictions.eq("unitCode", unit));

		if (StringUtils.isNotBlank(center))
			criteria.add(Restrictions.eq("centerCode", center));

		if (StringUtils.isNotBlank(smsStatus))
			criteria.add(Restrictions.eq("smsStatusCode", smsStatus));

		if (StringUtils.isNotBlank(accStatus))
			criteria.add(Restrictions.eq("accStatusCode", accStatus));

		if (StringUtils.isNotBlank(approveStatus))
			criteria.add(Restrictions.eq("approveStatusCode", approveStatus));
		
		if (StringUtils.isNotBlank(accountNumber))
			criteria.add(Restrictions.eq("accountNumber", accountNumber));
		
		
		if(StringUtils.isNotBlank(enrolDateStart)){
			try {
				Date date = Constantas.fdate.parse(enrolDateStart);
				criteria.add(Restrictions.ge("enrolled", DateUtil.truncate(date)));
			} catch (ParseException e) {
				e.printStackTrace();
			}	
		}
		
		if(StringUtils.isNotBlank(enrolDateEnd)){
			try {
				Date date = Constantas.fdate.parse(enrolDateEnd);
				criteria.add(Restrictions.le("enrolled", DateUtil.endOfDay(date)));
			} catch (ParseException e) {
				e.printStackTrace();
			}	
		}
		
		if(StringUtils.isNotBlank(activatedDateStart)){
			try {
				Date date = Constantas.fdate.parse(activatedDateStart);
				criteria.add(Restrictions.ge("activationDate", DateUtil.truncate(date)));
			} catch (ParseException e) {
				e.printStackTrace();
			}	
		}
		
		if(StringUtils.isNotBlank(activatedDateEnd)){
			try {
				Date date = Constantas.fdate.parse(activatedDateEnd);
				criteria.add(Restrictions.le("activationDate", DateUtil.endOfDay(date)));
			} catch (ParseException e) {
				e.printStackTrace();
			}	
		}

		return this.getDataTablesFromCriteria(criteria, dataTables);
	}
	
	@Override
	public DataTables searchByMapCriteriaTransactionLog(DataTables dataTables, HashMap<String, Object> searchMap) {
		try{
			System.out.println("############### M2");
			String coreId 			= (String) searchMap.get("coreId");
			String mobileId 		= (String) searchMap.get("mobileId");
			String transType 		= (String) searchMap.get("transType");
			String dateStart 		= (String) searchMap.get("dateStart");
			String dateEnd 			= (String) searchMap.get("dateEnd");
			String sourceAccount 	= (String) searchMap.get("sourceAccount");
			String targetAccount 	= (String) searchMap.get("targetAccount");
			String sourceCid 		= (String) searchMap.get("sourceCid");
			String targetCid		= (String) searchMap.get("targetCid");
			String status	 		= (String) searchMap.get("status");
			
			System.out.println("############### M3");
			
			
			Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(TransFinanceUnion.class);
			//criteria.createAlias("client", "client", JoinType.INNER_JOIN);
			//criteria.createAlias("account", "account", JoinType.LEFT_OUTER_JOIN);
			//

			System.out.println("############### M4");
			
			if (StringUtils.isNotBlank(mobileId))
				criteria.add(Restrictions.eq("transMobileRefNo", mobileId));
			
			if (StringUtils.isNotBlank(coreId))
				criteria.add(Restrictions.eq("transCoreRefNo", coreId));
	
			if (StringUtils.isNotBlank(transType))
				criteria.add(Restrictions.eq("transType", transType.trim()));
			
			System.out.println("############### M5");
			
			if(StringUtils.isNotBlank(dateStart)){
				try {
					Date date = Constantas.fdate.parse(dateStart);
					criteria.add(Restrictions.ge("transDate", DateUtil.truncate(date)));
				} catch (ParseException e) {
					e.printStackTrace();
				}	
			}
			
			if(StringUtils.isNotBlank(dateEnd)){
				try {
					Date date = Constantas.fdate.parse(dateEnd);
					criteria.add(Restrictions.le("transDate", DateUtil.endOfDay(date)));
				} catch (ParseException e) {
					e.printStackTrace();
				}	
			}
			
			System.out.println("############### M6");
			
			if (StringUtils.isNotBlank(sourceAccount))
				criteria.add(Restrictions.eq("account", sourceAccount));
	
			if (StringUtils.isNotBlank(targetAccount))
				criteria.add(Restrictions.eq("targetAccount", targetAccount));
	
			if (StringUtils.isNotBlank(sourceCid))
				criteria.add(Restrictions.eq("sourceCid", sourceCid));
	
			if (StringUtils.isNotBlank(targetCid))
				criteria.add(Restrictions.eq("targetCid", targetCid));
			
			if (StringUtils.isNotBlank(status))
				criteria.add(Restrictions.eq("status", status));
			
			// criteria.addOrder(Order.desc("transDate"));
			
			System.out.println("############### M7");

			System.out.println("############### criteria list\n" + criteria.list());
			
			return this.getDataTablesFromCriteria(criteria, dataTables);
		}catch (Exception e) {
			System.out.println("############### M8 = "+e.fillInStackTrace());
			e.printStackTrace();
			return null;
		}
	}
	
	
	@Override
	public DataTables searchByMapCriteriaMCollectionUser(DataTables dataTables, HashMap<String, Object> searchMap) {
		String customerId 			= (String) searchMap.get("customerId");
		String mobileNo 			= (String) searchMap.get("mobileNo");
		
		Criteria criteria;
		
		criteria = this.getSessionFactory().getCurrentSession().createCriteria(ViewClient.class);
		
		String[] tc = new String[]{"AO","UM"};
		criteria.add(Restrictions.in("typeCode", tc));
		if (StringUtils.isNotBlank(customerId))
			criteria.add(Restrictions.eq("cid", customerId));
		
		if (StringUtils.isNotBlank(mobileNo))
			criteria.add(Restrictions.eq("mobileNo", mobileNo));

		return this.getDataTablesFromCriteria(criteria, dataTables);
	}
	
	
	@SuppressWarnings("static-access")
	private String generateActCode(String cid, Long loginId) {
		String activationCode	= null;
		boolean logic			= true;
		try {
			while (logic) {
				activationCode = generator.generate(generator.GenerateRandomNumber(1), 6);

				if (! duplicateActivationCode(cid, activationCode)) {
					logic = false;
					
					insertHistoryActivationCode(cid, activationCode, loginId);
					
					return activationCode;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return activationCode;
	}
	
	@SuppressWarnings("rawtypes")
	private boolean duplicateActivationCode(String cid, String activationCode) {
		List list = getSessionFactory().getCurrentSession()
			.createSQLQuery("select 1 from mfs.log_activation_code where cid=:cid and act_code=:actCode")
			.setParameter("cid", cid)
			.setParameter("actCode", activationCode).list();

		if (list.isEmpty())
			return false;
		
		return true;
	}
	
	@Modifying
	@Transactional(propagation = Propagation.REQUIRED)
	private void insertHistoryActivationCode(String cid, String activationCode, Long loginId) {
		String SQL = "insert into mfs.log_activation_code(cid, act_code, created_date, created_by) values (:cid, :actCode, :createdDate, :createdBy)";
		
		this.getSessionFactory().getCurrentSession()
		.createSQLQuery(SQL)
		.setParameter("cid", cid)
		.setParameter("actCode", activationCode)
		.setParameter("createdDate", new Timestamp(System.currentTimeMillis()))
		.setParameter("createdBy", loginId)
		.executeUpdate();
	}
	
	private int getActCodeLifetime(String type){
		String configName = ParamConfig.MEMBER_ACT_CODE_LIFETIME;
		
		String value = this.getGenericService().getConfigValueByName(configName);
	    if(StringUtils.isNotBlank(value)){
	    	return Integer.valueOf(value);
	    }
		
		return 0;
	}
	
	private int getActivationLifetime(String type){
		String configName = ParamConfig.MEMBER_ACTIVATION_LIFETIME;
		if (Client.CLIENT_TYPE_AGENT.equals(type)) {
			configName = ParamConfig.AGENT_ACTIVATION_LIFETIME;
		}
		
		String value = this.getGenericService().getConfigValueByName(configName);
	    if(StringUtils.isNotBlank(value)){
	    	return Integer.valueOf(value);
	    }
		
		return 0;
	}

	@Override
	public ViewClient findByAccountAndTypeCode(String account) {
		Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(ViewClient.class);
		criteria.add(Restrictions.eq("account", account));
		String[] tc = new String[]{"AO","UM"};
		criteria.add(Restrictions.in("typeCode", tc));
		criteria.add(Restrictions.ne("approveStatusCode", Lookup.LOOKUP_APPROVAL_STATUS_REJECTED));
		
		List<ViewClient> list = criteria.list();
		if (ObjectUtil.isNotEmpty(list))
			return list.get(0);

		return null;
	}
	
	@Override
	public DataTables searchByMapCriteriaAoAccount(DataTables dataTables, HashMap<String, Object> searchMap) {
		Long loginId 			= (Long) searchMap.get("loginId");
		String cid 				= (String) searchMap.get("cid");
		String name 			= (String) searchMap.get("name");
		String mobileNo 		= (String) searchMap.get("mobileNo");
		String type 			= (String) searchMap.get("type");
		String branch 			= (String) searchMap.get("branch");
		String unit 			= (String) searchMap.get("unit");
		String center 			= (String) searchMap.get("center");
		String smsStatus 		= (String) searchMap.get("smsStatus");
		String accStatus 		= (String) searchMap.get("accStatus");
		String enrolDateStart 	= (String) searchMap.get("enrolDateStart");
		String enrolDateEnd 	= (String) searchMap.get("enrolDateEnd");
		String approveStatus 	= (String) searchMap.get("approveStatus");
		String module			= (String) searchMap.get("module");
		Criteria criteria 		= this.getSessionFactory().getCurrentSession().createCriteria(ViewClient.class);
		criteria.createAlias("approver", "approver", JoinType.LEFT_OUTER_JOIN);
		criteria.createAlias("reApprover", "reApprover", JoinType.LEFT_OUTER_JOIN);

//		if (loginId != null) {
//			Criterion cr1 = Restrictions.and(Restrictions.isNotNull("centerCode"), Subqueries.propertyIn("centerCode", centerService.criteriaBy(loginId)));
//			Criterion cr2 = Restrictions.and(Restrictions.isNull("centerCode"), Subqueries.propertyIn("branchCode", branchService.criteriaBy(loginId)));
//			criteria.add(Restrictions.or(cr1, cr2));
//		}
		
		if (loginId != null) {
//			Criterion cr1 = Restrictions.and(Restrictions.isNotNull("centerCode"), Subqueries.propertyIn("centerCode", centerService.criteriaBy(loginId)));
			Criterion cr2 = Restrictions.and(Subqueries.propertyIn("branchCode", branchService.criteriaBy(loginId)));
			criteria.add(Restrictions.or(cr2));
		}
		
		
		if ("REGISTRATION".equals(module)) { 
			criteria.add(Restrictions.ne("accStatusCode", Lookup.LOOKUP_ACTIVATION_STATUS_ACTIVE));
		} else if ("PENDINGTASK".equals(module)) {
			String[] status = new String[] { Lookup.LOOKUP_APPROVAL_STATUS_PENDING, Lookup.LOOKUP_APPROVAL_STATUS_REJECTED };
			criteria.add(Restrictions.in("approveStatusCode", status));
		}
		
		String[] typeCode = new String[] { "AO", "UM" };
		criteria.add(Restrictions.in("typeCode", typeCode));
		
		if (StringUtils.isNotBlank(name))
			criteria.add(Restrictions.ilike("fullname", name, MatchMode.ANYWHERE));
		
		if (StringUtils.isNotBlank(cid))
			criteria.add(Restrictions.eq("cid", cid));

		if (StringUtils.isNotBlank(mobileNo))
			criteria.add(Restrictions.eq("mobileNo", mobileNo));
		
		if (StringUtils.isNotBlank(type))
			criteria.add(Restrictions.eq("typeCode", type));
		
		if (StringUtils.isNotBlank(branch))
			criteria.add(Restrictions.eq("branchCode", branch));

		if (StringUtils.isNotBlank(unit))
			criteria.add(Restrictions.eq("unitCode", unit));

		if (StringUtils.isNotBlank(center))
			criteria.add(Restrictions.eq("centerCode", center));

		if (StringUtils.isNotBlank(smsStatus))
			criteria.add(Restrictions.eq("smsStatusCode", smsStatus));

		if (StringUtils.isNotBlank(accStatus))
			criteria.add(Restrictions.eq("accStatusCode", accStatus));

		if (StringUtils.isNotBlank(approveStatus))
			criteria.add(Restrictions.eq("approveStatusCode", approveStatus));
		
		if(StringUtils.isNotBlank(enrolDateStart)){
			try {
				Date date = Constantas.fdate.parse(enrolDateStart);
				criteria.add(Restrictions.ge("enrolled", DateUtil.truncate(date)));
			} catch (ParseException e) {
				e.printStackTrace();
			}	
		}
		
		if(StringUtils.isNotBlank(enrolDateEnd)){
			try {
				Date date = Constantas.fdate.parse(enrolDateEnd);
				criteria.add(Restrictions.le("enrolled", DateUtil.endOfDay(date)));
			} catch (ParseException e) {
				e.printStackTrace();
			}	
		}

		return this.getDataTablesFromCriteria(criteria, dataTables);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public ViewClient aoAccountprofile(ProfileModel model) {
		Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(ViewClient.class);
		criteria.add(Restrictions.isNotNull("registered"));

		if (model.getNumber() != null) {
			criteria.add(Restrictions.eq(model.getField(), model.getNumber()));
		} else if (StringUtils.isNotBlank(model.getString())) {
			criteria.add(Restrictions.eq(model.getField(), model.getString()));
		} else {
			criteria.add(Restrictions.eq("id", Long.valueOf(-1)));
		}

		String[] typeCode = new String[] { "AO", "UM" };
		criteria.add(Restrictions.in("typeCode", typeCode));
		
		List<ViewClient> list = criteria.list();
		if (ObjectUtil.isNotEmpty(list))
			return list.get(0);

		return null;
	}

	@Override
	public DataTables searchByMapCriteriaListClients(DataTables dataTables, HashMap<String, Object> searchMap) {
		String startDate	= (String) searchMap.get("startDate");
		String endDate		= (String) searchMap.get("endDate");
		String insti		= (String) searchMap.get("insti");
		String branch 		= (String) searchMap.get("branch");
		String unit 		= (String) searchMap.get("unit");
		String center 		= (String) searchMap.get("center");

		System.out.println("Start Date"+ startDate);
		System.out.println("End Date"+ endDate);
		System.out.println("Insti"+ insti);
		System.out.println("Branch"+ branch);
		System.out.println("Unit"+ unit);
		System.out.println("Center"+ center);

		Criteria criteria;
		
		criteria = this.getSessionFactory().getCurrentSession().createCriteria(ViewClient.class);
		
		if(StringUtils.isNotBlank(startDate)){
			try {
				Date date = Constantas.fdate.parse(startDate);
				criteria.add(Restrictions.ge("registered", DateUtil.truncate(date)));
			} catch (ParseException e) {
				e.printStackTrace();
			}	
		}
		
		if(StringUtils.isNotBlank(endDate)){
			try {
				Date date = Constantas.fdate.parse(endDate);
				criteria.add(Restrictions.le("registered", DateUtil.endOfDay(date)));
			} catch (ParseException e) {
				e.printStackTrace();
			}	
		}

		if (StringUtils.isNotBlank(insti))
			criteria.add(Restrictions.eq("instCode", insti));

		if (StringUtils.isNotBlank(branch))
			criteria.add(Restrictions.eq("branchCode", branch));

		if (StringUtils.isNotBlank(unit))
			criteria.add(Restrictions.eq("unitCode", unit));

		if (StringUtils.isNotBlank(center))
			criteria.add(Restrictions.eq("centerCode", center));

		return this.getDataTablesFromCriteria(criteria, dataTables);
	}
}