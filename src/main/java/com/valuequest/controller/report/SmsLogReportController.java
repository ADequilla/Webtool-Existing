package com.valuequest.controller.report;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;
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
import com.valuequest.controller.report.param.ReportParam;
import com.valuequest.entity.AsynReport;
import com.valuequest.entity.Lookup;
import com.valuequest.entity.SmsLogs;
import com.valuequest.entity.StructureInstitution;
import com.valuequest.entity.UserInstitution;
import com.valuequest.entity.security.SecUser;
import com.valuequest.services.SmsLogsService;
import com.valuequest.services.impl.SimpleServiceImpl;

@Controller
@RequestMapping("/report/sms-log-report")
public class SmsLogReportController extends BaseController{
	

	List<String> instiList = new ArrayList<String>();

	
	protected SecUser getLoginSecUser(HttpSession session) {
		return (SecUser) session.getAttribute("loginSecUser");
	}
	
	private class SimpleServ extends SimpleServiceImpl<SmsLogs> implements SmsLogsService{

		
		
		private DetachedCriteria criteriaBy(Long userId) {
			DetachedCriteria criteria = DetachedCriteria.forClass(UserInstitution.class);
			criteria.createAlias("user", "user");
			criteria.createAlias("institution", "institution");
			criteria.add(Restrictions.eq("user.id", userId));
			criteria.setProjection(Projections.projectionList().add(Projections.groupProperty("institution.code")));

			return criteria;
		}
		

		public void getUserInsti(Long userId) {
			
			Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(StructureInstitution.class);
			criteria.add(Subqueries.propertyIn("code", criteriaBy(userId)));
			List r = criteria.list();
			for (Iterator iterator = r.iterator(); iterator.hasNext();){
				StructureInstitution insti = (StructureInstitution) iterator.next();		
	            instiList.add(insti.getCode());
			}
			
			System.out.println("#####Arrray of Insti#####\n "+  instiList.toString());
			
		}


		@Override
		public DataTables searchByMapCriteria(DataTables dataTables, HashMap<String, Object> searchMap,
				HttpSession session) {
			// TODO Auto-generated method stub
			return null;
		}


		@Override
		public Class<SmsLogs> getRealClass() {
			// TODO Auto-generated method stub
			return null;
		}


		@Override
		public DataTables searchByMapCriteria(DataTables dataTables, HashMap<String, Object> searchMap) {
			// TODO Auto-generated method stub
			return null;
		}
		
	
	}
	
	
	
	final static String MENU 		= "REPORT";
	final static String PRIVILEDGE 	= "SMS_LOG_REPORT";
	final static String RPT_MENU	= "sms-log-report";
	final static String RPT_TITLE 	= "Report SMS Log";
	private String BASE_VIEW 		= "08.report/";
	private String LIST_VIEW 		= "report-list";
	private String GENERATE_VIEW	= "sms-log-report";
	
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
		searchMap.put("type", AsynReport.REPORT_TYPE_SMS_LOG);
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
			model.addAttribute("listSmsStatus", genericService.lookup(Lookup.LOOKUP_SMS_LOG_STATUS));
			putIntoRequest(model);
			
			return BASE_VIEW + GENERATE_VIEW;
		}

		return getUnauthorizedPage();
	}
	
	
	@RequestMapping(value = "/save", method = RequestMethod.POST, headers = { "content-type=application/json" })
	public @ResponseBody AjaxResponse save(@RequestBody ReportParam param, HttpSession session) {
		SimpleServ ss = new SimpleServ();
		SecUser user = this.getLoginSecUser(session);
		System.out.print("#######User get Id######"+user.getId());
		//ss.getUserInsti(user.getId());
		System.out.println("#####Insti List######" + instiList);
		
		String params = param.getDateRangeStart() + "|" + 
						param.getDateRangeEnd() + "|" + 
						param.getMessageType() +  "|" +
						param.getSmsStatus() + "|END";

		AsynReport report = new AsynReport();
		report.setType(AsynReport.REPORT_TYPE_SMS_LOG);
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