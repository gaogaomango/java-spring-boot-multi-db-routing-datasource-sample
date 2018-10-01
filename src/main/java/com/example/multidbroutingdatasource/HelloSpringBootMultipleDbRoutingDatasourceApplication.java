package com.example.multidbroutingdatasource;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import com.example.multidbroutingdatasource.routing.MyRoutingDataSource;

@SpringBootApplication
@EnableAutoConfiguration(exclude= {
		DataSourceAutoConfiguration.class,
		DataSourceTransactionManagerAutoConfiguration.class
})
@PropertySources({@PropertySource("classpath:datasource-cfg.properties")})
public class HelloSpringBootMultipleDbRoutingDatasourceApplication {

	private static final String DRIVER_CLASS_NAME_1 = "spring.datasource.driver-class-name.1";
	private static final String DRIVER_CLASS_NAME_2 = "spring.datasource.driver-class-name.2";
	private static final String DATA_SOURCE_URL_1 = "spring.datasource.url.1";
	private static final String DATA_SOURCE_URL_2 = "spring.datasource.url.2";
	private static final String DATA_SOURCE_USERNAME_1 = "spring.datasource.username.1";
	private static final String DATA_SOURCE_USERNAME_2 = "spring.datasource.username.2";
	private static final String DATA_SOURCE_PASSWORD_1 = "spring.datasource.password.1";
	private static final String DATA_SOURCE_PASSWORD_2 = "spring.datasource.password.2";
	
	// Stores all the properties loaded by the @PropertySource
	@Autowired
	private Environment env;
	
	public static void main(String[] args) {
		SpringApplication.run(HelloSpringBootMultipleDbRoutingDatasourceApplication.class, args);
	}
	
	// Return Routing DataSource(MyRoutingDataSource)
	@Autowired
	@Bean(name="dataSource")
	public DataSource getDataSource(DataSource dataSource1, DataSource dataSource2) {
		System.out.println("## Create DataSource from dataSource1 & dataSource2");
		
		MyRoutingDataSource dataSource = new MyRoutingDataSource();
		dataSource.initDataSources(dataSource1, dataSource2);
		
		return dataSource;
	}
	
	@Bean(name="dataSource1")
	public DataSource getDataSource1() throws SQLException {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		
		dataSource.setDriverClassName(env.getProperty(DRIVER_CLASS_NAME_1));
		dataSource.setUrl(env.getProperty(DATA_SOURCE_URL_1));
		dataSource.setUsername(env.getProperty(DATA_SOURCE_USERNAME_1));
		dataSource.setPassword(env.getProperty(DATA_SOURCE_PASSWORD_1));
		
		System.out.println("## DataSource1" + dataSource);
		
		return dataSource;		
	}

	@Bean(name="dataSource2")
	public DataSource getDataSource2() throws SQLException {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		
		dataSource.setDriverClassName(env.getProperty(DRIVER_CLASS_NAME_2));
		dataSource.setUrl(env.getProperty(DATA_SOURCE_URL_2));
		dataSource.setUsername(env.getProperty(DATA_SOURCE_USERNAME_2));
		dataSource.setPassword(env.getProperty(DATA_SOURCE_PASSWORD_2));
		
		System.out.println("## DataSource2" + dataSource);
		
		return dataSource;		
	}

	@Autowired
	@Bean(name="transactionManager")
	public DataSourceTransactionManager getTransactionManager(DataSource dataSource) {
		DataSourceTransactionManager txManager = new DataSourceTransactionManager();
		
		txManager.setDataSource(dataSource);
		
		return txManager;
	}
	
}
