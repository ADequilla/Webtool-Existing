package com.valuequest.controller.report;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
import com.valuequest.controller.report.param.ReportParam;
import com.valuequest.entity.AsynReport;
import com.valuequest.entity.Lookup;

@Controller
@RequestMapping("/report/registered-client")
public class RegisteredClientController extends BaseController {

	final static String MENU 		= "REPORT";
	final static String PRIVILEDGE 	= "RPT_REGISTERED_CLIENT";
	final static String RPT_MENU 	= "registered-client";
	final static String RPT_TITLE 	= "Registered Clients Report";
	private String BASE_VIEW 		= "08.report/";
	private String LIST_VIEW 		= "report-list";
	private String GENERATE_VIEW 	= "param-registered-client";

	@RequestMapping("/")
	public String index(Model model, HttpSession session) {

		if (getPriviledgeUser(session, PRIVILEDGE, VIEW)) {

			model.addAttribute("listReportStatus", genericService.lookup(Lookup.LOOKUP_REPORT_STATUS));
			putIntoRequest(model);

			return BASE_VIEW + LIST_VIEW;
		}

		return getUnauthorizedPage();
	}

	@RequestMapping("/search")
	public @ResponseBody DataTables search(DataTables dataTables,
			@RequestParam(required = false) String submitedDateStart,
			@RequestParam(required = false) String submitedDateEnd,
			@RequestParam(required = false) String completedDateStart,
			@RequestParam(required = false) String completedDateEnd, 
			@RequestParam(required = false) String status,
			@RequestParam(required = false) String reportId, 
			@RequestParam(required = false) String branch,
			@RequestParam(required = false) String username, 
			HttpSession session) {
		
		  

		HashMap<String, Object> searchMap = new HashMap<>();
		searchMap.put("loginId", getUserIdFromSession(session));
		searchMap.put("type", AsynReport.REPORT_TYPE_REGISTERED_CLIENT);
		searchMap.put("submitedDateStart", submitedDateStart);
		searchMap.put("submitedDateEnd", submitedDateEnd);
		searchMap.put("completedDateStart", completedDateStart);
		searchMap.put("completedDateEnd", completedDateEnd);
		searchMap.put("status", status);
		searchMap.put("reportId", reportId);
		searchMap.put("branch", branch);
		searchMap.put("username", username);

		return asynReportService.searchByMapCriteria(dataTables, searchMap);
	}

	@RequestMapping("/generate")
	public String generate(Model model, HttpSession session) {

		if (getPriviledgeUser(session, PRIVILEDGE, NEW)) {

			model.addAttribute("listFileType", genericService.lookup(Lookup.LOOKUP_REPORT_TYPE));
			putIntoRequest(model);

			return BASE_VIEW + GENERATE_VIEW;
		}

		return getUnauthorizedPage();
	}
	
	public static String theMonth(int month){
	    String[] monthNames = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
	    return monthNames[month];
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST, headers = { "content-type=application/json" })
	public @ResponseBody AjaxResponse save(@RequestBody ReportParam param, HttpSession session) {
		
		// Date date = new Date();
		// ZoneId zone = ZoneId.of("Asia/Manila");
		// LocalDate localDate = date.toInstant().atZone(zone.systemDefault()).toLocalDate();
		// int year  = localDate.getYear();
		// int month = localDate.getMonthValue();
		// int day   = localDate.getDayOfMonth();

		// String[] months = {"-","Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sept","Oct","Nov","Dec"};
		
		// String dateRange = day +"-"+months[month]+"-"+year;
		 
		String params = param.getDateRangeStart() + "|" 
						+ param.getDateRangeEnd() + "|" 
						+ param.getInstiCode() + "|"
						+ param.getBranchCode() + "|" 
						+ param.getUnitCode() + "|" 
						+ param.getCenterCode() + "|END";
		
		AsynReport report = new AsynReport();
		report.setType(AsynReport.REPORT_TYPE_REGISTERED_CLIENT);
		report.setParam(params);
		report.setFileType(param.getType());
		report.setStatus(Lookup.LOOKUP_REPORT_STATUS_WAITING);
		report.setSubmitBy(getLoginSecUser(session));
		report.setSubmitedDate(new Date(System.currentTimeMillis()));

		asynReportService.saveOrUpdate(report);

		return new AjaxResponse(report);
	}

	@RequestMapping(value = "/download/{id}", method = RequestMethod.GET)
	public void download(@PathVariable Long id, HttpServletResponse response) {

		downloadReport(id, response);
	}

	private void putIntoRequest(Model model) {
		model.addAttribute("SELECTED_MENU", MENU);
		model.addAttribute("SELECTED_SUBMENU", PRIVILEDGE);
		model.addAttribute("REPORT_MENU", RPT_MENU);
		model.addAttribute("REPORT_TITLE", RPT_TITLE);
	}
}