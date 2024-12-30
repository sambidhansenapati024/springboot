package com.alfaris.ipsh.authservice;

import java.io.IOException;
import java.net.URI;

import org.apache.hc.client5.http.classic.HttpClient;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;

public class CustomHttpComponentsClientHttpRequestFactory extends HttpComponentsClientHttpRequestFactory {

	public CustomHttpComponentsClientHttpRequestFactory(HttpClient client) {
		super(client);
		// TODO Auto-generated constructor stub
	}

	@Override
	public ClientHttpRequest createRequest(URI uri, HttpMethod httpMethod) throws IOException {
		// TODO Auto-generated method stub
		return super.createRequest(uri, httpMethod);
	}

}