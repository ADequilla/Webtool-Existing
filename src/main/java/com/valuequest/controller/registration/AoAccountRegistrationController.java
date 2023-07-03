package com.valuequest.controller.registration;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.valuequest.common.AjaxResponse;
import com.valuequest.common.DataTables;
import com.valuequest.controller.BaseController;
import com.valuequest.entity.Client;
import com.valuequest.entity.Lookup;

@Controller
@RequestMapping("/registration/ao-account-registration")
public class AoAccountRegistrationController extends BaseController {

	final static String MENU 		= "REGISTRATION";
	final static String PRIVILEDGE 	= "AO_ACCOUNT_REGISTRATION";
	private String BASE_VIEW 		= "03.registration/";
	private String LIST_VIEW 		= "ao-account-registration-list";

	private String C_RESEND_ACTCODE = "RESEND ACTIVATION CODE";
	private String C_RESET_ACTCODE 	= "RESET ACTIVATION CODE";
	
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
			@RequestParam(required = false) String unit,
			@RequestParam(required = false) String center, 
			@RequestParam(required = false) String accStatus,
			@RequestParam(required = false) String smsStatus, 
			HttpSession session) {

		HashMap<String, Object> searchMap = new HashMap<>();
		searchMap.put("loginId", getLoginIdFromSession(session));
		searchMap.put("cid", cid);
		searchMap.put("branch", branch);
		searchMap.put("unit", unit);
		searchMap.put("center", center);
		searchMap.put("smsStatus", smsStatus);
		searchMap.put("accStatus", accStatus);

		searchMap.put("approveStatus", Lookup.LOOKUP_APPROVAL_STATUS_APPROVED);
		searchMap.put("module", "REGISTRATION");

		return clientService.searchByMapCriteriaAoAccount(dataTables, searchMap);
	}

	@RequestMapping("/resend/{id}")
	public @ResponseBody AjaxResponse resend(@PathVariable Long id, HttpServletRequest request, HttpSession session) {

		if (getPriviledgeUser(session, PRIVILEDGE, C_RESEND_ACTCODE)) {
			Client client 	= clientService.findById(id);
			String data		= getXMLValue(client);
			
			clientService.resendActcode(client, getLoginSecUser(session));
			
			// save to audit trail
			String activity = "SMS_RESEND";
			long moduleId 	= 2005;
			auditTrailService.save(request, moduleId, activity, data, data, client.getCid(), getLoginSecUser(session));

			return new AjaxResponse(true, "Send SMS success.");
		} else {

			return new AjaxResponse(false, "You are not authorized to access this module.");
		}
	}
	
	@RequestMapping("/reset/{id}")
	public @ResponseBody AjaxResponse reset(@PathVariable Long id, HttpServletRequest request, HttpSession session) {

		if (getPriviledgeUser(session, PRIVILEDGE, C_RESET_ACTCODE)) {
			Client client 	= clientService.findById(id);
			String data		= getXMLValue(client);
			
			clientService.resetStatus(client, getLoginSecUser(session));
			
			// save to audit trail
			String activity = "RESET_STATUS";
			long moduleId 	= 2011;
			auditTrailService.save(request, moduleId, activity, data, data, client.getCid(), getLoginSecUser(session));

			return new AjaxResponse(true, "Reset Status success.");
		} else {

			return new AjaxResponse(false, "You are not authorized to access this module.");
		}
	}
	
	private void putIntoRequest(Model model) {
		model.addAttribute("SELECTED_MENU", MENU);
		model.addAttribute("SELECTED_SUBMENU", PRIVILEDGE);
		model.addAttribute("listAccountStatus", genericService.lookup(Lookup.LOOKUP_ACTIVATION_STATUS));
		model.addAttribute("listSmsStatus", genericService.lookup(Lookup.LOOKUP_SMS_STATUS));
	}
}