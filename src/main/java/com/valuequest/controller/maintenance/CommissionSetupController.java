package com.valuequest.controller.maintenance;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.valuequest.controller.maintenance.model.CommissionSetupModel;
import com.valuequest.entity.StructureCommissionSetup;
import com.valuequest.entity.security.SecUser;
import com.valuequest.services.CommissionSetupService;

@Controller
@RequestMapping("/utilities/commission-setup")
public class CommissionSetupController extends BaseController {

	final static String MENU 		= "UTILITIES";
	final static String PRIVILEDGE 	= "COMMISSION_SETUP";
	private String BASE_VIEW 		= "07.maintenance/";
	private String LIST_VIEW 		= "commissionSetup";
	private String CREATE_VIEW 		= "commissionSetup-create";
	private String EDIT_VIEW 		= "commissionSetup-edit";
	
	
	@Autowired
	private CommissionSetupService commissionSetupService;

	@RequestMapping("/")
	public String index(Model model, HttpSession session, HttpServletResponse response) {
		response.setHeader("X-Frame-Options", "DENY");

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
			@RequestParam(required = false) String transType,
			@RequestParam(required = false) String commissionType, 
			HttpSession session, HttpServletResponse response) {
			response.setHeader("X-Frame-Options", "DENY");

			HashMap<String, Object> searchMap = new HashMap<>();
			searchMap.put("transType", transType);
			searchMap.put("commissionType", commissionType);
	
			return commissionSetupService.searchByMapCriteria(dataTables, searchMap);
	}

	@RequestMapping("/create")
	public String create(Model model, HttpSession session, HttpServletResponse response) {
		response.setHeader("X-Frame-Options", "DENY");

		if (getPriviledgeUser(session, PRIVILEDGE, NEW)) {
			
			model.addAttribute("isNew", true);
			putIntoRequest(model);
			
			return BASE_VIEW + CREATE_VIEW;
		}

		return getUnauthorizedPage();
	}

	@RequestMapping("/edit/{id}")
	public String edit(@PathVariable Long id, Model model, HttpSession session, HttpServletResponse response) {
		response.setHeader("X-Frame-Options", "DENY");

		if (getPriviledgeUser(session, PRIVILEDGE, EDIT)) {
			model.addAttribute("commissionSetup", commissionSetupService.findById(id));
			model.addAttribute("isNew", false);
			model.addAttribute("id", id);
			putIntoRequest(model);

			return BASE_VIEW + EDIT_VIEW;
		}

		return getUnauthorizedPage();
	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST, headers = { "content-type=application/json" })
	public @ResponseBody AjaxResponse delete(@RequestBody List<CommissionSetupModel> states, HttpSession session, HttpServletResponse response) {
		response.setHeader("X-Frame-Options", "DENY");

		if (getPriviledgeUser(session, PRIVILEDGE, DELETE)) {

			commissionSetupService.delete(states);

			return new AjaxResponse(true, "Commission Setup succesfully deleted.");
		} else {

			return new AjaxResponse(false, "You are not authorized to access this module.");
		}
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST, headers = { "content-type=application/json" })
	public @ResponseBody AjaxResponse save(@RequestBody CommissionSetupModel model, HttpSession session, HttpServletResponse response) {
		response.setHeader("X-Frame-Options", "DENY");

		if (model.getIsNew()) {
			if (commissionSetupService.isExist(model.getId())) {
				
				return new AjaxResponse(false, "Commission Code Duplicate.");
			}
		}

		commissionSetupService.save(model, getLoginSecUser(session));

		return new AjaxResponse(model);
	}

	private void putIntoRequest(Model model) {
		model.addAttribute("SELECTED_MENU", MENU);
		model.addAttribute("SELECTED_SUBMENU", PRIVILEDGE);
		List<StructureCommissionSetup> listCommissionSetup = commissionSetupService.getAll();
		model.addAttribute("listCommissionSetup", listCommissionSetup);
	}
}
