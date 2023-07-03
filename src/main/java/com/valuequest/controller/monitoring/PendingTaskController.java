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
import com.valuequest.entity.Lookup;

@Controller
@RequestMapping("/monitoring/task")
public class PendingTaskController extends BaseController {

	final static String MENU 		= "MONITORING";
	final static String PRIVILEDGE 	= "PENDING_TASK";
	private String BASE_VIEW 		= "04.monitoring/";
	private String LIST_VIEW 		= "pending-task";

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
			@RequestParam(required = false) String name,
			@RequestParam(required = false) String mobileNo,
			@RequestParam(required = false) String clientType,
			@RequestParam(required = false) String enrolDateStart,
			@RequestParam(required = false) String enrolDateEnd,
			@RequestParam(required = false) String branch, 
			@RequestParam(required = false) String unit,
			@RequestParam(required = false) String center, 
			@RequestParam(required = false) String approvalStatus,
			HttpSession session) {

		HashMap<String, Object> searchMap = new HashMap<>();
		searchMap.put("loginId", getLoginIdFromSession(session));
		searchMap.put("cid", cid);
		searchMap.put("name", name);
		searchMap.put("mobileNo", mobileNo);
		searchMap.put("type", clientType);
		searchMap.put("enrolDateStart", enrolDateStart);
		searchMap.put("enrolDateEnd", enrolDateEnd);
		searchMap.put("branch", branch);
		searchMap.put("unit", unit);
		searchMap.put("center", center);
		searchMap.put("approveStatus", approvalStatus);

		searchMap.put("module", "PENDINGTASK");
		
		return clientService.searchByMapCriteria(dataTables, searchMap);
	}

	@RequestMapping(value = "/approve", method = RequestMethod.POST, headers = { "content-type=application/json" })
	public @ResponseBody AjaxResponse approve(@RequestBody List<StateModel> states, HttpSession session) {

		if (getPriviledgeUser(session, PRIVILEDGE, EDIT)) {

			clientService.update(states, Lookup.LOOKUP_APPROVAL_STATUS_APPROVED, getLoginSecUser(session));

			return new AjaxResponse(true, "Pending Task succesfully approved.");
		} else {
			return new AjaxResponse(false, "You are not authorized to access this module.");
		}
	}

	@RequestMapping(value = "/reject", method = RequestMethod.POST, headers = { "content-type=application/json" })
	public @ResponseBody AjaxResponse reject(@RequestBody List<StateModel> states, HttpSession session) {

		if (getPriviledgeUser(session, PRIVILEDGE, EDIT)) {

			clientService.update(states, Lookup.LOOKUP_APPROVAL_STATUS_REJECTED, getLoginSecUser(session));

			return new AjaxResponse(true, "Pending Task succesfully rejected.");
		} else {
			return new AjaxResponse(false, "You are not authorized to access this module.");
		}
	}

	private void putIntoRequest(Model model) {
		model.addAttribute("SELECTED_MENU", MENU);
		model.addAttribute("SELECTED_SUBMENU", PRIVILEDGE);
		model.addAttribute("listClientType", genericService.lookup(Lookup.LOOKUP_CLIENT_TYPE));
		model.addAttribute("listApprovalStatus", genericService.lookup(Lookup.LOOKUP_APPROVAL_STATUS));
	}
}