package com.gcit.lms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.gcit.lms")
@EnableAutoConfiguration
public class LmsBootJdbcApplication {

	public static void main(String[] args) {
		SpringApplication.run(LmsBootJdbcApplication.class, args);
	}

}
