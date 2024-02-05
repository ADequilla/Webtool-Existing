package com.valuequest.controller.maintenance;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
import com.valuequest.entity.StructureBillerPay;
import com.valuequest.entity.security.SecUser;
import com.valuequest.controller.maintenance.model.BillerPayModel;
import com.valuequest.services.BillerPayService;

@Controller
@RequestMapping("/utilities/biller-pay")
public class BillerPayController extends BaseController {

	final static String MENU 		= "UTILITIES";
	final static String PRIVILEDGE 	= "BILLER_PAY";
	private String BASE_VIEW 		= "07.maintenance/";
	private String LIST_VIEW 		= "billerPay";
	private String CREATE_VIEW 		= "billerPay-create";
	private String EDIT_VIEW 		= "billerPay-edit";
	
	@Autowired
	private BillerPayService billerPayService;

	@RequestMapping("/")
	public String index(Model model, HttpSession session, HttpServletResponse response) {
		response.setHeader("X-Frame-Options", "DENY");

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
			@RequestParam(required = false) String billerCode,
			@RequestParam(required = false) String billerName, 
			HttpSession session, HttpServletResponse response) {
			response.setHeader("X-Frame-Options", "DENY");
		
			HashMap<String, Object> searchMap = new HashMap<>();
			searchMap.put("billerCode", billerCode);
			searchMap.put("billerName", billerName);
			
			return billerPayService.searchByMapCriteria(dataTables, searchMap);
		
	}

	@RequestMapping("/create")
	public String create(Model model, HttpSession session, HttpServletResponse response) {
		response.setHeader("X-Frame-Options", "DENY");

		if (getPriviledgeUser(session, PRIVILEDGE, NEW)) {
			
			model.addAttribute("isNew", true);
			putIntoRequest(model);
			
			return BASE_VIEW + CREATE_VIEW;
		}

		return getUnauthorizedPage();
	}

	@RequestMapping("/edit/{billerCode}")
	public String edit(@PathVariable String billerCode, Model model, HttpSession session, HttpServletResponse response) {
		response.setHeader("X-Frame-Options", "DENY");

		if (getPriviledgeUser(session, PRIVILEDGE, EDIT)) {

			model.addAttribute("billerPay", billerPayService.findByCode(billerCode));
			model.addAttribute("isNew", false);
			putIntoRequest(model);

			return BASE_VIEW + EDIT_VIEW;
		}

		return getUnauthorizedPage();
	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST, headers = { "content-type=application/json" })
	public @ResponseBody AjaxResponse delete(@RequestBody List<BillerPayModel> states, HttpSession session, HttpServletResponse response) {
		response.setHeader("X-Frame-Options", "DENY");

		if (getPriviledgeUser(session, PRIVILEDGE, DELETE)) {

			billerPayService.delete(states);

			return new AjaxResponse(true, "Biller Pay succesfully deleted.");
		} else {

			return new AjaxResponse(false, "You are not authorized to access this module.");
		}
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST, headers = { "content-type=application/json" })
	public @ResponseBody AjaxResponse save(@RequestBody BillerPayModel model, HttpSession session, HttpServletResponse response) {
		response.setHeader("X-Frame-Options", "DENY");

		if (model.getIsNew()) {
			if (billerPayService.isExist(model.getBillerCode())) {
				
				return new AjaxResponse(false, "Biller Code Duplicate.");
			}
		}

		billerPayService.save(model, getLoginSecUser(session));

		return new AjaxResponse(model);
	}

	private void putIntoRequest(Model model) {
		model.addAttribute("SELECTED_MENU", MENU);
		model.addAttribute("SELECTED_SUBMENU", PRIVILEDGE);
		List<StructureBillerPay> listBillerPay = billerPayService.getAll();
		model.addAttribute("listBillerPay", listBillerPay);
	}
}
