package com.bubbleShooter.common;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BaseSessionClass {
	@Autowired(required = true)
	private HttpServletRequest req;

	public int getUserId() {
		try {
			return Integer.parseInt(req.getHeader("userId"));
		} catch (Exception e) {
			return ConstantVal.DEFAULT_VALUE;
		}
	}
}
