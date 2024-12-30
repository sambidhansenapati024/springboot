package com.alfaris.ipsh.liquidity.client;

import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import feign.RequestInterceptor;
import feign.RequestTemplate;

@Component
public class FeignClientInterceptor implements RequestInterceptor {

	private static final String AUTHORIZATION_HEADER = "Authorization";

	@Override
	public void apply(RequestTemplate requestTemplate) {
		requestTemplate.header(AUTHORIZATION_HEADER,
				((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest()
						.getHeader("Authorization"));
	}
}
