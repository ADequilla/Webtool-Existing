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
import com.valuequest.controller.maintenance.model.ProductDenomModel;
import com.valuequest.entity.StructureProductPay;
import com.valuequest.services.ProductDenomService;
import com.valuequest.services.ProductPayService;
import com.valuequest.services.ProviderDenomService;

@Controller
@RequestMapping("/utilities/product-denom")
public class ProductDenomController extends BaseController {

	final static String MENU 		= "UTILITIES";
	final static String PRIVILEDGE 	= "PRODUCT_DENOM";
	private String BASE_VIEW 		= "07.maintenance/";
	private String LIST_VIEW 		= "productDenom";
	private String CREATE_VIEW 		= "productDenom-create";
	private String EDIT_VIEW 		= "productDenom-edit";
	
	@Autowired
	private ProductDenomService productDenomService;
	
	@Autowired
	private ProviderDenomService providerDenomService;
	
	@Autowired
	private ProductPayService productPayService;

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
			@RequestParam(required = false) String productCode,
			@RequestParam(required = false) String productName, 
			HttpSession session) {

			HashMap<String, Object> searchMap = new HashMap<>();
			searchMap.put("productCode", productCode);
			searchMap.put("productName", productName);
	
			return productDenomService.searchByMapCriteria(dataTables, searchMap);
	}

	@RequestMapping("/create")
	public String create(Model model, HttpSession session) {

		if (getPriviledgeUser(session, PRIVILEDGE, NEW)) {
			
			model.addAttribute("id", null);
			model.addAttribute("isNew", true);
			putIntoRequest(model);
			
			return BASE_VIEW + CREATE_VIEW;
		}

		return getUnauthorizedPage();
	}

	@RequestMapping("/edit/{id}")
	public String edit(@PathVariable Long id, Model model, HttpSession session) {

		if (getPriviledgeUser(session, PRIVILEDGE, EDIT)) {

			model.addAttribute("productDenom", productDenomService.findProductProviderDenom(id));
			model.addAttribute("id", id);
			model.addAttribute("isNew", false);
			putIntoRequest(model);

			return BASE_VIEW + EDIT_VIEW;
		}

		return getUnauthorizedPage();
	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST, headers = { "content-type=application/json" })
	public @ResponseBody AjaxResponse delete(@RequestBody List<ProductDenomModel> states, HttpSession session) {

		if (getPriviledgeUser(session, PRIVILEDGE, DELETE)) {

			productDenomService.delete(states);
			providerDenomService.delete(states);
			
			return new AjaxResponse(true, "Product and Provider Denom succesfully deleted.");
		} else {

			return new AjaxResponse(false, "You are not authorized to access this module.");
		}
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST, headers = { "content-type=application/json" })
	public @ResponseBody AjaxResponse save(@RequestBody ProductDenomModel model, HttpSession session) {

		if (model.getIsNew()) {
			if (productDenomService.isExist(model.getId())) {
				
				return new AjaxResponse(false, "Id Denom Duplicate.");
			}
		}

		productDenomService.save(model, getLoginSecUser(session));

		return new AjaxResponse(model);
	}

	private void putIntoRequest(Model model) {
		model.addAttribute("SELECTED_MENU", MENU);
		model.addAttribute("SELECTED_SUBMENU", PRIVILEDGE);
		List<StructureProductPay> listProductPay = productPayService.getAll();
		model.addAttribute("listProductPay", listProductPay);
	}
}
