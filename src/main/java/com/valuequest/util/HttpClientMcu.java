package com.valuequest.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.beans.factory.annotation.Autowired;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;

public class HttpClientMcu {

	private CloseableHttpClient httpClient 	= null;
	
	@Autowired
	private Client client;
	
	  public HttpClientMcu()
	  {
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
	      Registry socketFactoryRegistry = RegistryBuilder.create()
	        .register("http", PlainConnectionSocketFactory.getSocketFactory())
	        .register("https", sslSocketFactory).build();

	      PoolingHttpClientConnectionManager connMgr = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
	      builder.setConnectionManager(connMgr);

	      httpClient = builder.build();
	    }
	    catch (Exception e) {
	      e.printStackTrace();
	    }
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
			e.printStackTrace();
			message	= e.getMessage();
			result.append(message);
		}
		
		System.out.println("############################ message = "+message);
		System.out.println("=====================================================================================================================");
		System.out.println("=====================================================================================================================");
		return result.toString();
	}
	
	public String callMcu(String host, String id){
        WebResource webResource = client.resource(host)
                .queryParam("userId", id);
        
        String response = webResource.get(String.class);

        System.out.println("######################### response = "+response);
        
        if (!response.equals("500") && !response.equals("600")) {
            return "";
        }

        return "";
	}
	
}
