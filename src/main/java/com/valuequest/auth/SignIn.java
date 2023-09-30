package com.valuequest.auth;

import java.util.HashMap;
import com.valuequest.auth.model.SignInModel;
import com.valuequest.controller.api.http.HttpRequestSender;


public class SignIn {
	
//	@Value("${soteria.auth.signin.username}")
//	public String username;
//	
//	@Value("${soteria.auth.signin.password}")
//	private String password;
	 
	public String request(String uname, String pass) {
		HttpRequestSender sender = null;
		
		try {
			SignInModel mdl = new SignInModel();
			mdl.setUsername(uname);
			mdl.setPassword(pass);
			
			sender = new HttpRequestSender("https://prod-api-janus.fortress-asya.com:8083/Soteria/api/auth/signin",mdl);	//PROD
			// sender = new HttpRequestSender("https://dev-api-janus.fortress-asya.com:8083/api/auth/signin",mdl);		//TEST
			// sender = new HttpRequestSender("https://rbi-kplus.fortress-asya.com/api/auth/signin",mdl);	//PROD RBI
			// sender = new HttpRequestSender("34.92.119.77",mdl);	//PROD RBI
			

			final HashMap<String, String> headers = new HashMap<String, String>();
			headers.put("Content-Type", "application/json");
			headers.put("deviceId", "ABCFA090E00B7911");
			headers.put("deviceModel", "POSTMAN");
			headers.put("fcmToken", "POSTMAN");
			headers.put("osVersion", "POSTMAN");
			headers.put("appVersion", "POSTMAN");
			sender.setHeaders(headers);
			if(sender.requestSent()){
				 return  sender.getResponse();
			}else {
				return sender.getErrorMsg();
			}

		}catch(final Exception e) {

			System.out.println("ERROR: " + e.toString());
			return  e.toString();

		}
	}
}
