/**
 * 
 */
package com.valuequest.controller.mobilecollection;

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
import com.valuequest.controller.report.param.TransactionParam;
import com.valuequest.entity.AsynReport;
import com.valuequest.entity.Lookup;
import com.valuequest.entity.security.SecUser;

/**
 * @author Eki Nurhadi
 *
 */

@Controller
@RequestMapping("/mobilecollection/auditrail")
public class MCAuditTrailController extends BaseController{

	final static String MENU = "MOBILECOLLECTION";
	final static String PRIVILEDGE = "MCAUDITTRAIL";
	final static String RPT_MENU = "auditrail";
	final static String RPT_TITLE = "MC Audit Trail";
	private String BASE_VIEW = "11.mobilecollection/";
	private String LIST_VIEW = "report-list";
	private String GENERATE_VIEW = "param-audittrail";

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

	@RequestMapping("/search")
	public @ResponseBody DataTables search(DataTables dataTables,
			@RequestParam(required = false) String submitedDateStart,
			@RequestParam(required = false) String submitedDateEnd,
			@RequestParam(required = false) String reportId, HttpSession session, HttpServletResponse response) {
				response.setHeader("X-Frame-Options", "DENY");
		HashMap<String, Object> searchMap = new HashMap<>();
		searchMap.put("loginId", getUserIdFromSession(session));
		searchMap.put("type", AsynReport.REPORT_TYPE_MC_AUDIT_TRAIL);
		searchMap.put("submitedDateStart", submitedDateStart);
		searchMap.put("submitedDateEnd", submitedDateEnd);
		searchMap.put("reportId", reportId);

		return asynReportService.searchByMapCriteria(dataTables, searchMap);
	}

	@RequestMapping("/generate")
	public String generate(Model model, HttpSession session, HttpServletResponse response) {
		response.setHeader("X-Frame-Options", "DENY");
		if (getPriviledgeUser(session, PRIVILEDGE, DOWNLOAD)) {

			model.addAttribute("listTransaction", genericService.lookup(Lookup.LOOKUP_TRANSACTION_LOG_TYPE));
			model.addAttribute("listTransStatus", genericService.lookup(Lookup.LOOKUP_TRANSACTION_STATUS));
			model.addAttribute("listFileType", genericService.lookup(Lookup.LOOKUP_REPORT_TYPE));
			model.addAttribute("listActionType", genericService.lookup(Lookup.LOOKUP_MC_ACTION));
			putIntoRequest(model);

			return BASE_VIEW + GENERATE_VIEW;
		}

		return getUnauthorizedPage();
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST, headers = { "content-type=application/json" })
	public @ResponseBody AjaxResponse save(@RequestBody TransactionParam param, HttpSession session, HttpServletResponse response) {
		response.setHeader("X-Frame-Options", "DENY");
		String params = param.getTransDateStart() + "|" + param.getTransDateEnd() + "|" + param.getBranchCode() + "|"
				+ param.getAction() + "|" + param.getChanges() + "|" + param.getApplication() + "|END";

		AsynReport report = new AsynReport();
		report.setType(AsynReport.REPORT_TYPE_MC_AUDIT_TRAIL);
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
		response.setHeader("X-Frame-Options", "DENY");
		downloadReport(id, response);
	}

	private void putIntoRequest(Model model) {
		model.addAttribute("SELECTED_MENU", MENU);
		model.addAttribute("SELECTED_SUBMENU", PRIVILEDGE);
		model.addAttribute("REPORT_MENU", RPT_MENU);
		model.addAttribute("REPORT_TITLE", RPT_TITLE);
		model.addAttribute("listMCChanges", genericService.lookup(Lookup.LOOKUP_MC_CHANGES));
		model.addAttribute("listMCActions", genericService.lookup(Lookup.LOOKUP_MC_ACTION));
		model.addAttribute("listApplications", genericService.lookup(Lookup.LOOKUP_APPLICATION));
	}
}
