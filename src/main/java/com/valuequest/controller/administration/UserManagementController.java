package com.valuequest.controller.administration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.valuequest.common.AjaxResponse;
import com.valuequest.common.DataTables;
import com.valuequest.controller.BaseController;
import com.valuequest.controller.administration.model.UserModel;
import com.valuequest.entity.Client;
import com.valuequest.entity.Lookup;
import com.valuequest.entity.SmsLogs;
import com.valuequest.entity.StructureBranch;
import com.valuequest.entity.StructureCenter;
import com.valuequest.entity.StructureInstitution;
import com.valuequest.entity.StructureUnit;
import com.valuequest.entity.StructureUser;
import com.valuequest.entity.UserInstitution;
import com.valuequest.entity.security.SecUser;
import com.valuequest.services.SmsLogsService;
import com.valuequest.services.impl.SimpleServiceImpl;

@Controller
@RequestMapping("/administration/user")
public class UserManagementController extends BaseController {

	List<String> instiList = new ArrayList<String>();
  
	
	protected SecUser getLoginSecUser(HttpSession session) {
		return (SecUser) session.getAttribute("loginSecUser");
	}
	
	private class SimpleServ extends SimpleServiceImpl<StructureUser> implements SmsLogsService{

		
		
		private DetachedCriteria criteriaBy(Long userId) {
			DetachedCriteria criteria = DetachedCriteria.forClass(UserInstitution.class);
			criteria.createAlias("user", "user");
			criteria.createAlias("institution", "institution");
			criteria.add(Restrictions.eq("user.id", userId));
			criteria.setProjection(Projections.projectionList().add(Projections.groupProperty("institution.code")));

			return criteria;
		}
		

		public void getUserInsti(Long userId) {
			
			Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(StructureInstitution.class);
			criteria.add(Subqueries.propertyIn("code", criteriaBy(userId)));
			List r = criteria.list();
			for (Iterator iterator = r.iterator(); iterator.hasNext();){
				StructureInstitution insti = (StructureInstitution) iterator.next();		
	            instiList.add(insti.getCode());
			}
			
			System.out.println("#####Arrray of Insti#####\n "+  instiList.toString());
			
		}


		@Override
		public DataTables searchByMapCriteria(DataTables dataTables, HashMap<String, Object> searchMap,
				HttpSession session) {
			// TODO Auto-generated method stub
			return null;
		}


		@Override
		public Class<StructureUser> getRealClass() {
			// TODO Auto-generated method stub
			return null;
		}


		@Override
		public DataTables searchByMapCriteria(DataTables dataTables, HashMap<String, Object> searchMap) {
			// TODO Auto-generated method stub
			return null;
		}
		
	
	}

	final static String MENU 		= "ADMINISTRATION";
	final static String PRIVILEDGE 	= "USER_MANAGEMENT";
	private String BASE_VIEW 		= "02.administration/";
	private String LIST_VIEW 		= "user";
	private String EDIT_VIEW		= "user-edit";
	
	@RequestMapping("/")
	public String index(Model model, HttpSession session) {

		SecUser user = this.getLoginSecUser(session);

        user.setIsLogin(true);
        adminService.updateCekStatus(user, session.getId());

		if (getPriviledgeUser(session, PRIVILEDGE, VIEW)) {
			
			putIntoRequest(model);
			
			return BASE_VIEW + LIST_VIEW; 
		}

		return getUnauthorizedPage();
	}

	@RequestMapping("/search")
	public @ResponseBody DataTables search(DataTables dataTables, 
			@RequestParam(required = false) String firstName,
			@RequestParam(required = false) String middleName,
			@RequestParam(required = false) String lastName,
			@RequestParam(required = false) String userLogin,
			@RequestParam(required = false) String branch,
			@RequestParam(required = false) String status,
			HttpSession session) {

		HashMap<String, Object> searchMap = new HashMap<>();
		searchMap.put("loginId",	getLoginIdFromSession(session));
		searchMap.put("firstName", 	firstName);
		searchMap.put("middleName", middleName);
		searchMap.put("lastName", 	lastName);
		searchMap.put("userLogin", 	userLogin);
		searchMap.put("branch", 	branch);
		searchMap.put("status", 	status);

		return adminService.searchByMapCriteria(dataTables, searchMap);
	}

	@RequestMapping("/create")
	public String create(Model model, HttpSession session) {
		
		if (getPriviledgeUser(session, PRIVILEDGE, NEW)) {
			
			model.addAttribute("listRole", roleService.list());
			model.addAttribute("availableInstitutionList", institutionService.mappedList());
			putIntoRequest(model);
			
			return BASE_VIEW + EDIT_VIEW;
		}
		  
		return getUnauthorizedPage();
	}

	@RequestMapping("/edit/{id}")
	public String edit(@PathVariable Long id, Model model, HttpSession session) {
		
		if (getPriviledgeUser(session, PRIVILEDGE, EDIT)) {
			
			List<StructureInstitution> institutionList	= institutionService.mappedListBy(id);
			List<StructureBranch> branchList			= branchService.mappedListBy(id);
			/*List<StructureUnit> unitList				= unitService.mappedListBy(id);
			List<StructureCenter> centerList			= centerService.mappedUserBy(id);*/

			model.addAttribute("user", adminService.findById(id));
			model.addAttribute("listUserrole", adminService.listSecUserrole(id));
			model.addAttribute("listRole", roleService.list());
			model.addAttribute("selectedInstitutionList", institutionList);
			model.addAttribute("selectedBranchList", branchList);
			/*model.addAttribute("selectedUnitList", unitList);
			model.addAttribute("selectedCenterList", centerList);*/
			model.addAttribute("availableInstitutionList", institutionService.mappedList());
			
			if(! institutionList.isEmpty()) {
				String[] institutions	= new String[institutionList.size()];
				for (StructureInstitution institution : institutionList) {
					institutions[institutionList.indexOf(institution)]	= institution.getCode();
				}
				
				model.addAttribute("availableBranchList", branchService.mappedListBy(institutions));
			}
			
			if(! branchList.isEmpty()) {
				String[] branchs	= new String[branchList.size()];
				for (StructureBranch branch : branchList) {
					branchs[branchList.indexOf(branch)]	= branch.getCode();
				}
				
				System.out.println("unitService.mappedListBy(branchs) = "+unitService.mappedListBy(branchs));;
				model.addAttribute("availableUnitList", unitService.mappedListBy(branchs));
			}
			
//			if(! unitList.isEmpty()) {
//				String[] units	= new String[unitList.size()];
//				for (StructureUnit unit : unitList) {
//					units[unitList.indexOf(unit)]	= unit.getCode();
//				}
//				
//				System.out.println("centerService.mappedListBy(units) = "+centerService.mappedListBy(units));;
//				model.addAttribute("availableCenterList", centerService.mappedListBy(units));
//			}
			
			putIntoRequest(model);

			return BASE_VIEW + EDIT_VIEW;
		}

		return getUnauthorizedPage();
	}
	
	@RequestMapping(value = "/save", method = RequestMethod.POST, headers = { "content-type=application/json" })
	public @ResponseBody AjaxResponse save(@RequestBody UserModel model, HttpSession session) {

		SecUser findUser = adminService.getSecUser(model.getLogin());
		if ((model.getId() == null && findUser != null) || (findUser != null && ! model.getId().equals(findUser.getId())))
			return new AjaxResponse(false, "Username already exist.");

		adminService.save(model, getLoginSecUser(session));

		return new AjaxResponse(model);
	}
	
	@RequestMapping(value = "/resetPassword", method = RequestMethod.POST, headers = { "content-type=application/json" })
	public @ResponseBody AjaxResponse reset(@RequestBody UserModel model, HttpSession session) {
		
		adminService.resetUserPassword(model, getLoginSecUser(session));
		
		return new AjaxResponse(true, "Password successfully reset.", model);
	}
	
	private void putIntoRequest(Model model) {
		model.addAttribute("SELECTED_MENU", MENU);
		model.addAttribute("SELECTED_SUBMENU", PRIVILEDGE);
		model.addAttribute("listUserStatus", genericService.lookup(Lookup.LOOKUP_USER_STATUS));
		model.addAttribute("listConcurrent", genericService.lookup(Lookup.LOOKUP_CONCURRENT_USER));
	}
	
	@SuppressWarnings("rawtypes")
	@RequestMapping("/getBranch/{institution}")
	public @ResponseBody List getBranch(@PathVariable String[] institution) {
		return branchService.mappedListBy(institution);
	}
	
	@RequestMapping("/getBranchDesc/{branch}")
	public @ResponseBody StructureBranch getBranch(@PathVariable String branch) {
		return branchService.findByCode(branch);
	}
	
	
	@SuppressWarnings("rawtypes")
	@RequestMapping("/getUnit/{branch}")
	public @ResponseBody List getUnit(@PathVariable String[] branch) {
		return unitService.mappedListBy(branch);
	}
	
	@RequestMapping("/getUnitDesc/{unit}")
	public @ResponseBody StructureUnit getUnit(@PathVariable String unit) {
		return unitService.findByCode(unit);
	}
	
	@SuppressWarnings("rawtypes")
	@RequestMapping("/getCenter/{unit}")
	public @ResponseBody List getCenter(@PathVariable String[] unit) {
		return centerService.mappedListBy(unit);
	}
	
	@RequestMapping("/getCenterDesc/{center}")
	public @ResponseBody StructureCenter getCenter(@PathVariable String center) {
		return centerService.findByCode(center);
	}
	
	@RequestMapping("/getInstitutionDesc/{insti}")
	public @ResponseBody StructureInstitution getInsti(@PathVariable String insti) {
		return institutionService.findByCode(insti);
	}
}
