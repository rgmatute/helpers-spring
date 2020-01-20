package com.github.rgmatute;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
// import lombok.extern.log4j.Log4j2;

/**
 * Source code: https://github.com/rgmatute
 * @author Ronny Matute
 **/
//@Log4j2
@SuppressWarnings({ "unchecked", "unused" })
public class EasyHttpClient {

	private String request;
	private HttpHeaders headers;
	private ResponseEntity<?> response;
	private int timeout;

	public EasyHttpClient() {
		this.request = "{}";
		this.headers = new HttpHeaders();
		this.timeout = 0;
	}
	
	public EasyHttpClient(int timeout) {
		this.request = "{}";
		this.headers = new HttpHeaders();
		this.timeout = timeout;
	}

	public <T> ResponseEntity<T> callSoap(String urlWS, String methodHHTP) {
		this.setContentType("text/xml;charset=utf-8");
		return (ResponseEntity<T>) this.call(urlWS, methodHHTP, String.class);
	}

	public <T> ResponseEntity<T> callRest(String urlWS, String methodHHTP, Class<T> responseType) {
		this.setContentType("application/json;charset=utf-8");
		return this.call(urlWS, methodHHTP, responseType);
	}

	public <T> ResponseEntity<T> call(String urlWS, String methodHHTP, Class<T> responseType) {

		//log.info("URL WS: " + urlWS);
		//log.info("REQUEST: " + headers);
		//log.info("METHOD HTTP: " + methodHHTP);
		//log.info("TypeResponse: " + responseType.getName());

		RestTemplate restTemplate = new RestTemplate(this.getClientHttpRequestFactory());
		// RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<T> call = null;
		try {
			HttpEntity<String> requestEntity = null;
			requestEntity = new HttpEntity<>(request, headers);
			call = restTemplate.exchange(urlWS, HttpMethod.resolve(methodHHTP.toUpperCase()), requestEntity,responseType);
			return (ResponseEntity<T>) (this.response = call);
		} catch (HttpStatusCodeException e) {
			return (ResponseEntity<T>) (this.response = (ResponseEntity<T>) ResponseEntity.status(e.getRawStatusCode()).headers(e.getResponseHeaders()).body(e.getResponseBodyAsString()));
		} catch (ResourceAccessException e) {
			return (ResponseEntity<T>) (this.response = (ResponseEntity<T>) ResponseEntity.status(408).body(e.getMessage()));
		} catch (Exception e) {
			//log.error("Error:" + e.getMessage());
		}
		return call;
	}
	
	private ClientHttpRequestFactory getClientHttpRequestFactory() {
		HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory();
		clientHttpRequestFactory.setConnectTimeout(this.timeout);
		clientHttpRequestFactory.setReadTimeout(this.timeout);
		return clientHttpRequestFactory;
	}

	public EasyHttpClient setRequest(String jsonORxml) {
		request = jsonORxml;
		return this;
	}

	public EasyHttpClient setContentType(String contentType) {
		if (headers.get("Content-Type") == null) {
			headers.add("Content-Type", contentType);
		}
		return this;
	}

	public EasyHttpClient setAccept(String accept) {
		headers.add("Accept", accept);
		return this;
	}

	public EasyHttpClient addRequestHeaders(String key, Object value) {
		headers.add(key, value.toString());
		return this;
	}

	public Object getRequestHeaders() {
		return headers;
	}

	public EasyHttpClient setTimeOut(int timeout) {
		this.timeout = timeout;
		return this;
	}

}