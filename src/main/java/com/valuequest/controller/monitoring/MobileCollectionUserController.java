package com.valuequest.controller.monitoring;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
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
import com.valuequest.controller.monitoring.model.ProfileModel;
import com.valuequest.entity.Client;
import com.valuequest.entity.Lookup;
import com.valuequest.entity.StructureBranch;
import com.valuequest.entity.StructureCenter;
import com.valuequest.entity.StructureUnit;
import com.valuequest.entity.ViewClient;
import com.valuequest.entity.security.SecUser;
import com.valuequest.services.BranchService;
import com.valuequest.services.CenterService;
import com.valuequest.services.ClientService;
import com.valuequest.services.UnitService;
import com.valuequest.util.HttpClientMcu;

/**
 * 
 * @author Gilang ZW
 *
 */

@Controller
@RequestMapping("/monitoring/mobileCollectionUser")
public class MobileCollectionUserController extends BaseController {

	final static String MENU 		= "MONITORING";
	final static String PRIVILEDGE 	= "MOBILE_COLLECTION_USER";
	private String BASE_VIEW 		= "04.monitoring/";
	private String LIST_VIEW 		= "mobile_collection_user_list";
	private String EDIT_VIEW		= "mobile_collection_user_edit";
	private String ADD_VIEW			= "mobile_collection_user_add";
	
	private String EDIT_DATA 		= "EDIT";
	private String SAVE_DATA    	= "NEW";
	private String RESET_PASSWORD 	= "RESET PASSWORD";
	private String RESET_MPIN 		= "RESET PIN";
	private String DEACTIVATE		= "DEACTIVATE";
	private String SYNCHRONIZE		= "SYNCHRONIZE";
	private String C_VIEW_USERNAME  = "USERNAME";
	
	@Autowired
	private BranchService branchService;
	
	@Autowired
	private CenterService centerService;
	
	@Autowired
	private UnitService unitService;
	
	@Autowired
	private ClientService clientService;
	
	private ViewClient viewClient;
	
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
			@RequestParam(required = false) String customerId,
			@RequestParam(required = false) String mobileNo,
			HttpSession session) {

		HashMap<String, Object> searchMap = new HashMap<>();
		searchMap.put("customerId", customerId);
		searchMap.put("mobileNo", mobileNo);
		
		System.out.println("################MASUK SEARCH");
		
		return clientService.searchByMapCriteriaMCollectionUser(dataTables, searchMap);
	}
	
	@RequestMapping("/create")
	public String create(Model model, HttpSession session) {
		
		if (getPriviledgeUser(session, PRIVILEDGE, NEW)) {
			
			putIntoRequest(model);
			
			return BASE_VIEW + ADD_VIEW;
		}
		
		return getUnauthorizedPage();
	}
	
	@RequestMapping(value = "/get", method = RequestMethod.POST, headers = { "content-type=application/json" })
	public @ResponseBody AjaxResponse get(@RequestBody ProfileModel model, HttpSession session) {
		ViewClient view = new ViewClient();
		
		ViewClient vcc = clientService.findByCidAndTypeCode(model.getString());
		if(vcc == null){
			HttpClientMcu httpClient = new HttpClientMcu();
			String url = genericService.getConfigValueByName("URL_WEBCOLLUSER");
			
			System.out.println("########### CID = "+model.getString());
			System.out.println("########### url = "+url + model.getString());
			
			String json = "";
			if (StringUtils.isNotBlank(url)) {
				json = httpClient.callMbo(url + model.getString());
			}
			
			System.out.println("########### json = "+json);
			
			try{
				if(json.contains("errorCode")){
					JSONParser parser = new JSONParser();
					Object obj = parser.parse(json);
					JSONObject oj =  (JSONObject) obj;
					if(oj != null){
						System.out.println("jo = "+oj.toJSONString());
						String err = (String) oj.get("message");
						return new AjaxResponse(false, err);
					}
				}else{
					JSONParser parser = new JSONParser();
					Object obj = parser.parse(json);
					JSONObject oj =  (JSONObject) obj;
					view.setCid((String) oj.get("customerNo"));
					
					view.setFullname((String) oj.get("name"));
					view.setFirstname((String) oj.get("firstName"));
					view.setMiddlename((String) oj.get("middleName"));
					view.setLastname((String) oj.get("lastName"));
					view.setEmail((String) oj.get("email"));
					view.setMobileNo((String) oj.get("sms"));
					
					view.setAccStatusCode("INACTIVE");
					view.setAccStatusDesc("INACTIVE");
					
					try{
						view.setBranchCode((String) oj.get("branchCode"));
						StructureBranch b = branchService.findByCode(view.getBranchCode());
						view.setBranchDesc(b.getDescription());
					}catch (Exception e) {
						e.printStackTrace();
					}
					
					try{
						view.setCenterCode((String) oj.get("centerCode"));
						StructureCenter c = centerService.findByCode(view.getCenterCode());
						view.setCenterDesc(c.getDescription());
					}catch (Exception e) {
						e.printStackTrace();
					}
					
					try{
						view.setUnitCode((String) oj.get("unitCode"));
						StructureUnit u = unitService.findByCode(view.getUnitCode());
						view.setUnitDesc(u.getDescription());
					}catch (Exception e) {
						e.printStackTrace();
					}
					
					SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
					Date d = sdf.parse((String)oj.get("birthDate"));
					view.setDob(d);
					view.setAddress((String) oj.get("address"));
					
					viewClient = new ViewClient();
					
					viewClient = view;
				}
			}catch (Exception e) {
				e.printStackTrace();
			}
		}else{
			return new AjaxResponse(false, "Customer Id Already Exist");
		}
		
		return new AjaxResponse(view);
	}
	
	

	@RequestMapping("/edit/{id}")
	public String edit(@PathVariable Long id, Model model, HttpSession session) {
		
		System.out.println("################################################# id = "+id);
		
		ProfileModel pm = new ProfileModel();
		pm.setField("id");
		pm.setNumber(id);
		
		if (getPriviledgeUser(session, PRIVILEDGE, EDIT)) {

			ViewClient view = clientService.profileMcu(pm);
			System.out.println("##################################################### clientService.profileMcu(pm) = "+view);
			view.setId(id);
			view.setBirthDateStr(new SimpleDateFormat("yyyy-MM-dd").format(view.getDob()));
			model.addAttribute("view", view);
			
			putIntoRequest(model);

			return BASE_VIEW + EDIT_VIEW;
		}

		return getUnauthorizedPage();
	}
	
	@RequestMapping("/save-data/{clientType}")
	public @ResponseBody AjaxResponse saveData(@PathVariable String clientType, HttpServletRequest request, HttpSession session) {
			
			System.out.println("#################### clientType = "+clientType);
			
			if (getPriviledgeUser(session, PRIVILEDGE, SAVE_DATA)) {
				if(viewClient != null){
					try{
						String[] cp = clientType.split("\\|");
						
						if(cp[1] != null){
							ViewClient vc = clientService.findByAccountAndTypeCode(cp[1]);
							if(vc != null){
								return new AjaxResponse(false, "Account Number is Already Exist with CIF = "+vc.getCid());
							}
							viewClient.setAccount(cp[1]);
						} else {
							return new AjaxResponse(false, "Account Number Is Required");
						}
						
						if (!cp[0].equals("")){
							viewClient.setTypeCode(cp[0]);
						} else {
							return new AjaxResponse(false, "Client Type Is Required");
						}		
						
					}catch(Exception e){
						e.printStackTrace();
						return new AjaxResponse(false, "Account Number or Client Type Is Required");
					}
					saveData(viewClient, getLoginSecUser(session));
					return new AjaxResponse(true, "Data Saved");
				}else{
					return new AjaxResponse(false, "Error Saved");
				}
			}else{
				return new AjaxResponse(false, "You are not authorized to access this module.");
			}
			
	}
	
	@RequestMapping("/updateData/{clientType}")
	public @ResponseBody AjaxResponse updateData(@PathVariable String clientType, HttpServletRequest request, HttpSession session) {
			
			System.out.println("#################### update-data = "+clientType);
			
			if (getPriviledgeUser(session, PRIVILEDGE, EDIT_DATA)) {
				String[] cp = clientType.split("\\|");
				ProfileModel pm = new ProfileModel();
				pm.setField("id");
				pm.setNumber(Long.parseLong(cp[0]));
				ViewClient view = clientService.profileMcu(pm);
				
				if(view != null){
					Client c = clientService.findById(Long.parseLong(cp[0]));
					try{
						if (cp[1].equals("") && cp[2].equals("")) {
							return new AjaxResponse(false, "Account Number or Client Type Is Required");
						} else {
							if (!cp[1].equals("")){
								view.setTypeCode(cp[1]);
							} else {
								return new AjaxResponse(false, "Client Type Is Required");
							}
							
							if (!cp[2].equals("")){
								ViewClient vc = clientService.findByAccountAndTypeCode(cp[2]);
								if(vc != null){
									return new AjaxResponse(false, "Account Number is Already Exist with CIF = "+vc.getCid());
								}
								view.setAccount(cp[2]);
							} else {
								return new AjaxResponse(false, "Account Number Is Required");
							}
						}
						
					}catch(Exception e){
						e.printStackTrace();
						return new AjaxResponse(false, "Account Number or Client Type Is Required");
					}
					updateData(view, getLoginSecUser(session), c);
					return new AjaxResponse(true, "Data Updated");
				}else{
					return new AjaxResponse(false, "Error Updated");
				}
			}else{
				return new AjaxResponse(false, "You are not authorized to access this module.");
			}
			
	}
	
	@RequestMapping("/resetPassword/{id}")
	public @ResponseBody AjaxResponse resetPassword(@PathVariable String id, HttpServletRequest request, HttpSession session) {

			System.out.println("#################### update-data = "+id);
			if (getPriviledgeUser(session, PRIVILEDGE, RESET_PASSWORD)) {
				String[] cp = id.split("\\|");
			
				Client client 		= clientService.findById(Long.parseLong(cp[0]));
				String dataBefore 	= getXMLValue(client);
				clientService.resetPassword(client, getLoginSecUser(session));
				String dataAfter 	= getXMLValue(clientService.findById(Long.parseLong(cp[0])));
				
				// save to audit trail
				String activity 	= "PASSWD_RESET";
				long moduleId 		= 2006;
				auditTrailService.save(request, moduleId, activity, dataBefore, dataAfter, client.getCid(), getLoginSecUser(session));
	
				return new AjaxResponse(true, "User password has been reset.");
			}else{
				return new AjaxResponse(false, "You are not authorized to access this module.");
			}
	}
	
	@RequestMapping("/resetMpin/{id}")
	public @ResponseBody AjaxResponse resetMpin(@PathVariable String id, HttpServletRequest request, HttpSession session) {

			System.out.println("#################### update-data = "+id);
			
			if (getPriviledgeUser(session, PRIVILEDGE, RESET_MPIN)) {
				String[] cp = id.split("\\|");
		
			
				Client client		= clientService.findById(Long.parseLong(cp[0]));
				String dataBefore 	= getXMLValue(client);
				clientService.resetPin(client, getLoginSecUser(session));
				String dataAfter 	= getXMLValue(clientService.findById(Long.parseLong(cp[0])));
				
				// save to audit trail
				String activity 	= "MPIN_RESET";
				long moduleId 		= 2007;
				auditTrailService.save(request, moduleId, activity, dataBefore, dataAfter, client.getCid(), getLoginSecUser(session));
	
				return new AjaxResponse(true, "User PIN has been reset.");
			}else{
				return new AjaxResponse(false, "You are not authorized to access this module.");
			}
	}
	
	@RequestMapping("/deactivate/{id}")
	public @ResponseBody AjaxResponse deactivate(@PathVariable String id, HttpServletRequest request, HttpSession session) {

			System.out.println("#################### update-data = "+id);
			
			if (getPriviledgeUser(session, PRIVILEDGE, DEACTIVATE)) {
				String[] cp = id.split("\\|");
			
				Client client		= clientService.findById(Long.parseLong(cp[0]));
				String dataBefore	= getXMLValue(client);
				clientService.deactivate(client, getLoginSecUser(session));
				String dataAfter	= getXMLValue(clientService.findById(Long.parseLong(cp[0])));
				
				// save to audit trail
				String activity 	= "USER_DEACTIVATE";
				long moduleId 		= 2008;
				auditTrailService.save(request, moduleId, activity, dataBefore, dataAfter, client.getCid(), getLoginSecUser(session));
	
				return new AjaxResponse(true, "Client was successfully deactivated.");
			}else{
				return new AjaxResponse(false, "You are not authorized to access this module.");
			}
	}
	
	@RequestMapping("/synchronize/{id}")
	public @ResponseBody AjaxResponse synchronize(@PathVariable String id, HttpServletRequest request, HttpSession session) {

			System.out.println("#################### update-data = "+id);
			
			if (getPriviledgeUser(session, PRIVILEDGE, SYNCHRONIZE)) {
				
				
				String[] cp = id.split("\\|");
				
				ViewClient view = clientService.findByCidAndTypeCode(cp[3]);
				if(view != null){
					HttpClientMcu httpClient = new HttpClientMcu();
					String url = genericService.getConfigValueByName("URL_WEBCOLLUSER");
					
					System.out.println("########### CID = "+cp[3]);
					System.out.println("########### url = "+url + cp[3]);
					
					String json = "";
					if (StringUtils.isNotBlank(url)) {
						json = httpClient.callMbo(url + cp[3]);
					}
					
					System.out.println("########### json = "+json);
					
					try{
						if(json.contains("errorCode")){
							JSONParser parser = new JSONParser();
							Object obj = parser.parse(json);
							JSONObject oj =  (JSONObject) obj;
							if(oj != null){
								System.out.println("jo = "+oj.toJSONString());
								String err = (String) oj.get("message");
								return new AjaxResponse(false, err);
							}
						}else{
							JSONParser parser = new JSONParser();
							Object obj = parser.parse(json);
							JSONObject oj =  (JSONObject) obj;
							view.setCid((String) oj.get("customerNo"));
							
							view.setFullname((String) oj.get("name"));
							view.setFirstname((String) oj.get("firstName"));
							view.setMiddlename((String) oj.get("middleName"));
							view.setLastname((String) oj.get("lastName"));
							view.setEmail((String) oj.get("email"));
							view.setMobileNo((String) oj.get("sms"));
							
							view.setAccStatusCode("INACTIVE");
							view.setAccStatusDesc("INACTIVE");
							
							try{
								view.setBranchCode((String) oj.get("branchCode"));
								StructureBranch b = branchService.findByCode(view.getBranchCode());
								if(b == null){
									view.setBranchDesc(view.getBranchCode());
								}else{
									view.setBranchDesc(b.getDescription());
								}
							}catch (Exception e) {
								e.printStackTrace();
							}
							
							try{
								view.setCenterCode((String) oj.get("centerCode"));
								StructureCenter c = centerService.findByCode(view.getCenterCode());
								if(c == null){
									view.setCenterDesc(view.getCenterCode());
								}else{
									view.setCenterDesc(c.getDescription());
								}
							}catch (Exception e) {
								e.printStackTrace();
							}
							
							try{
								view.setUnitCode((String) oj.get("unitCode"));
								StructureUnit u = unitService.findByCode(view.getUnitCode());
								if(u == null){
									view.setUnitDesc(view.getUnitCode());
								}else{
									view.setUnitDesc(u.getDescription());
								}
								
							}catch (Exception e) {
								e.printStackTrace();
							}
							
							SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
							Date d = sdf.parse((String)oj.get("birthDate"));
							view.setDob(d);
							view.setAddress((String) oj.get("address"));
							
							if(view != null){
								Client c = clientService.findById(Long.parseLong(cp[0]));
								try{
									view.setAccount(cp[2]);
									view.setTypeCode(cp[1]);
								}catch(Exception e){
									e.printStackTrace();
									return new AjaxResponse(false, "Account Number or Client Type Is Required");
								}
								updateDataSync(view, getLoginSecUser(session), c);
							}
						}
					}catch (Exception e) {
						e.printStackTrace();
					}
					return new AjaxResponse(true,"Data Updated",view);
				}else{
					return new AjaxResponse(false, "Customer Id Exist");
				}
			
				
			}else{
				return new AjaxResponse(false, "You are not authorized to access this module.");
			}
	}
	
	public void updateData(ViewClient vc, SecUser userLogin, Client c){
		c.setAccount(vc.getAccount());
		c.setLastUpdatedBy(userLogin.getId());
		c.setLastUpdatedDate(new Date());
		c.setType(vc.getTypeCode());
		clientService.save(c, userLogin);
	}
	
	public void updateDataSync(ViewClient vc, SecUser userLogin, Client c){
		c.setAccount(vc.getAccount());
		c.setLastUpdatedBy(userLogin.getId());
		c.setLastUpdatedDate(new Date());
		c.setType(vc.getTypeCode());
		c.setMobileNo(vc.getMobileNo());
		c.setFirstName(vc.getFirstname());
		c.setMiddleName(vc.getMiddlename());
		c.setLastName(vc.getLastname());
		c.setEmail(vc.getEmail());
		c.setBranchCode(vc.getBranchCode());
		c.setCenterCode(vc.getCenterCode());
		c.setUnitCode(vc.getUnitCode());
		c.setDob(vc.getDob());
		c.setAddress(vc.getAddress());
		clientService.save(c, userLogin);
	}
	
	public void saveData(ViewClient vc, SecUser userLogin){
		Client c = new Client();
		c.setName(vc.getFullname());
		c.setFirstName(vc.getFirstname());
		c.setMiddleName(vc.getMiddlename());
		c.setLastName(vc.getLastname());
		c.setEmail(vc.getEmail());
		c.setMobileNo(vc.getMobileNo());
		c.setActCode(vc.getAccStatusCode());
		c.setAccount(vc.getAccount());
		c.setAddress(vc.getAddress());
		c.setBranchCode(vc.getBranchCode());
		c.setCenterCode(vc.getCenterCode());
		c.setUnitCode(vc.getUnitCode());
		c.setCid(vc.getCid());
		c.setDob(vc.getDob());
		c.setType(vc.getTypeCode());
		c.setCreatedBy(userLogin.getId());
		c.setCreatedDate(new Date());
		c.setType(vc.getTypeCode());
		c.setEnabled(1);
		c.setResetPasswdFlag(0);
		c.setResetPinFlag(0);
		c.setLoginRetry(0);
		c.setPinRetry(0);
		c.setFailTokenAttempts(0);
		c.setApproverStatus(Lookup.LOOKUP_APPROVAL_STATUS_APPROVED);
		c.setStatus(Lookup.LOOKUP_ACTIVATION_STATUS_INACTIVE);
		clientService.save(c, userLogin);
	}
	
	@RequestMapping("/viewUsername/{id}")
	public @ResponseBody AjaxResponse viewUsername(@PathVariable String id, HttpServletRequest request, HttpSession session) {

		if (getPriviledgeUser(session, PRIVILEDGE, C_VIEW_USERNAME)) {

			String[] cp = id.split("\\|");
			Client client = clientService.findById(Long.parseLong(cp[0]));

			return new AjaxResponse(true, client.getLogin());
		} else {

			return new AjaxResponse(false, "You are not authorized to access this module.");
		}
	}
	
	private void putIntoRequest(Model model) {
		model.addAttribute("SELECTED_MENU", MENU);
		model.addAttribute("SELECTED_SUBMENU", PRIVILEDGE);
		List<Lookup> listLookUp = new ArrayList<Lookup>();
		for(Lookup l : genericService.lookup(Lookup.LOOKUP_CLIENT_TYPE)){
			if(l.getValue().equals("AO") || l.getValue().equals("UM")){
				listLookUp.add(l);
			}
		}
		
		model.addAttribute("listClientType", listLookUp);
		model.addAttribute("listSuspiciousType", genericService.lookup(Lookup.LOOKUP_TRANSACTION_TYPE));
		model.addAttribute("listStatus", genericService.lookup(Lookup.LOOKUP_TRANSACTION_STATUS));
	}
	
}