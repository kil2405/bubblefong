package com.bubbleShooter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.ErrorPageFilter;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BubbleShooterApplication extends SpringBootServletInitializer {
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(BubbleShooterApplication.class);
	}
	
	public static void main(String[] args) {
		SpringApplication.run(BubbleShooterApplication.class, args);
	}
	
	@Bean
   public ErrorPageFilter errorPageFilter() {
      return new ErrorPageFilter();
   }
}
