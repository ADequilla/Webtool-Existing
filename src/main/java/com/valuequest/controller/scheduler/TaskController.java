package com.valuequest.controller.scheduler;

import java.util.HashMap;

import javax.servlet.http.HttpSession;

import com.valuequest.common.DataTables;
import com.valuequest.controller.BaseController;
import com.valuequest.services.TaskService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.apache.log4j.Logger;

@Controller
@RequestMapping("/scheduler/task")
public class TaskController extends BaseController {

    final Logger log = Logger.getLogger("SchedulerController");
	final static String MENU 		= "SCHEDULER";
	final static String PRIVILEDGE 	= "TASK";
	private String BASE_VIEW 		= "14.scheduler/";
	private String LIST_VIEW 		= "task-list";

	@Autowired
	private TaskService taskService;

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
			@RequestParam(required = false) String taskID,
			@RequestParam(required = false) String taskName,
			@RequestParam(required = false) String searchDateStart,
			@RequestParam(required = false) String searchDateEnd,
			HttpSession session) {

		HashMap<String, Object> searchMap = new HashMap<>();
		searchMap.put("taskID", taskID);
		searchMap.put("taskName", taskName);
		searchMap.put("searchDateStart", searchDateStart);
		searchMap.put("searchDateEnd", searchDateEnd);

		return taskService.searchByMapCriteria(dataTables, searchMap);
	}

	private void putIntoRequest(Model model) {
		model.addAttribute("SELECTED_MENU", MENU);
		model.addAttribute("SELECTED_SUBMENU", PRIVILEDGE);

	}
    
}
