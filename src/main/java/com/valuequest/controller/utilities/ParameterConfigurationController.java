package com.valuequest.controller.utilities;

import java.util.HashMap;

import javax.servlet.http.HttpServletResponse;
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
import com.valuequest.entity.Lookup;
import com.valuequest.entity.ParamConfig;
import com.valuequest.entity.security.SecUser;

@Controller
@RequestMapping("/utilities/config")
public class ParameterConfigurationController extends BaseController {

	final static String MENU 		= "UTILITIES";
	final static String PRIVILEDGE 	= "PARAMETER_CONFIG";
	private String BASE_VIEW 		= "05.utilities/";
	private String LIST_VIEW 		= "param-config";
	private String EDIT_VIEW 		= "param-config-edit";

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
			@RequestParam(required = false) String configType,
			@RequestParam(required = false) String configName, 
			HttpSession session) {

		HashMap<String, Object> searchMap = new HashMap<>();
		searchMap.put("type", configType);
		searchMap.put("name", configName);

		return paramConfigService.searchByMapCriteria(dataTables, searchMap);
	}
	
	@RequestMapping("/create")
	public String create(Model model, HttpSession session, HttpServletResponse response) {
		response.setHeader("X-Frame-Options", "DENY");
		if (getPriviledgeUser(session, PRIVILEDGE, NEW)) {

			putIntoRequest(model);

			return BASE_VIEW + EDIT_VIEW;
		}

		return getUnauthorizedPage();
	}

	@RequestMapping("/edit/{id}")
	public String edit(@PathVariable Long id, Model model, HttpSession session, HttpServletResponse response) {
		response.setHeader("X-Frame-Options", "DENY");
		if (getPriviledgeUser(session, PRIVILEDGE, EDIT)) {

			ParamConfig config = (ParamConfig) paramConfigService.findById(id);
			if ("LIST".equals(config.getValueType())) {
				model.addAttribute("configValueList", genericService.lookup(config.getValueLookup()));
			}
			model.addAttribute("paramConfig", paramConfigService.findById(id));
			model.addAttribute("config", config);
			putIntoRequest(model);

			return BASE_VIEW + EDIT_VIEW;
		}

		return getUnauthorizedPage();
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST, headers = { "content-type=application/json" })
	public @ResponseBody AjaxResponse save(@RequestBody ParamConfig model, HttpSession session, HttpServletResponse response) {
		response.setHeader("X-Frame-Options", "DENY");
		AjaxResponse ajaxResponse = new AjaxResponse();
		Boolean isDuplicated = paramConfigService.checkingDuplicateParamName(model.getName(),  model.getId());
		
		if(isDuplicated) {
			ajaxResponse.setData(model);
			ajaxResponse.setMessage("Parameter Name could not same.");
			ajaxResponse.setSuccess(false);
			return ajaxResponse;
		}else {
			paramConfigService.save(model, getLoginSecUser(session));
			refreshParameters("ALL");
			return new AjaxResponse(model);
		}
		 
//		paramConfigService.save(model, getLoginSecUser(session));
//
//		if (model.getType().equals("MBL_AGNT")) {
//			refreshParameters(Client.CLIENT_TYPE_AGENT);
//		} else if (model.getType().equals("CUST_SYSTEM") || model.getType().equals("CUST_SYSTEM_NM")) {
//			refreshParameters(Client.CLIENT_TYPE_MEMBER);
//		} else if (model.getType().equals("MCO_SYSTEM")) {
//			refreshParameters(Client.CLIENT_TYPE_MC);
//		}
//
//		return new AjaxResponse(model);
	}

	private void putIntoRequest(Model model) {
		model.addAttribute("SELECTED_MENU", MENU);
		model.addAttribute("SELECTED_SUBMENU", PRIVILEDGE);
		model.addAttribute("listConfigType", genericService.lookup(Lookup.LOOKUP_PARAMCONFIG_TYPE));
	}
}