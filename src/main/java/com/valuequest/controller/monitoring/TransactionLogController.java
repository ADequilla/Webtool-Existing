package com.valuequest.controller.monitoring;

import java.util.HashMap;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.valuequest.common.DataTables;
import com.valuequest.controller.BaseController;
import com.valuequest.entity.Lookup;

@Controller
@RequestMapping("/monitoring/transactionLog")
public class TransactionLogController extends BaseController {

	final static String MENU 		= "MONITORING";
	final static String PRIVILEDGE 	= "TRANSACTION_LOG";
	private String BASE_VIEW 		= "04.monitoring/";
	private String LIST_VIEW 		= "transaction-log";

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
			@RequestParam(required = false) String coreId,
			@RequestParam(required = false) String mobileId,
			@RequestParam(required = false) String transType,
			@RequestParam(required = false) String dateStart,
			@RequestParam(required = false) String dateEnd,
			@RequestParam(required = false) String sourceAccount,
			@RequestParam(required = false) String targetAccount, 
			@RequestParam(required = false) String sourceCid,
			@RequestParam(required = false) String targetCid,
			@RequestParam(required = false) String status,
			HttpSession session) {

		HashMap<String, Object> searchMap = new HashMap<>();
		searchMap.put("coreId", coreId);
		searchMap.put("mobileId", mobileId);
		searchMap.put("transType", transType);
		searchMap.put("dateStart", dateStart);
		searchMap.put("dateEnd", dateEnd);
		searchMap.put("sourceAccount", sourceAccount);
		searchMap.put("targetAccount", targetAccount);
		searchMap.put("sourceCid", sourceCid);
		searchMap.put("targetCid", targetCid);
		searchMap.put("status", status);
		
		
		DataTables dt = clientService.searchByMapCriteriaTransactionLog(dataTables, searchMap);
		return dt;
	}

	private void putIntoRequest(Model model) {
		model.addAttribute("SELECTED_MENU", MENU);
		model.addAttribute("SELECTED_SUBMENU", PRIVILEDGE);
		model.addAttribute("listClientType", genericService.lookup(Lookup.LOOKUP_CLIENT_TYPE));
		model.addAttribute("listTransType", genericService.lookup(Lookup.LOOKUP_TRANSACTION_LOG_TYPE));
		model.addAttribute("listStatus", genericService.lookup(Lookup.LOOKUP_TRANSACTION_STATUS));
	}
	
}