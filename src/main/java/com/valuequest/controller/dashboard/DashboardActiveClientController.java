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
import org.springframework.web.servlet.ModelAndViewDefiningException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("/dashboard/active-client")
public class DashboardActiveClientController extends BaseController {
    

    final static String MENU 		= "DASHBOARD";
	final static String PRIVILEDGE 	= "ACTIVE_CLIENT";
	private String BASE_VIEW 		= "13.dashboard/";
	// private String LIST_VIEW 		= "main-list";
    private String LIST_VIEW 		= "active-client";


    @RequestMapping("/")
	public String index(Model model, HttpSession session) {
        loginstat(session);
        sessionCheck(session);
		if (getPriviledgeUser(session, PRIVILEDGE, VIEW)) {

			putIntoRequest(model);

			return BASE_VIEW + LIST_VIEW;
        }

		return getUnauthorizedPage();
	}
    private void sessionCheck(HttpSession session) {
        if (session != null && !session.isNew() && !session.getAttributeNames().hasMoreElements()) {
            logoutRedirect(session);  // Call the updated method to handle the redirection
        } else {
            checkIdleTimeAndLogout(session);
        }
    }
    

	public void checkIdleTimeAndLogout(HttpSession session) {
        ParamConfig config = genericService.getConfigByName(ParamConfig.SESSION_TIMEOUT_WEBTOOL);
        String valueTimeout = config.getValue().replaceAll("[.,]", ""); 
        Instant currentTime = Instant.now();
        Instant lastActivityTime = (Instant) session.getAttribute("lastActivityTime");
        
        if (lastActivityTime != null) {
            Duration idleDuration = Duration.between(lastActivityTime, currentTime);
            if (idleDuration.getSeconds() >= Integer.parseInt(valueTimeout)) {
                logoutRedirect(session);  // Call the updated method to handle the redirection
                return;
            }
        }
        session.setAttribute("lastActivityTime", currentTime);
    }

    private void logoutRedirect(HttpSession session) {
        // Perform a redirect using Spring's RedirectView
        RedirectView redirectView = new RedirectView("01.misc/login.jsp", true);
        redirectView.setExposeModelAttributes(false); // Optional, to prevent exposing model attributes
        redirectView.setStatusCode(HttpStatus.SEE_OTHER); // Set an appropriate status code
        logout(session);
        // Redirect the user
        ServletUriComponentsBuilder.fromCurrentContextPath().path(redirectView.getUrl()).build();
    }
    
    public String logout( HttpSession session) {
		SecUser user = this.getLoginSecUser(session);

    	user.setIsLogin(false);
		adminService.updateCekStatus(user, session.getId());
        session.invalidate();
    	return "redirect:j_spring_security_logout";
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
        model.addAttribute("ACTIVE_CLIENT_REPORT", genericService.getConfigValueByName(ParamConfig.WEB_DASHBOARD_ACTIVE_CLIENT));
		model.addAttribute("SELECTED_MENU", MENU);
		model.addAttribute("SELECTED_SUBMENU", PRIVILEDGE);
	}
    
}