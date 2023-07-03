package com.valuequest.listener;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.valuequest.entity.security.SecUser;
import com.valuequest.services.AdminService;
import com.valuequest.services.AuditTrailService;

@Component("authenticationSuccessHandler")
public class AuthenticationHandler extends SavedRequestAwareAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

	@Autowired
	private AdminService adminService;

	
	@Autowired
	private AuditTrailService auditTrailService;
	
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

		SecUser secUser 	= adminService.getSecUser(((SecUser) authentication.getPrincipal()).getUsrLoginname());
       
		HttpSession session = request.getSession();
		session.setAttribute("loginSecUser", secUser);
		session.setAttribute("menuList", adminService.getAllMenuModel());
		
		// save login to audit trail
		String activity	= "LOGIN";
		long moduleId	= 2001;
		System.out.println("Masuk Auth Handler");
		auditTrailService.save(request, moduleId, activity, null, null, null, secUser);
		
		
		super.onAuthenticationSuccess(request, response, authentication);
	}

	
}
