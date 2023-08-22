package com.valuequest.controller.dashboard;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.time.Duration;
import java.time.Instant;
import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.http.HttpSession;

import com.valuequest.common.DataTables;
import com.valuequest.controller.BaseController;
import com.valuequest.controller.IndexController;
import com.valuequest.entity.ViewClient;
import com.valuequest.entity.security.SecUser;
import com.valuequest.services.ClientService;
import com.valuequest.entity.ParamConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("/dashboard/registered-client")
public class DashboardRegisteredClientController extends BaseController {

    final static String MENU 		= "DASHBOARD";
	final static String PRIVILEDGE 	= "REGISTERED_CLIENT";
	private String BASE_VIEW 		= "13.dashboard/";
	// private String LIST_VIEW 		= "main-list";
    private String LIST_VIEW 		= "registered-client";


    @RequestMapping("/")
	public String index(Model model, HttpSession session) {
		if (getPriviledgeUser(session, PRIVILEDGE, VIEW)) {

            loginstat(session);

			putIntoRequest(model);

			return BASE_VIEW + LIST_VIEW;
        }

		return getUnauthorizedPage();
	}

    public void loginstat(HttpSession session) {
		
		SecUser user = this.getLoginSecUser(session);

		user.setIsLogin(true);
		adminService.updateCekStatus(user, session.getId());
}

    
 
    @RequestMapping("/search")
	public @ResponseBody List<ViewClient> search( 
        @RequestParam(required = false) String dateStart,
        @RequestParam(required = false) String dateEnd,
        @RequestParam(required = false) String insti,
        @RequestParam(required = false) String branch,
        @RequestParam(required = false) String unit,
        @RequestParam(required = false) String center,
        HttpSession session) {

        // HashMap<String, Object> searchMap = new HashMap<>();
        // searchMap.put("startDate", startDate);
        // searchMap.put("endDate", endDate);
        // searchMap.put("insti", insti);
        // searchMap.put("branch", branch);
        // searchMap.put("unit", unit);
        // searchMap.put("center", center);

        System.out.println("Start Date"+ dateStart);
		System.out.println("End Date"+ dateEnd);
		System.out.println("Insti"+ insti);
		System.out.println("Branch"+ branch);
		System.out.println("Unit"+ unit);
		System.out.println("Center"+ center);

        return dashboardService.findByCriteria(dateStart, dateEnd, insti, branch, unit, center);
	}

    private void putIntoRequest(Model model) {
        model.addAttribute("REGISTERED_CLIENT_REPORT", genericService.getConfigValueByName(ParamConfig.WEB_DASHBOARD_REGISTERED_CLIENT));
		model.addAttribute("SELECTED_MENU", MENU);
		model.addAttribute("SELECTED_SUBMENU", PRIVILEDGE);
	}
    
}