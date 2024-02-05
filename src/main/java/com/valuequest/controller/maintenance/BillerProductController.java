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
import com.valuequest.controller.maintenance.model.BillerProductModel;
import com.valuequest.entity.Lookup;
import com.valuequest.entity.MCUserMapper;
import com.valuequest.entity.StructureBillerCategory;
import com.valuequest.entity.ViewBillerProduct;
import com.valuequest.entity.security.SecUser;
import com.valuequest.services.BillerCategoryService;
import com.valuequest.services.BillerProductService;
import com.valuequest.util.GenerateReport;
import com.valuequest.util.HttpClientMcu;

import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Controller
@RequestMapping("/utilities/biller-product")
public class BillerProductController extends BaseController {

	final static String MENU 		= "UTILITIES";
	final static String PRIVILEDGE 	= "BILLER_PRODUCT";
	private String BASE_VIEW 		= "07.maintenance/";
	private String LIST_VIEW 		= "billerProduct";
	private String CREATE_VIEW 		= "billerProduct-create";
	private String EDIT_VIEW 		= "billerProduct-edit";  
	private String REPORT_NAME		= "biller-product";
	private String TEMPLATE_NAME 	= "BillerProductExtract";
	private String GENERATE_VIEW 	= "billerProduct-generate";
	
	@Autowired
	private BillerProductService billerProductService;
	
	@Autowired
	private BillerCategoryService billerCategoryService;

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

	@RequestMapping("/report")
	public String report(Model model, HttpSession session, HttpServletResponse response) {
		response.setHeader("X-Frame-Options", "DENY");

		if (getPriviledgeUser(session, PRIVILEDGE, DOWNLOAD)) {

			putIntoRequest(model);

			return BASE_VIEW + GENERATE_VIEW;
		}

		return getUnauthorizedPage();
	}
	
	@RequestMapping("/search")
	public @ResponseBody DataTables search(DataTables dataTables, 
			@RequestParam(required = false) String billerProductId,
			@RequestParam(required = false) String billerProductName, 
			@RequestParam(required = false) String productCategoryName, 
			@RequestParam(required = false) String providerName,
			HttpSession session, HttpServletResponse response) {
			response.setHeader("X-Frame-Options", "DENY");

			HashMap<String, Object> searchMap = new HashMap<>();
			searchMap.put("billerProductId", billerProductId);
			searchMap.put("billerProductName", billerProductName);
			searchMap.put("productCategoryName", productCategoryName);
			searchMap.put("providerName", providerName);
	
			return billerProductService.searchByMapCriteria(dataTables, searchMap);
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

	@RequestMapping("/edit/{billerProductId}")
	public String edit(@PathVariable Long billerProductId, Model model, HttpSession session, HttpServletResponse response) {
		response.setHeader("X-Frame-Options", "DENY");

		if (getPriviledgeUser(session, PRIVILEDGE, EDIT)) {

			model.addAttribute("billerProduct", billerProductService.findById(billerProductId));
			model.addAttribute("isNew", false);
			putIntoRequest(model);

			return BASE_VIEW + EDIT_VIEW;
		}

		return getUnauthorizedPage();
	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST, headers = { "content-type=application/json" })
	public @ResponseBody AjaxResponse delete(@RequestBody List<BillerProductModel> states, HttpSession session, HttpServletResponse response) {
		response.setHeader("X-Frame-Options", "DENY");

		if (getPriviledgeUser(session, PRIVILEDGE, DELETE)) {

			billerProductService.delete(states);

			return new AjaxResponse(true, "Biller Product succesfully deleted.");
		} else {

			return new AjaxResponse(false, "You are not authorized to access this module.");
		}
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST, headers = { "content-type=application/json" })
	public @ResponseBody AjaxResponse save(@RequestBody BillerProductModel model, HttpSession session, HttpServletResponse response) {
		response.setHeader("X-Frame-Options", "DENY");

		if (model.getIsNew()) {
			if (billerProductService.isExist(model.getBillerProductId())) {
				
				return new AjaxResponse(false, "Product Code Duplicate.");
			}
		}

		billerProductService.save(model, getLoginSecUser(session));

		return new AjaxResponse(model);
	}
	
	@RequestMapping("/generate")   
	public void generate(
			@RequestParam(required = false) String billerProductId,
			@RequestParam(required = false) String billerProductName, 
			@RequestParam(required = false) String productCategoryName, 
			@RequestParam(required = false) String providerName,
			HttpSession session, HttpServletResponse response) {
			response.setHeader("X-Frame-Options", "DENY");

		HashMap<String, Object> map = new HashMap<>();
		map.put("billerProductId", billerProductId);
		map.put("billerProductName", billerProductName);
		map.put("productCategoryName", productCategoryName);
		map.put("providerName", providerName);
		

		List<ViewBillerProduct> billerProduct = billerProductService.searchByMapCriteria(map);

		JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(billerProduct);
		GenerateReport report = new GenerateReport();
		report.generatePdfReport(TEMPLATE_NAME, REPORT_NAME, map, beanColDataSource, "CSV", response);
	}
	
	
	private void putIntoRequest(Model model) {
		model.addAttribute("SELECTED_MENU", MENU);
		model.addAttribute("SELECTED_SUBMENU", PRIVILEDGE);
		List<StructureBillerCategory> listBillerCategory = billerCategoryService.getAll();
		model.addAttribute("listBillerCategory", listBillerCategory);
		model.addAttribute("listBillsParameter", genericService.lookup(Lookup.LOOKUP_BILLS_PARAMETER));
		model.addAttribute("listProductCategory", genericService.productCategory());
		model.addAttribute("listProductCategoryBiller", genericService.productCategoryBiller());
		model.addAttribute("listProvider", genericService.provider());
	}
}


