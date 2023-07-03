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
import com.valuequest.controller.maintenance.model.ProductPayModel;
import com.valuequest.entity.StructureBillerPay;
import com.valuequest.services.BillerPayService;
import com.valuequest.services.ProductPayService;

@Controller
@RequestMapping("/utilities/product-pay")
public class ProductPayController extends BaseController {

	final static String MENU 		= "UTILITIES";
	final static String PRIVILEDGE 	= "PRODUCT_PAY";
	private String BASE_VIEW 		= "07.maintenance/";
	private String LIST_VIEW 		= "productPay";
	private String CREATE_VIEW 		= "productPay-create";
	private String EDIT_VIEW 		= "productPay-edit";
	
	@Autowired
	private ProductPayService productPayService;
	
	@Autowired
	private BillerPayService billerPayService;

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
	
			return productPayService.searchByMapCriteria(dataTables, searchMap);
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

	@RequestMapping("/edit/{productCode}")
	public String edit(@PathVariable String productCode, Model model, HttpSession session) {

		if (getPriviledgeUser(session, PRIVILEDGE, EDIT)) {

			model.addAttribute("productPay", productPayService.findByCode(productCode));
			model.addAttribute("isNew", false);
			putIntoRequest(model);

			return BASE_VIEW + EDIT_VIEW;
		}

		return getUnauthorizedPage();
	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST, headers = { "content-type=application/json" })
	public @ResponseBody AjaxResponse delete(@RequestBody List<ProductPayModel> states, HttpSession session) {

		if (getPriviledgeUser(session, PRIVILEDGE, DELETE)) {

			productPayService.delete(states);

			return new AjaxResponse(true, "Product Pay succesfully deleted.");
		} else {

			return new AjaxResponse(false, "You are not authorized to access this module.");
		}
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST, headers = { "content-type=application/json" })
	public @ResponseBody AjaxResponse save(@RequestBody ProductPayModel model, HttpSession session) {

		if (model.getIsNew()) {
			if (productPayService.isExist(model.getProductCode())) {
				
				return new AjaxResponse(false, "Product Code Duplicate.");
			}
		}

		productPayService.save(model, getLoginSecUser(session));

		return new AjaxResponse(model);
	}

	private void putIntoRequest(Model model) {
		model.addAttribute("SELECTED_MENU", MENU);
		model.addAttribute("SELECTED_SUBMENU", PRIVILEDGE);
		List<StructureBillerPay> listBillerPay = billerPayService.getAll();
		model.addAttribute("listBillerPay", listBillerPay);
	}
}
