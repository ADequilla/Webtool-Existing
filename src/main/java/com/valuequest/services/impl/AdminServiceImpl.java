package com.valuequest.services.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.valuequest.common.DataTables;
import com.valuequest.controller.administration.model.MenuModel;
import com.valuequest.controller.administration.model.StateModel;
import com.valuequest.controller.administration.model.UserModel;
import com.valuequest.dao.AdminDao;
import com.valuequest.dao.BranchDao;
import com.valuequest.dao.CenterDao;
import com.valuequest.dao.InstitutionDao;
import com.valuequest.dao.UnitDao;
import com.valuequest.entity.Lookup;
import com.valuequest.entity.ParamConfig;
import com.valuequest.entity.UserBranch;
import com.valuequest.entity.UserCenter;
import com.valuequest.entity.UserInstitution;
import com.valuequest.entity.UserUnit;
import com.valuequest.entity.ViewUser;
import com.valuequest.entity.security.SecComponent;
import com.valuequest.entity.security.SecMenu;
import com.valuequest.entity.security.SecUser;
import com.valuequest.entity.security.SecUserrole;
import com.valuequest.services.AdminService;

@Service
@Transactional(readOnly = true)
public class AdminServiceImpl extends SimpleServiceImpl<SecUser> implements AdminService {

	@Autowired
	private AdminDao adminDao;

	@Autowired
	protected InstitutionDao institutionDao;

	@Autowired
	protected BranchDao branchDao;

	@Autowired
	protected UnitDao unitDao;

	@Autowired
	protected CenterDao centerDao;

	protected Md5PasswordEncoder encoder = new Md5PasswordEncoder();

	@Override
	public Class<SecUser> getRealClass() {
		return SecUser.class;
	}

	@Override
	public SecUser getSecUser(String username) {
		return adminDao.getSecUser(username);
	}

	@Override
	public SecUser getSecUserName(String username) {
		return adminDao.getSecUserName(username);
	}
	
	@Override
	public ViewUser getCheckStatusByName(String username) {
		return adminDao.getCheckStatusByName(username);
	}
	
	@Override
	public String encodePassword(String password) {
		return encoder.encodePassword(password, null);
	}

	@Modifying
	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public void save(UserModel model, SecUser userLogin) {
		List<Long> unchecked = new ArrayList<Long>();
		List<Long> checked = new ArrayList<Long>();
		for (StateModel stateModel : model.getStates()) {
			if (stateModel.getId() != null) {
				if ("1".equals(stateModel.getState())) {
					checked.add(stateModel.getId());
				} else if ("0".equals(stateModel.getState())) {
					unchecked.add(stateModel.getId());
				}
			}
		}

		SecUser user = null;
		if (model.getId() == null) {
			user = new SecUser();
			user.setCreatedBy(userLogin.getId());
			user.setCreatedDate(new Date(System.currentTimeMillis()));
			user.setUsrEnabled(true);
			user.setIsLogin(true);
			user.setUsrPosition(SecUser.USER_USER);
			setResetPasswordValue(user);
		} else {
			// delete any unchecked mapping for the group
			List<Long> roleIds = new ArrayList<Long>();
			roleIds.addAll(checked);
			roleIds.addAll(unchecked);
			if (roleIds.size() > 0) {
				this.getSessionFactory().getCurrentSession().createQuery("delete from SecUserrole where user.id = :userId and role.id in :roleIds")
						.setParameter("userId", model.getId()).setParameterList("roleIds", roleIds).executeUpdate();
			}
			institutionDao.deleteUserMappedBy(model.getId());
			branchDao.deleteUserMappedBy(model.getId());
			unitDao.deleteUserMappedBy(model.getId());
			centerDao.deleteUserMappedBy(model.getId());

			user = this.findById(model.getId());
			user.setLastUpdatedBy(userLogin.getId());
			user.setLastUpdatedDate(new Date(System.currentTimeMillis()));
		}
		user.setUsrLoginname(model.getLogin());
		user.setGivenName(model.getGivenName());
		user.setMiddleName(model.getMiddleName());
		user.setLastName(model.getLastName());
		user.setUsrName(user.getName());
		user.setUsrEmail(model.getEmail());
		user.setUsrPhone(model.getPhone());
		user.setUsrStatus(model.getStatus());
		user.setIsLogin(model.getLoginStat());
		user.setCheckStatus(model.getCheckStatus());

		this.saveOrUpdate(user);
		model.setId(user.getId());

		
		if (!checked.isEmpty()) {
			SecUserrole userrole = null;
			for (Long roleId : checked) {
				userrole = new SecUserrole();
				userrole.setUser(user);
				userrole.setRoleId(roleId);
				userrole.setCreatedBy(userLogin.getId());
				userrole.setCreatedDate(new Date(System.currentTimeMillis()));

				this.getSessionFactory().getCurrentSession().save(userrole);
			}
		}

		String[] institutions = model.getInstitution();
		if (institutions != null && institutions.length > 0) {
			UserInstitution userInstitution = null;
			for (String institution : institutions) {
				userInstitution = new UserInstitution();
				userInstitution.setUser(user);
				userInstitution.setInstCode(institution);

				this.getSessionFactory().getCurrentSession().save(userInstitution);
			}
		}

		String[] branchs = model.getBranch();
		if (branchs != null && branchs.length > 0) {
			UserBranch userBranch = null;
			for (String branch : branchs) {
				userBranch = new UserBranch();
				userBranch.setUser(user);
				userBranch.setBranchCode(branch);

				this.getSessionFactory().getCurrentSession().save(userBranch);
			}
		}

		String[] units = model.getUnit();
		if (units != null && units.length > 0) {
			UserUnit userUnit = null;
			for (String unit : units) {
				userUnit = new UserUnit();
				userUnit.setUser(user);
				userUnit.setUnitCode(unit);

				this.getSessionFactory().getCurrentSession().save(userUnit);
			}
		}

		String[] centers = model.getCenter();
		if (centers != null && centers.length > 0) {
			UserCenter userCenter = null;
			for (String center : centers) {
				userCenter = new UserCenter();
				userCenter.setUser(user);
				userCenter.setCenterCode(center);

				this.getSessionFactory().getCurrentSession().save(userCenter);
			}
		}
	}

	@Modifying
	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public void resetFailAttempts(SecUser userLogin) {
		if(userLogin != null){
			userLogin.setLoginAttempts(0);
			this.saveOrUpdate(userLogin);
		}
	}

	@Modifying
	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public void updateFailAttempts(SecUser userLogin, int maxRetry) {
		if(userLogin != null){
			Integer attempts = userLogin.getLoginAttempts();
			if(attempts == null){
				attempts = 0;
			}
			attempts++;
			userLogin.setLoginAttempts(attempts);
			
			if(attempts >= maxRetry){
				userLogin.setUsrStatus(Lookup.LOOKUP_USER_STATUS_LOCK);
			}
			
			this.saveOrUpdate(userLogin);
		}
	}
	
	@Modifying
	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public void updateCekStatus(SecUser userLogin, String sessionId) {
		if(userLogin != null){
			userLogin.setCheckStatus(sessionId);
			
			this.saveOrUpdate(userLogin);
		}
	}

	@Modifying
	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public void resetUserPassword(UserModel model, SecUser userLogin) {
		SecUser user = this.findById(model.getId());
		setResetPasswordValue(user);
		user.setLastUpdatedBy(userLogin.getId());
		user.setLastUpdatedDate(new Date(System.currentTimeMillis()));
		user.setUsrStatus(Lookup.LOOKUP_USER_STATUS_ACTIVE);
		user.setUsrExpiredPassword(null);
		user.setLoginAttempts(0);

		this.saveOrUpdate(user);
	}

	@Override
	public List<String> getAllRights() {
		return adminDao.getAllRights();
	}

	@Override
	public List<String> getRightsByUser(SecUser userLogin) {
		return adminDao.getRightsByUser(userLogin);
	}

	@Override
	public List<MenuModel> getAllMenuModel() {
		List<MenuModel> menuModels 	= new ArrayList<MenuModel>();
		List<SecMenu> parentMenu 	= adminDao.getAllParentMenu();
		for (SecMenu parent : parentMenu) {
			menuModels.add(generateMenuModel(parent));
		}

		return menuModels;
	}

	@Override
	public List<String> getComponent(Long userId, String priviledge) {
		return adminDao.getComponent(userId, priviledge);
	}

	@Override
	public List<SecUserrole> listSecUserrole(Long userId) {
		return adminDao.listSecUserrole(userId);
	}

	@Override
	public boolean getPriviledge(Long userId, String priviledge) {
		return adminDao.getPriviledge(userId, priviledge);
	}

	@Override
	public boolean getPriviledge(Long userId, String menu, String component) {
		return adminDao.getPriviledge(userId, menu, component);
	}

	@Override
	public DataTables searchByMapCriteria(DataTables dataTables, HashMap<String, Object> searchMap) {
		Long loginId 		= (Long) searchMap.get("loginId");
		String firstName 	= (String) searchMap.get("firstName");
		String middleName 	= (String) searchMap.get("middleName");
		String lastName 	= (String) searchMap.get("lastName");
		String userLogin 	= (String) searchMap.get("userLogin");
		String branch 		= (String) searchMap.get("branch");
		String status 		= (String) searchMap.get("status");
		Criteria criteria 	= this.getSessionFactory().getCurrentSession().createCriteria(ViewUser.class);

		if (loginId != null)
			criteria.add(Subqueries.propertyIn("id", criteriaBy(loginId)));

		if (StringUtils.isNotBlank(firstName))
			criteria.add(Restrictions.ilike("givenName", firstName, MatchMode.ANYWHERE));

		if (StringUtils.isNotBlank(middleName))
			criteria.add(Restrictions.ilike("middleName", middleName, MatchMode.ANYWHERE));

		if (StringUtils.isNotBlank(lastName))
			criteria.add(Restrictions.ilike("lastName", lastName, MatchMode.ANYWHERE));

		if (StringUtils.isNotBlank(userLogin))
			criteria.add(Restrictions.ilike("login", userLogin, MatchMode.ANYWHERE));

		if (StringUtils.isNotBlank(status))
			criteria.add(Restrictions.eq("status", status));

		if (StringUtils.isNotBlank(branch))
			criteria.add(Restrictions.ilike("branchCodes", branch, MatchMode.ANYWHERE));

		return this.getDataTablesFromCriteria(criteria, dataTables);
	}

	
	
	//-- private method
	private void setResetPasswordValue(SecUser user) {
		if (user != null) {
			user.setPasswordDefault(true);
			ParamConfig config = this.getGenericService().getConfigByName(ParamConfig.WEB_USER_DEFAULT_PASSWORD);
			if (config != null) {
				user.setUsrPassword(encodePassword(config.getValue()));
			}
			config = this.getGenericService().getConfigByName(ParamConfig.WEB_ACTIVATION_TIME_LIMIT);
			if (config != null) {
				Integer limit = Integer.valueOf(config.getValue());
				Calendar cal = Calendar.getInstance();
				cal.setTime(new Date(System.currentTimeMillis()));
				cal.add(Calendar.HOUR, limit);
				user.setActivationLimit(cal.getTime());
			}
		}
	}

	private MenuModel generateMenuModel(SecMenu menu) {
		List<SecMenu> childs 			= adminDao.getChildMenuByParent(menu);
		List<SecComponent> components 	= adminDao.getComponentByMenu(menu);
		MenuModel parentModel 			= new MenuModel();
		parentModel.setMenu(menu);
		parentModel.setComponents(components);

		if (childs.size() > 0) {
			List<MenuModel> childModelList = new ArrayList<MenuModel>();
			for (Object child : childs) {
				MenuModel childModel = generateMenuModel((SecMenu) child);
				childModelList.add(childModel);
			}
			parentModel.setChilds(childModelList);
		}

		return parentModel;
	}

	private DetachedCriteria criteriaBy(Long userId) {
		DetachedCriteria criteriaUser = DetachedCriteria.forClass(UserBranch.class);
		criteriaUser.createAlias("user", "user");
		criteriaUser.createAlias("branch", "branch");
		criteriaUser.add(Restrictions.eq("user.id", userId));
		criteriaUser.setProjection(Projections.projectionList().add(Projections.groupProperty("branch.code")));

		DetachedCriteria criteria = DetachedCriteria.forClass(UserBranch.class);
		criteria.createAlias("user", "user");
		criteria.createAlias("branch", "branch");
		criteria.add(Subqueries.propertyIn("branch.code", criteriaUser));
		criteria.setProjection(Projections.projectionList().add(Projections.groupProperty("user.id")));

		return criteria;
	}

	
}
