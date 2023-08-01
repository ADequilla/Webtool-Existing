package com.valuequest.controller.mbo;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.valuequest.common.AjaxResponse;
import com.valuequest.controller.BaseController;
import com.valuequest.controller.mbo.model.MboOperationModel;
import com.valuequest.entity.security.SecUser;

@Controller
@RequestMapping("/mbo/operation")
public class MboOperationController extends BaseController {

	final static String MENU 		= "MBO";
	final static String PRIVILEDGE 	= "MBO_OPERATION";
	private String BASE_VIEW 		= "09.mbo/";
	private String LIST_VIEW 		= "mbo-operation";

	@RequestMapping("/")
	public String index(Model model, HttpSession session) {

		 SecUser user = this.getLoginSecUser(session);

        user.setIsLogin(true);
        adminService.updateCekStatus(user, session.getId());
		if (getPriviledgeUser(session, PRIVILEDGE, VIEW)) {

			model.addAttribute("operationList", operationService.list());
			putIntoRequest(model);

			return BASE_VIEW + LIST_VIEW;
		}

		return getUnauthorizedPage();
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST, headers = { "content-type=application/json" })
	public @ResponseBody AjaxResponse save(@RequestBody List<MboOperationModel> operations, HttpSession session) {

		if (getPriviledgeUser(session, PRIVILEDGE, EDIT)) {
			
			operationService.save(operations, getLoginSecUser(session));
		} else {
			
			return new AjaxResponse(false, "Not Authorized : You are not authorized to access this page");
		}
		
		return new AjaxResponse(operations);
	}

	private void putIntoRequest(Model model) {
		model.addAttribute("SELECTED_MENU", MENU);
		model.addAttribute("SELECTED_SUBMENU", PRIVILEDGE);
	}
}