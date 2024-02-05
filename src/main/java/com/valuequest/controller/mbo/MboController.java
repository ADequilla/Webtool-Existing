package com.valuequest.controller.mbo;

import java.util.HashMap;

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

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.valuequest.common.AjaxResponse;
import com.valuequest.common.DataTables;
import com.valuequest.controller.BaseController;
import com.valuequest.controller.mbo.model.MboModel;
import com.valuequest.controller.monitoring.model.ProfileModel;
import com.valuequest.entity.Client;
import com.valuequest.entity.ViewClient;
import com.valuequest.entity.security.SecUser;
import com.valuequest.util.HttpClientMbo;
import com.valuequest.util.HttpClientUpload;

@Controller
@RequestMapping("/mbo/list")
public class MboController extends BaseController {

	final static String MENU 		= "MBO";
	final static String PRIVILEDGE 	= "MBO_LIST";
	private String BASE_VIEW 		= "09.mbo/";
	private String LIST_VIEW 		= "mbo-list";
	private String EDIT_VIEW 		= "mbo-edit";

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
			@RequestParam(required = false) String branch,
			@RequestParam(required = false) String name, 
			HttpSession session, HttpServletResponse response) {
				response.setHeader("X-Frame-Options", "DENY");
		HashMap<String, Object> searchMap = new HashMap<>();
		searchMap.put("loginId", getLoginIdFromSession(session));
		searchMap.put("branch", branch);
		searchMap.put("name", name);
		searchMap.put("type", Client.CLIENT_TYPE_MBO);

		return clientService.searchByMapCriteria(dataTables, searchMap);
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
		if (getPriviledgeUser(session, PRIVILEDGE, EDIT)) {

			model.addAttribute("mbo", clientService.findById(id));
			putIntoRequest(model);

			return BASE_VIEW + EDIT_VIEW;
		}

		return getUnauthorizedPage();
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST, headers = { "content-type=application/json" })
	public @ResponseBody AjaxResponse save(@RequestBody MboModel model, HttpSession session, HttpServletResponse response) {
		response.setHeader("X-Frame-Options", "DENY");
		clientService.save(model, getLoginSecUser(session));
		return new AjaxResponse(model);
	}
	
	@RequestMapping(value = "/get", method = RequestMethod.POST, headers = { "content-type=application/json" })
	public @ResponseBody AjaxResponse get(@RequestBody ProfileModel model, HttpSession session, HttpServletResponse response) {
		response.setHeader("X-Frame-Options", "DENY");
		System.out.println("============================== account = "+model);
		if (!validationIA(model.getString())){
			return new AjaxResponse(false, "Invalid Internal Account");
		}

		return new AjaxResponse(model);
	}

	
	public boolean validationIA(String ia){
		try{
			HttpClientMbo httpClientMbo = new HttpClientMbo();
			String result = httpClientMbo.connect("http://10.100.27.36:19091/members/rest/administration/accountValidation?accountNumber="+ia);
			System.out.println("============================================= result = "+result);
			if(result.contains("errorCode")){
				return false;
			}else{
				return true;
			}
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	private void putIntoRequest(Model model) {
		model.addAttribute("SELECTED_MENU", MENU);
		model.addAttribute("SELECTED_SUBMENU", PRIVILEDGE);
	}
}
