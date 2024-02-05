package com.valuequest.controller.monitoring;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
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
import com.valuequest.entity.ListUsedDevice;
import com.valuequest.entity.Lookup;
import com.valuequest.entity.MerchantEntity;
import com.valuequest.entity.security.SecUser;
import com.valuequest.controller.monitoring.model.ListUsedDeviceModel;

@Controller
@RequestMapping("/monitoring/list-used-device")
public class ListUsedDeviceController extends BaseController {

	final static String MENU 		= "MONITORING";
	final static String PRIVILEDGE 	= "LIST_USED_DEVICE";
	private String BASE_VIEW 		= "04.monitoring/";
	private String LIST_VIEW 		= "list-used-device";
	private String EDIT_VIEW 		= "list-used-device-edit";
 
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
			@RequestParam(required = false) String searchDateStart,
			@RequestParam(required = false) String searchDateEnd,
			@RequestParam(required = false) String deviceId,
			@RequestParam(required = false) String cif,
			@RequestParam(required = false) String mobileNumber,
			@RequestParam(required = false) String clientType,
			@RequestParam(required = false) String status,
			HttpSession session) {

		HashMap<String, Object> searchMap = new HashMap<>();
		searchMap.put("searchDateStart", searchDateStart);
		searchMap.put("searchDateEnd", searchDateEnd);
		searchMap.put("deviceId", deviceId);
		searchMap.put("cid", cif);
		searchMap.put("mobileNumber", mobileNumber);
		searchMap.put("clientType", clientType);
		searchMap.put("deviceStatus", status);

		return listUsedDeviceService.searchByMapCriteria(dataTables, searchMap);
	}
	
	@RequestMapping("/edit/{id}")
	public String edit(@PathVariable Long id, Model model, HttpSession session, HttpServletResponse response) {
		response.setHeader("X-Frame-Options", "DENY");
		System.out.println("#################### edit-data = "+id);
		
		if (getPriviledgeUser(session, PRIVILEDGE, EDIT)) {

			model.addAttribute("usedDevice", listUsedDeviceService.findById(id));
			model.addAttribute("isNew", false);
			putIntoRequest(model);

			return BASE_VIEW + EDIT_VIEW;
		}

		return getUnauthorizedPage();
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST, headers = { "content-type=application/json" })
	public @ResponseBody AjaxResponse save(@RequestBody ListUsedDeviceModel model, HttpServletRequest request, HttpSession session, HttpServletResponse response) {

		/*if (!listUsedDeviceService.dataCid(model.getCid())) {
			return new AjaxResponse(false, "Change Data can be enabled if the CIF have 2 or more device that was logged");
		}*/
		response.setHeader("X-Frame-Options", "DENY");
		ListUsedDevice usedDevice = listUsedDeviceService.findById(model.getId());
		String dataBefore, dataAfter;
		if (usedDevice.getDeviceStatus().equals(0L)) {
			dataBefore = "Unused";
		} else {
			dataBefore = "Used";
		}
		listUsedDeviceService.save(model, getLoginSecUser(session));

		ListUsedDevice usedDeviceAfter = listUsedDeviceService.findById(model.getId());
		if (usedDeviceAfter.getDeviceStatus().equals(0L)) {
			dataAfter = "Unused";
		} else {
			dataAfter = "Used";
		}
		
		String activity 	= "UPDATE_STATUS_DEVICE";
		long moduleId 		= 2014;
		auditTrailService.save(request, moduleId, activity, dataBefore, dataAfter, model.getCid(), getLoginSecUser(session));
		
		return new AjaxResponse(model);
	}
	
	private void putIntoRequest(Model model) {
		model.addAttribute("SELECTED_MENU", MENU);
		model.addAttribute("SELECTED_SUBMENU", PRIVILEDGE);	
		model.addAttribute("listClientType", genericService.lookup(Lookup.LOOKUP_CLIENT_TYPE_DEVICE));
		model.addAttribute("listStatusDevice", genericService.lookup(Lookup.LOOKUP_STATUS_DEVICE));
	}
}