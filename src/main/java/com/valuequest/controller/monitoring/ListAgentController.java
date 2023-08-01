package com.valuequest.controller.monitoring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.valuequest.common.DataTables;
import com.valuequest.controller.BaseController;
import com.valuequest.entity.Lookup;
import com.valuequest.entity.security.SecUser;
import com.valuequest.services.ListAgentService;

@Controller
@RequestMapping("/monitoring/list-agent")
public class ListAgentController extends BaseController {

	final static String MENU 		= "MONITORING";
	final static String PRIVILEDGE 	= "LIST_AGENT";
	private String BASE_VIEW 		= "04.monitoring/";
	private String LIST_VIEW 		= "list-agent";


	@Autowired
	private ListAgentService listAgentService;

	@RequestMapping("/")
	public String index(Model model, HttpSession session) {
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
			@RequestParam(required = false) String mobileNumber,
			@RequestParam(required = false) String cid,
			@RequestParam(required = false) String instiCode,
			@RequestParam(required = false) String branchCode,
			@RequestParam(required = false) String unitCode,
			@RequestParam(required = false) String centerCode,
			HttpSession session) {
			
		System.out.print("#### Agent CID###### " + cid);
		HashMap<String, Object> searchMap = new HashMap<>();
		searchMap.put("searchDateStart", searchDateStart);
		searchMap.put("searchDateEnd", searchDateEnd);
		searchMap.put("mobileNumber", mobileNumber);
		searchMap.put("cid", cid);
		searchMap.put("instiCode", instiCode);
		searchMap.put("branchCode", branchCode);
		searchMap.put("unitCode", unitCode);
		searchMap.put("centerCode", centerCode);

		return listAgentService.searchByMapCriteriaAgent(dataTables, searchMap);
	}

	private void putIntoRequest(Model model) {
		model.addAttribute("SELECTED_MENU", MENU);
		model.addAttribute("SELECTED_SUBMENU", PRIVILEDGE);	
		model.addAttribute("listClientType", genericService.lookup(Lookup.LOOKUP_CLIENT_TYPE));
	}
}