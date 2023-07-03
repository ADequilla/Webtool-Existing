package com.valuequest.controller.monitoring;

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
import com.valuequest.entity.ViewDashboardTransaction;
import com.valuequest.services.OperationDashboardService;

/**
 * 
 * @author Gilang ZW
 *
 */

@Controller
@RequestMapping("/monitoring/operation-dashboard")
public class OperationDashboardController extends BaseController {

	final static String MENU 		= "MONITORING";
	final static String PRIVILEDGE 	= "OPERATION_DASHBOARD";
	private String BASE_VIEW 		= "04.monitoring/";
	private String LIST_VIEW 		= "operation_dashboard_list";
	
	@Autowired
	private OperationDashboardService operationDashboardService;
	
	private List<ViewDashboardTransaction> listData;
	
	@RequestMapping("/")
	public String index( Model model, HttpSession session) {

		if (getPriviledgeUser(session, PRIVILEDGE, VIEW)) {
			
			listData = operationDashboardService.getAll();
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
		searchMap.put("dateStart", dateStart);
		searchMap.put("dateEnd", dateEnd);
		searchMap.put("branch", branch);

		return operationDashboardService.searchByMapCriteria(dataTables, searchMap);
	}
	
	private void putIntoRequest(Model model) {
		model.addAttribute("SELECTED_MENU", MENU);
		model.addAttribute("SELECTED_SUBMENU", PRIVILEDGE);
		model.addAttribute("countAgent", operationDashboardService.countAgent());
		model.addAttribute("countMember", operationDashboardService.countMember());
		model.addAttribute("countNonMember", operationDashboardService.countNonMember());
		
		model.addAttribute("listData", listData);
	}
	
}