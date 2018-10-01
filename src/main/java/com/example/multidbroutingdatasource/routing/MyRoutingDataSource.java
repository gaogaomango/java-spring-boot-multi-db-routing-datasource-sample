package com.example.multidbroutingdatasource.routing;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class MyRoutingDataSource extends AbstractRoutingDataSource {

	private static final String PUBLISHER_DS = "PUBLISHER_DS";
	private static final String ADVERTISER_DS = "ADVERTISER_DS";
	
	@Override
	protected Object determineCurrentLookupKey() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		String keyDS = (String) request.getAttribute("keyDS");
		
		System.out.println("KeyDS=" + keyDS);
		
		if(keyDS == null) {
			keyDS = PUBLISHER_DS;
		}
		
		return keyDS;
	}
	
	public void initDataSources(DataSource dataSource1, DataSource dataSource2) {
		Map<Object, Object> dsMap = new HashMap<>();
		dsMap.put(PUBLISHER_DS, dataSource1);
		dsMap.put(ADVERTISER_DS, dataSource2);
		
		this.setTargetDataSources(dsMap);
	}

}
