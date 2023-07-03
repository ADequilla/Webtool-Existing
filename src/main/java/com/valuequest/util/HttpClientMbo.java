package com.valuequest.util;

import java.io.File;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.conn.ssl.X509HostnameVerifier;
//import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

public class HttpClientMbo {

	// private CloseableHttpClient httpClient = null;

	public HttpClientMbo() {
		super();
		// TODO Auto-generated constructor stub
	}

	/*
	 * public HttpClientMbo() { try {
	 * 
	 * HttpClientBuilder builder = HttpClientBuilder.create(); SSLContext
	 * sslContext = new SSLContextBuilder().loadTrustMaterial(null, new
	 * TrustStrategy() { public boolean isTrusted(X509Certificate[] arg0, String
	 * arg1) throws CertificateException { return true; } }).build();
	 * builder.setSslcontext(sslContext);
	 * 
	 * X509HostnameVerifier hostnameVerifier =
	 * SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER;
	 * SSLConnectionSocketFactory sslSocketFactory = new
	 * SSLConnectionSocketFactory(sslContext, hostnameVerifier);
	 * 
	 * httpClient = builder.build();
	 * 
	 * } catch (Exception e) { e.printStackTrace(); } }
	 * 
	 * public String call(String host) { String message = null; HttpGet
	 * getRequest = null; HttpResponse response = null;
	 * 
	 * try { getRequest = new HttpGet(host); response =
	 * httpClient.execute(getRequest); message =
	 * response.getStatusLine().getReasonPhrase();
	 * 
	 * return message; } catch (Exception e) { message = e.getMessage(); }
	 * 
	 * System.out.println(
	 * "====================================================================================================================="
	 * ); System.out.println("Response Call : " + message); System.out.println(
	 * "====================================================================================================================="
	 * ); }
	 */
	public static String connect(String url) throws ClientProtocolException, SocketException, SocketTimeoutException,
			ConnectTimeoutException, Exception {
		String result = "";

		HttpParams httpParameters = new BasicHttpParams();
		int timeoutConnection = 90000;
		HttpConnectionParams.setConnectionTimeout(httpParameters, timeoutConnection);

		int timeoutSocket = 90000;

		HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);

		HttpClient httpClient = new DefaultHttpClient(httpParameters);

		ResponseHandler<String> handler = new BasicResponseHandler();

		HttpUriRequest request;
		request = new HttpGet(url);

		result = httpClient.execute(request, handler);

		System.out.println("=================================== RESULT = " + result);

		return result;
	}
}