package com.valuequest.controller.maintenance;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
import com.valuequest.controller.administration.model.StateModel;
import com.valuequest.controller.maintenance.model.UnitModel;
import com.valuequest.entity.security.SecUser;

@Controller
@RequestMapping("/utilities/unit")
public class UnitController extends BaseController {

	final static String MENU 		= "UTILITIES";
	final static String PRIVILEDGE 	= "UNIT";
	private String BASE_VIEW 		= "07.maintenance/";
	private String LIST_VIEW 		= "unit";
	private String CREATE_VIEW 		= "unit-create";

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
			@RequestParam(required = false) String code,
			@RequestParam(required = false) String description, 
			HttpSession session, HttpServletResponse response) {
			response.setHeader("X-Frame-Options", "DENY");

		HashMap<String, Object> searchMap = new HashMap<>();
		searchMap.put("code", code);
		searchMap.put("description", description);

		return unitService.searchByMapCriteria(dataTables, searchMap);
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

	@RequestMapping("/edit/{code}")
	public String edit(@PathVariable String code, Model model, HttpSession session, HttpServletResponse response) {
		response.setHeader("X-Frame-Options", "DENY");

		if (getPriviledgeUser(session, PRIVILEDGE, EDIT)) {

			model.addAttribute("unit", unitService.findByCode(code));
			model.addAttribute("isNew", false);
			putIntoRequest(model);

			return BASE_VIEW + CREATE_VIEW;
		}

		return getUnauthorizedPage();
	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST, headers = { "content-type=application/json" })
	public @ResponseBody AjaxResponse delete(@RequestBody List<StateModel> states, HttpSession session, HttpServletResponse response) {
		response.setHeader("X-Frame-Options", "DENY");

		if (getPriviledgeUser(session, PRIVILEDGE, DELETE)) {

			unitService.delete(states);

			return new AjaxResponse(true, "Unit succesfully deleted.");
		} else {

			return new AjaxResponse(false, "You are not authorized to access this module.");
		}
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST, headers = { "content-type=application/json" })
	public @ResponseBody AjaxResponse save(@RequestBody UnitModel model, HttpSession session, HttpServletResponse response) {
		response.setHeader("X-Frame-Options", "DENY");

		if (model.getIsNew()) {
			if (unitService.isExist(model.getCode())) {
				
				return new AjaxResponse(false, "Unit Code Duplicate.");
			}
		}

		unitService.save(model, getLoginSecUser(session));

		return new AjaxResponse(model);
	}

	private void putIntoRequest(Model model) {
		model.addAttribute("SELECTED_MENU", MENU);
		model.addAttribute("SELECTED_SUBMENU", PRIVILEDGE);
	}
}
