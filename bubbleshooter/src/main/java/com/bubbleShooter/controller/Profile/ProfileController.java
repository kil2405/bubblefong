package com.bubbleShooter.controller.Profile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bubbleShooter.common.BaseSessionClass;
import com.bubbleShooter.request.ReqProfileConfirm;
import com.bubbleShooter.request.ReqProfileUse;

@RestController
@RequestMapping("api/")
public class ProfileController {
    @Autowired
    private ProfileService profileService;

    @Autowired
	private BaseSessionClass baseSessionClass;

    @RequestMapping(value = "client-secure/profile/confirm", method = RequestMethod.POST)
	public Object ProfileConfirm(@RequestBody ReqProfileConfirm req) throws Exception {
		return profileService.ConfirmProfile(baseSessionClass.getUserId(), req.profileId);
	}

    @RequestMapping(value = "client-secure/profile/use", method = RequestMethod.POST)
	public Object ProfileUse(@RequestBody ReqProfileUse req) throws Exception {
		return profileService.UseProfile(baseSessionClass.getUserId(), req.profileId);
	}

	@RequestMapping(value = "client-secure/profile/info", method = RequestMethod.POST)
	public Object ProfileInfo() throws Exception {
		return profileService.getProfile(baseSessionClass.getUserId(), null, false);
	}
}
