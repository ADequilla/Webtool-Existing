package com.valuequest.controller.maintenance;

import java.util.HashMap;
import java.util.List;

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
import com.valuequest.controller.maintenance.model.MobilePrefixModel;
import com.valuequest.entity.Lookup;
import com.valuequest.entity.StructureMobilePrefix;
import com.valuequest.entity.security.SecUser;
import com.valuequest.services.MobilePrefixService;

@Controller
@RequestMapping("/utilities/mobile-prefix")
public class MobilePrefixController extends BaseController {

	final static String MENU 		= "UTILITIES";
	final static String PRIVILEDGE 	= "MOBILE_PREFIX";
	private String BASE_VIEW 		= "07.maintenance/";
	private String LIST_VIEW 		= "mobilePrefix";
	private String CREATE_VIEW 		= "mobilePrefix-create";
	private String EDIT_VIEW 		= "mobilePrefix-edit";
	
	@Autowired
	private MobilePrefixService mobilePrefixService;
	
	//@Autowired
	//private BillerCategoryService billerCategoryService;

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
			@RequestParam(required = false) String prefixValue,
			@RequestParam(required = false) String productName, 
			HttpSession session) {

			HashMap<String, Object> searchMap = new HashMap<>();
			searchMap.put("prefixValue", prefixValue);
			searchMap.put("productName", productName);
	
			return mobilePrefixService.searchByMapCriteria(dataTables, searchMap);
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

	@RequestMapping("/edit/{id}")
	public String edit(@PathVariable Long id, Model model, HttpSession session) {

		if (getPriviledgeUser(session, PRIVILEDGE, EDIT)) {

			model.addAttribute("mobilePrefix", mobilePrefixService.findById(id));
			model.addAttribute("isNew", false);
			putIntoRequest(model);

			return BASE_VIEW + EDIT_VIEW;
		}

		return getUnauthorizedPage();
	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST, headers = { "content-type=application/json" })
	public @ResponseBody AjaxResponse delete(@RequestBody List<MobilePrefixModel> states, HttpSession session) {

		if (getPriviledgeUser(session, PRIVILEDGE, DELETE)) {

			mobilePrefixService.delete(states);

			return new AjaxResponse(true, "Mobile Prefix succesfully deleted.");
		} else {

			return new AjaxResponse(false, "You are not authorized to access this module.");
		}
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST, headers = { "content-type=application/json" })
	public @ResponseBody AjaxResponse save(@RequestBody MobilePrefixModel model, HttpSession session) {

		if (model.getIsNew()) {
			if (mobilePrefixService.isExist(model.getPrefixValue())) {
				
				return new AjaxResponse(false, "Prefix Code Duplicate.");
			}
		}

		mobilePrefixService.save(model, getLoginSecUser(session));

		return new AjaxResponse(model);
	}

	private void putIntoRequest(Model model) {
		model.addAttribute("SELECTED_MENU", MENU);
		model.addAttribute("SELECTED_SUBMENU", PRIVILEDGE);
		List<StructureMobilePrefix> listMobilePrefix = mobilePrefixService.getAll();
		model.addAttribute("listMobilePrefix", listMobilePrefix);
		model.addAttribute("listProductCategory", genericService.productCategory());
	}
}
