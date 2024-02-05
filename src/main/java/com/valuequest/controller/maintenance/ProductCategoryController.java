package com.valuequest.controller.maintenance;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
import com.valuequest.controller.maintenance.model.ProductCategoryModel;
import com.valuequest.entity.Lookup;
import com.valuequest.entity.StructureProductCategory;
import com.valuequest.entity.security.SecUser;
import com.valuequest.services.ProductCategoryService;
import com.valuequest.util.HttpClientMcu;

@Controller
@RequestMapping("/utilities/product-category")
public class ProductCategoryController extends BaseController {

	final static String MENU 		= "UTILITIES";
	final static String PRIVILEDGE 	= "PRODUCT_CATEGORY";
	private String BASE_VIEW 		= "07.maintenance/";
	private String LIST_VIEW 		= "productCategory";
	private String CREATE_VIEW 		= "productCategory-create";
	private String EDIT_VIEW 		= "productCategory-edit";
	
	@Autowired
	private ProductCategoryService productCategoryService;
	
	//@Autowired
	//private BillerCategoryService billerCategoryService;

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
			@RequestParam(required = false) String productCategoryId,
			@RequestParam(required = false) String productCategoryName, 
			@RequestParam(required = false) String productTypeName, 
			@RequestParam(required = false) String providerName,
			HttpSession session, HttpServletResponse response) {
			response.setHeader("X-Frame-Options", "DENY");

			HashMap<String, Object> searchMap = new HashMap<>();
			searchMap.put("productCategoryId", productCategoryId);
			searchMap.put("productCategoryName", productCategoryName);
			searchMap.put("productTypeName", productTypeName);
			searchMap.put("providerName", providerName);
			
			return productCategoryService.searchByMapCriteria(dataTables, searchMap);
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

	@RequestMapping("/edit/{productCategoryId}")
	public String edit(@PathVariable Long productCategoryId, Model model, HttpSession session, HttpServletResponse response) {
		response.setHeader("X-Frame-Options", "DENY");
		
		System.out.println("#################### edit-data = "+productCategoryId);
		
		if (getPriviledgeUser(session, PRIVILEDGE, EDIT)) {

			model.addAttribute("productCategory", productCategoryService.findById(productCategoryId));
			model.addAttribute("isNew", false);
			putIntoRequest(model);

			return BASE_VIEW + EDIT_VIEW;
		}

		return getUnauthorizedPage();
	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST, headers = { "content-type=application/json" })
	public @ResponseBody AjaxResponse delete(@RequestBody List<ProductCategoryModel> states, HttpSession session, HttpServletResponse response) {
		response.setHeader("X-Frame-Options", "DENY");

		if (getPriviledgeUser(session, PRIVILEDGE, DELETE)) {

			productCategoryService.delete(states);

			return new AjaxResponse(true, "Product Category succesfully deleted.");
		} else {

			return new AjaxResponse(false, "You are not authorized to access this module.");
		}
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST, headers = { "content-type=application/json" })
	public @ResponseBody AjaxResponse save(@RequestBody ProductCategoryModel model, HttpSession session, HttpServletResponse response) {
		response.setHeader("X-Frame-Options", "DENY");

		if (model.getIsNew()) {
			if (productCategoryService.isExist(model.getProductCategoryId())) {
				
				return new AjaxResponse(false, "Product Code Duplicate.");
			}
		}

		productCategoryService.save(model, getLoginSecUser(session));

		return new AjaxResponse(model);
	}

	private void putIntoRequest(Model model) {
		model.addAttribute("SELECTED_MENU", MENU);
		model.addAttribute("SELECTED_SUBMENU", PRIVILEDGE);
		List<StructureProductCategory> listProductCategory = productCategoryService.getAll();
		model.addAttribute("listProductCategory", listProductCategory);
		model.addAttribute("listBillsParameter", genericService.lookup(Lookup.LOOKUP_BILLS_PARAMETER));
		model.addAttribute("listProductType", genericService.productType());
		model.addAttribute("listProvider", genericService.provider());
	}
}
