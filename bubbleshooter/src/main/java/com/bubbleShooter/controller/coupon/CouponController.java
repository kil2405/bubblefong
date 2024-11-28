package com.bubbleShooter.controller.coupon;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bubbleShooter.common.BaseSessionClass;
import com.bubbleShooter.request.ReqCoupon;
import com.bubbleShooter.request.ReqWebCoupon;

@RestController
@RequestMapping("api/")
public class CouponController {

	@Autowired
	private CouponService couponService;
	
	@Autowired
	private BaseSessionClass baseSessionClass;
	
	@RequestMapping(value = "client-secure/coupon", method = RequestMethod.POST)
	public Object CheckCoupon(@RequestBody ReqCoupon req) throws Exception {
		return couponService.UseCoupon(baseSessionClass.getUserId(), req);
	}

	@RequestMapping(value = "client/coupon", method = RequestMethod.POST)
	public Object WebCoupon(@RequestBody ReqWebCoupon req) throws Exception {
		return couponService.UseWebCoupon(req);
	}
}
