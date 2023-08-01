package com.valuequest.controller.maintenance;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
import com.valuequest.controller.maintenance.model.BankListModel;
import com.valuequest.entity.Lookup;
import com.valuequest.entity.security.SecUser;
import com.valuequest.services.BankListService;
import com.valuequest.util.HttpClientMcu;

@Controller
@RequestMapping("/utilities/bank-list")
public class BankListController extends BaseController {

	final static String MENU 		= "UTILITIES";
	final static String PRIVILEDGE 	= "BANK_LIST";
	private String BASE_VIEW 		= "07.maintenance/";
	private String LIST_VIEW 		= "bankList";
	private String CREATE_VIEW 		= "bankList-create";
	private String EDIT_VIEW 		= "bankList-edit";
	
	@Autowired
	private BankListService bankListService;

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
			@RequestParam(required = false) String bankCode, 
			@RequestParam(required = false) String bankName, 
			@RequestParam(required = false) String shortName, 
			HttpSession session) {

			HashMap<String, Object> searchMap = new HashMap<>();
			searchMap.put("bankCode", bankCode);
			searchMap.put("bankName", bankName);
			searchMap.put("shortName", shortName);
	
			return bankListService.searchByMapCriteria(dataTables, searchMap);
	}

	@RequestMapping("/create")
	public String create(Model model, HttpSession session) {

		if (getPriviledgeUser(session, PRIVILEDGE, NEW)) {
			
			model.addAttribute("isNew", true);
			putIntoRequest(model);
			
			return BASE_VIEW + CREATE_VIEW;
		}

		return getUnauthorizedPage();
	}

	@RequestMapping("/edit/{bankCode}")
	public String edit(@PathVariable String bankCode, Model model, HttpSession session) {

		if (getPriviledgeUser(session, PRIVILEDGE, EDIT)) {

			model.addAttribute("bankList", bankListService.findByBankCode(bankCode));
			model.addAttribute("isNew", false);
			putIntoRequest(model);

			return BASE_VIEW + EDIT_VIEW;
		}

		return getUnauthorizedPage();
	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST, headers = { "content-type=application/json" })
	public @ResponseBody AjaxResponse delete(@RequestBody List<BankListModel> states, HttpSession session) {

		if (getPriviledgeUser(session, PRIVILEDGE, DELETE)) {

			bankListService.delete(states);

			return new AjaxResponse(true, "Bank List succesfully deleted.");
		} else {

			return new AjaxResponse(false, "You are not authorized to access this module.");
		}
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST, headers = { "content-type=application/json" })
	public @ResponseBody AjaxResponse save(@RequestBody BankListModel model, HttpSession session) {

	
			if (bankListService.isExist(model.getBankCode())) {
				
				return new AjaxResponse(false, "Bank Code Duplicate.");
			}

		bankListService.save(model, getLoginSecUser(session));

		return new AjaxResponse(model);
	}
	

	private void putIntoRequest(Model model) {
		model.addAttribute("SELECTED_MENU", MENU);
		model.addAttribute("SELECTED_SUBMENU", PRIVILEDGE);
	}
}
