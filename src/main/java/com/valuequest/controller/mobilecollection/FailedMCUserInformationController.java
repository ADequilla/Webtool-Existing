package com.valuequest.controller.mobilecollection;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.valuequest.common.AjaxResponse;
import com.valuequest.common.DataTables;
import com.valuequest.controller.BaseController;
import com.valuequest.entity.TempAccountOfficer;
import com.valuequest.entity.security.SecUser;
import com.valuequest.util.GenerateReport;

import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Controller
@RequestMapping("/mobilecollection/failedinformation")
public class FailedMCUserInformationController extends BaseController {
	final static String MENU = "MOBILECOLLECTION";
	final static String PRIVILEDGE = "FAILED_MCUSER_INFORMATION";
	private String BASE_VIEW = "11.mobilecollection/";
	private String LIST_VIEW = "failed-mcuser-information";
	private String REPORT_NAME = "failed-mcuser-information";
	private String TEMPLATE_NAME = "FailedMcUserInformation";

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
			@RequestParam(required = false) String submitedDateStart,
			@RequestParam(required = false) String submitedDateEnd, @RequestParam(required = false) String userUploader,
			@RequestParam(required = false) String reportId, HttpSession session) {

		HashMap<String, Object> searchMap = new HashMap<>();
		searchMap.put("loginId", getUserIdFromSession(session));
		searchMap.put("submitedDateStart", submitedDateStart);
		searchMap.put("submitedDateEnd", submitedDateEnd);
		searchMap.put("userUploader", userUploader);
		searchMap.put("reportId", reportId);

		return logUploadFileService.searchByMapCriteria(dataTables, searchMap);
	}

	@RequestMapping(value = "/download/{id}", method = RequestMethod.GET)
	public @ResponseBody AjaxResponse download(@PathVariable Long id, HttpServletResponse response, HttpSession session) {
		if (getPriviledgeUser(session, PRIVILEDGE, DOWNLOAD)) {
			HashMap<String, Object> map = new HashMap<>();
			map.put("logUploadFileId", id);

			List<TempAccountOfficer> accountOfficers = tempAccountOfficerService.searchByMapCriteria(map);
			JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(accountOfficers);
			GenerateReport report = new GenerateReport();
			report.generatePdfReport(TEMPLATE_NAME, REPORT_NAME, map, beanColDataSource, "CSV", response);
		}else {
			return new AjaxResponse(false, "You are not authorized to access this module.");
		}

		return null;
	}

	private void putIntoRequest(Model model) {
		model.addAttribute("SELECTED_MENU", MENU);
		model.addAttribute("SELECTED_SUBMENU", PRIVILEDGE);
	}
}
