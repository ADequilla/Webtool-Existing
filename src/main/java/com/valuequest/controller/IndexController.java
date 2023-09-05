package com.valuequest.controller;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.servlet.view.RedirectView;

import com.valuequest.entity.ParamConfig;
import com.valuequest.common.DataTables;
import com.valuequest.entity.MappingHierarchy;
import com.valuequest.entity.security.SecUser;
import com.valuequest.util.DateUtil;
import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;


@Controller     
public class IndexController extends BaseController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(Model model, HttpSession session) {
    	SecUser user = this.getLoginSecUser(session);
		user.setIsLogin(true);
    	adminService.updateCekStatus(user, session.getId());
        
    	if (USER_SUPER_ADMIN.equals(user.getUsrPosition())
		){
    		return "redirect:dashboard/registered-client/";
    	}else{
    		if((user.getUsrExpiredPassword() != null && DateUtil.compare(user.getUsrExpiredPassword(), new Date()) < 0) || user.isPasswordDefault()){
    			session.setAttribute("forceChangePasswd", true);
    			return "redirect:password/change/default";
    		}
    		
    		return "01.misc/blank";
    	}
    }
    
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        return "01.misc/login";
    }

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(HttpServletRequest request, HttpSession session) {
		SecUser user = this.getLoginSecUser(session);

    	// save logout to audit trail
    	String activity	= "LOGOUT";
    	long moduleId	= 2002;
    	auditTrailService.save(request, moduleId, activity, null, null, null, getLoginSecUser(session));

    	user.setIsLogin(false);
		adminService.updateCekStatus(user, session.getId());
    	return "redirect:j_spring_security_logout";
    }
    
    @RequestMapping(value = "/404", method = RequestMethod.GET)
    public String _404() {
        return "01.misc/404";
	}

	@RequestMapping(value = "/updateLoginStatus", method = RequestMethod.POST)
	@ResponseBody
	public void updateLoginStatus(HttpSession session) {
    SecUser user = this.getLoginSecUser(session);
    user.setIsLogin(false);
    adminService.updateCekStatus(user, session.getId());
}

@RequestMapping(value = "/concurrentlogin", method = RequestMethod.GET)
    @ResponseBody
    public int getSessionTimeout() {
        ParamConfig config = genericService.getConfigByName(ParamConfig.SESSION_TIMEOUT_WEBTOOL);
        String valueTimeout = config.getValue();
        valueTimeout = valueTimeout.replace(".", "");
        valueTimeout = valueTimeout.replace(",", "");
        return Integer.parseInt(valueTimeout);
    }
    
    @SuppressWarnings("rawtypes")
	@RequestMapping("/getStructure")
	public @ResponseBody DataTables getStructure(DataTables dataTables,
			@RequestParam(required = false) String type, 
			@RequestParam(required = false) String description, 
			@RequestParam(required = false) String filter,
			@RequestParam(required = false) String institutionCode, 
			@RequestParam(required = false) String branchCode, 
			@RequestParam(required = false) String unitCode, 
			HttpSession session) {
    	SecUser user = this.getLoginSecUser(session);
		HashMap<String, Object> searchMap = new HashMap<>();
		searchMap.put("loginId",	user.getId());
		searchMap.put("description",description);
		searchMap.put("institution",institutionCode);
		searchMap.put("branch", 	branchCode);
		searchMap.put("unit", 		unitCode);
		
		if(StringUtils.isNotBlank(filter)){
			searchMap.put("isFiltered", true);
		}
		
		if(MappingHierarchy.STRUCTURE_INST.equals(type)){
			
			dataTables = institutionService.searchByMapCriteria(dataTables, searchMap);
		}else if(MappingHierarchy.STRUCTURE_BRANCH.equals(type)){
			
			dataTables = branchService.searchByMapCriteria(dataTables, searchMap);
		}else if(MappingHierarchy.STRUCTURE_UNIT.equals(type)){
			
			dataTables = unitService.searchByMapCriteria(dataTables, searchMap);
		}else if(MappingHierarchy.STRUCTURE_CENTER.equals(type)){
			
			dataTables = centerService.searchByMapCriteria(dataTables, searchMap);
		}else{
			
			dataTables.extract(new ArrayList(), 0L);
		}
		
		return dataTables;
	}
	
	@RequestMapping("/getUser")
	public @ResponseBody DataTables getUser(DataTables dataTables,
			@RequestParam(required = false) String userName,
			HttpSession session) {
		
		HashMap<String, Object> searchMap = new HashMap<>();
		searchMap.put("loginId",	getLoginIdFromSession(session));
		searchMap.put("userLogin", 	userName);

		return adminService.searchByMapCriteria(dataTables, searchMap);
	}
}
