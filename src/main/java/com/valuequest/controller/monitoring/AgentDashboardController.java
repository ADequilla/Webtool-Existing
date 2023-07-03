package com.valuequest.controller.monitoring;

import java.util.HashMap;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.valuequest.common.DataTables;
import com.valuequest.controller.BaseController;
import com.valuequest.services.AgentDashboardTempService;

/**
 * 
 * @author Gilang ZW
 *
 */

@Controller
@RequestMapping("/monitoring/agent-dashboard")
public class AgentDashboardController extends BaseController {

	final static String MENU 		= "MONITORING";
	final static String PRIVILEDGE 	= "AGENT_DASHBOARD";
	private String BASE_VIEW 		= "04.monitoring/";
	private String LIST_VIEW 		= "agent_dashboard_list";
	
	@Autowired
	private AgentDashboardTempService agentDashboardTempService;
	
	@RequestMapping("/")
	public String index( Model model, HttpSession session) {

		if (getPriviledgeUser(session, PRIVILEDGE, VIEW)) {
			
			putIntoRequest(model);

			return BASE_VIEW + LIST_VIEW;
		}

		return getUnauthorizedPage();
	}
	
	@RequestMapping("/search")
	public @ResponseBody DataTables search(DataTables dataTables, 
			@RequestParam(required = false) String dateStart,
			@RequestParam(required = false) String dateEnd, 
			@RequestParam(required = false) String branch, 
			HttpSession session) {

		HashMap<String, Object> searchMap = new HashMap<>();
		searchMap.put("branch", branch);

		return agentDashboardTempService.searchByMapCriteria(dataTables, searchMap);
	}
	
	@RequestMapping("/search-agent")
	public @ResponseBody DataTables searchAgent(DataTables dataTables, 
			@RequestParam(required = false) String dateStart,
			@RequestParam(required = false) String dateEnd, 
			@RequestParam(required = false) String branch, 
			HttpSession session) {

		HashMap<String, Object> searchMap = new HashMap<>();
		searchMap.put("branch", branch);

		return agentDashboardTempService.searchByMapCriteriaAgent(dataTables, searchMap);
	}
	
	private void putIntoRequest(Model model) {
		model.addAttribute("SELECTED_MENU", MENU);
		model.addAttribute("SELECTED_SUBMENU", PRIVILEDGE);
	}
	
}