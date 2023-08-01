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
import com.valuequest.controller.maintenance.model.ProviderModel;
import com.valuequest.entity.Lookup;
import com.valuequest.entity.StructureProvider;
import com.valuequest.entity.security.SecUser;
import com.valuequest.services.ProviderService;
import com.valuequest.util.HttpClientMcu;

@Controller
@RequestMapping("/utilities/provider")
public class ProviderController extends BaseController {

	final static String MENU 		= "UTILITIES";
	final static String PRIVILEDGE 	= "PROVIDER";
	private String BASE_VIEW 		= "07.maintenance/";
	private String LIST_VIEW 		= "provider";
	private String CREATE_VIEW 		= "provider-create";
	
	private String SYNCHRONIZE		= "SYNCHRONIZE";
	
	@Autowired
	private ProviderService providerService;

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
			@RequestParam(required = false) String id,
			@RequestParam(required = false) String providerName, 
			HttpSession session) {

		HashMap<String, Object> searchMap = new HashMap<>();
		searchMap.put("id", id);
		searchMap.put("providerName", providerName);

		return providerService.searchByMapCriteria(dataTables, searchMap);
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

			model.addAttribute("provider", providerService.findById(id));
			model.addAttribute("isNew", false);
			putIntoRequest(model);

			return BASE_VIEW + CREATE_VIEW;
		}

		return getUnauthorizedPage();
	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST, headers = { "content-type=application/json" })
	public @ResponseBody AjaxResponse delete(@RequestBody List<ProviderModel> states, HttpSession session) {

		if (getPriviledgeUser(session, PRIVILEDGE, DELETE)) {

			providerService.delete(states);

			return new AjaxResponse(true, "Provider succesfully deleted.");
		} else {

			return new AjaxResponse(false, "You are not authorized to access this module.");
		}
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST, headers = { "content-type=application/json" })
	public @ResponseBody AjaxResponse save(@RequestBody ProviderModel model, HttpSession session) {

		if (model.getIsNew()) {
			if (providerService.isExist(model.getId())) {
				
				return new AjaxResponse(false, "Provider Id Duplicate.");
			}
		}

		providerService.save(model, getLoginSecUser(session));

		return new AjaxResponse(model);
	}

	@RequestMapping("/synchronize/{providerId}")
	public String synchronize(@PathVariable Long providerId, HttpServletRequest request, HttpSession session) {
		
			System.out.println("#################### sync-data = "+providerId);
			
			if (getPriviledgeUser(session, PRIVILEDGE, SYNCHRONIZE)) {
				
					HttpClientMcu httpClient = new HttpClientMcu();
					String url = genericService.getConfigValueByName("URL_PRODUCT_TYPE");
					
					System.out.println("########### url = "+url);
					
					String json = "";
					if (StringUtils.isNotBlank(url)) {
						json = httpClient.callMbo(url);
					}
					
					System.out.println("########### json = "+json);
					
					return "redirect:/utilities/provider/";
			}
			return getUnauthorizedPage();
	}
	
	private void putIntoRequest(Model model) {
		model.addAttribute("SELECTED_MENU", MENU);
		model.addAttribute("SELECTED_SUBMENU", PRIVILEDGE);
		List<StructureProvider> listProvider = providerService.getAll();
		model.addAttribute("listProvider", listProvider);
		model.addAttribute("listBillsParameter", genericService.lookup(Lookup.LOOKUP_BILLS_PARAMETER));
	}
}
