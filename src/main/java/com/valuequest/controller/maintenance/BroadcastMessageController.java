package com.valuequest.controller.maintenance;

import java.util.ArrayList;
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
import com.valuequest.controller.maintenance.model.NewsModel;
import com.valuequest.entity.Lookup;
import com.valuequest.entity.security.SecUser;

import org.springframework.dao.DataAccessException;

@Controller
@RequestMapping("/cs/message")
public class BroadcastMessageController extends BaseController {

	final static String MENU 		= "CUSTOMER_SERVICE";
	final static String PRIVILEDGE 	= "BROADCAST_MESSAGE";
	private String BASE_VIEW 		= "06.customer-service/";
	private String LIST_VIEW 		= "message";
	private String EDIT_VIEW 		= "message-edit";

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
			@RequestParam(required = false) String subject,
			HttpSession session) {

		HashMap<String, Object> searchMap = new HashMap<>();
		searchMap.put("subject", subject);

		return newsService.searchByMapCriteria(dataTables, searchMap, session);
	}

	@RequestMapping("/create")
	public String create(Model model, HttpSession session) {
		SecUser user = this.getLoginSecUser(session);
		if (getPriviledgeUser(session, PRIVILEDGE, NEW)) {
			model.addAttribute("availableBranchList", branchService.getUserBranchList(user.getId()));
			putIntoRequest(model);

			return BASE_VIEW + EDIT_VIEW;
		}

		return getUnauthorizedPage();
	}

	@RequestMapping("/edit/{id}")
	public String edit(@PathVariable Long id, Model model, HttpSession session) {

		if (getPriviledgeUser(session, PRIVILEDGE, EDIT)) {
			
			model.addAttribute("inbox", newsService.findById(id));
			putIntoRequest(model);

			return BASE_VIEW + EDIT_VIEW;
		}
    
		return getUnauthorizedPage();
	}

@RequestMapping(value = "/delete", method = RequestMethod.POST, headers = { "content-type=application/json" })
	public @ResponseBody AjaxResponse delete(@RequestBody List<StateModel> states, HttpSession session) {

		if (getPriviledgeUser(session, PRIVILEDGE, DELETE)) {

			newsService.delete(states);
			
			return new AjaxResponse(true, "Broadcast Message successfully deleted.");
		} else {

			return new AjaxResponse(false, "You are not authorized to access this module.");
		}
	}


@RequestMapping(value = "/save", method = RequestMethod.POST, headers = { "content-type=application/json" })
	public @ResponseBody AjaxResponse save(@RequestBody NewsModel model, HttpSession session) {

		newsService.save(model, getLoginSecUser(session));
		 

		return new AjaxResponse(model);
	}

	@RequestMapping("/getBranch")
	public @ResponseBody List getBranch() {
		return branchService.getAllBranch();
	}

	
	private void putIntoRequest(Model model) {
		model.addAttribute("SELECTED_MENU", MENU);
		model.addAttribute("SELECTED_SUBMENU", PRIVILEDGE);
		List<Lookup> listLookup = new ArrayList<Lookup>();
		Lookup lookup = new Lookup();
		lookup.setValue("ALL");
		lookup.setDescription("All");
		listLookup.add(lookup);
		
		lookup = new Lookup();
		lookup.setValue("AGENT");
		lookup.setDescription("Agent");
		listLookup.add(lookup);

		lookup = new Lookup();
		lookup.setValue("CUSTOMER");
		lookup.setDescription("Customer");
		listLookup.add(lookup);
		
		model.addAttribute("listClientType", listLookup);
		
		
		
	}
}