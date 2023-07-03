package com.valuequest.controller.customer_service;

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
import com.valuequest.entity.CSRHotlainView;
import com.valuequest.controller.administration.model.StateModel;

/**
 * 
 * @author Gilang ZW 
 *
 */

@Controller
@RequestMapping("/cs/csr-hotlain")
public class CSRHotlineController extends BaseController {

	final static String MENU = "cs";
	final static String PRIVILEDGE = "CSR_HOTLINE";
	private String BASE_VIEW = "06.customer-service/";
	private String LIST_VIEW = "csr_hotline_list";
	private String ADD_VIEW = "csr_hotline_add";
	private String EDIT_VIEW = "csr_hotline_edit";

	

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
			@RequestParam(required = false) String contactNumber,
			@RequestParam(required = false) String networkProvider, 
			@RequestParam(required = false) String institutionCode, 
			HttpSession session) {

		HashMap<String, Object> searchMap = new HashMap<>();
		searchMap.put("contactNumber", contactNumber);
		searchMap.put("networkProvider", networkProvider);
		searchMap.put("instCode", institutionCode);

		System.out.println("################MASUK SEARCH");

		return csrHotlineService.searchByMapCriteria(dataTables, searchMap, session);
	}

	@RequestMapping("/create")
	public String create(Model model, HttpSession session) {

		if (getPriviledgeUser(session, PRIVILEDGE, NEW)) {
			model.addAttribute("isNew", true);
			putIntoRequest(model);

			return BASE_VIEW + ADD_VIEW;
		}

		return getUnauthorizedPage();
	}

	@RequestMapping("/edit/{id}")
	public String edit(@PathVariable Long id, Model model, HttpSession session) {

		if (getPriviledgeUser(session, PRIVILEDGE, EDIT)) {
			model.addAttribute("CSRHotline", csrHotlineService.findById(id));
			putIntoRequest(model);

			return BASE_VIEW + EDIT_VIEW;
		}

		return getUnauthorizedPage();
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST, headers = { "content-type=application/json" })
	public @ResponseBody AjaxResponse save(@RequestBody CSRHotlainView model, HttpSession session) {

		if (csrHotlineService.isExist(model.getContactNumber())) {

			return new AjaxResponse(false, "Contact Number Duplicate.");
		}

		csrHotlineService.save(model, getLoginSecUser(session));

		return new AjaxResponse(model);
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST, headers = { "content-type=application/json" })
	public @ResponseBody AjaxResponse update(@RequestBody CSRHotlainView model, HttpSession session) {

	
		System.out.println("### ID ==================== " + model.getId());

		csrHotlineService.update(model, getLoginSecUser(session));

		return new AjaxResponse(model);
	}
	
	@RequestMapping(value = "/delete", method = RequestMethod.POST, headers = { "content-type=application/json" })
	public @ResponseBody AjaxResponse delete(@RequestBody List<StateModel> states, HttpSession session) {

		if (getPriviledgeUser(session, PRIVILEDGE, DELETE)) {

			csrHotlineService.delete(states);

			return new AjaxResponse(true, "CSR Hotline succesfully deleted.");
		} else {

			return new AjaxResponse(false, "You are not authorized to access this module.");
		}
	}

	private void putIntoRequest(Model model) {
		model.addAttribute("SELECTED_MENU", MENU);
		model.addAttribute("SELECTED_SUBMENU", PRIVILEDGE);
	}

}