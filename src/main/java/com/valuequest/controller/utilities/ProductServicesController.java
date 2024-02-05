package com.valuequest.controller.utilities;

import java.util.HashMap;
import java.util.List;

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
import com.valuequest.controller.administration.model.StateModel;
import com.valuequest.entity.Lookup;
import com.valuequest.entity.ServiceDownTime;
import com.valuequest.entity.security.SecUser;

@Controller
@RequestMapping("/utilities/product-and-services")
public class ProductServicesController extends BaseController {
    
    final static String MENU 		= "UTILITIES";
	final static String PRIVILEDGE 	= "PRODUCT_AND_SERVICES";
	private String BASE_VIEW 		= "05.utilities/";
	private String LIST_VIEW 		= "product-and-services-list";
	private String EDIT_VIEW 		= "product-and-services-create";

    @RequestMapping("/")
    public String index(Model model, HttpSession session, HttpServletResponse response) {
		response.setHeader("X-Frame-Options", "DENY");
		SecUser user = this.getLoginSecUser(session);

        user.setIsLogin(true);
        adminService.updateCekStatus(user, session.getId());
		//  SecUser user = this.getLoginSecUser(session);

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
			@RequestParam(required = false) String serviceName,
			@RequestParam(required = false) String serviceBanner, 
			@RequestParam(required = false) String createdDate,
			HttpSession session) {

		HashMap<String, Object> searchMap = new HashMap<>();
		searchMap.put("serviceName", serviceName);
		searchMap.put("serviceBanner", serviceBanner);
		searchMap.put("createdDate", createdDate);

		return productAndServicesService.searchByMapCriteria(dataTables, searchMap);
	}


    private void putIntoRequest(Model model) {
		model.addAttribute("SELECTED_MENU", MENU);
		model.addAttribute("SELECTED_SUBMENU", PRIVILEDGE);
		model.addAttribute("listClientType", genericService.lookup(Lookup.LOOKUP_ALL_CLIENT_TYPE));
	}

}



