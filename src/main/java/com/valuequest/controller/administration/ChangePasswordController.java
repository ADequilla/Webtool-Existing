package com.valuequest.controller.administration;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.valuequest.common.AjaxResponse;
import com.valuequest.controller.BaseController;
import com.valuequest.entity.ParamConfig;
import com.valuequest.entity.security.SecUser;

@Controller
@RequestMapping("/password/change")
public class ChangePasswordController extends BaseController {

	String BASE_VIEW 	= "02.administration/";
	String EDIT_VIEW 	= "password-change";
	String DEFAULT_VIEW = "password-change-default";

	@RequestMapping(method = RequestMethod.GET)
	public String index() {
		return BASE_VIEW + EDIT_VIEW;
	}

	@RequestMapping(value = "/default", method = RequestMethod.GET)
    public String changePassword() {
    	return BASE_VIEW + DEFAULT_VIEW;
    }
	
	@SuppressWarnings("unchecked")
	@RequestMapping(method = RequestMethod.POST)
	public @ResponseBody AjaxResponse change(HttpSession session, 
			@RequestParam String oldPassword,
			@RequestParam String newPassword, 
			@RequestParam String retypePassword) {

		SecUser secUser = getLoginSecUser(session);
		String regex 	= "^(?=.*?[a-z])(?=.*?[A-Z])(?=.*?[0-9])(?=.*?[~!@#$%^&*])[a-zA-Z0-9~!@#$%^&*()+=]{8,900}$";
		
		if(newPassword.trim().matches(regex)){
			String encodedPassword 	= adminService.encodePassword(oldPassword);
			if (StringUtils.equals(encodedPassword, secUser.getUsrPassword())) {
				ParamConfig config 	= genericService.getConfigByName(ParamConfig.WEB_USED_PASSWD_ITERATION);
				if ( config != null ){
					int times				= Integer.valueOf(config.getValue());
					List<String> passwords	= logPasswordService.lastPasswordList(secUser.getUsrLoginname(), times);
					encodedPassword			= adminService.encodePassword(newPassword);
					if(passwords.contains(encodedPassword)){
						
						return new AjaxResponse(false, "This Password already used before, please use another password.");
					}else{
						secUser.setPasswordDefault(false);
						secUser.setUsrPassword(encodedPassword);
						config = genericService.getConfigByName(ParamConfig.WEB_CHANGE_PASSWD_PERIOD);
						if(config != null){
							Calendar cal = Calendar.getInstance();
							cal.setTime(new Date(System.currentTimeMillis()));
							cal.add(Calendar.DAY_OF_MONTH, Integer.valueOf(config.getValue()));
							secUser.setUsrExpiredPassword(cal.getTime());
						}
						
						adminService.saveOrUpdate(secUser);
						adminService.updateCekStatus(getLoginSecUser(session), "INACTIVE");
						logPasswordService.save(secUser.getUsrLoginname(), encodedPassword);
						session.setAttribute("loginSecUser", secUser);
						session.removeAttribute("forceChangePasswd");
					}
				}
			}else{
				return new AjaxResponse(false, "Old Password is wrong.");
			}
			
			return new AjaxResponse();
		} else {
			return new AjaxResponse(false, "Password must contain letter(Upper and Lower case), number and symbol, min 8.");
		}
	}
}
