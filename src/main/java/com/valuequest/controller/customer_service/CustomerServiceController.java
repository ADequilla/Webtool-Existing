package com.valuequest.controller.customer_service;

import java.util.HashMap;

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
import com.valuequest.common.DataTables;
import com.valuequest.controller.BaseController;
import com.valuequest.controller.administration.model.StateModel;
import com.valuequest.controller.customer_service.model.TicketModel;
import com.valuequest.entity.Lookup;
import com.valuequest.entity.ViewClient;
import com.valuequest.entity.ViewCsTicket;

@Controller
@RequestMapping("/cs/dashboard")
public class CustomerServiceController extends BaseController {

	final static String MENU 		= "CUSTOMER_SERVICE";
	final static String PRIVILEDGE 	= "DASHBOARD";
	private String BASE_VIEW 		= "06.customer-service/";
	private String LIST_VIEW 		= "dashboard";
	private String NEW_VIEW 		= "dashboard-new";
	private String EDIT_VIEW 		= "dashboard-edit";

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
			@RequestParam(required = false) String ticketNo,
			@RequestParam(required = false) String cid, 
			@RequestParam(required = false) String submittedBy,
			@RequestParam(required = false) String startDate, 
			@RequestParam(required = false) String endDate,
			@RequestParam(required = false) Long concern, 
			@RequestParam(required = false) String status,
			HttpSession session) {

		HashMap<String, Object> searchMap = new HashMap<>();
		searchMap.put("ticketNo", ticketNo);
		searchMap.put("cid", cid);
		searchMap.put("submittedBy", submittedBy);
		searchMap.put("postedDateStart", startDate);
		searchMap.put("postedDateEnd", endDate);
		searchMap.put("concern", concern);
		searchMap.put("status", status);

		return ticketService.searchByMapCriteria(dataTables, searchMap);
	}

	@RequestMapping("/create")
	public String create(Model model, HttpSession session) {

		if (getPriviledgeUser(session, PRIVILEDGE, NEW)) {

			model.addAttribute("listAction", genericService.lookup(Lookup.LOOKUP_CS_ACTION));
			putIntoRequest(model);

			return BASE_VIEW + NEW_VIEW;
		}

		return getUnauthorizedPage();
	}

	@RequestMapping("/edit/{id}")
	public String edit(@PathVariable Long id, Model model, HttpSession session) {

		if (getPriviledgeUser(session, PRIVILEDGE, EDIT)) {

			ViewCsTicket ticket = ticketService.findViewById(id);

			model.addAttribute("ticket", ticket);
			model.addAttribute("client", clientService.findByCid(ticket.getCid()));
			model.addAttribute("listAction", genericService.lookup(Lookup.LOOKUP_CS_ACTION));
			putIntoRequest(model);

			return BASE_VIEW + EDIT_VIEW;
		}

		return getUnauthorizedPage();
	}

	@RequestMapping(value = "/close", method = RequestMethod.POST, headers = { "content-type=application/json" })
	public @ResponseBody AjaxResponse close(@RequestBody StateModel model, HttpSession session) {

		if (getPriviledgeUser(session, PRIVILEDGE, EDIT)) {

			ticketService.closeTicket(model.getId(), getLoginSecUser(session));

			return new AjaxResponse(true, "Ticket succesfully closed.");
		} else {
			return new AjaxResponse(false, "You are not authorized to access this module.");
		}
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST, headers = { "content-type=application/json" })
	public @ResponseBody AjaxResponse save(@RequestBody TicketModel model, HttpSession session) {

		ticketService.saveTicket(model, getLoginSecUser(session));

		return new AjaxResponse(model);
	}

	@RequestMapping(value = "/getClient", method = RequestMethod.POST, headers = { "content-type=application/json" })
	public @ResponseBody AjaxResponse getClient(@RequestBody String cid, HttpSession session) {
		
		ViewClient view = clientService.findByCid(cid);
		if (view == null) {
			return new AjaxResponse(false, "CID not found.");
		}

		return new AjaxResponse(view);
	}

	private void putIntoRequest(Model model) {
		model.addAttribute("SELECTED_MENU", MENU);
		model.addAttribute("SELECTED_SUBMENU", PRIVILEDGE);
		model.addAttribute("listClientType", genericService.lookup(Lookup.LOOKUP_CLIENT_TYPE));
		model.addAttribute("listTransaction", genericService.lookup(Lookup.LOOKUP_TRANSACTION_TYPE));
		model.addAttribute("listStatus", genericService.lookup(Lookup.LOOKUP_CS_STATUS));
		model.addAttribute("listConcern", typeOfConcernService.list());
	}
}