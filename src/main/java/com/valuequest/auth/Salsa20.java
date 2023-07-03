package com.valuequest.auth;

import java.util.HashMap;

import com.valuequest.auth.model.Salsa20EncryptModel;
import com.valuequest.controller.api.http.HttpRequestSender;

public class Salsa20 {

    public String Encrypt(String password){
        HttpRequestSender sender = null;
		
		try {
			Salsa20EncryptModel enc = new Salsa20EncryptModel();
			enc.setToEncrypt(password);
			
			sender = new HttpRequestSender("http://dev-webtools-test.fortress-asya.com:8886/API/V1/Encrypt",enc);

			final HashMap<String, String> headers = new HashMap<String, String>();
			headers.put("Content-Type", "application/json");
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
