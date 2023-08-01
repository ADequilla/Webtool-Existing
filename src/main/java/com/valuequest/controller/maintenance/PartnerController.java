package com.valuequest.controller.maintenance;

import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
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
import com.valuequest.controller.maintenance.model.PartnerModel;
import com.valuequest.entity.Lookup;
import com.valuequest.entity.security.SecUser;
import com.valuequest.services.PartnerService;
import com.valuequest.util.HttpClientMcu;

@Controller
@RequestMapping("/utilities/partner")
public class PartnerController extends BaseController {

	final static String MENU 		= "UTILITIES";
	final static String PRIVILEDGE 	= "PARTNER";
	private String BASE_VIEW 		= "07.maintenance/";
	private String LIST_VIEW 		= "partner";
	private String CREATE_VIEW 		= "partner-create";
	private String EDIT_VIEW 		= "partner-edit";
	
	@Autowired
	private PartnerService partnerService;

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
			@RequestParam(required = false) String partnerId, 
			@RequestParam(required = false) String partnerName, 
			@RequestParam(required = false) String partnerDesc, 
			@RequestParam(required = false) String status, 
			HttpSession session) {

			HashMap<String, Object> searchMap = new HashMap<>();
			searchMap.put("partnerId", partnerId);
			searchMap.put("partnerName", partnerName);
			searchMap.put("partnerDesc", partnerDesc);
			searchMap.put("status", status);
	
			return partnerService.searchByMapCriteria(dataTables, searchMap);
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

	@RequestMapping("/edit/{partnerId}")
	public String edit(@PathVariable String partnerId, Model model, HttpSession session) {
		
		if (getPriviledgeUser(session, PRIVILEDGE, EDIT)) {

			model.addAttribute("partner", partnerService.findByPartnerId(partnerId));
			model.addAttribute("isNew", false);
			putIntoRequest(model);

			return BASE_VIEW + EDIT_VIEW;
		}

		return getUnauthorizedPage();
	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST, headers = { "content-type=application/json" })
	public @ResponseBody AjaxResponse delete(@RequestBody List<PartnerModel> states, HttpSession session) {

		if (getPriviledgeUser(session, PRIVILEDGE, DELETE)) {

			partnerService.delete(states);

			return new AjaxResponse(true, "Partner succesfully deleted.");
		} else {

			return new AjaxResponse(false, "You are not authorized to access this module.");
		}
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST, headers = { "content-type=application/json" })
	public @ResponseBody AjaxResponse save(@RequestBody PartnerModel model, HttpSession session) {

		if (model.getIsNew()) {
			if (partnerService.isExist(model.getPartnerId())) {
				
				return new AjaxResponse(false, "Partner Id Duplicate.");
			}
		}
		
		if (!model.getMerchantUrl().equals("")&& model.getMerchantPrefix().equals("")) {
			return new AjaxResponse(false, "Merchant Id Prefix is required if Merchant Payment Callback URL is filled");
		}
		if (model.getMerchantUrl().equals("") && !model.getMerchantPrefix().equals("")) {
			return new AjaxResponse(false, "Merchant Payment Callback URL is required if Merchant Id Prefix is filled");
		}
		
		HttpClientMcu httpClient = new HttpClientMcu();
		String url = genericService.getConfigValueByName("URL_E_WALLET");
				
		url = url + "accountNumber=" + model.getPartnerAccount() + "&accountName="+model.getPartnerName();
				
		System.out.println("##### url " + url);
				
		String json = "";
		if (StringUtils.isNotBlank(url)) {
			json = httpClient.callMbo(url);
		}
				
		System.out.println("########### json = "+json);
		
		partnerService.save(model, getLoginSecUser(session));

		return new AjaxResponse(model);
	}
	

	private void putIntoRequest(Model model) {
		model.addAttribute("SELECTED_MENU", MENU);
		model.addAttribute("SELECTED_SUBMENU", PRIVILEDGE);
		model.addAttribute("listPartner", genericService.lookup(Lookup.LOOKUP_PARTNER));
		model.addAttribute("listMriGroup", genericService.lookup(Lookup.LOOKUP_MRI_GROUP));
	}
}
