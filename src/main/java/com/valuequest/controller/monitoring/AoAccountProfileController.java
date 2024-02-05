package com.valuequest.controller.monitoring;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
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
import com.valuequest.controller.BaseController;
import com.valuequest.controller.monitoring.model.ProfileModel;
import com.valuequest.entity.Client;
import com.valuequest.entity.ViewClient;
import com.valuequest.entity.security.SecUser;

@Controller
@RequestMapping("/monitoring/ao-account-profile")
public class AoAccountProfileController extends BaseController {

	final static String MENU 		= "MONITORING";
	final static String PRIVILEDGE 	= "AO_ACCOUNT_PROFILE";
	private String BASE_VIEW 		= "04.monitoring/";
	private String LIST_VIEW 		= "client-profile";

	private String C_RESET_PASSWD 	= "RESET PASSWORD";
	private String C_RESET_PIN 		= "RESET PIN";
	private String C_DEACTIVATE 	= "DEACTIVATE";
	private String C_RESTRICT 		= "RESTRICT";
	private String C_VIEW_USERNAME 	= "USERNAME";
	private String C_AGENT_FEATURE 	= "AGENT_FEATURE";

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

	@SuppressWarnings("rawtypes")
	@RequestMapping("/names")
	public @ResponseBody List menuList(@RequestParam(required = false) String name, @RequestParam(required = false) int size) {

		return clientService.list(name, size);
	}

	@RequestMapping(value = "/get", method = RequestMethod.POST, headers = { "content-type=application/json" })
	public @ResponseBody AjaxResponse get(@RequestBody ProfileModel model, HttpSession session, HttpServletResponse response) {
		response.setHeader("X-Frame-Options", "DENY");
		ViewClient view = clientService.aoAccountprofile(model);
		if (view == null) {
			return new AjaxResponse(false, "Client not found.");
		}

		return new AjaxResponse(view);
	}

	@RequestMapping("/resetPassword/{id}")
	public @ResponseBody AjaxResponse resetPassword(@PathVariable Long id, HttpServletRequest request, HttpSession session, HttpServletResponse response) {
		response.setHeader("X-Frame-Options", "DENY");
		if (getPriviledgeUser(session, PRIVILEDGE, C_RESET_PASSWD)) {
			Client client 		= clientService.findById(id);
			String dataBefore 	= getXMLValue(client);
			clientService.resetPassword(client, getLoginSecUser(session));
			String dataAfter 	= getXMLValue(clientService.findById(id));
			
			// save to audit trail
			String activity 	= "PASSWD_RESET";
			long moduleId 		= 2006;
			auditTrailService.save(request, moduleId, activity, dataBefore, dataAfter, client.getCid(), getLoginSecUser(session));

			return new AjaxResponse(true, "User password has been reset.");
		} else {

			return new AjaxResponse(false, "You are not authorized to access this module.");
		}
	}

	@RequestMapping("/resetMpin/{id}")
	public @ResponseBody AjaxResponse resetMpin(@PathVariable Long id, HttpServletRequest request, HttpSession session, HttpServletResponse response) {
		response.setHeader("X-Frame-Options", "DENY");
		if (getPriviledgeUser(session, PRIVILEDGE, C_RESET_PIN)) {
			Client client		= clientService.findById(id);
			String dataBefore 	= getXMLValue(client);
			clientService.resetPin(client, getLoginSecUser(session));
			String dataAfter 	= getXMLValue(clientService.findById(id));
			
			// save to audit trail
			String activity 	= "MPIN_RESET";
			long moduleId 		= 2007;
			auditTrailService.save(request, moduleId, activity, dataBefore, dataAfter, client.getCid(), getLoginSecUser(session));

			return new AjaxResponse(true, "User PIN has been reset.");
		} else {

			return new AjaxResponse(false, "You are not authorized to access this module.");
		}
	}

	@RequestMapping("/deactivate/{id}")
	public @ResponseBody AjaxResponse deactivate(@PathVariable Long id, HttpServletRequest request, HttpSession session, HttpServletResponse response) {
		response.setHeader("X-Frame-Options", "DENY");
		if (getPriviledgeUser(session, PRIVILEDGE, C_DEACTIVATE)) {
			Client client		= clientService.findById(id);
			String dataBefore	= getXMLValue(client);
			clientService.deactivate(client, getLoginSecUser(session));
			String dataAfter	= getXMLValue(clientService.findById(id));
			
			// save to audit trail
			String activity 	= "USER_DEACTIVATE";
			long moduleId 		= 2008;
			auditTrailService.save(request, moduleId, activity, dataBefore, dataAfter, client.getCid(), getLoginSecUser(session));

			return new AjaxResponse(true, "Client was successfully deactivated.");
		} else {

			return new AjaxResponse(false, "You are not authorized to access this module.");
		}
	}

	@RequestMapping("/restricted/{id}")
	public @ResponseBody AjaxResponse restricted(@PathVariable Long id, HttpServletRequest request, HttpSession session, HttpServletResponse response) {
		response.setHeader("X-Frame-Options", "DENY");
		if (getPriviledgeUser(session, PRIVILEDGE, C_RESTRICT)) {
			Client client = clientService.findById(id);
			client.setRestrict(1);
			clientService.save(client, getLoginSecUser(session));

			// save to audit trail
			String dataBefore 	= "Unrestrict";
			String dataAfter 	= "Restricted";
			String activity 	= "RESTRICTED";
			long moduleId 		= 2011;
			auditTrailService.save(request, moduleId, activity, dataBefore, dataAfter, client.getCid(), getLoginSecUser(session));

			return new AjaxResponse(true, "Client was successfully restricted.");
		} else {

			return new AjaxResponse(false, "You are not authorized to access this module.");
		}
	}
	
	@RequestMapping("/unrestrict/{id}")
	public @ResponseBody AjaxResponse unrestrict(@PathVariable Long id, HttpServletRequest request, HttpSession session, HttpServletResponse response) {
		response.setHeader("X-Frame-Options", "DENY");
		if (getPriviledgeUser(session, PRIVILEDGE, C_RESTRICT)) {
			Client client = clientService.findById(id);
			client.setRestrict(0);
			clientService.save(client, getLoginSecUser(session));

			// save to audit trail
			String dataBefore	= "Restricted";
			String dataAfter 	= "Unrestrict";
			String activity 	= "UNRESTRICT";
			long moduleId 		= 2012;
			auditTrailService.save(request, moduleId, activity, dataBefore, dataAfter, client.getCid(), getLoginSecUser(session));

			return new AjaxResponse(true, "Client was successfully unrestrict.");
		} else {

			return new AjaxResponse(false, "You are not authorized to access this module.");
		}
	}

	@RequestMapping("/viewUsername/{id}")
	public @ResponseBody AjaxResponse viewUsername(@PathVariable Long id, HttpServletRequest request, HttpSession session, HttpServletResponse response) {
		response.setHeader("X-Frame-Options", "DENY");
		if (getPriviledgeUser(session, PRIVILEDGE, C_VIEW_USERNAME)) {

			Client client = clientService.findById(id);

			return new AjaxResponse(true, client.getLogin());
		} else {

			return new AjaxResponse(false, "You are not authorized to access this module.");
		}
	}

	@RequestMapping("/enableAgentFeature/{id}")
	public @ResponseBody AjaxResponse enableAgentFeature(@PathVariable Long id, HttpServletRequest request, HttpSession session, HttpServletResponse response) {
		response.setHeader("X-Frame-Options", "DENY");
		if (getPriviledgeUser(session, PRIVILEDGE, C_AGENT_FEATURE)) {
			Client client = clientService.findById(id);
			client.setAgentFeature(1);
			clientService.save(client, getLoginSecUser(session));

			// save to audit trail
			String dataBefore	= "Disable";
			String dataAfter 	= "Enable";
			String activity 	= "AGENTFEATURE_ENABLE";
			long moduleId 		= 2111;
			auditTrailService.save(request, moduleId, activity, dataBefore, dataAfter, client.getCid(), getLoginSecUser(session));

			return new AjaxResponse(true, "Enable Agent Feature success.");
		} else {

			return new AjaxResponse(false, "You are not authorized to access this module.");
		}
	}

	@RequestMapping("/disableAgentFeature/{id}")
	public @ResponseBody AjaxResponse disableAgentFeature(@PathVariable Long id, HttpServletRequest request, HttpSession session, HttpServletResponse response) {
		response.setHeader("X-Frame-Options", "DENY");
		if (getPriviledgeUser(session, PRIVILEDGE, C_AGENT_FEATURE)) {
			Client client = clientService.findById(id);
			client.setAgentFeature(0);
			clientService.save(client, getLoginSecUser(session));

			// save to audit trail
			String dataBefore 	= "Enable";
			String dataAfter 	= "Disable";
			String activity 	= "AGENTFEATURE_DISABLE";
			long moduleId 		= 2112;
			auditTrailService.save(request, moduleId, activity, dataBefore, dataAfter, client.getCid(), getLoginSecUser(session));

			return new AjaxResponse(true, "Disable Agent Feature success.");
		} else {

			return new AjaxResponse(false, "You are not authorized to access this module.");
		}
	}

	private void putIntoRequest(Model model) {
		model.addAttribute("SELECTED_MENU", MENU);
		model.addAttribute("SELECTED_SUBMENU", PRIVILEDGE);
	}
}