package com.valuequest.controller.customer_service;

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
import com.valuequest.entity.Concern;
import com.valuequest.entity.Lookup;
import com.valuequest.entity.security.SecUser;
     
@Controller
@RequestMapping("/cs/concern")
public class TypeOfConcernController extends BaseController {

	final static String MENU 		= "CUSTOMER_SERVICE";
	final static String PRIVILEDGE 	= "TYPE_CONCERN";
	private String BASE_VIEW 		= "06.customer-service/";
	private String LIST_VIEW 		= "concern";
	private String EDIT_VIEW 		= "concern-edit";

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
			@RequestParam(required = false) String name,
			@RequestParam(required = false) String level, 
			HttpSession session, HttpServletResponse response) {
			response.setHeader("X-Frame-Options", "DENY");

		HashMap<String, Object> searchMap = new HashMap<>();
		searchMap.put("name", name);
		searchMap.put("level", level);

		return typeOfConcernService.searchByMapCriteria(dataTables, searchMap);
	}

	@RequestMapping("/create")
	public String create(Model model, HttpSession session, HttpServletResponse response) {
		response.setHeader("X-Frame-Options", "DENY");

		if (getPriviledgeUser(session, PRIVILEDGE, NEW)) {

			putIntoRequest(model);

			return BASE_VIEW + EDIT_VIEW;
		}

		return getUnauthorizedPage();
	}

	@RequestMapping("/edit/{id}")
public String edit(@PathVariable Long id, Model model, HttpSession session, HttpServletResponse response) {
	response.setHeader("X-Frame-Options", "DENY");
    try {
        if (getPriviledgeUser(session, PRIVILEDGE, EDIT)) {
            model.addAttribute("concern", typeOfConcernService.findById(id));
            putIntoRequest(model);
            return BASE_VIEW + EDIT_VIEW;
        } else {
            return getUnauthorizedPage();
        }
    } catch (Exception e) {
      
        model.addAttribute("errorMessage", "An error occurred: " + e.getMessage());
        return "error-page"; 
    }
}

@RequestMapping(value = "/delete", method = RequestMethod.POST, headers = { "content-type=application/json" })
public @ResponseBody AjaxResponse delete(@RequestBody List<StateModel> states, HttpSession session, HttpServletResponse response) {
	response.setHeader("X-Frame-Options", "DENY");
    if (getPriviledgeUser(session, PRIVILEDGE, DELETE)) {

		typeOfConcernService.delete(states);
		
		return new AjaxResponse(true, "Type Of Concern successfully deleted.");
	} else {
	
		return new AjaxResponse(false, "You are not authorized to access this module.");
	}
	}


	@RequestMapping(value = "/save", method = RequestMethod.POST, headers = { "content-type=application/json" })
public @ResponseBody AjaxResponse save(@RequestBody Concern model, HttpSession session, HttpServletResponse response) {
	response.setHeader("X-Frame-Options", "DENY");
    try {
        typeOfConcernService.save(model, getLoginSecUser(session));
        return new AjaxResponse(model);
    } catch (Exception e) {
        e.printStackTrace(); 
        return new AjaxResponse("An error occurred while saving the concern.");
    }
}


	private void putIntoRequest(Model model) {
		model.addAttribute("SELECTED_MENU", MENU);
		model.addAttribute("SELECTED_SUBMENU", PRIVILEDGE);
		model.addAttribute("listComplexityLevel", genericService.lookup(Lookup.LOOKUP_COMPLEXITY_LEVEL));
	}
}
