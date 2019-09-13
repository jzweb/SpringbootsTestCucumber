package com.integrationTestCucumber;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
@ComponentScan(basePackages= {	"com.integrationTestCucumber",
								"com.integrationTestCucumber.model",
								"com.integrationTestCucumber.service",
								"com.integrationTestCucumber.controller"})	
@SpringBootApplication
public class IntegrationTestCucumberApplication {

	public static void main(String[] args) {
		SpringApplication.run(IntegrationTestCucumberApplication.class, args);
	}

}
