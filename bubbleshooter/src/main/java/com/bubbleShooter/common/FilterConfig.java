package com.bubbleShooter.common;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {
	@Bean
	public FilterRegistrationBean<bubbleFilter> getFilterRegistrationBean() {
		FilterRegistrationBean<bubbleFilter> registrationBean = new FilterRegistrationBean<>(new bubbleFilter());
		registrationBean.addUrlPatterns("/api/*"); // 서블릿 등록 빈 처럼 패턴을 지정해 줄 수 있다.
		return registrationBean;
	}
}
