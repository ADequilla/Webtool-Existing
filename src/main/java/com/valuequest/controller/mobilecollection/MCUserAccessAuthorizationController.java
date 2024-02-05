/**
 * 
 */
package com.valuequest.controller.mobilecollection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.valuequest.common.AjaxResponse;
import com.valuequest.common.DataTables;
import com.valuequest.controller.BaseController;
import com.valuequest.controller.administration.model.StateModel;
import com.valuequest.controller.mobilecollection.model.McUserModel;
import com.valuequest.entity.Lookup;
import com.valuequest.entity.MCUser;
import com.valuequest.entity.StructureBranch;
import com.valuequest.entity.StructureUnit;
import com.valuequest.entity.security.SecUser;
import com.valuequest.util.PojoJsonMapper;
import com.valuequest.util.StatusConstantas;

/**
 * @author Eki Nurhadi
 *
 */
@Controller
@RequestMapping("/mobilecollection/userauthorization")
public class MCUserAccessAuthorizationController extends BaseController {
	final static String MENU 		= "MOBILECOLLECTION";
	final static String PRIVILEDGE 	= "MCUSER_AUTHORIZATION";
	private String BASE_VIEW 		= "11.mobilecollection/";
	private String LIST_VIEW 		= "mcuserauthorization";
	
	
	@RequestMapping("/")
	public String index(Model model, HttpSession session, HttpServletResponse response) {
		response.setHeader("X-Frame-Options", "DENY");
		 SecUser user = this.getLoginSecUser(session);

        user.setIsLogin(true);
        adminService.updateCekStatus(user, session.getId());

		if (getPriviledgeUser(session, PRIVILEDGE, VIEW)) {
			
			model.addAttribute("availableBranchList", branchService.mappedList());
			putIntoRequest(model);
			
			return BASE_VIEW + LIST_VIEW;
		}

		return getUnauthorizedPage();
	}

	@RequestMapping("/search")
	public @ResponseBody DataTables search(DataTables dataTables, 
			@RequestParam(required = false) String mcuId,
			@RequestParam(required = false) String staffId,
			@RequestParam(required = false) String mobileNumber,
			@RequestParam(required = false) String internalAccount,
			@RequestParam(required = false) String branchCode,
			@RequestParam(required = false) String unitCode,
			@RequestParam(required = false) String designation,
			@RequestParam(required = false) String aoStatus,
			@RequestParam(required = false) String dateStart,
			@RequestParam(required = false) String dateEnd,
			HttpSession session, HttpServletResponse response) {
				response.setHeader("X-Frame-Options", "DENY");
		HashMap<String, Object> searchMap = new HashMap<>();
		searchMap.put("mcuId",	mcuId);
		searchMap.put("staffId", 	staffId);
		searchMap.put("mobileNumber", mobileNumber);
		searchMap.put("internalAccount", 	internalAccount);
		searchMap.put("branchCode", 	branchCode);
		searchMap.put("unitCode", 	unitCode);
		searchMap.put("designation", 	designation);
		searchMap.put("aoStatus", 	aoStatus);
		searchMap.put("dateStart", 	dateStart);
		searchMap.put("dateEnd", 	dateEnd);
		
		DataTables searchByMapCriteria = mcUserService.searchByMapCriteria(dataTables, searchMap, true);
		List<MCUser> mcUsers = searchByMapCriteria.getAaData();
		List<MCUser> modifyData = new ArrayList<>();
		for (MCUser t : mcUsers) {
			if(t.getFreeData() != null) {
				McUserModel fromJson = PojoJsonMapper.fromJson(t.getFreeData(), McUserModel.class);
				t.setStaffId(fromJson.getStaffId());
				t.setGivenName(fromJson.getGivenName());
				t.setMiddleName(fromJson.getMiddleName());
				t.setSurname(fromJson.getSurname());
				t.setDesignation(fromJson.getDesignation());
				t.setInternalAccount(fromJson.getInternalAccount());
				t.setEmail(fromJson.getEmail());
				t.setMobileNumber(fromJson.getMobileNumber());
				
				String[] branchs = fromJson.getBranch();
				if (branchs != null && branchs.length > 0) {
					StructureBranch structureBranch = null;
					for (String branch : branchs) {
						structureBranch = new StructureBranch();
						structureBranch.setCode(branch);
						StructureBranch findDesc = branchService.findByCode(branch);
						structureBranch.setDescription(findDesc.getDescription());
						t.setBranch(structureBranch);
					}
				}

				String[] units = fromJson.getUnit();
				if (units != null && units.length > 0) {
					StructureUnit structureUnit = null;
					for (String unit : units) {
						structureUnit = new StructureUnit();
						structureUnit.setCode(unit);
						StructureUnit findDesc = unitService.findByCode(unit);
						structureUnit.setDescription(findDesc.getDescription());
						t.setUnit(structureUnit);
					}
				}
				
			}
			modifyData.add(t);
		}
		searchByMapCriteria.getAaData().clear();
		searchByMapCriteria.getAaData().addAll(modifyData);
		return searchByMapCriteria;
	}
	
	@RequestMapping(value = "/approve", method = RequestMethod.POST, headers = { "content-type=application/json" })
	public @ResponseBody AjaxResponse approve(@RequestBody List<StateModel> states, HttpSession session, HttpServletResponse response) {
		response.setHeader("X-Frame-Options", "DENY");
		if (getPriviledgeUser(session, PRIVILEDGE, APPROVE)) {
			Boolean isValid = Boolean.TRUE;
			for (StateModel stateModel : states) {
				MCUser mcUser = mcUserService.findById(stateModel.getId());

				if (mcUser.getUpdatedBy() == null) {
					if(mcUser.getCreatedBy().getId().equals(getLoginSecUser(session).getId())) {
						isValid = Boolean.FALSE;
						break;
					}
				} else {
					if(mcUser.getUpdatedBy().getId().equals(getLoginSecUser(session).getId())) {
						isValid = Boolean.FALSE;
						break;
					}
				}
			}
			
			if(isValid) {
				mcUserService.update(states, StatusConstantas.APPROVED, getLoginSecUser(session));
				return new AjaxResponse(true, "MC User Access Authorization succesfully approved.");
			}else {
				return new AjaxResponse(false, "You are not allow to approve your own changes or created account.");
			}
			
		} else {
			return new AjaxResponse(false, "You are not authorized to access this module.");
		}
	}

	@RequestMapping(value = "/reject", method = RequestMethod.POST, headers = { "content-type=application/json" })
	public @ResponseBody AjaxResponse reject(@RequestBody List<StateModel> states, HttpSession session, HttpServletResponse response) {
		response.setHeader("X-Frame-Options", "DENY");
		if (getPriviledgeUser(session, PRIVILEDGE, REJECT)) {
			
			Boolean isValid = Boolean.TRUE;
			for (StateModel stateModel : states) {
				MCUser mcUser = mcUserService.findById(stateModel.getId());
			
				if (mcUser.getUpdatedBy() == null) {
					if(mcUser.getCreatedBy().getId().equals(getLoginSecUser(session).getId())) {
						isValid = Boolean.FALSE;
						break;
					}
				} else {
					if(mcUser.getUpdatedBy().getId().equals(getLoginSecUser(session).getId())) {
						isValid = Boolean.FALSE;
						break;
					}
				}
				
			}
			
			if(isValid) {
				
			mcUserService.update(states, StatusConstantas.REJECTED, getLoginSecUser(session));
			return new AjaxResponse(true, "MC User Access Authorization succesfully rejected.");
			
			}else {
				return new AjaxResponse(false, "You are not allow to reject your own changes or created account.");
			}
		} else {
			return new AjaxResponse(false, "You are not authorized to access this module.");
		}
	}
	
	private void putIntoRequest(Model model) {
		model.addAttribute("SELECTED_MENU", MENU);
		model.addAttribute("SELECTED_SUBMENU", PRIVILEDGE);
		model.addAttribute("listMCUserStatus", genericService.lookup(Lookup.LOOKUP_MUSER_STATUS));
		model.addAttribute("listDesignation", genericService.lookup(Lookup.LOOKUP_DESIGNATION));
		model.addAttribute("listFileType", genericService.lookup(Lookup.LOOKUP_REPORT_TYPE));
	}
}
