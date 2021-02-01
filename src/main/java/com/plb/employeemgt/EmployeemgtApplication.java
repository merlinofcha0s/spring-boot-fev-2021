package com.plb.employeemgt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.StandardEnvironment;

@SpringBootApplication
public class EmployeemgtApplication {

	public static void main(String[] args) {
		SpringApplication application = new SpringApplication(EmployeemgtApplication.class);
		ConfigurableEnvironment environment = new StandardEnvironment();
		environment.setDefaultProfiles("dev");
		application.setEnvironment(environment);
		application.run(args);
	}
}
