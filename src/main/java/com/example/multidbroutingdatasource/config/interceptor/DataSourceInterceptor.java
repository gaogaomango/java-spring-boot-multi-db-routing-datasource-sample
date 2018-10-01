package com.example.multidbroutingdatasource.config.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class DataSourceInterceptor extends HandlerInterceptorAdapter {

	private static final String KEY_DS = "keyDS";
	private static final String PUBLISHER_DS = "PUBLISHER_DS";
	private static final String ADVERTISER_DS = "ADVERTISER_DS";
	
	// Request
	
	// /publisher/list
	// /advertiser/list
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		String contextPath = request.getServletContext().getContextPath();
		
		// /SomeContextPath/publisher
		String prefixPublisher = contextPath + "/publisher";
		
		// /SomeContextPath/advertiser
		String prefixAdvertiser = contextPath + "/advertiser";
		
		// SomeContextPath/publisher/dashboard
		// SomeContextPath/advertiser/dashboard
		
		String uri = request.getRequestURI();
		System.out.println("URI: " + uri);
		
		if(uri.startsWith(prefixPublisher)) {
			request.setAttribute(KEY_DS,  PUBLISHER_DS);
		} else if(uri.startsWith(prefixAdvertiser)) {
			request.setAttribute(KEY_DS,  ADVERTISER_DS);			
		}
		
		return true;
	}
}
