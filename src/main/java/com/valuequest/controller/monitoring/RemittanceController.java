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
import com.valuequest.entity.Lookup;
import com.valuequest.entity.ViewRemittance;
import com.valuequest.entity.security.SecUser;
import com.valuequest.services.RemittanceService;

/**
 * 
 * @author Fitri Nuraini
 *
 */

@Controller
@RequestMapping("/monitoring/remittance")
public class RemittanceController extends BaseController {

	final static String MENU 		= "MONITORING";
	final static String PRIVILEDGE 	= "REMITTANCE_MONITORING";
	private String BASE_VIEW 		= "04.monitoring/";
	private String LIST_VIEW 		= "remittance-list";
	
	@Autowired
	private RemittanceService remittanceService;
	
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
			@RequestParam(required = false) String senderMobileNumber,
			@RequestParam(required = false) String startSendDate,
			@RequestParam(required = false) String endSendDate,
			@RequestParam(required = false) String status,
			@RequestParam(required = false) String mobileReference,
			@RequestParam(required = false) String sourceBranch,
			@RequestParam(required = false) String targetBranch,
			HttpSession session) {

		HashMap<String, Object> searchMap = new HashMap<>();
		searchMap.put("senderMobileNumber", senderMobileNumber);
		searchMap.put("startSendDate", startSendDate);
		searchMap.put("endSendDate", endSendDate);
		searchMap.put("status", status);
		searchMap.put("mobileReference", mobileReference);
		searchMap.put("sourceBranch", sourceBranch);
		searchMap.put("targetBranch", targetBranch);

		return remittanceService.searchMonitoring(dataTables, searchMap);
	}
	
	private void putIntoRequest(Model model) {
		model.addAttribute("SELECTED_MENU", MENU);
		model.addAttribute("SELECTED_SUBMENU", PRIVILEDGE);
		List<ViewRemittance> listRemittance = remittanceService.getAll();
		model.addAttribute("listRemittance", listRemittance);
		model.addAttribute("listRemittanceStatus", genericService.lookup(Lookup.LOOKUP_REMITTANCE_STATUS));
	}
}