package com.valuequest.controller.monitoring;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.valuequest.common.DataTables;
import com.valuequest.controller.BaseController;
import com.valuequest.controller.api.http.HttpRequestSender;
import com.valuequest.controller.api.model.CoreTransactionReportModel;
import com.valuequest.controller.api.model.SuperAppTransactionModel;
import com.valuequest.controller.monitoring.model.SMSLogModel;
import com.valuequest.controller.monitoring.model.UpdateSavingsStatusFeatureModel;
import com.valuequest.entity.Lookup;
import com.valuequest.entity.security.SecUser;

@Controller
@RequestMapping("/monitoring/sms")
public class SmsLogsController extends BaseController {

	final static String MENU 		= "MONITORING";
	final static String PRIVILEDGE 	= "SMS_LOGS";
	private String BASE_VIEW 		= "04.monitoring/";
	private String LIST_VIEW 		= "sms-logs";

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
	
	
	@RequestMapping(value = "/logs", method = RequestMethod.POST)
	public @ResponseBody  String searchSMSLogs( 
			@RequestParam(required = false) String dtStart,	
			@RequestParam(required = false) String dtEnd,
			HttpSession session, HttpServletResponse response) {
			response.setHeader("X-Frame-Options", "DENY");
			HttpRequestSender sender = null;
	
			try {
				
				SMSLogModel reqLogModel = new SMSLogModel();
				
				reqLogModel.setStartDate(dtStart);
				reqLogModel.setEndDate(dtEnd);
				
				Gson gson = new Gson();
				System.out.println("SMS Logs JSON Request :::::::::" + gson.toJson(reqLogModel)); 
				
				//sender = new HttpRequestSender("https://prod-api-janus.fortress-asya.com:8443/api/sms/get",reqLogModel);
				sender = new HttpRequestSender("https://prod-api-janus.fortress-asya.com:17000/api/sms/get",reqLogModel); //TEST CHANGE PORT
				//sender = new HttpRequestSender("https://rbi-webtools.fortress-asya.com/api/sms/get",reqLogModel);

				final HashMap<String, String> headers = new HashMap<String, String>();
				headers.put("Content-Type", "application/json");
				sender.setHeaders(headers);
			
				if(sender.requestSent()){
					System.out.println("SMS Logs JSON Response :::::::::" + sender.getResponse()); 
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
	
	protected SecUser getLoginSecUser(HttpSession session) {
		return (SecUser) session.getAttribute("loginSecUser");
	}
	
	
	
	@RequestMapping("/search")
	public @ResponseBody DataTables search(DataTables dataTables, 
			@RequestParam(required = false) String cid,
			@RequestParam(required = false) String searchDateStart,
			@RequestParam(required = false) String searchDateEnd,
			@RequestParam(required = false) String mobileNo,
			@RequestParam(required = false) String msgStatus,
			@RequestParam(required = false) String searchMt,
			HttpSession session, HttpServletResponse response) {
			response.setHeader("X-Frame-Options", "DENY");
		HashMap<String, Object> searchMap = new HashMap<>();
		searchMap.put("cid", cid);
		searchMap.put("searchDateStart", searchDateStart);
		searchMap.put("searchDateEnd", searchDateEnd);
		searchMap.put("mobileNo", mobileNo);
		searchMap.put("msgStatus", msgStatus);
		searchMap.put("searchMt", searchMt);
		
		//smsLogsService.searchByMapCriteria(dataTables, searchMap,session);
		return smsLogsService.searchByMapCriteria(dataTables, searchMap, session);
	}
	
	
	

	private void putIntoRequest(Model model) {
		model.addAttribute("SELECTED_MENU", MENU);
		model.addAttribute("SELECTED_SUBMENU", PRIVILEDGE);
		model.addAttribute("listSmsStatus", genericService.lookup(Lookup.LOOKUP_SMS_LOG_STATUS));

		List<Lookup> listMessageType = new ArrayList<Lookup>();
		Lookup l = new Lookup();
		l.setValue("Enrollment");
		l.setDescription("Enrollment");
		listMessageType.add(l);

		l = new Lookup();
		l.setValue("Cash In");
		l.setDescription("Cash In");
		listMessageType.add(l);

		l = new Lookup();
		l.setValue("Agent Assisted Payment");
		l.setDescription("Agent Assisted Payment");
		listMessageType.add(l);

		l = new Lookup();
		l.setValue("Reset Password");
		l.setDescription("Reset Password");
		listMessageType.add(l);

		l = new Lookup();
		l.setValue("Reset MPIN");
		l.setDescription("Reset MPIN");
		listMessageType.add(l);

		l = new Lookup();
		l.setValue("Reset Credential");
		l.setDescription("Reset Credential");
		listMessageType.add(l);

		l = new Lookup();
		l.setValue("Remittance");
		l.setDescription("Remittance");
		listMessageType.add(l);
		
		l = new Lookup();
		l.setValue("Bill Payment");
		l.setDescription("Bill Payment");
		listMessageType.add(l);

		l = new Lookup();
		l.setValue("Forgot Password - Mobcol");
		l.setDescription("Forgot Password - Mobcol");
		listMessageType.add(l);
		
		l = new Lookup();
		l.setValue("Forgot MPIN - Mobcol");
		l.setDescription("Forgot MPIN - Mobcol");
		listMessageType.add(l);
		
		model.addAttribute("listMessageType", listMessageType);
	}
}