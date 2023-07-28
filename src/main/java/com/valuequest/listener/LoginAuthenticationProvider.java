package com.valuequest.listener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.valuequest.common.AjaxResponse;
import com.valuequest.controller.maintenance.model.ProductCategoryModel;
import com.valuequest.entity.Lookup;
import com.valuequest.entity.ParamConfig;
import com.valuequest.entity.StructureUser;
import com.valuequest.entity.ViewUser;
import com.valuequest.entity.security.SecUser;
import com.valuequest.services.AdminService;
import com.valuequest.services.GenericService;

@Component("authenticationProvider")
public class LoginAuthenticationProvider implements AuthenticationProvider{
	private static final Map<String, HttpSession> activeSessions = new HashMap<>();	

	@Autowired
	protected AdminService adminService;
	
	@Autowired
	protected GenericService genericService;

	
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
	
		String username = authentication.getName();
        String password = (String) authentication.getCredentials();
        SecUser user 	= adminService.getSecUser(username);

       
        //     HttpSession activeSession = activeSessions.get(user);
        //     if (activeSession != null && !activeSession.getId().equals(session.getId())) {
        //         // If there is an active session for the user, invalidate the new login attempt
        //         session.invalidate();
        //         // You can redirect the user to a login error page or show a message explaining the reason for failure.
		// 		throw new BadCredentialsException("Concurrent login detected. Please log out from other sessions.");
        //     }
			//  else {
            //     // Update the active session for the user
            //     activeSessions.put(username, session);
            // }
      
	
	

		// StructureUser userId = (String) authentication.getUserId();

        if (user == null) {
			throw new BadCredentialsException("User does not exist.");
		}
		// else if(user != null && !user.getUsrName().equals(user.getUsrName())){	
        // 	throw new BadCredentialsException("Concurrent Login Test");
		// }
		
		else{

			// if(userId == userId){
			// 	throw new BadCredentialsException("User already login.");
			// }
			String passwordEncode = adminService.encodePassword(password);
			if(StringUtils.equals(passwordEncode, user.getUsrPassword())){
			
			// 	if (user.getSessionId() != null && !user.getSessionId().equals(session.getId())) {
            //     response.getWriter().println("User is already logged in from another session.");
            //     return;
            // }


				// if (user.getId() == (user.getId())){
				// 	throw new BadCredentialsException("User already login.");
				// }
				if (HttpSessionCollector.find(user.getCheckStatus()) != null){
					 ParamConfig config = genericService.getConfigByName(ParamConfig.SESSION_TIMEOUT_WEBTOOL);
			         String valueTimeout = config.getValue();
			         valueTimeout = valueTimeout.replace(".","");
			         valueTimeout = valueTimeout.replace(",","");
					throw new DisabledException("Your account is being used/locked. Please re-login again after "+valueTimeout+" minutes or contact your Web Application Administrator to unlock your account.");
				}
				else {
					System.out.println("##################### user? "+ user);
					if (Lookup.LOOKUP_USER_STATUS_LOCK.equals(user.getUsrStatus())){
						throw new DisabledException("Your Account has been Blocked. Please Contact Customer Service.");
						
					}else if (Lookup.LOOKUP_USER_STATUS_INACTIVE.equals(user.getUsrStatus())){
						throw new DisabledException("Your Account is Inactive. Please Contact Customer Service.");
						
					}else{
						Date activationLimit = user.getActivationLimit();
						if(activationLimit != null){
							Calendar cal1 = Calendar.getInstance();
							cal1.setTime(activationLimit);
							Calendar cal2 = Calendar.getInstance();
							cal2.setTime(new Date());
							
							if(user.isPasswordDefault() && cal1.compareTo(cal2) < 0){
								throw new DisabledException("Your Password is expired. Please Contact Customer Service.");
							}
						}
	
						adminService.resetFailAttempts(user);
						Collection<? extends GrantedAuthority> authorities = getGrantedAuthority(user);
						return new UsernamePasswordAuthenticationToken(user, password, authorities);
					}
				}
			}else{
				int MAX_LOGIN_ATTEMPTS = 3;
				ParamConfig config	= genericService.getConfigByName(ParamConfig.WEB_INVALID_PASSWD_LIMIT);
				
				if( config != null ){
					MAX_LOGIN_ATTEMPTS	= Integer.valueOf(config.getValue());
				}
				
				adminService.updateFailAttempts(user, MAX_LOGIN_ATTEMPTS);
				
				if(user.getLoginAttempts() != null && user.getLoginAttempts() >= MAX_LOGIN_ATTEMPTS){
					throw new BadCredentialsException("Your account has been blocked due to " + MAX_LOGIN_ATTEMPTS + " or more wrong input of password, please request password reset in order to login your account.");
					
				}else{
					throw new BadCredentialsException("Please Input correct username and password");
					
				}
				
			}
		}
	}
	
	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}

	private Collection<GrantedAuthority> getGrantedAuthority(SecUser user) {

		Collection<String> rights = null;
		if(user.getUsrPosition() != null && user.getUsrPosition().equalsIgnoreCase(SecUser.USER_SUPER_ADMIN)){
			rights = adminService.getAllRights();

		}else{
			rights = adminService.getRightsByUser(user);
		}
		
		ArrayList<GrantedAuthority> rechteGrantedAuthorities = new ArrayList<GrantedAuthority>(rights.size());

		for (String right : rights) {
			rechteGrantedAuthorities.add(new GrantedAuthorityImpl(right));
			
		}
		
		return rechteGrantedAuthorities;
	}
	
	class GrantedAuthorityImpl implements GrantedAuthority {
		
		private static final long serialVersionUID = 1L;
		
		private String role;
	 
	    public GrantedAuthorityImpl(String role) {
	        this.role = role;
	    }
	 
	    public String getAuthority() {
	        return role;
	    }
	}
}