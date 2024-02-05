package com.valuequest.controller.monitoring;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
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
import com.valuequest.controller.monitoring.model.AuthorResetPasswordModel;
import com.valuequest.entity.Client;
import com.valuequest.entity.ViewAuthorResetPassword;
import com.valuequest.entity.security.SecUser;
import com.valuequest.services.AuthorResetPasswordService;
import com.valuequest.util.HttpClientMcu;

@Controller
@RequestMapping("/monitoring/author-reset-password")
public class AuthorResetPasswordController extends BaseController {

	final static String MENU 		= "MONITORING";
	final static String PRIVILEDGE 	= "AUTHOR_RESET_PASSWORD";
	private String BASE_VIEW 		= "04.monitoring/";
	private String LIST_VIEW 		= "author-reset-password";
	private String CREATE_VIEW 		= "authorResetPassword-create";
	
	private Long clientId;
	private Long idG;
	private Long idUser;
	
	@Autowired
	private AuthorResetPasswordService authorResetPasswordService;

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
			@RequestParam(required = false) String cid,
			@RequestParam(required = false) String status, 
			HttpSession session, HttpServletResponse response) {
			response.setHeader("X-Frame-Options", "DENY");

		HashMap<String, Object> searchMap = new HashMap<>();
		searchMap.put("loginId", getLoginIdFromSession(session));
		searchMap.put("cid", cid);
		searchMap.put("status", status);

		return authorResetPasswordService.searchByMapCriteria(dataTables, searchMap);
	}

	@RequestMapping("/edit/{id}")
	public String edit(@PathVariable Long id, Model model, HttpSession session, HttpServletResponse response) {
		response.setHeader("X-Frame-Options", "DENY");
		if (getPriviledgeUser(session, PRIVILEDGE, EDIT)) {

			ViewAuthorResetPassword va = authorResetPasswordService.findByParam(id);
			
			model.addAttribute("authorResetPassword", authorResetPasswordService.findByParam(id));
			clientId = va.getClientId();
			idG = id;
			idUser = va.getCreatedBy();
			model.addAttribute("isNew", false);
			putIntoRequest(model);

			return BASE_VIEW + CREATE_VIEW;
		}

		return getUnauthorizedPage();
	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST, headers = { "content-type=application/json" })
	public @ResponseBody AjaxResponse delete(@RequestBody List<AuthorResetPasswordModel> states, HttpSession session, HttpServletResponse response) {
		response.setHeader("X-Frame-Options", "DENY");
		if (getPriviledgeUser(session, PRIVILEDGE, DELETE)) {

			authorResetPasswordService.delete(states);

			return new AjaxResponse(true, "Author Reset Password succesfully deleted.");
		} else {

			return new AjaxResponse(false, "You are not authorized to access this module.");
		}
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST, headers = { "content-type=application/json" })
	public @ResponseBody AjaxResponse save(@RequestBody AuthorResetPasswordModel model, HttpServletRequest request, HttpSession session, HttpServletResponse response) {
		response.setHeader("X-Frame-Options", "DENY");
		System.out.println("A = "+idUser);
		System.out.println("B = "+getLoginSecUser(session).getId());
		
		if(idUser.equals(getLoginSecUser(session).getId())){
			return new AjaxResponse(false, "You are not allow to authorized your own changes.");
		}
		
		if("P".equals(model.getStatus())){
			if("RESET_PASSWORD".equals(model.getType())){
				Client client 		= clientService.findById(clientId);
				String dataBefore 	= getXMLValue(client);
				String randomPassword = clientService.resetPasswordRandom(client, getLoginSecUser(session));
				
				String dataAfter 	= getXMLValue(clientService.findById(clientId));
				
				ViewAuthorResetPassword authorResetPassword = authorResetPasswordService.findByParam(idG);
				authorResetPassword.setStatus("S");
				
				AuthorResetPasswordModel am = new AuthorResetPasswordModel();
				am.setCid(authorResetPassword.getCid());
				am.setClientId(authorResetPassword.getClientId());
				am.setClientName(authorResetPassword.getClientName());
				am.setId(authorResetPassword.getId());
				am.setStatus(authorResetPassword.getStatus());
				authorResetPasswordService.save(am, getLoginSecUser(session));
				
				HttpClientMcu httpClientMcu = new HttpClientMcu();
				
				String url = genericService.getConfigValueByName("URL_SEND_SMS_RESET");
				url = url + "customerId="+client.getId() + "&activity=RESET_PASSWORD" + "&code=" +randomPassword.trim();
				System.out.println("##### url "+url);
				httpClientMcu.callMbo(url);
				
				// save to audit trail
				String activity 	= "PASSWD_RESET|"+idG;
				long moduleId 		= 2006;
				auditTrailService.save(request, moduleId, activity, dataBefore, dataAfter, client.getCid(), getLoginSecUser(session));
				
				return new AjaxResponse(model);
			}else if("RESET_MPIN".equals(model.getType())){
				Client client		= clientService.findById(clientId);
				String dataBefore 	= getXMLValue(client);
				String randomPassword = clientService.resetPinAuthor(client, getLoginSecUser(session));
				String dataAfter 	= getXMLValue(clientService.findById(clientId));
				
				ViewAuthorResetPassword authorResetPassword = authorResetPasswordService.findByParam(idG);
				authorResetPassword.setStatus("S");
				
				AuthorResetPasswordModel am = new AuthorResetPasswordModel();
				am.setCid(authorResetPassword.getCid());
				am.setClientId(authorResetPassword.getClientId());
				am.setClientName(authorResetPassword.getClientName());
				am.setId(authorResetPassword.getId());
				am.setStatus(authorResetPassword.getStatus());
				authorResetPasswordService.save(am, getLoginSecUser(session));
				
				HttpClientMcu httpClientMcu = new HttpClientMcu();
				
				String url = genericService.getConfigValueByName("URL_SEND_SMS_RESET");
				url = url + "customerId="+client.getId() + "&activity=RESET_MPIN" + "&code=" +randomPassword;
				System.out.println("##### url "+url);
				httpClientMcu.callMbo(url);
				
				// save to audit trail
				String activity 	= "MPIN_RESET|"+idG;
				long moduleId 		= 2007;
				auditTrailService.save(request, moduleId, activity, dataBefore, dataAfter, client.getCid(), getLoginSecUser(session));
				
				return new AjaxResponse(model);
			}else if("RESET_STATUS".equals(model.getType())){
				ViewAuthorResetPassword authorResetPassword = authorResetPasswordService.findByParam(idG);
				authorResetPassword.setStatus("S");
				
				AuthorResetPasswordModel am = new AuthorResetPasswordModel();
				am.setCid(authorResetPassword.getCid());
				am.setClientId(authorResetPassword.getClientId());
				am.setClientName(authorResetPassword.getClientName());
				am.setId(authorResetPassword.getId());
				am.setStatus(authorResetPassword.getStatus());
				authorResetPasswordService.save(am, getLoginSecUser(session));
				
				Client client = clientService.findById(authorResetPassword.getClientId());
				client.setStatus("INACTIVE");
				clientService.save(client, getLoginSecUser(session));
				return new AjaxResponse(model);
			}else{
				ViewAuthorResetPassword authorResetPassword = authorResetPasswordService.findByParam(idG);
				authorResetPassword.setStatus("S");
				
				AuthorResetPasswordModel am = new AuthorResetPasswordModel();
				am.setCid(authorResetPassword.getCid());
				am.setClientId(authorResetPassword.getClientId());
				am.setClientName(authorResetPassword.getClientName());
				am.setId(authorResetPassword.getId());
				am.setStatus(authorResetPassword.getStatus());
				authorResetPasswordService.save(am, getLoginSecUser(session));
				
				Client client = clientService.findById(authorResetPassword.getClientId());
				client.setStatus("INACTIVE");
				client.setAgentFeature(0);
				client.setClientRstCredential(1);
				clientService.save(client, getLoginSecUser(session));
				return new AjaxResponse(model);
			}
		}else{
			return new AjaxResponse(false, "This transaction already used.");
		}
	}

	@RequestMapping(value = "/rejected", method = RequestMethod.POST, headers = { "content-type=application/json" })
	public @ResponseBody AjaxResponse rejected(@RequestBody AuthorResetPasswordModel model, HttpServletRequest request, HttpSession session, HttpServletResponse response) {
		response.setHeader("X-Frame-Options", "DENY");
		System.out.println("A = "+idUser);
		System.out.println("B = "+getLoginSecUser(session).getId());
		
		if(idUser.equals(getLoginSecUser(session).getId())){
			return new AjaxResponse(false, "You are not allow to authorized your own changes.");
		}
		
		if("P".equals(model.getStatus())){
			ViewAuthorResetPassword authorResetPassword = authorResetPasswordService.findByParam(idG);
			authorResetPassword.setStatus("R");
			
			AuthorResetPasswordModel am = new AuthorResetPasswordModel();
			am.setCid(authorResetPassword.getCid());
			am.setClientId(authorResetPassword.getClientId());
			am.setClientName(authorResetPassword.getClientName());
			am.setId(authorResetPassword.getId());
			am.setStatus(authorResetPassword.getStatus());
			authorResetPasswordService.save(am, getLoginSecUser(session));
			
			return new AjaxResponse(model);
		}else{
			return new AjaxResponse(false, "This transaction already used.");
		}
	}

	private void putIntoRequest(Model model) {
		model.addAttribute("SELECTED_MENU", MENU);
		model.addAttribute("SELECTED_SUBMENU", PRIVILEDGE);
	}
}
