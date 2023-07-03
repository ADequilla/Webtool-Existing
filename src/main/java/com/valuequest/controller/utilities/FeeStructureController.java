package com.valuequest.controller.utilities;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.valuequest.common.AjaxResponse;
import com.valuequest.common.DataTables;
import com.valuequest.controller.BaseController;
import com.valuequest.controller.administration.model.StateModel;
import com.valuequest.entity.FeeStructure;
import com.valuequest.entity.Lookup;

@Controller
@RequestMapping("/utilities/fee")
public class FeeStructureController extends BaseController {

	final static String MENU = "UTILITIES";
	final static String PRIVILEDGE = "FEE_STRUCTURE";
	private String BASE_VIEW = "05.utilities/";
	private String LIST_VIEW = "fee-structure";
	private String EDIT_VIEW = "fee-structure-edit";

	@RequestMapping("/")
	public String index(Model model, HttpSession session) {

		if (getPriviledgeUser(session, PRIVILEDGE, VIEW)) {

			putIntoRequest(model);

			return BASE_VIEW + LIST_VIEW;
		}
		return getUnauthorizedPage();
	}

	@RequestMapping("/search")
	public @ResponseBody DataTables search(DataTables dataTables, @RequestParam(required = false) String transType,
			HttpSession session) {

		HashMap<String, Object> searchMap = new HashMap<>();
		searchMap.put("transType", transType);

		return feeStructureService.searchByMapCriteria(dataTables, searchMap);
	}

	@RequestMapping("/create")
	public String create(Model model, HttpSession session) {

		if (getPriviledgeUser(session, PRIVILEDGE, NEW)) {

			putIntoRequest(model);

			return BASE_VIEW + EDIT_VIEW;
		}

		return getUnauthorizedPage();
	}

	@RequestMapping("/edit/{id}")
	public String edit(@PathVariable Long id, Model model, HttpSession session) {

		if (getPriviledgeUser(session, PRIVILEDGE, EDIT)) {

			model.addAttribute("feeStructure", feeStructureService.findById(id));
			putIntoRequest(model);

			return BASE_VIEW + EDIT_VIEW;
		}

		return getUnauthorizedPage();
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST, headers = { "content-type=application/json" })
	public @ResponseBody AjaxResponse save(@RequestBody FeeStructure model, HttpSession session, HttpServletRequest request) {

		AjaxResponse ajaxResponse = new AjaxResponse();
		Boolean isDuplicated = feeStructureService.checkingDuplicateRange(model.getStartRange(), model.getEndRange(), model.getTransType(), model.getId());
		Gson gson = new Gson();
		

				
		if(isDuplicated) {
			ajaxResponse.setData(model);
			ajaxResponse.setMessage("Range value could not same.");
			ajaxResponse.setSuccess(false);
			return ajaxResponse;
		}else {
			feeStructureService.save(model, getLoginSecUser(session));
			
			// save to audit trail
			String dataBefore 	= "";
			String dataAfter 	= gson.toJson(model);
			String activity 	= "FEE_STRUCTURE";
			long moduleId 		= 1018;
			auditTrailService.save(request, moduleId, activity, dataBefore, dataAfter,"", getLoginSecUser(session));
			refreshParameters("ALL");
			return new AjaxResponse(model);
		}
		
	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST, headers = { "content-type=application/json" })
	public @ResponseBody AjaxResponse delete(@RequestBody List<StateModel> states, HttpSession session) {

		if (getPriviledgeUser(session, PRIVILEDGE, DELETE)) {

			feeStructureService.delete(states);

			return new AjaxResponse(true, "Fee Structure succesfully deleted.");
		} else {
			return new AjaxResponse(false, "You are not authorized to access this module.");
		}
	}

	private void putIntoRequest(Model model) {
		model.addAttribute("SELECTED_MENU", MENU);
		model.addAttribute("SELECTED_SUBMENU", PRIVILEDGE);
		model.addAttribute("listTransType", genericService.lookup(Lookup.LOOKUP_TRANSACTION_TYPE));
		model.addAttribute("listClientType", genericService.lookup(Lookup.LOOKUP_FEE_CLIENT_TYPE));
	}
}
