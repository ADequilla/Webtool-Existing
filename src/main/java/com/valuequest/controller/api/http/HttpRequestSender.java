package com.valuequest.controller.api.http;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.log4j.Logger;

import com.google.gson.Gson;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HttpRequestSender {


	final Logger log = Logger.getLogger(this.getClass().getSimpleName());

	public enum METHOD {
		POST, GET
	}

	public static long RequestTimeOut = 20000000;// 50 seconds
	public OkHttpClient client;
	private MediaType JSON;
	private String errorMsg;
	private final String url;
	private final METHOD method;
//	private final OkHttpClient client = new OkHttpClient();
	private Object body;
	private String reply, headerValue = "";// default header blank
	private final String HEADERNAME = "USER_SESSION";
	private int status;
	HashMap<String, String> headers;

	public void setHeaders(HashMap<String, String> headers) {
		this.headers = headers;
 
	}

	public HttpRequestSender(String url) {
		log.info("---------------");
		log.info("GET URL: " + url);
		this.url = url;
		method = METHOD.GET;
		//this.headerValue = Global.getMySession();
	}

	public HttpRequestSender(String url, String headerValue) {
		log.info("---------------");
		log.info("GET URL: " + url);
		log.info("GET HEADER: " + headerValue);
		this.url = url;
		method = METHOD.GET;
		this.headerValue = headerValue;
	}

	public HttpRequestSender(String url, Object body) {
		log.info("---------------");
		log.info("POST URL: " + url);
		method = METHOD.POST;
		this.url = url;
		this.body = body;
	}

	public HttpRequestSender(String url, Object body, String headerValue) {
		log.info("---------------");
		log.info("POST URL: " + url);
		log.info("GET HEADER: " + headerValue);
		method = METHOD.POST;
		this.url = url;
		this.body = body;
		this.headerValue = headerValue;
	}

	public boolean requestSent() {
		if (method.equals(METHOD.POST)) {
			return postRequest();
		} else {
			return getRequest();
		}
	}

	private boolean postRequest() {
		JSON = MediaType.parse("application/json; charset=utf-8");
		client = new OkHttpClient();

		/********** By PASS SSL ERROR ***************/
		// OkHttpClient postClient = getUnsafeOkHttpClient();//Uncomment to use
		/******************************************/
		/******* OLD SECURED CODE **************/
		// OkHttpClient postClient = client.newBuilder()
		// .connectionSpecs(Collections.singletonList(spec))//set for https
		// .readTimeout(RequestTimeOut, java.util.concurrent.TimeUnit.MILLISECONDS)
		// .writeTimeout(RequestTimeOut, java.util.concurrent.TimeUnit.MILLISECONDS)
		// .build();
		/******************************************/

		OkHttpClient postClient = getUnsafeOkHttpClient();

		try {
			log.info("SESSION:"+headerValue);
			RequestBody requestBody = RequestBody.create(JSON, new Gson().toJson(body));
			log.info("POST BODY:  " + new Gson().toJson(body));
//            Request request = new Request.Builder()
//                    .url(url)
//                    .post(requestBody)
//                    .addHeader(HEADERNAME,headerValue)
//                    .build();

			Request.Builder reqBuilder = new Request.Builder();
			if (headers != null) {
				for (String name : headers.keySet()) {

					reqBuilder.addHeader(name, headers.get(name));

				}
			}
			Request req1 = reqBuilder.url(url).post(requestBody).build();

			//Response response = client.newCall(request).execute();
			Response response = postClient.newCall(req1).execute();
			reply = response.body().string();
			log.info("POST REPONSE: " + reply);
			log.info("GET Status Code "+response.code());
			status = response.code();
			return true;

		} catch (Exception ex) {
			log.info("REQUEST ERROR: " + ex.toString());
			errorMsg = ex.toString();

		}

		return false;
	}

	private boolean getRequest() {
		JSON = MediaType.parse("application/json; charset=utf-8");
		client = new OkHttpClient();
		/******* OLD SECURED CODE **************/
		/*
		 * OkHttpClient getClient = client.newBuilder() .readTimeout(RequestTimeOut,
		 * java.util.concurrent.TimeUnit.MILLISECONDS) .writeTimeout(RequestTimeOut,
		 * java.util.concurrent.TimeUnit.MILLISECONDS) .build();
		 */
		/************* FIX SSL ERROR ******************/
		OkHttpClient getClient = getUnsafeOkHttpClient();

		try {
			Request request = new Request.Builder().url(url).addHeader(HEADERNAME, headerValue).build();

			//Response response = client.newCall(request).execute();
			Response response = getClient.newCall(request).execute();
			reply = response.body().string();
			 log.info("GET REPONSE "+reply);
			if (response.code() == 200 || response.code() == 201) {
				log.info("GET Status Code "+response.code());
				status = response.code();
				return true;
			} else {
				errorMsg = reply;
			}

		} catch (Exception ex) {
			log.info("REQUEST ERROR: " + ex.toString());
			errorMsg = ex.toString();
		}

		return false;
	}

	private static OkHttpClient getUnsafeOkHttpClient() {
		try {
			// Create a trust manager that does not validate certificate chains
			final TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
				@Override
				public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType)
						throws CertificateException {
				}

				@Override
				public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType)
						throws CertificateException {
				}

				@Override
				public java.security.cert.X509Certificate[] getAcceptedIssuers() {
					return new X509Certificate[0];
				}
			} };

			// Install the all-trusting trust manager
			final SSLContext sslContext = SSLContext.getInstance("SSL");
			sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
			// Create an ssl socket factory with our all-trusting manager
			final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

			return new OkHttpClient.Builder().sslSocketFactory(sslSocketFactory, (X509TrustManager) trustAllCerts[0])
					.hostnameVerifier(new HostnameVerifier() {
						@Override
						public boolean verify(String hostname, SSLSession session) {
							return true;
						}
					}).readTimeout(RequestTimeOut, java.util.concurrent.TimeUnit.MILLISECONDS)
					.writeTimeout(RequestTimeOut, java.util.concurrent.TimeUnit.MILLISECONDS).build();

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public String getResponse() {
		return reply;
	}

	public int getStatus(){
		return status;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setHeaderValue(String headerValue) {
		this.headerValue = headerValue;
	}
}
