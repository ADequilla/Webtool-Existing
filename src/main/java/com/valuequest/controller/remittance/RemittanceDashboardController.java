package com.valuequest.controller.remittance;

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
//import com.valuequest.entity.StructureRemittance;
import com.valuequest.entity.ViewRemittance;
import com.valuequest.services.RemittanceService;

/**
 * 
 * @author Fitri Nuraini
 *
 */

@Controller
@RequestMapping("/monitoring/remittance-dashboard/")
public class RemittanceDashboardController extends BaseController {

	final static String MENU 		= "MONITORING";
	final static String PRIVILEDGE 	= "DASHBOARD_REMITTANCE";
	private String BASE_VIEW 		= "10.remittance/";
	private String LIST_VIEW 		= "list_dashboard";
	private String CLAIMED_VIEW 	= "claimed_view";
	private String CANCELLED_VIEW 	= "cancelled_view";
	private String PENDING_VIEW 	= "pending_view";
	private String SENT_VIEW 		= "sent_view";
	private String TOTAL_VIEW 		= "total_view";
	
	@Autowired
	private RemittanceService remittanceService;
	
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
			@RequestParam(required = false) String startSendDate,
			@RequestParam(required = false) String endSendDate, 
			@RequestParam(required = false) Long status,
			HttpSession session) {

			HashMap<String, Object> searchMap = new HashMap<>();
			searchMap.put("startSendDate", startSendDate);
			searchMap.put("endSendDate", endSendDate);
			searchMap.put("status", status);
			
	
			return remittanceService.searchByMapCriteria(dataTables, searchMap);
	}
	
	@RequestMapping("/searchCancelled")
	public @ResponseBody DataTables searchCancelled(DataTables dataTables, 
			@RequestParam(required = false) String startCancelledDate,
			@RequestParam(required = false) String endCancelledDate, 
			@RequestParam(required = false) Long status,
			HttpSession session) {

			HashMap<String, Object> searchMap = new HashMap<>();
			searchMap.put("startCancelledDate", startCancelledDate);
			searchMap.put("endCancelledDate", endCancelledDate);
			searchMap.put("status", status);
			
	
			return remittanceService.searchCancelledByMapCriteria(dataTables, searchMap);
	}
	
	@RequestMapping("/searchClaimed")
	public @ResponseBody DataTables searchClaimed(DataTables dataTables, 
			@RequestParam(required = false) String startClaimedDate,
			@RequestParam(required = false) String endClaimedDate, 
			@RequestParam(required = false) Long status,
			HttpSession session) {

			HashMap<String, Object> searchMap = new HashMap<>();
			searchMap.put("startClaimedDate", startClaimedDate);
			searchMap.put("endClaimedDate", endClaimedDate);
			searchMap.put("status", status);
			
	
			return remittanceService.searchClaimedByMapCriteria(dataTables, searchMap);
	}
	
	@RequestMapping("/searchAll")
	public @ResponseBody DataTables searchAll(DataTables dataTables, 
			@RequestParam(required = false) String startSendDate,
			@RequestParam(required = false) String endSendDate, 
			HttpSession session) {

			HashMap<String, Object> searchMap = new HashMap<>();
			searchMap.put("startSendDate", startSendDate);
			searchMap.put("endSendDate", endSendDate);
	
			return remittanceService.searchCriteria(dataTables, searchMap);
	}
	
	@RequestMapping("/claimed")
	public String claimed(Model model, HttpSession session) {

		if (getPriviledgeUser(session, PRIVILEDGE, VIEW)) {
				
				
				putIntoRequest(model);

				return BASE_VIEW + CLAIMED_VIEW;
			}

			return getUnauthorizedPage();
		}
	
	@RequestMapping("/cancelled")
	public String cancelled(Model model, HttpSession session) {

		if (getPriviledgeUser(session, PRIVILEDGE, VIEW)) {
				
				putIntoRequest(model);

				return BASE_VIEW + CANCELLED_VIEW;
			}

			return getUnauthorizedPage();
		}
	
	@RequestMapping("/pending")
	public String pending(Model model, HttpSession session) {

		if (getPriviledgeUser(session, PRIVILEDGE, VIEW)) {
				
				putIntoRequest(model);

				return BASE_VIEW + PENDING_VIEW;
			}

			return getUnauthorizedPage();
		}
	
	@RequestMapping("/sent")
	public String sent(Model model, HttpSession session) {

		if (getPriviledgeUser(session, PRIVILEDGE, VIEW)) {
				
				putIntoRequest(model);

				return BASE_VIEW + SENT_VIEW;
			}

			return getUnauthorizedPage();
		}
	
	@RequestMapping("/total")
	public String total(Model model, HttpSession session) {

		if (getPriviledgeUser(session, PRIVILEDGE, VIEW)) {
				
				putIntoRequest(model);

				return BASE_VIEW + TOTAL_VIEW;
			}

			return getUnauthorizedPage();
		}
	
	private void putIntoRequest(Model model) {
		model.addAttribute("SELECTED_MENU", MENU);
		model.addAttribute("SELECTED_SUBMENU", PRIVILEDGE);
		List<ViewRemittance> listRemittance = remittanceService.getAll();
		model.addAttribute("listRemittance", listRemittance);
		model.addAttribute("countStatus0", remittanceService.countStatus0());
		model.addAttribute("countStatus1", remittanceService.countStatus1());
		model.addAttribute("countStatus2", remittanceService.countStatus2());
		model.addAttribute("countStatus3", remittanceService.countStatus3());
		model.addAttribute("countStatusAll", remittanceService.countStatusAll());
	}
	
}