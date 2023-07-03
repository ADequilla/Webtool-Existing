package com.valuequest.controller.utilities;

import java.util.HashMap;
import java.util.List;
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
import com.valuequest.entity.SplashScreen;

@Controller
@RequestMapping("/utilities/splash-screen")
public class SplashScreenController extends BaseController {
    
    final static String MENU 		= "UTILITIES";
	final static String PRIVILEDGE 	= "SPLASH_SCREEN";
	private String BASE_VIEW 		= "05.utilities/";
	private String LIST_VIEW 		= "splash-screen";	
	private String EDIT_VIEW 		= "splash-screen-edit";

    @RequestMapping("/")
    public String index(Model model, HttpSession session) {

        if (getPriviledgeUser(session, PRIVILEDGE, VIEW)) {

            putIntoRequest(model);

            return BASE_VIEW + LIST_VIEW;
        }

        return getUnauthorizedPage();
    }

    @RequestMapping("/search")
	public @ResponseBody DataTables search(DataTables dataTables, 
			@RequestParam(required = false) String title,
			@RequestParam(required = false) String action, 
			HttpSession session) {

		HashMap<String, Object> searchMap = new HashMap<>();
		searchMap.put("title", title);
		searchMap.put("action", action);

		return splashScreenService.searchByMapCriteria(dataTables, searchMap);
	}

	@RequestMapping("/edit/{id}")
	public String edit(@PathVariable Long id, Model model, HttpSession session) {

		if (getPriviledgeUser(session, PRIVILEDGE, EDIT)) {

			model.addAttribute("splash", splashScreenService.findById(id));
			model.addAttribute("productServicesStatus", genericService.lookup(Lookup.LOOKUP_PRODUCT_SERVICES_STATUS));
			//model.addAttribute("ImageMaxSize", getUploadImageSizeMax());
			putIntoRequest(model);

			return BASE_VIEW + EDIT_VIEW;
		}

		return getUnauthorizedPage();
	}

	@RequestMapping("/create")
	public String create(Model model, HttpSession session) {

		if (getPriviledgeUser(session, PRIVILEDGE, NEW)) {

			//model.addAttribute("splash", splashScreenService.findById(id));
			model.addAttribute("productServicesStatus", genericService.lookup(Lookup.LOOKUP_PRODUCT_SERVICES_STATUS));
			//model.addAttribute("ImageMaxSize", getUploadImageSizeMax());
			putIntoRequest(model);

			return BASE_VIEW + EDIT_VIEW;
		}

		return getUnauthorizedPage();
	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST, headers = { "content-type=application/json" })
	public @ResponseBody AjaxResponse delete(@RequestBody List<StateModel> states, HttpSession session) {

		if (getPriviledgeUser(session, PRIVILEDGE, DELETE)) {

			splashScreenService.delete(states);

			return new AjaxResponse(true, "Splash screen succesfully deleted.");
		} else {

			return new AjaxResponse(false, "You are not authorized to access this module.");
		}
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST, headers = { "content-type=application/json" })
	public @ResponseBody AjaxResponse save(@RequestBody SplashScreen splash, Model model, HttpSession session) {

			splashScreenService.save(splash, getLoginSecUser(session));
			return new AjaxResponse(splash);
	
	}	

    private void putIntoRequest(Model model) {
		model.addAttribute("SELECTED_MENU", MENU);
		model.addAttribute("SELECTED_SUBMENU", PRIVILEDGE);
		//model.addAttribute("listClientType", genericService.lookup(Lookup.LOOKUP_ALL_CLIENT_TYPE));
	}

}



