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
import com.valuequest.controller.maintenance.model.LoadProductModel;
import com.valuequest.entity.Lookup;
import com.valuequest.entity.security.SecUser;
import com.valuequest.services.LoadProductService;
import com.valuequest.util.HttpClientMcu;

@Controller
@RequestMapping("/utilities/load-product")
public class LoadProductController extends BaseController {

	final static String MENU 		= "UTILITIES";
	final static String PRIVILEDGE 	= "LOAD_PRODUCT";
	private String BASE_VIEW 		= "07.maintenance/";
	private String LIST_VIEW 		= "loadProduct";
	private String CREATE_VIEW 		= "loadProduct-create";
	private String EDIT_VIEW 		= "loadProduct-edit";
	
	@Autowired
	private LoadProductService loadProductService;

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
			@RequestParam(required = false) String loadProductId,
			@RequestParam(required = false) String loadProductName, 
			@RequestParam(required = false) String productCategoryName, 
			HttpSession session) {

			HashMap<String, Object> searchMap = new HashMap<>();
			searchMap.put("loadProductId", loadProductId);
			searchMap.put("loadProductName", loadProductName);
			searchMap.put("productCategoryName", productCategoryName);
	
			return loadProductService.searchByMapCriteria(dataTables, searchMap);
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

	@RequestMapping("/edit/{loadProductId}")
	public String edit(@PathVariable Long loadProductId, Model model, HttpSession session) {

		if (getPriviledgeUser(session, PRIVILEDGE, EDIT)) {

			model.addAttribute("loadProduct", loadProductService.findById(loadProductId));
			model.addAttribute("isNew", false);
			putIntoRequest(model);

			return BASE_VIEW + EDIT_VIEW;
		}

		return getUnauthorizedPage();
	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST, headers = { "content-type=application/json" })
	public @ResponseBody AjaxResponse delete(@RequestBody List<LoadProductModel> states, HttpSession session) {

		if (getPriviledgeUser(session, PRIVILEDGE, DELETE)) {

			loadProductService.delete(states);

			return new AjaxResponse(true, "Load Product succesfully deleted.");
		} else {

			return new AjaxResponse(false, "You are not authorized to access this module.");
		}
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST, headers = { "content-type=application/json" })
	public @ResponseBody AjaxResponse save(@RequestBody LoadProductModel model, HttpSession session) {

		if (model.getIsNew()) {
			if (loadProductService.isExist(model.getLoadProductId())) {
				
				return new AjaxResponse(false, "Product Code Duplicate.");
			}
		}

		loadProductService.save(model, getLoginSecUser(session));

		return new AjaxResponse(model);
	}
	

	private void putIntoRequest(Model model) {
		model.addAttribute("SELECTED_MENU", MENU);
		model.addAttribute("SELECTED_SUBMENU", PRIVILEDGE);
		model.addAttribute("listBillsParameter", genericService.lookup(Lookup.LOOKUP_BILLS_PARAMETER));
		model.addAttribute("listProductCategory", genericService.productCategory());
		model.addAttribute("listProductCategoryLoad", genericService.productCategoryLoad());
	}
}
