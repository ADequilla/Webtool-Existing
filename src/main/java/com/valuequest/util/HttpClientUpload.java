package com.valuequest.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.conn.ssl.X509HostnameVerifier;
//import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

public class HttpClientUpload {
	
	private CloseableHttpClient httpClient 	= null;

	public HttpClientUpload() {
		try {

			HttpClientBuilder builder = HttpClientBuilder.create();
			SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
				public boolean isTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
					return true;
				}
			}).build();
			builder.setSslcontext(sslContext);

			X509HostnameVerifier hostnameVerifier = SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER;
			SSLConnectionSocketFactory sslSocketFactory = new SSLConnectionSocketFactory(sslContext, hostnameVerifier);
			Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory> create()
					.register("https", sslSocketFactory).build();

			PoolingHttpClientConnectionManager connMgr = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
			builder.setConnectionManager(connMgr);

			httpClient = builder.build();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String upload(String host, String dirname, String filename) {
		String message 					= "";
		HttpPost postRequest			= null;
//		MultipartEntityBuilder builder	= null;
		HttpResponse response			= null;
		
		try {
			postRequest = new HttpPost(host);
//			builder 	= MultipartEntityBuilder.create();
//			builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
			
			File file = new File(dirname + filename);
			ContentBody cbFile = new FileBody(file);

//			builder.addPart("file", cbFile);
//			HttpEntity entity = builder.build();

//			postRequest.setEntity(entity);
			response 	= httpClient.execute(postRequest);
			message 	= response.getStatusLine().getReasonPhrase();

		} catch (Exception e) {
			message = e.getMessage();
		}
		
		System.out.println("=====================================================================================================================");
		System.out.println("Response Upload : " + message);
		System.out.println("=====================================================================================================================");
		
		return message;
	}
	
	public void call(String host) {
		String message			= null;
		HttpGet getRequest		= null;
		HttpResponse response 	= null;
		StringBuffer result = new StringBuffer();
		System.out.println("================================ HIT TO URL "+host);
		try {
			getRequest 	= new HttpGet(host);
			response 	= httpClient.execute(getRequest);
			message		= response.getStatusLine().getReasonPhrase();

			BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			String line = "";
			while ((line = rd.readLine()) != null) {
				result.append(line);
			}
		
			System.out.println("==================== Result CALL = "+result.toString());
		} catch (Exception e) {
			message	= e.getMessage();
		}
		
		System.out.println("=====================================================================================================================");
		System.out.println("Response Call : " + message);
		System.out.println("=====================================================================================================================");
	}
	
	public String callMbo(String host) {
		String message			= null;
		HttpGet getRequest		= null;
		HttpResponse response 	= null;
		StringBuffer result = new StringBuffer();
		
		try {
			getRequest 	= new HttpGet(host);
			response 	= httpClient.execute(getRequest);
			message		= response.getStatusLine().getReasonPhrase();

			BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			String line = "";
			while ((line = rd.readLine()) != null) {
				result.append(line);
			}
		
			System.out.println("==================== response nya = "+result.toString());
			
			return result.toString();
		} catch (Exception e) {
			message	= e.getMessage();
		}
		
		System.out.println("=====================================================================================================================");
		System.out.println("=====================================================================================================================");
		return result.toString();
	}
}