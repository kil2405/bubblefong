package com.bubbleShooter.controller.user;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.bubbleShooter.common.BaseSessionClass;
import com.bubbleShooter.request.ReqChangeLanguage;
import com.bubbleShooter.request.ReqChangeNickname;
import com.bubbleShooter.request.ReqLogin;
import com.bubbleShooter.request.ReqUserDebug;

@RestController
@RequestMapping("api/")
public class UserController {
	@Autowired
	private UserService userService;
	
	@Autowired
	private BaseSessionClass baseSessionClass;

	@RequestMapping(value = "client/login", method = RequestMethod.POST)
	public Object login(@RequestBody ReqLogin login) throws Exception {
		HttpServletRequest servlet = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();
		String clientIp = getClientIP(servlet);
		return userService.Login(login, clientIp);
	}
	
	@RequestMapping(value = "client-secure/user/refresh", method = RequestMethod.POST)
	public Object UserProfile() throws Exception {
		return userService.UserProfile(baseSessionClass.getUserId());
	}
	
	@RequestMapping(value = "client-secure/user/change-nickname", method = RequestMethod.POST)
	public Object ChangeNickname(@RequestBody ReqChangeNickname nickname) throws Exception {
		return userService.ChangeNickname(baseSessionClass.getUserId(), nickname);
	}

	@RequestMapping(value = "client-secure/user/change-language", method = RequestMethod.POST)
	public Object ChangeLanguage(@RequestBody ReqChangeLanguage req) throws Exception {
		return userService.ChangeLanguage(baseSessionClass.getUserId(), req.language);
	}
	
	@RequestMapping(value = "client-secure/notice/refresh", method = RequestMethod.POST)
	public Object Notice(@RequestBody ReqChangeNickname nickname) throws Exception {
		return userService.notice(baseSessionClass.getUserId());
	}
	
	@RequestMapping(value = "client-secure/item/refresh", method = RequestMethod.POST)
	public Object UserItemRefresh() throws Exception {
		return userService.ItemRefresh(baseSessionClass.getUserId());
	}
	
	@RequestMapping(value = "client-secure/user/debug", method = RequestMethod.POST)
	public Object UserDebug(@RequestBody ReqUserDebug debug) throws Exception {
		return userService.UserDebug(baseSessionClass.getUserId(), debug);
	}
	
	private String getClientIP(HttpServletRequest servlet)
	{
		String ip = servlet.getHeader("X-FORWARDED-FOR");
       if (ip == null || ip.length() == 0) 
       {
       	ip = servlet.getHeader("Proxy-Client-IP");
       }
       
       //웹로직 서버일 경우
	    if (ip == null || ip.length() == 0) 
	    {
	    	ip = servlet.getHeader("WL-Proxy-Client-IP");
	    }
        
	    if (ip == null || ip.length() == 0) 
	    {
	    	ip = servlet.getRemoteAddr() ;
	    }
	    return ip;
	}
}
