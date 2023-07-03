package com.valuequest.controller.maintenance;

import java.util.HashMap;
import java.util.List;

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
import com.valuequest.entity.BillerTemp;
import com.valuequest.entity.Lookup;

@Controller
@RequestMapping("/utilities/biller")
public class BillerController extends BaseController {

	final static String MENU 		= "UTILITIES";
	final static String PRIVILEDGE 	= "BILLER";
	final static String APPROVE		= "APPROVE";
	private String BASE_VIEW 		= "07.maintenance/";
	private String LIST_VIEW 		= "biller";
	private String EDIT_VIEW 		= "biller-edit";

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
			@RequestParam(required = false) String name,
			@RequestParam(required = false) String account, 
			HttpSession session) {

		HashMap<String, Object> searchMap = new HashMap<>();
		searchMap.put("name", name);
		searchMap.put("account", account);

		return billerService.searchByMapCriteria(dataTables, searchMap);
	}

	@RequestMapping("/create")
	public String create(Model model, HttpSession session) {

		if (getPriviledgeUser(session, PRIVILEDGE, NEW)) {

			model.addAttribute("billerType", genericService.lookup(Lookup.LOOKUP_BILLER_TYPE));
			putIntoRequest(model);

			return BASE_VIEW + EDIT_VIEW;
		}

		return getUnauthorizedPage();
	}

	@RequestMapping("/edit/{id}")
	public String edit(@PathVariable Long id, Model model, HttpSession session) {

		if (getPriviledgeUser(session, PRIVILEDGE, EDIT)) {
			
			model.addAttribute("biller", billerService.findById(id));
			model.addAttribute("billerType", genericService.lookup(Lookup.LOOKUP_BILLER_TYPE));
			putIntoRequest(model);

			return BASE_VIEW + EDIT_VIEW;
		}

		return getUnauthorizedPage();
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST, headers = { "content-type=application/json" })
	public @ResponseBody AjaxResponse save(@RequestBody BillerTemp model, HttpSession session) {

		billerService.save(model, getLoginSecUser(session));

		return new AjaxResponse(model);
	}

	@RequestMapping(value = "/approve", method = RequestMethod.POST, headers = { "content-type=application/json" })
	public @ResponseBody AjaxResponse approve(@RequestBody List<StateModel> states, HttpSession session) {
		
		if (getPriviledgeUser(session, PRIVILEDGE, APPROVE)) {

			billerService.update(states, BillerTemp.BILLER_STATUS_APPROVED, getLoginSecUser(session));

			return new AjaxResponse(true, "Billing request succesfully approved.");
		} else {
			return new AjaxResponse(false, "You are not authorized to access this module.");
		}
	}
	
	@RequestMapping(value = "/reject", method = RequestMethod.POST, headers = { "content-type=application/json" })
	public @ResponseBody AjaxResponse reject(@RequestBody List<StateModel> states, HttpSession session) {
		
		if (getPriviledgeUser(session, PRIVILEDGE, APPROVE)) {

			billerService.update(states, BillerTemp.BILLER_STATUS_REJECTED, getLoginSecUser(session));

			return new AjaxResponse(true, "Billing request succesfully rejected.");
		} else {
			return new AjaxResponse(false, "You are not authorized to access this module.");
		}
	}

	private void putIntoRequest(Model model) {
		model.addAttribute("SELECTED_MENU", MENU);
		model.addAttribute("SELECTED_SUBMENU", PRIVILEDGE);
	}
}