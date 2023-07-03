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
import com.valuequest.controller.monitoring.model.SuspiciousModel;
import com.valuequest.entity.Lookup;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Controller
@RequestMapping("/monitoring/confirmation")
public class TransConfirmationController extends BaseController {

	final static String MENU 		= "MONITORING";
	final static String PRIVILEDGE 	= "TRANSACTION_CONFIRMATION";
	private String BASE_VIEW 		= "04.monitoring/";
	private String LIST_VIEW 		= "confirmation";

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
			@RequestParam(required = false) String transType, 
			@RequestParam(required = false) String status,
			@RequestParam(required = false) String branch, 
			@RequestParam(required = false) String dateStart,
			@RequestParam(required = false) String dateEnd, 
			HttpSession session) {

		HashMap<String, Object> searchMap = new HashMap<>();
		searchMap.put("loginId", getLoginIdFromSession(session));
		searchMap.put("cid", cid);
		searchMap.put("transType", transType);
		searchMap.put("status", status);
		searchMap.put("branch", branch);
		searchMap.put("dateStart", dateStart);
		searchMap.put("dateEnd", dateEnd);

		return suspiciousService.searchByMapCriteria(dataTables, searchMap);
	}



	@RequestMapping(value = "/confirm", method = RequestMethod.POST, headers = { "content-type=application/json" })
	public ResponseEntity<AjaxResponse> save(@RequestBody SuspiciousModel model, HttpSession session) {
		try {
			if (getPriviledgeUser(session, PRIVILEDGE, EDIT)) {
				suspiciousService.save(model, getLoginSecUser(session));
				return ResponseEntity.ok(new AjaxResponse(true, "Transaction successfully confirmed."));
			} else {
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new AjaxResponse(false, "You are not authorized to access this module."));
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new AjaxResponse(false, "An error occurred while processing the request. " +e.getMessage()));
		}
	}	

private void putIntoRequest(Model model) {
	model.addAttribute("SELECTED_MENU", MENU);
	model.addAttribute("SELECTED_SUBMENU", PRIVILEDGE);
	List<Lookup> listLookup = genericService.lookup(Lookup.LOOKUP_SUSPICIOUS_TYPE);
	
	for(int i = 0 ; i < listLookup.size() ; i++){
		Lookup l = listLookup.get(i);
		if(l.getValue().equals("WRONG_PIN") || l.getValue().equals("WRONG_PASS") || l.getValue().equals("FUND_TRANSFER")){
			listLookup.remove(i);
			i--;
		}
	}
	model.addAttribute("listSuspiciousType", listLookup);
	model.addAttribute("listSuspiciousStatus", genericService.lookup(Lookup.LOOKUP_SUSPICIOUS_STATUS));
}
}


