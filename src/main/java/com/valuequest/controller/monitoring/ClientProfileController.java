package com.valuequest.controller.monitoring;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.hibernate.SessionFactory;
import org.hibernate.type.TrueFalseType;
import org.apache.commons.beanutils.converters.StringArrayConverter;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.valuequest.auth.Salsa20;
import com.valuequest.auth.SignIn;
import com.valuequest.common.AjaxResponse;
import com.valuequest.controller.BaseController;
import com.valuequest.controller.api.http.HttpRequestSender;

import com.valuequest.controller.api.model.ResetOTPModel;
import com.valuequest.controller.api.model.ResetPasswordModel;
import com.valuequest.controller.api.model.SuperAppTransactionModel;
import com.valuequest.controller.monitoring.model.AuthorResetPasswordModel;
import com.valuequest.controller.monitoring.model.InquireResponseModel;
import com.valuequest.controller.monitoring.model.InquireRequestModel;
import com.valuequest.controller.monitoring.model.ProfileModel;
import com.valuequest.controller.monitoring.model.UpdateAgentFeatureModel;
import com.valuequest.controller.monitoring.model.UpdateDeviceBlockModel;
import com.valuequest.controller.monitoring.model.UpdateDeviceStatusModel;
import com.valuequest.controller.monitoring.model.UpdateEnablePerInstiModel;
import com.valuequest.controller.monitoring.model.UpdateMerchantFeatureModel;
import com.valuequest.controller.monitoring.model.UpdateMobileModel;
import com.valuequest.controller.monitoring.model.ResetCredentialModel;
import com.valuequest.controller.monitoring.model.UpdateRestrictModel;
import com.valuequest.controller.monitoring.model.UpdateSavingsStatusFeatureModel;
import com.valuequest.dao.ClientProfileDao;
import com.valuequest.entity.Client;
import com.valuequest.entity.StructureBranch;
import com.valuequest.entity.StructureCenter;
import com.valuequest.entity.StructureUnit;
import com.valuequest.entity.ViewClient;
import com.valuequest.entity.security.SecUser;
import com.valuequest.entity.MerchantEntity;
import com.valuequest.services.AuthorResetPasswordService;
import com.valuequest.util.GenerateAlphaNumeric;
import com.valuequest.util.HttpClientMcu;
import com.valuequest.util.SoteriaProperties;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.nio.file.*; 

@Component
@Controller
@RequestMapping("/monitoring/profile")
public class ClientProfileController extends BaseController {

	final Logger log = Logger.getLogger("ClientProfileController");

	final static String MENU 			= "MONITORING";
	final static String PRIVILEDGE 		= "CLIENT_PROFILE";
	private String BASE_VIEW 			= "04.monitoring/";
	private String LIST_VIEW 			= "client-profile";

	private String C_RESET_PASSWD 		= "RESET PASSWORD";
	private String C_RESET_PIN 			= "RESET PIN";
	private String C_DEACTIVATE 		= "DEACTIVATE";
	private String C_RESTRICT 			= "RESTRICT";
	private String C_VIEW_USERNAME 		= "USERNAME";
	private String C_AGENT_FEATURE 		= "AGENT FEATURE";
	private String C_RESET_STATUS 		= "RESET STATUS";
	private String C_BLOCK_STATUS 		= "UNBLOCKED ACCOUNT (AC/VC)";
	private String C_MERCHANT_FEATURE 	= "MERCHANT FEATURE";
	private String C_UPDATE_MERCHANT	= "UPDATE MERCHANT NAME";
	private String C_SYNCHRONIZE 		= "SYNCHRONIZE PROFILE";

	@Value("${soteria.auth.signin.username}")
	private String api;

	
	

    // constructor
    // public void UserService(@Value("${soteria.auth.signin.username}") String uname) {
	// 	log.info("API Properties URL - Method: " + uname);
    // 	this.soteriaUsername = uname;
    // }

	
	private static void generateQRCodeImage(String text, int width, int height, String filePath) throws WriterException, IOException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);

        Path path = FileSystems.getDefault().getPath(filePath);
        MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);
    }

	@Autowired
	SessionFactory sessionFactory;

	@Autowired
	ClientProfileDao clientProfileDao;
	
	@Autowired
	private AuthorResetPasswordService authorResetPasswordService;
	
	SoteriaProperties soteriaProp = new SoteriaProperties();

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

	
	@RequestMapping("/get")
	public @ResponseBody String searchClientProfile( 
			@RequestParam(required = false) String cid,	
			@RequestParam(required = false) String username,	
			@RequestParam(required = false) String mobile,	
			HttpSession session) {
			HttpRequestSender sender = null;

			log.info("API Properties URL: " + api);
			try {
				
				InquireRequestModel irm = new InquireRequestModel();
				
				if(cid != null || cid != "") {
					irm.setCid(cid);
				}
				
				if(username != null || username != "") {
					irm.setUsername(username);
				}
				
				if(mobile != null || mobile != "") {
					irm.setMobile(mobile);
				}
				
				Gson gson = new Gson();
				System.out.println("#### Inquire Request Model #####"+gson.toJson(irm));
				//sender = new HttpRequestSender("https://prod-api-janus.fortress-asya.com:8083/Soteria/api/auth/inquire",irm);				//PROD
				sender = new HttpRequestSender("https://dev-api-janus.fortress-asya.com:8083/api/auth/inquire",irm); 		//TEST

				final HashMap<String, String> headers = new HashMap<String, String>();
				headers.put("Content-Type", "application/json");
				sender.setHeaders(headers);
			
				String ret;
				if(sender.requestSent()){
					
						ret ="[" + sender.getResponse() + "]";
						System.out.println(ret);
					
					 //final List<InquireResponseModel> clientProfile = new Gson().fromJson(ret,new TypeToken<ArrayList<InquireResponseModel>>(){}.getType());
					 //System.out.println("Response Body: " + clientProfile.);       
						
					 return ret;
				}else {
					
					System.out.println("ERROR#######: " + sender.getErrorMsg());
					return null;
	
				}
	
			}catch(final Exception e) {
	
				System.out.println("ERROR: " + e.toString());
				return null;
	
			}
	}


	
	@RequestMapping(value = "/updateAgentFeature", method = RequestMethod.POST)
	public @ResponseBody  String enableAgentfeature( 
			@RequestParam(required = false) String username,	
			@RequestParam(required = false) Integer isAgent,
			@RequestParam(required = false) String insti,
			@RequestParam(required = false) String cid,
			HttpServletRequest request,
			HttpSession session) {
			HttpRequestSender sender = null;
	
			try {

				UpdateAgentFeatureModel updateAgent = new UpdateAgentFeatureModel();
				
				updateAgent.setIsAgent(isAgent);
				updateAgent.setUsername(username);
				updateAgent.setInstiCode(insti);
				Gson gson = new Gson();
				System.out.println("::::::::: Update Agent JSON Request :::::::::" + gson.toJson(updateAgent)); 
				
				//sender = new HttpRequestSender("https://prod-api-janus.fortress-asya.com:8083/Soteria/api/auth/addAccount",updateAgent);				//PROD
				sender = new HttpRequestSender("https://dev-api-janus.fortress-asya.com:8083/api/auth/addAccount",updateAgent); 		//TEST

				final HashMap<String, String> headers = new HashMap<String, String>();
				headers.put("Content-Type", "application/json");
				sender.setHeaders(headers);
				
				if(sender.requestSent()){
					System.out.println("::::::::: Update Agent JSON Response :::::::::" + sender.getResponse()); 
					System.out.println("::::::::: Update Agent Status :::::::::" + sender.getStatus()); 

					if(sender.getStatus() == 200 || sender.getStatus() == 201){
						String dataBefore = "";
						String dataAfter = "";
						String activity = "";
						long moduleId = 0;

						if(isAgent == 1){
							dataBefore = "Disabled";
							dataAfter = "Enable";
							activity = "AGENT_FEATURE_ENABLE";
							moduleId = 2011;
						}

						if(isAgent == 0){
							dataBefore = "Enable";
							dataAfter = "Disabled";
							activity = "AGENT_FEATURE_DISABLE";
							moduleId = 2012;
						}

						
						clientProfileDao.updateAgent(cid, isAgent, session);

						auditTrailService.save(request, moduleId, activity, dataBefore, dataAfter, cid, getLoginSecUser(session));
					}

					
					return sender.getResponse();
				}else {
					
					System.out.println("ERROR: " + sender.getErrorMsg());
					return null;
	
				}
	
			}catch(final Exception e) {
	
				System.out.println("ERROR: " + e.toString());
				return null;
	
			}
	}

	@RequestMapping(value = "/updateSavingsStatusFeature", method = RequestMethod.POST)
	public @ResponseBody  String updateSavingsStatusFeature( 
			@RequestParam(required = false) String instiCode,	
			@RequestParam(required = false) String accountNumber,
			@RequestParam(required = false) String username,
			@RequestParam(required = false) int isEnabled,
			HttpSession session) {
			HttpRequestSender sender = null;
	
			try {
				
				SignIn si = new SignIn();
				String payload = si.request("webtool","NtmdQ1KYyxvWfjIg6vgdzA==");
				
			    Object obj=JSONValue.parse(payload); 
			    JSONObject jsonObject = (JSONObject) obj;  
			  
			    String tkn = (String) jsonObject.get("accessToken");  
			    
			    System.out.print("#######Token##### " + tkn);  
				
				UpdateSavingsStatusFeatureModel updateAgent = new UpdateSavingsStatusFeatureModel();
				
				updateAgent.setInstiCode(instiCode);
				updateAgent.setAccountNumber(accountNumber);
				updateAgent.setUsername(username);
				updateAgent.setIsEnabled(isEnabled);
				
				Gson gson = new Gson();
				System.out.println("Update Agent JSON Request :::::::::" + gson.toJson(updateAgent)); 
				
				//sender = new HttpRequestSender("https://prod-api-janus.fortress-asya.com:8083/Soteria/api/auth/addAccount",updateAgent);
				sender = new HttpRequestSender("https://dev-api-janus.fortress-asya.com:8083/api/auth/addAccount",updateAgent);


				final HashMap<String, String> headers = new HashMap<String, String>();
				headers.put("Content-Type", "application/json");
				headers.put("Authorization", "Bearer "+tkn);
				sender.setHeaders(headers);
			
				if(sender.requestSent()){
					System.out.println("Update Agent JSON Response :::::::::" + sender.getResponse()); 
					return sender.getResponse();
				}else {
					
					System.out.println("ERROR: " + sender.getErrorMsg());
					return null;
	
				}
	
			}catch(final Exception e) {
	
				System.out.println("ERROR: " + e.toString());
				return null;
	
			}
	}
	
	@RequestMapping(value = "/updateStatusFeature", method = RequestMethod.POST)
	public @ResponseBody  String enableStatusFeature( 
			@RequestParam(required = false) String username,	
			@RequestParam(required = false) String isEnabled,
			@RequestParam(required = false) String insti,
			HttpSession session) {
			HttpRequestSender sender = null;
	
			try {
				
				UpdateEnablePerInstiModel updateAgent = new UpdateEnablePerInstiModel();
				
				updateAgent.setIsEnabled(isEnabled);
				updateAgent.setUsername(username);
				updateAgent.setInstiCode(insti);
				Gson gson = new Gson();
				System.out.println("Update Agent JSON Request :::::::::" + gson.toJson(updateAgent)); 
				
				//sender = new HttpRequestSender("https://prod-api-janus.fortress-asya.com:8083/Soteria/api/auth/addAccount",updateAgent);			//PROD
				sender = new HttpRequestSender("https://dev-api-janus.fortress-asya.com:8083/api/auth/addAccount",updateAgent);	//TEST

				final HashMap<String, String> headers = new HashMap<String, String>();
				headers.put("Content-Type", "application/json");
				sender.setHeaders(headers);
			
				if(sender.requestSent()){
					System.out.println("Update Agent JSON Response :::::::::" + sender.getResponse()); 
					return sender.getResponse();
				}else {
					
					System.out.println("ERROR: " + sender.getErrorMsg());
					return null;
	
				}
	
			}catch(final Exception e) {
	
				System.out.println("ERROR: " + e.toString());
				return null;
	
			}
	}

	@RequestMapping(value = "/updateMobileNumber", method = RequestMethod.POST)
	public @ResponseBody  String updateMobileNumber( 
			@RequestParam(required = true) String username,	
			@RequestParam(required = true) String newMobile,
			@RequestParam(required = true) String oldMobile,
			@RequestParam(required = true) String cid,
			HttpServletRequest request,
			HttpSession session) {
			HttpRequestSender sender = null;
	
			try {

				SignIn si = new SignIn();
				String payload = si.request("webtool","NtmdQ1KYyxvWfjIg6vgdzA==");
				
			    Object obj=JSONValue.parse(payload); 
			    JSONObject jsonObject = (JSONObject) obj;  
			  
			    String tkn = (String) jsonObject.get("accessToken");  
			    
			    System.out.print("#######Token##### " + tkn);  
				
				UpdateMobileModel updateMobileNumberModel = new UpdateMobileModel();
				updateMobileNumberModel.setUsername(username);
				updateMobileNumberModel.setMobile(newMobile);
				Gson gson = new Gson();
				System.out.println("Update Agent JSON Request :::::::::" + gson.toJson(updateMobileNumberModel)); 
				
				//sender = new HttpRequestSender("https://prod-api-janus.fortress-asya.com:8083/Soteria/api/secure/updateInfo",updateMobileNumberModel);			//PROD
				sender = new HttpRequestSender("https://dev-api-janus.fortress-asya.com:8083/api/secure/updateInfo",updateMobileNumberModel);	//TEST

				final HashMap<String, String> headers = new HashMap<String, String>();
				headers.put("Content-Type", "application/json");
				headers.put("Authorization", "Bearer "+tkn);
				sender.setHeaders(headers);

				String dataBefore = oldMobile;
				String dataAfter = newMobile;
				String activity = "CHANGE_MOBILE_NUMBER";
				long moduleId = 2003;
			
				if(sender.requestSent()){

					auditTrailService.save(request, moduleId, activity, dataBefore, dataAfter, cid, getLoginSecUser(session));
					
					return sender.getResponse();
							
				}else {
					
					System.out.println("ERROR: " + sender.getErrorMsg());
					return null;
	
				}
	
			}catch(final Exception e) {
	
				System.out.println("ERROR: " + e.toString());
				return null;
	
			}
	}

	@RequestMapping(value = "/resetCredential", method = RequestMethod.POST)
	public @ResponseBody  String resetCredential( 
			@RequestParam(required = true) String mobile,	
			@RequestParam(required = true) String username,
			@RequestParam(required = true) String password,
			@RequestParam(required = false) String cid,
			HttpServletRequest request,
			HttpSession session) {
			HttpRequestSender requestEncryptString = null;
			HttpRequestSender requestResetCredential = null;
	
			try {

				InquireRequestModel irm = new InquireRequestModel();

				SignIn si = new SignIn();
				String payload = si.request("webtool","NtmdQ1KYyxvWfjIg6vgdzA==");
				
			    Object obj=JSONValue.parse(payload); 
			    JSONObject jsonObject = (JSONObject) obj;  
			  
			    String tkn = (String) jsonObject.get("accessToken");  
			    
			    System.out.print("#######Token##### " + tkn);  
				
				ResetCredentialModel toEncrypt = new ResetCredentialModel();
				toEncrypt.toEncrypt(password);

				requestEncryptString = new HttpRequestSender("http://dev-api-janus.fortress-asya.com:8886/API/V1/Encrypt",toEncrypt);	//TEST

					if(requestEncryptString.requestSent()){
						ResetCredentialModel rstcrdntl = new ResetCredentialModel();
						rstcrdntl.username(username);
						rstcrdntl.password(requestEncryptString.getResponse());
						Gson gson = new Gson();
					System.out.println("Update Agent JSON Request :::::::::" + gson.toJson(rstcrdntl)); 
					requestResetCredential = new HttpRequestSender("https://dev-api-janus.fortress-asya.com:8083/api/auth/resetCredencials", rstcrdntl);	//TEST
				}else{
					return "Error Reset Credential!";
				}
	
					final HashMap<String, String> headers = new HashMap<>();
					headers.put("Content-Type", "application/json");
					headers.put("Authorization", "Bearer " + tkn);
					irm.setMobile(mobile); 
					String mobileHeaderValue = irm.getMobile(); 
					headers.put("Mobile", mobileHeaderValue);
					requestResetCredential.setHeaders(headers);
	
					String dataBefore = "";
					String dataAfter = "";
					String activity = "RESET_CREDENTIAL";
					long moduleId = 2003;
				
					if(requestResetCredential.requestSent()){
	
						auditTrailService.save(request, moduleId, activity, dataBefore, dataAfter, cid, getLoginSecUser(session));
						
						return requestResetCredential.getResponse();
								
					}else {
						
						System.out.println("ERROR: " + requestResetCredential.getErrorMsg());
						return null;
		
					}
	
				}catch(final Exception e) {
		
					System.out.println("ERROR: " + e.toString());
					return null;
		
				}
		}
	
	@RequestMapping(value = "/updateDeviceBlock", method = RequestMethod.POST)
	public @ResponseBody  String updateDeviceBlock( 
			@RequestParam(required = false) String username,	
			@RequestParam(required = false) String status,
			@RequestParam(required = false) String id,
			HttpSession session) {
			HttpRequestSender sender = null;
	
			try {
				
				SignIn si = new SignIn();
				String payload = si.request("webtool","NtmdQ1KYyxvWfjIg6vgdzA==");
				
			    Object obj=JSONValue.parse(payload); 
			    JSONObject jsonObject = (JSONObject) obj;  
			  
			    String tkn = (String) jsonObject.get("accessToken");  
			    System.out.println("##########Token###########: "+ tkn); 
				
				UpdateDeviceBlockModel updateDevice = new UpdateDeviceBlockModel();
				
				updateDevice.setIsBlocked(status);
				updateDevice.setUsername(username);
				updateDevice.setDeviceId(id);
				Gson gson = new Gson();

				//sender = new HttpRequestSender("https://prod-api-janus.fortress-asya.com:8083/Soteria/api/webtool/updateDevice",updateDevice);			//PROD
				sender = new HttpRequestSender("https://dev-api-janus.fortress-asya.com:8083/api/webtool/updateDevice",updateDevice);	//TEST

				final HashMap<String, String> headers = new HashMap<String, String>();
				headers.put("Content-Type", "application/json");
				headers.put("Authorization", "Bearer "+tkn);
				sender.setHeaders(headers);
			
				if(sender.requestSent()){
					System.out.println("Update Device Status :::::::::" + sender.getResponse()); 
					
					return sender.getResponse();
				}else {
					
					System.out.println("ERROR: " + sender.getErrorMsg());
					return null;
	
				}
	
			}catch(final Exception e) {
	
				System.out.println("ERROR: " + e.toString());
				return null;
	
			}
	}

	@RequestMapping(value = "/updateDeviceStatus", method = RequestMethod.POST)
	public @ResponseBody  String updateDeviceStatus( 
			@RequestParam(required = false) String username,	
			@RequestParam(required = false) String id,
			HttpSession session) {
			HttpRequestSender sender = null;
	
			try {
				
				SignIn si = new SignIn();
				String payload = si.request("webtool","NtmdQ1KYyxvWfjIg6vgdzA==");
				
			    Object obj=JSONValue.parse(payload); 
			    JSONObject jsonObject = (JSONObject) obj;  
			  
			    String tkn = (String) jsonObject.get("accessToken");  
				
				UpdateDeviceStatusModel updateDevice = new UpdateDeviceStatusModel();

				updateDevice.setUsername(username);
				updateDevice.setDeviceId(id);

				//sender = new HttpRequestSender("https://prod-api-janus.fortress-asya.com:8083/Soteria/api/webtool/unUsedDevice",updateDevice);			//PROD
				sender = new HttpRequestSender("https://dev-api-janus.fortress-asya.com:8083/api/webtool/unUsedDevice",updateDevice);	//TEST

				final HashMap<String, String> headers = new HashMap<String, String>();
				headers.put("Content-Type", "application/json");
				headers.put("Authorization", "Bearer "+tkn);
				sender.setHeaders(headers);
			
				if(sender.requestSent()){
					return sender.getResponse();
				}else {
					return null;
				}
	
			}catch(final Exception e) {
				System.out.println("ERROR: " + e.toString());
				return null;
	
			}
	}
	
	@RequestMapping(value = "/restrict", method = RequestMethod.POST)
	public @ResponseBody  String restrict( 
			@RequestParam(required = false) String username,	
			@RequestParam(required = false) Long value,
			@RequestParam(required = false) String cid,
			HttpSession session) {
				System.out.println("###############Restrcit########");

			HttpRequestSender sender = null;
			if (getPriviledgeUser(session, PRIVILEDGE, C_RESTRICT)) {
				try {
					
					SignIn si = new SignIn();
					String payload = si.request("webtool","NtmdQ1KYyxvWfjIg6vgdzA==");
					
					Object obj=JSONValue.parse(payload); 
					JSONObject jsonObject = (JSONObject) obj;  
				
					String tkn = (String) jsonObject.get("accessToken");  
					
					UpdateRestrictModel updateRestrictModel = new UpdateRestrictModel();

					updateRestrictModel.setUsername(username);
					updateRestrictModel.setIsBlocked(value.toString());

					//sender = new HttpRequestSender("https://prod-api-janus.fortress-asya.com:8083/Soteria/api/secure/updateInfo",updateRestrictModel);			//PROD
					sender = new HttpRequestSender("https://dev-api-janus.fortress-asya.com:8083/api/secure/updateInfo",updateRestrictModel);	//TEST


					final HashMap<String, String> headers = new HashMap<String, String>();
					headers.put("Content-Type", "application/json");
					headers.put("Authorization", "Bearer "+tkn);
					sender.setHeaders(headers);
				
					if(sender.requestSent()){
						return sender.getResponse();
					}else {
						return null;
					}
		
				}catch(final Exception e) {
					System.out.println("ERROR: " + e.toString());
					return null;
		
				}
			} else {
				return "You are not authorized to access this module.";
			}
	}
	


	@RequestMapping(value = "/updateMerchantFeature", method = RequestMethod.POST)
	public @ResponseBody  String enableMerchantFeature( 
			@RequestParam(required = false) String username,	
			@RequestParam(required = false) String isMerchant,
			@RequestParam(required = false) String insti,
			HttpSession session) {
			HttpRequestSender sender = null;
	
			try {
				
				UpdateMerchantFeatureModel updateAgent = new UpdateMerchantFeatureModel();
				
				updateAgent.setIsMerchant(isMerchant);
				updateAgent.setUsername(username);
				updateAgent.setInstiCode(insti);
				Gson gson = new Gson();
				System.out.println("Update Agent JSON Request :::::::::" + gson.toJson(updateAgent)); 
				
				//sender = new HttpRequestSender("https://prod-api-janus.fortress-asya.com:8083/Soteria/api/auth/addAccount",updateAgent);			//PROD
				sender = new HttpRequestSender("https://dev-api-janus.fortress-asya.com:8083/api/auth/addAccount",updateAgent);	//TEST

				final HashMap<String, String> headers = new HashMap<String, String>();
				headers.put("Content-Type", "application/json");
				sender.setHeaders(headers);
			
				if(sender.requestSent()){
					System.out.println("Update Agent JSON Response :::::::::" + sender.getResponse()); 
					
					return sender.getResponse();
				}else {
					
					System.out.println("ERROR: " + sender.getErrorMsg());
					return null;
	
				}
	
			}catch(final Exception e) {
	
				System.out.println("ERROR: " + e.toString());
				return null;
	
			}
			
	}

	@RequestMapping(value = "/requestOTP", method = RequestMethod.POST)
	public @ResponseBody  String requestOTP( 
			@RequestParam(required = false) String username,
			@RequestParam(required = false) String instiCode,	
			@RequestParam(required = false) String to,
			@RequestParam(required = false) String msg,
			@RequestParam(required = false) String action,
			@RequestParam(required = false) String cid,
			HttpServletRequest request,
			HttpSession session) {
			HttpRequestSender sender = null;
	
			try {

				GenerateAlphaNumeric gan = new GenerateAlphaNumeric();
				ResetOTPModel resetOTP = new ResetOTPModel();

				String pass = gan.Generate();
				
				resetOTP.setInstiCode(instiCode);
				resetOTP.setTo(to);
				// resetOTP.setMsg(msg + pass);

				resetOTP.setMsg("Good day! Para po maipagpatuloy ang paggamit ng konek2CARD, gamitin ang temporary password: "+pass +

				"\n\nPalitan agad ang password pagkalog-in. Salamat po.");

				resetOTP.setAction(action);

				Gson gson = new Gson();
				System.out.println("Reset OTP Request - "+ action +":::::::::" + gson.toJson(resetOTP)); 
				
				sender = new HttpRequestSender("https://dev-api-janus.fortress-asya.com:8083/api/otp/request",resetOTP);			//TEST
				//sender = new HttpRequestSender("https://prod-api-janus.fortress-asya.com:8083/Soteria/api/otp/request",resetOTP);			//PROD

				final HashMap<String, String> headers = new HashMap<String, String>();
				headers.put("Content-Type", "application/json");
				sender.setHeaders(headers);
			
				if(sender.requestSent()){

					System.out.println("Reset OTP Request - "+ action +":::::::::" + sender.getResponse()); 

					resetPassword(username,pass,to,cid,request,session);

					return sender.getResponse();
				}else {
					
					System.out.println("ERROR: " + sender.getErrorMsg());
					return null;
	
				}
	
			}catch(final Exception e) {
	
				System.out.println("ERROR: " + e.toString());
				return null;
	
			}
			
	}

	@RequestMapping(value = "/resetPassword", method = RequestMethod.POST)
	public @ResponseBody  String resetPassword( 
			@RequestParam(required = false) String username,	
			@RequestParam(required = false) String password,
			@RequestParam(required = false) String mobile,
			@RequestParam(required = false) String cid,
			HttpServletRequest request,
			HttpSession session) {
			HttpRequestSender sender = null;
	
			try {

				Salsa20 salsa = new Salsa20();
				ResetPasswordModel rpm = new ResetPasswordModel();

				String pass = salsa.Encrypt(password);
				log.info("Salsa20 Pass: " + pass);


				rpm.setUsername(username);
				rpm.setPassword(pass);
				rpm.setMobile(mobile);
				
				

				Gson gson = new GsonBuilder().disableHtmlEscaping().create();
				String payload = gson.toJson(rpm);

				System.out.println("Initial Change Password Request :::::::::" + rpm); 
				
				sender = new HttpRequestSender("https://dev-api-janus.fortress-asya.com:8083/api/auth/initialChangePassword",rpm);			//TEST
				//sender = new HttpRequestSender("https://prod-api-janus.fortress-asya.com:8083/Soteria/api/auth/initialChangePassword",rpm);			//PROD

				final HashMap<String, String> headers = new HashMap<String, String>();
				headers.put("Content-Type", "application/json");
				sender.setHeaders(headers);
			
				if(sender.requestSent()){
					if(sender.getStatus() == 200 || sender.getStatus() == 2011){
						System.out.println("Initial Change Password :::::::::" + sender.getResponse()); 
								// save to audit trail
					String activity 	= "PASSWD_RESET";
					long moduleId 		= 2006;
					auditTrailService.save(request, moduleId, activity, null, null, cid, getLoginSecUser(session));
					}
					
					return sender.getResponse();
				}else {
					
					System.out.println("ERROR: " + sender.getErrorMsg());
					return null;
	
				}
	
			}catch(final Exception e) {
	
				System.out.println("ERROR: " + e.toString());
				return null;
	
			}
			
	}


	@SuppressWarnings("rawtypes")
	@RequestMapping("/names")
	public @ResponseBody List menuList(@RequestParam(required = false) String name, @RequestParam(required = false) int size) {

		return clientService.list(name, size);
	}
	

	@RequestMapping(value = "/get", method = RequestMethod.POST, headers = { "content-type=application/json" })
	public @ResponseBody AjaxResponse get(@RequestBody ProfileModel model, HttpServletRequest request, HttpSession session) {
		
		System.out.println("##############session: "+ model.getField());
		System.out.println("##############session: "+ model.getString());
		ViewClient view = clientService.profile(model);
		
		System.out.println("##############view: "+ view);
		if (view == null) {
			return new AjaxResponse(false, "Client Id not found.");
		}
		
		System.out.println("##############view: "+ view.getMerchantQrCode());
		if (view.getMerchantQrCode() != null) {
			try {
				String QR_CODE_IMAGE_PATH = "/usr/local/share/apache-tomcat-7.0.27/webapps/webtools/assets/img/konek2PAY/Merchant-"+view.getId()+"-"+view.getMerchantName()+".png";
	            generateQRCodeImage(view.getMerchantQrCode(), 350, 350, QR_CODE_IMAGE_PATH);
				
	            System.out.println("==========###########DONE");
	        } catch (WriterException e) {
	            System.out.println("==========###########Could not generate QR Code, WriterException :: " + e.getMessage());
	        } catch (IOException e) {
	            System.out.println("==========###########Could not generate QR Code, IOException :: " + e.getMessage());
	        }
		}
		
		return new AjaxResponse(view);
	}

	@RequestMapping("/resetPassword/{id}")
	public @ResponseBody AjaxResponse resetPassword1(@PathVariable Long id, HttpServletRequest request, HttpSession session) {
		System.out.println("###########id: "+ id);
		if (getPriviledgeUser(session, PRIVILEDGE, C_RESET_PASSWD)) {
			Client client 		= clientService.findById(id);

//			String dataBefore 	= getXMLValue(client) ;
//			clientService.resetPassword(client, getLoginSecUser(session));
//			String dataAfter 	= getXMLValue(clientService.findById(id));

			AuthorResetPasswordModel authorResetPassword = new AuthorResetPasswordModel();
			authorResetPassword.setClientId(client.getId());
			authorResetPassword.setStatus("P");
			authorResetPassword.setType("RESET_PASSWORD");
			authorResetPasswordService.save(authorResetPassword, getLoginSecUser(session));
			
//			// save to audit trail
//			String activity 	= "PASSWD_RESET";
//			long moduleId 		= 2006;
//			auditTrailService.save(request, moduleId, activity, dataBefore, dataAfter, client.getCid(), getLoginSecUser(session));

			return new AjaxResponse(true, "Success for authorization reset");
		} else {

			return new AjaxResponse(false, "You are not authorized to access this module.");
		}
	}

	@RequestMapping("/resetMpin/{id}")
	public @ResponseBody AjaxResponse resetMpin(@PathVariable Long id, HttpServletRequest request, HttpSession session) {
		System.out.println("###########id: "+ id);
		if (getPriviledgeUser(session, PRIVILEDGE, C_RESET_PIN)) {
			Client client		= clientService.findById(id);
//			String dataBefore 	= getXMLValue(client);
//			clientService.resetPin(client, getLoginSecUser(session));
//			String dataAfter 	= getXMLValue(clientService.findById(id));
			
			//
			AuthorResetPasswordModel authorResetPassword = new AuthorResetPasswordModel();
			authorResetPassword.setClientId(client.getId());
			authorResetPassword.setStatus("P");
			authorResetPassword.setType("RESET_MPIN");
			authorResetPasswordService.save(authorResetPassword, getLoginSecUser(session));
			
			
//			// save to audit trail
//			String activity 	= "MPIN_RESET";
//			long moduleId 		= 2007;
//			auditTrailService.save(request, moduleId, activity, dataBefore, dataAfter, client.getCid(), getLoginSecUser(session));

			return new AjaxResponse(true, "Success for authorization reset");
		} else {

			return new AjaxResponse(false, "You are not authorized to access this module.");
		}
	}

	@RequestMapping("/deactivate/{id}")
	public @ResponseBody AjaxResponse deactivate(@PathVariable Long id, HttpServletRequest request, HttpSession session) {
		System.out.println("###########id: "+ id);
		if (getPriviledgeUser(session, PRIVILEDGE, C_DEACTIVATE)) {
			Client client		= clientService.findById(id);
			String dataBefore	= getXMLValue(client);
			clientService.deactivate(client, getLoginSecUser(session));
			String dataAfter	= getXMLValue(clientService.findById(id));
			
			// save to audit trail
			String activity 	= "USER_DEACTIVATE";
			long moduleId 		= 2008;
			auditTrailService.save(request, moduleId, activity, dataBefore, dataAfter, client.getCid(), getLoginSecUser(session));

			return new AjaxResponse(true, "Client was successfully deactivated.");
		} else {

			return new AjaxResponse(false, "You are not authorized to access this module.");
		}
	}

	@RequestMapping("/restrict--")
	public @ResponseBody AjaxResponse restricted(
		@RequestParam(required = false) Long cid,	
		@RequestParam(required = false) String username,	
		@RequestParam(required = false) String value,	 
		HttpServletRequest request, 
		HttpSession session) {
		if (getPriviledgeUser(session, PRIVILEDGE, C_RESTRICT)) {
			

			// save to audit trail
			String dataBefore 	= "Unrestrict";
			String dataAfter 	= "Restricted";
			String activity 	= "RESTRICTED";
			long moduleId 		= 2011;
			auditTrailService.save(request, moduleId, activity, dataBefore, dataAfter, cid.toString(), getLoginSecUser(session));

			return new AjaxResponse(true, "Client was successfully restricted.");
		} else {

			return new AjaxResponse(false, "You are not authorized to access this module.");
		}
	}
	
	@RequestMapping("/unrestrict/{id}")
	public @ResponseBody AjaxResponse unrestrict(@PathVariable Long id, HttpServletRequest request, HttpSession session) {
		System.out.println("###########id: "+ id);
		if (getPriviledgeUser(session, PRIVILEDGE, C_RESTRICT)) {
			Client client = clientService.findById(id);
			client.setRestrict(0);
			clientService.save(client, getLoginSecUser(session));

			// save to audit trail
			String dataBefore	= "Restricted";
			String dataAfter 	= "Unrestrict";
			String activity 	= "UNRESTRICT";
			long moduleId 		= 2012;
			auditTrailService.save(request, moduleId, activity, dataBefore, dataAfter, client.getCid(), getLoginSecUser(session));

			return new AjaxResponse(true, "Client was successfully unrestrict.");
		} else {

			return new AjaxResponse(false, "You are not authorized to access this module.");
		}
	}

	@RequestMapping("/viewUsername/{id}")
	public @ResponseBody AjaxResponse viewUsername(@PathVariable Long id, HttpServletRequest request, HttpSession session) {
		System.out.println("###########id: "+ id);
		if (getPriviledgeUser(session, PRIVILEDGE, C_VIEW_USERNAME)) {

			Client client = clientService.findById(id);

			return new AjaxResponse(true, client.getLogin());
		} else {

			return new AjaxResponse(false, "You are not authorized to access this module.");
		}
	}

	@RequestMapping("/enableAgentFeature/{id}")
	public @ResponseBody AjaxResponse enableAgentFeature(@PathVariable Long id, HttpServletRequest request, HttpSession session) {
		System.out.println("###########id: "+ id);
		if (getPriviledgeUser(session, PRIVILEDGE, C_AGENT_FEATURE)) {
			Client client = clientService.findById(id);
			client.setAgentFeature(1);
			client.setEnableAgentFeatures(new Date());
			client.setEnableAgentFeaturesBy(getLoginSecUser(session).getId());
			
			clientService.save(client, getLoginSecUser(session));

			// save to audit trail
			String dataBefore	= "Disable";
			String dataAfter 	= "Enable";
			String activity 	= "AGENT_FEATURE_ENABLE";
			long moduleId 		= 2011;
			auditTrailService.save(request, moduleId, activity, dataBefore, dataAfter, client.getCid(), getLoginSecUser(session));

			return new AjaxResponse(true, "Enable agent feature success.");
		} else {

			return new AjaxResponse(false, "You are not authorized to access this module.");
		}
	}

	@RequestMapping("/disableAgentFeature/{id}")
	public @ResponseBody AjaxResponse disableAgentFeature(@PathVariable Long id, HttpServletRequest request, HttpSession session) {
		System.out.println("###########id: "+ id);
		if (getPriviledgeUser(session, PRIVILEDGE, C_AGENT_FEATURE)) {
			Client client = clientService.findById(id);
			client.setAgentFeature(0);
			clientService.save(client, getLoginSecUser(session));

			// save to audit trail
			String dataBefore 	= "Enable";
			String dataAfter 	= "Disable";
			String activity 	= "AGENT_FEATURE_DISABLE";
			long moduleId 		= 2012;
			auditTrailService.save(request, moduleId, activity, dataBefore, dataAfter, client.getCid(), getLoginSecUser(session));

			return new AjaxResponse(true, "Disable agent feature success.");
		} else {

			return new AjaxResponse(false, "You are not authorized to access this module.");
		}
	}

	private void putIntoRequest(Model model) {
		model.addAttribute("SELECTED_MENU", MENU);
		model.addAttribute("SELECTED_SUBMENU", PRIVILEDGE);
	}
	
	@RequestMapping("/resetStatus/{id}")
	public @ResponseBody AjaxResponse resetStatus(@PathVariable Long id, HttpServletRequest request, HttpSession session) {
		System.out.println("###########id: "+ id);
		if (getPriviledgeUser(session, PRIVILEDGE, C_RESET_STATUS)) {
			Client client 		= clientService.findById(id);
			
			AuthorResetPasswordModel authorResetPassword = new AuthorResetPasswordModel();
			authorResetPassword.setClientId(client.getId());
			authorResetPassword.setStatus("P");
			authorResetPassword.setType("RESET_STATUS");
			authorResetPasswordService.save(authorResetPassword, getLoginSecUser(session));
			
			return new AjaxResponse(true, "Success for authorization reset status");
		} else {

			return new AjaxResponse(false, "You are not authorized to access this module.");
		}
	}
	
	@RequestMapping("/resetStatusAndCredential/{id}")
	public @ResponseBody AjaxResponse resetStatusAndCredential(@PathVariable Long id, HttpServletRequest request, HttpSession session) {
		System.out.println("###########id: "+ id);
		if (getPriviledgeUser(session, PRIVILEDGE, C_RESET_STATUS)) {
			Client client 		= clientService.findById(id);
			
			AuthorResetPasswordModel authorResetPassword = new AuthorResetPasswordModel();
			authorResetPassword.setClientId(client.getId());
			authorResetPassword.setStatus("P");
			authorResetPassword.setType("RESET_STATUS_AND_CREDENTIAL");
			authorResetPasswordService.save(authorResetPassword, getLoginSecUser(session));
			
			return new AjaxResponse(true, "Success for authorization reset status");
		} else {

			return new AjaxResponse(false, "You are not authorized to access this module.");
		}
	}
	
	@RequestMapping("/blockedActCode/{id}")
	public @ResponseBody AjaxResponse blockedActCode(@PathVariable Long id, HttpServletRequest request, HttpSession session) {
		System.out.println("###########id: "+ id);
		if (getPriviledgeUser(session, PRIVILEDGE, C_BLOCK_STATUS)) {
			Client client = clientService.findById(id);
			client.setLoginRetry(0);
			client.setStatus("INACTIVE");
			clientService.save(client, getLoginSecUser(session));

			return new AjaxResponse(true, "Unblock activation success.");
		} else {

			return new AjaxResponse(false, "You are not authorized to access this module.");
		}
	}
	
	@RequestMapping("/blockedValCode/{id}")
	public @ResponseBody AjaxResponse blockedValCode(@PathVariable Long id, HttpServletRequest request, HttpSession session) {
		System.out.println("###########id: "+ id);
		if (getPriviledgeUser(session, PRIVILEDGE, C_BLOCK_STATUS)) {
			Client client = clientService.findById(id);
			client.setLoginRetry(0);
			client.setStatus("ACTIVE");
			clientService.save(client, getLoginSecUser(session));

			return new AjaxResponse(true, "Unblock reset credential success.");
		} else {

			return new AjaxResponse(false, "You are not authorized to access this module.");
		}
	}
	
	@RequestMapping("/disableMerchantFeature/{id}")
	public @ResponseBody AjaxResponse disableMerchantFeature(@PathVariable Long id, HttpServletRequest request, HttpSession session) {
		System.out.println("###########id: "+ id);
		if (getPriviledgeUser(session, PRIVILEDGE, C_MERCHANT_FEATURE)) {
			SecUser userLogin = getLoginSecUser(session);
			Client client 		= clientService.findById(id);
			MerchantEntity merchant = merchantService.findByClientId(id);
			String dataBefore	= "Enable|"+merchant.getMerchantName()+"|"+merchant.getMerchantDeactivatedRemarks();
			
			
			merchant.setMerchantStatus(0);
			merchant.setMerchantDeactivatedDate(new Date());
			merchant.setMerchantDeactivatedBy(userLogin.getId());
			merchant.setMerchantDeactivatedRemarks("Manual");
			
			merchantService.save(merchant, getLoginSecUser(session));

			// save to audit trail
			String dataAfter 	= "Disable|"+merchant.getMerchantName()+"|"+merchant.getMerchantDeactivatedRemarks();
			String activity 	= "DISABLE_MERCHANT_FEATURE";
			long moduleId 		= 2011;
			auditTrailService.save(request, moduleId, activity, dataBefore, dataAfter, client.getCid(), getLoginSecUser(session));
						
			return new AjaxResponse(true, "Disable merchant feature success.");
		} else {

			return new AjaxResponse(false, "You are not authorized to access this module.");
		}
	}
	
	@RequestMapping("/enableMerchantFeature/{dataParsing}")
	public @ResponseBody AjaxResponse enableMerchantFeature(@PathVariable String dataParsing, HttpServletRequest request, HttpSession session) {
		System.out.println("###########enable-dataParsing: "+ dataParsing);
		if (getPriviledgeUser(session, PRIVILEDGE, C_MERCHANT_FEATURE)) {
			Long id = 0L;
			String businessName = "";
			try {
				System.out.println("###########enable-dataParsing: "+ dataParsing);
				String[] data = dataParsing.split("\\|");
				
				if(data[0] != null){
					id = Long.parseLong(data[0]);
					System.out.println("###########enable-id: "+ id);
				}
				if(data[1] != null){
					businessName = data[1];
					businessName = businessName.replace("-", ".");
					System.out.println("###########enable-businessName: "+ businessName);
				}
				
			} catch(Exception e){
				e.printStackTrace();
				return new AjaxResponse(false, "Business name is required");
			}
			
			SecUser userLogin = getLoginSecUser(session);
			Client client 		= clientService.findById(id);
			MerchantEntity merchant = merchantService.findByClientId(id);
			
			if (merchant == null) {
				merchant = new MerchantEntity();
				merchant.setClientId(client.getId());
				merchant.setMerchantId("");
				merchant.setMerchantCity("");
				merchant.setMerchantAccountNumber("");
				merchant.setMerchantName("");
				merchant.setCreatedBy(userLogin.getId());
				merchant.setCreatedDate(new Date());
				merchantService.save(merchant, getLoginSecUser(session));
			}
			
			merchant.setMerchantStatus(1);
			merchant.setMerchantActivatedDate(new Date());
			merchant.setMerchantActivatedBy(userLogin.getId());
			
			/*if(!merchant.getMerchantName().equals("")){
				ParamConfig config 	= genericService.getConfigByName(ParamConfig.MERCHANT_QR_CODE_EXPIRATION);
		        String merchantQrExpiration = config.getValue();
		         
				Calendar cal = Calendar.getInstance();
		        cal.setTime(new Date());
		        cal.add(Calendar.MONTH, Integer.parseInt(merchantQrExpiration));
		        merchant.setMerchantExpiredDate(cal.getTime());
			}*/
			merchantService.save(merchant, getLoginSecUser(session));

			// save to audit trail
			String dataBefore	= "Disable|"+merchant.getMerchantName();
			String dataAfter 	= "Enable|"+businessName;
			System.out.println("###########dataBefore: "+ dataBefore);
			System.out.println("###########dataAfter: "+ dataAfter);
			String activity 	= "ENABLE_MERCHANT_FEATURE";
			long moduleId 		= 2011;
			auditTrailService.save(request, moduleId, activity, dataBefore, dataAfter, client.getCid(), getLoginSecUser(session));
							
			return new AjaxResponse(true, "Enable merchant feature success.");
		} else {

			return new AjaxResponse(false, "You are not authorized to access this module.");
		}
	}
	
	@RequestMapping("/updateMerchant/{dataParsing}")
	public @ResponseBody AjaxResponse updateMerchant(@PathVariable String dataParsing, HttpServletRequest request, HttpSession session) {
		
		if (getPriviledgeUser(session, PRIVILEDGE, C_UPDATE_MERCHANT)) {
			Long id = 0L;
			String businessName = "";
			try {
				System.out.println("###########dataParsing: "+ dataParsing);
				String[] data = dataParsing.split("\\|");
				
				if(data[0] != null){
					id = Long.parseLong(data[0]);
					System.out.println("###########id: "+ id);
				}
				if(data[1] != null){
					businessName = data[1];
					businessName = businessName.replace("-", ".");
					System.out.println("###########businessName: "+ businessName);
				}
				
			} catch(Exception e){
				e.printStackTrace();
				return new AjaxResponse(false, "Business name is required");
			}
			
			Client client 		= clientService.findById(id);
			MerchantEntity merchant = merchantService.findByClientId(id);
			
			Boolean ifExist = merchantService.ifExist(id, businessName);
			
			if(businessName.equals("")){
				return new AjaxResponse(false, "Business Name Is Required");
			} else {
				System.out.println("###########ifExist: "+ ifExist);
				if(ifExist) {
					return new AjaxResponse(false, String.format("Business Name '%s' Is Already Exists", businessName));
				}else {
					// save to audit trail
					String dataBefore 	= getXMLValue(merchant);
					
					/*if(merchant.getMerchantName().equals("")){
						ParamConfig config 	= genericService.getConfigByName(ParamConfig.MERCHANT_QR_CODE_EXPIRATION);
				        String merchantQrExpiration = config.getValue();
				         
						Calendar cal = Calendar.getInstance();
				        cal.setTime(new Date());
				        cal.add(Calendar.MONTH, Integer.parseInt(merchantQrExpiration));
				        merchant.setMerchantExpiredDate(cal.getTime());
					}*/
					
					merchant.setMerchantName(businessName);
					
					HttpClientMcu httpClient = new HttpClientMcu();
					String url = genericService.getConfigValueByName("URL_GENERATE_MERCHANT");
					
					String json = "";
					if (StringUtils.isNotBlank(url)) {
						try {
							url = url + "customerId=" + merchant.getClientId() + "&merchantName=" + URLEncoder.encode(businessName,"UTF-8");
							System.out.println("########### url = "+url);
							
							json = httpClient.callMbo(url);
						} catch (UnsupportedEncodingException e) {
						    e.printStackTrace();
						}
					}
					System.out.println("########### json = "+json);
					
					try{
						JSONParser parser = new JSONParser();
						Object obj = parser.parse(json);
						JSONObject oj =  (JSONObject) obj;
						if(!oj.get("errorCode").equals("")){
							if(oj != null){
								System.out.println("jo = "+oj.toJSONString());
								String err = (String) oj.get("fullMessage");
								return new AjaxResponse(false, err);
							}
						}else{
							JSONObject data = (JSONObject) oj.get("data");
							
							merchant.setMerchantId((String) data.get("merchantId"));			
							merchant.setMerchantCity((String) data.get("merchantCity"));
							merchant.setMerchantAccountNumber((String) data.get("merchantAccountNumber"));
							merchant.setMerchantQrCode((String) data.get("merchantQrCode"));
							merchant.setMerchantPostalCode((String) data.get("merchantPostalCode"));
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
					
					merchantService.save(merchant, getLoginSecUser(session));
								
					String dataAfter 	= getXMLValue(merchantService.findByClientId(id));
					String activity 	= "UPDATE_MERCHANT_BUSINESS_NAME";
					long moduleId 		= 2013;
					auditTrailService.save(request, moduleId, activity, dataBefore, dataAfter, client.getCid(), getLoginSecUser(session));
					
					return new AjaxResponse(true, "Update merchant business name success.");
				}
			}
		} else {

			return new AjaxResponse(false, "You are not authorized to access this module.");
		}
	}
	
	@RequestMapping("/syncronize/{id}")
	public @ResponseBody AjaxResponse syncronize(@PathVariable String id, HttpServletRequest request, HttpSession session) {

			System.out.println("#################### update-data = "+id);
			
			if (getPriviledgeUser(session, PRIVILEDGE, C_SYNCHRONIZE)) {
				
				
				String[] cp = id.split("\\|");
				
				ViewClient view = clientService.findByCidAndTypeCodeClientType(cp[3]);
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
							System.out.println("########### masuk sini ");
							JSONParser parser = new JSONParser();
							Object obj = parser.parse(json);
							JSONObject oj =  (JSONObject) obj;
							if(oj != null){
								System.out.println("jo = "+oj.toJSONString());
								String err = (String) oj.get("message");
								return new AjaxResponse(false, err);
							}
						}else{
							System.out.println("########### engga ko masuk sini ");
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
					return new AjaxResponse(true,"Data Updated", view);
				}else{
					return new AjaxResponse(false, "Customer Id Not Exist");
				}
			
				
			}else{
				return new AjaxResponse(false, "You are not authorized to access this module.");
			}
	}
	
	public void updateDataSync(ViewClient vc, SecUser userLogin, Client c){
		c.setAccount(vc.getAccount());
		c.setLastUpdatedBy(userLogin.getId());
		c.setLastUpdatedDate(new Date());
		c.setType(vc.getTypeCode());
		c.setMobileNo(vc.getMobileNo());
		c.setName(vc.getFullname());
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
	
	
}