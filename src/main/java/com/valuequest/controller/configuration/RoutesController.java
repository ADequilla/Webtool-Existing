package com.valuequest.controller.configuration;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.valuequest.common.DataTables;
import com.valuequest.controller.BaseController;
import com.valuequest.entity.ViewRoutes;
import com.valuequest.entity.security.SecUser;

@Controller
@RequestMapping("/configuration/routes/")
public class RoutesController extends BaseController {
	
	final static String MENU 		= "CONFIGURATION";
	final static String PRIVILEDGE 	= "ROUTES";
	private String BASE_VIEW 		= "12.configuration/";
	private String LIST_VIEW 		= "routes";
	//private String CREATE_VIEW 		= "unit-create";
	
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
		System.out.println("Unauthorized");
		return getUnauthorizedPage();
	}
	
	@RequestMapping("/search")
	public @ResponseBody DataTables search(DataTables dataTables, 
			@RequestParam(required = false) String trnCode,
			@RequestParam(required = false) String description,
			HttpSession session, HttpServletResponse response) {
			response.setHeader("X-Frame-Options", "DENY");


		HashMap<String, Object> searchMap = new HashMap<>();
		searchMap.put("trnCode", trnCode);
		searchMap.put("description", description);

		return routesService.searchByMapCriteria(dataTables, searchMap);
	}

	private void putIntoRequest(Model model) {
		model.addAttribute("SELECTED_MENU", MENU);
		model.addAttribute("SELECTED_SUBMENU", PRIVILEDGE);
		List<ViewRoutes> listRoutes = routesService.getAll();
		model.addAttribute("listRoutes", listRoutes);
		
	}
	
}
