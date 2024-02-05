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
import com.valuequest.controller.maintenance.model.ProductTypeModel;
import com.valuequest.entity.Lookup;
import com.valuequest.entity.ProductTypeEntity;
import com.valuequest.entity.security.SecUser;
import com.valuequest.services.ProductTypeService;
import com.valuequest.util.HttpClientMcu;

@Controller
@RequestMapping("/utilities/product-type")
public class ProductTypeController extends BaseController {
	
	final static String MENU 		= "UTILITIES";
	final static String PRIVILEDGE 	= "PRODUCT_TYPE";
	private String BASE_VIEW 		= "07.maintenance/";
	private String LIST_VIEW 		= "product-type-list";
	private String CREATE_VIEW 		= "product-type-create";
	private String EDIT_VIEW 		= "product-type-edit";
	
	private String SYNCHRONIZE		= "SYNCHRONIZE";
	
	@Autowired
	private ProductTypeService productTypeService;
	
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
			@RequestParam(required = false) String productTypeId,
			@RequestParam(required = false) String productTypeName,
			@RequestParam(required = false) String providerName, 
			HttpSession session, HttpServletResponse response) {
			response.setHeader("X-Frame-Options", "DENY");

			HashMap<String, Object> searchMap = new HashMap<>();
			searchMap.put("productTypeId", productTypeId);
			searchMap.put("productTypeName", productTypeName);
			searchMap.put("providerName", providerName);
	
			return productTypeService.searchByMapCriteria(dataTables, searchMap);
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
	
	@RequestMapping("/edit/{productTypeId}")
	public String edit(@PathVariable Long productTypeId, Model model, HttpSession session, HttpServletResponse response) {
		response.setHeader("X-Frame-Options", "DENY");

		if (getPriviledgeUser(session, PRIVILEDGE, EDIT)) {

			model.addAttribute("productType", productTypeService.findById(productTypeId));
			model.addAttribute("isNew", false);
			putIntoRequest(model);

			return BASE_VIEW + EDIT_VIEW;
		}

		return getUnauthorizedPage();
	}
	
	@RequestMapping(value = "/delete", method = RequestMethod.POST, headers = { "content-type=application/json" })
	public @ResponseBody AjaxResponse delete(@RequestBody List<ProductTypeModel> states, HttpSession session, HttpServletResponse response) {
		response.setHeader("X-Frame-Options", "DENY");

		if (getPriviledgeUser(session, PRIVILEDGE, DELETE)) {

			productTypeService.delete(states);

			return new AjaxResponse(true, "Product Type succesfully deleted.");
		} else {

			return new AjaxResponse(false, "You are not authorized to access this module.");
		}
	}
	
	@RequestMapping(value = "/save", method = RequestMethod.POST, headers = { "content-type=application/json" })
	public @ResponseBody AjaxResponse save(@RequestBody ProductTypeModel model, HttpSession session, HttpServletResponse response) {
		response.setHeader("X-Frame-Options", "DENY");

		if (model.getIsNew()) {
			if (productTypeService.isExist(model.getProductTypeId())) {
				
				return new AjaxResponse(false, "Product Type Id Duplicate.");
			}
		}

		productTypeService.save(model, getLoginSecUser(session));

		return new AjaxResponse(model);      
	}
	
	@RequestMapping("/synchronize/{productTypeId}")
	public String synchronize(@PathVariable Long productTypeId, HttpServletRequest request, HttpSession session, HttpServletResponse response) {
		response.setHeader("X-Frame-Options", "DENY");
		
			System.out.println("#################### sync-data = "+productTypeId);
			
			if (getPriviledgeUser(session, PRIVILEDGE, SYNCHRONIZE)) {
				
					HttpClientMcu httpClient = new HttpClientMcu();
					String url = genericService.getConfigValueByName("URL_PRODUCT_CATEGORY");
					
					System.out.println("########### url = "+url+productTypeId);
					
					String json = "";
					if (StringUtils.isNotBlank(url)) {
						json = httpClient.callMbo(url+productTypeId);
					}
					
					System.out.println("########### json = "+json);
					
					return "redirect:/utilities/product-type/";
			}
			return getUnauthorizedPage();
	}
	
	private void putIntoRequest(Model model) {
		model.addAttribute("SELECTED_MENU", MENU);
		model.addAttribute("SELECTED_SUBMENU", PRIVILEDGE);
		List<ProductTypeEntity> listProductType = productTypeService.getAll();
		model.addAttribute("listProductType", listProductType);
		model.addAttribute("listBillsParameter", genericService.lookup(Lookup.LOOKUP_BILLS_PARAMETER));
		model.addAttribute("listProvider", genericService.provider());
	}
}
