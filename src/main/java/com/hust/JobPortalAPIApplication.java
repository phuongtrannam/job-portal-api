package com.hust;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class JobPortalAPIApplication extends SpringBootServletInitializer {

	public static void main(final String[] args) {
		SpringApplication.run(JobPortalAPIApplication.class, args);
	}
	// Override the configure method from the SpringBootServletInitializer class
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(JobPortalAPIApplication.class);
	}
	
}
