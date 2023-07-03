package com.valuequest.controller.monitoring;

import java.util.HashMap;
import java.util.List;

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
 
@Controller
@RequestMapping("/monitoring/slf")
public class SlfRequestController extends BaseController {

	final static String MENU 		= "MONITORING";
	final static String PRIVILEDGE 	= "SLF_REQUEST";
	private String BASE_VIEW 		= "04.monitoring/";
	private String LIST_VIEW 		= "slf-request";

	@RequestMapping("/")
	public String index(Model model, HttpSession session) {

		if (getPriviledgeUser(session, PRIVILEDGE, VIEW)) {

			putIntoRequest(model);

			return BASE_VIEW + LIST_VIEW;
		}

		return getUnauthorizedPage();
	}

	@RequestMapping("/search")
	public @ResponseBody DataTables search(DataTables dataTables, 
			@RequestParam(required = false) String cid,
			@RequestParam(required = false) String branch, 
			@RequestParam(required = false) String transDate,
			HttpSession session) {

		HashMap<String, Object> searchMap = new HashMap<>();
		searchMap.put("loginId", getLoginIdFromSession(session));
		searchMap.put("cid", cid);
		searchMap.put("branch", branch);
		searchMap.put("transDate", transDate);

		return slfRequestService.searchByMapCriteria(dataTables, searchMap);
	}

	@RequestMapping(value = "/reject", method = RequestMethod.POST, headers = { "content-type=application/json" })
	public @ResponseBody AjaxResponse reject(@RequestBody List<StateModel> states, HttpSession session) {

		if (getPriviledgeUser(session, PRIVILEDGE, EDIT)) {

			slfRequestService.reject(states, getLoginSecUser(session));

			return new AjaxResponse(true, "SLF Request succesfully rejected.");
		} else {
			return new AjaxResponse(false, "You are not authorized to access this module.");
		}
	}

	@RequestMapping(value = "/confirm", method = RequestMethod.POST, headers = { "content-type=application/json" })
	public @ResponseBody AjaxResponse confirm(@RequestBody List<StateModel> states, HttpSession session) {

		if (getPriviledgeUser(session, PRIVILEDGE, EDIT)) {

			slfRequestService.confirm(states, getLoginSecUser(session));

			return new AjaxResponse(true, "SLF Request succesfully confirmed.");
		} else {
			return new AjaxResponse(false, "You are not authorized to access this module.");
		}
	}

	private void putIntoRequest(Model model) {
		model.addAttribute("SELECTED_MENU", MENU);
		model.addAttribute("SELECTED_SUBMENU", PRIVILEDGE);
	}
}