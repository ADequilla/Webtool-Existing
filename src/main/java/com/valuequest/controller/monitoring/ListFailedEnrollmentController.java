package com.valuequest.controller.monitoring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.valuequest.common.DataTables;
import com.valuequest.controller.BaseController;
import com.valuequest.entity.Lookup;
import com.valuequest.entity.security.SecUser;

@Controller
@RequestMapping("/monitoring/failed-enrollment-list")
public class ListFailedEnrollmentController extends BaseController {

	final static String MENU 		= "MONITORING";
	final static String PRIVILEDGE 	= "FAILED_ENROLLMENT_LIST";
	private String BASE_VIEW 		= "04.monitoring/";
	private String LIST_VIEW 		= "failed-enrollment";

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
			@RequestParam(required = false) String searchDateStart,
			@RequestParam(required = false) String searchDateEnd,
			@RequestParam(required = false) String accountNumber,
			@RequestParam(required = false) String dateBirth,
			@RequestParam(required = false) String mobileNumber,
			@RequestParam(required = false) String clientType,
			@RequestParam(required = false) String errorMessage,
			HttpSession session, HttpServletResponse response) {
			response.setHeader("X-Frame-Options", "DENY");
		HashMap<String, Object> searchMap = new HashMap<>();
		searchMap.put("searchDateStart", searchDateStart);
		searchMap.put("searchDateEnd", searchDateEnd);
		searchMap.put("accountNumber", accountNumber);
		searchMap.put("dateBirth", dateBirth);
		searchMap.put("mobileNumber", mobileNumber);
		searchMap.put("clientType", clientType);
		searchMap.put("errorMessage", errorMessage);

		return failedEnrollmentService.searchByMapCriteria(dataTables, searchMap);
	}

	private void putIntoRequest(Model model) {
		model.addAttribute("SELECTED_MENU", MENU);
		model.addAttribute("SELECTED_SUBMENU", PRIVILEDGE);	
		model.addAttribute("listClientType", genericService.lookup(Lookup.LOOKUP_CLIENT_TYPE));
	}
}