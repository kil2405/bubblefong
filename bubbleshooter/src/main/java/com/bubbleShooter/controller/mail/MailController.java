package com.bubbleShooter.controller.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bubbleShooter.common.BaseSessionClass;
import com.bubbleShooter.request.ReqMailConfirm;
import com.bubbleShooter.request.ReqMailOpen;
import com.bubbleShooter.request.ReqMailRemove;

@RestController
@RequestMapping("api/")
public class MailController {
	@Autowired
	private MailService mailService;
	
	@Autowired
	private BaseSessionClass baseSessionClass;
	
	@RequestMapping(value = "client-secure/mail/info", method = RequestMethod.POST)
	public Object MailInfo() throws Exception {
		return mailService.MailInfo(baseSessionClass.getUserId());
	}
	
	@RequestMapping(value = "client-secure/mail/confirm", method = RequestMethod.POST)
	public Object MailConfirm(@RequestBody ReqMailConfirm req) throws Exception {
		return mailService.MailConfirm(baseSessionClass.getUserId(), req);
	}
	
	@RequestMapping(value = "client-secure/mail/open", method = RequestMethod.POST)
	public Object MailOpen(@RequestBody ReqMailOpen req) throws Exception {
		return mailService.MailOpen(baseSessionClass.getUserId(), req);
	}
	
	@RequestMapping(value = "client-secure/mail/remove", method = RequestMethod.POST)
	public Object MailRemove(@RequestBody ReqMailRemove req) throws Exception {
		return mailService.MailRemove(baseSessionClass.getUserId(), req);
	}
}
