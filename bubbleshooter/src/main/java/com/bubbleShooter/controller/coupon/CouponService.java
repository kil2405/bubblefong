package com.bubbleShooter.controller.coupon;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import com.bubbleShooter.VO.RewardItemVO;
import com.bubbleShooter.common.BubbleException;
import com.bubbleShooter.common.ConstantVal;
import com.bubbleShooter.common.ErrorCodeInfo;
import com.bubbleShooter.common.GameResource;
import com.bubbleShooter.common.RepositoryService;
import com.bubbleShooter.controller.mail.MailService;
import com.bubbleShooter.domain.User;
import com.bubbleShooter.relation.MailTitle;
import com.bubbleShooter.request.ReqCoupon;
import com.bubbleShooter.request.ReqWebCoupon;
import com.bubbleShooter.response.ResCoupon;
import com.bubbleShooter.response.ResWebCoupon;
import com.bubbleShooter.util.MapperVO;
import com.bubbleShooter.util.TimeCalculation;

@Service
public class CouponService {

	@Value("${gamebase.api.url}")
	private String gamebase_url;
	
	@Value("${gamebase.app.id}")
	private String gamebase_appid;
	
	@Value("${gamebase.secret.key}")
	private String gamebase_secret_key;
	
	@Autowired
	private RepositoryService repositoryService;
	
	@Autowired
	private MailService mailService;
	
	@Autowired
	private GameResource gameResource;
	
	@Autowired
	private MapperVO mapperVO;
	
	public ResCoupon UseCoupon(int userId, ReqCoupon req) throws Exception
	{
		ResCoupon res = new ResCoupon();
		
		User user = repositoryService.getUser(userId, false);
		if(user == null)
			throw new BubbleException(ErrorCodeInfo.ERROR_CODE_COUPON_1600);
		
		String url = gamebase_url + "/tcgb-gateway/v1.3/apps/" + gamebase_appid + "/members/" + user.getUuid() + "/coupons/" + req.coupon + "?storeCode=" + req.storeCode;
		JSONObject couponResult = send(url);
		
		JSONObject header = couponResult.getJSONObject("header");
		String result = header.getString("isSuccessful");
		
		List<RewardItemVO> vo = new ArrayList<>();
		
		if(result.equals("true"))
		{
			JSONObject resultObj = couponResult.getJSONObject("result");
			JSONArray array = resultObj.getJSONArray("benefits");
			
			for(int i = 0; i < array.length(); i++)
			{
				String items = array.getJSONObject(i).getString("itemId");
				int count = array.getJSONObject(i).getInt("amount");
				
				String[] temp = items.split(",");
				RewardItemVO item = mapperVO.makeRewardItemVO(Integer.valueOf(temp[0]), Integer.valueOf(temp[1]), count);
				vo.add(item);
			}
			
			MailTitle mailRS = gameResource.getMail().get(ConstantVal.MAIL_TEXT_COUPON_REWARD, user.getLanguage());
			mailService.SendMails(userId, ConstantVal.MAIL_TYPE_NORMAL, mailRS.title, mailRS.contents, TimeCalculation.getCurrentUnixTime() + ConstantVal.MAIL_EXPIRE_TIME, vo);
		}
		
		res.result = result.equals("true") ? ConstantVal.DEFAULT_SUCCESS : ConstantVal.DEFAULT_FAIL;
		res.message = header.getString("resultMessage");
		if(res.result == ConstantVal.DEFAULT_FAIL)
		{
			res.message = getErrorMessage(header.getInt("resultCode"));
		}
		res.mails = mapperVO.makeMailsVO(userId, null);
		
		return res;
	}

	public ResWebCoupon UseWebCoupon(ReqWebCoupon req) throws Exception
	{
		ResWebCoupon res = new ResWebCoupon();
		if(req == null)
		{
			res.result_code = -9000191;
			res.message = "Invalid request.";
			return res;
		}

		String reqNickname = req.user_name.strip();
		String reqCoupon = req.coupon_code.strip();
		if(reqCoupon.isEmpty())
		{
			// 쿠폰을 입력해주세요.
			res.result_code = -9000192;
			res.message = "Please enter a coupon.";
			return res;
		}

		// 닉네임으로 유저 정보 가져오기(db 인터페이스 만들어야함)
		User user = repositoryService.getUserToNickname(reqNickname);
		if(user == null)
		{
			// 존재하지 않는 유저입니다.
			res.result_code = -9000193;
			res.message = "Account not found.";
			return res;
		}

		String url = gamebase_url + "/tcgb-gateway/v1.3/apps/" + gamebase_appid + "/members/" + user.getUuid() + "/coupons/" + reqCoupon + "?storeCode=ALL";//getStoreCode(user.getMarket());
		JSONObject couponResult = send(url);
		
		JSONObject header = couponResult.getJSONObject("header");
		String result = header.getString("isSuccessful");
		
		List<RewardItemVO> vo = new ArrayList<>();
		
		if(result.equals("true"))
		{
			JSONObject resultObj = couponResult.getJSONObject("result");
			JSONArray array = resultObj.getJSONArray("benefits");
			
			for(int i = 0; i < array.length(); i++)
			{
				String items = array.getJSONObject(i).getString("itemId");
				int count = array.getJSONObject(i).getInt("amount");
				
				String[] temp = items.split(",");
				RewardItemVO item = mapperVO.makeRewardItemVO(Integer.valueOf(temp[0]), Integer.valueOf(temp[1]), count);
				vo.add(item);
			}
			
			MailTitle mailRS = gameResource.getMail().get(ConstantVal.MAIL_TEXT_COUPON_REWARD, user.getLanguage());
			mailService.SendMails(user.getId(), ConstantVal.MAIL_TYPE_NORMAL, mailRS.title, mailRS.contents, TimeCalculation.getCurrentUnixTime() + ConstantVal.MAIL_EXPIRE_TIME, vo);
		}
		
		res.result_code = result.equals("true") ? ConstantVal.DEFAULT_SUCCESS : ConstantVal.DEFAULT_FAIL;
		res.message = "It has been sent to your mailbox.";
		if(res.result_code == ConstantVal.DEFAULT_FAIL)
		{
			res.result_code = header.getInt("resultCode");
			res.message = getErrorMessage(res.result_code);
		}

		return res;
	}
	
	private JSONObject send(String url) throws Exception
	{
		try {
			HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
			factory.setConnectTimeout(5000); // ��아�정 5�
			factory.setReadTimeout(5000);// ��아�정 5�

			RestTemplate restTemplate = new RestTemplate(factory);

			HttpHeaders headers = new HttpHeaders();
			headers.add("Content-Type", "application/json; charset=UTF-8");
			headers.add("X-Secret-Key", gamebase_secret_key);

			HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(null, headers);
			
			String result = restTemplate.postForObject(new URI(url), request, String.class);
			
			JSONObject jsonObj = new JSONObject(result);
			return jsonObj;

		} catch (HttpClientErrorException | HttpServerErrorException e) {
			throw new BubbleException(ErrorCodeInfo.ERROR_CODE_COUPON_1601);

		} catch (Exception e) {
			throw new BubbleException(ErrorCodeInfo.ERROR_CODE_COUPON_1602);
		}
	}
	
//	private boolean couponStringCheck(Coupon coupon, ReqCoupon req)
//	{
//		if(coupon.getCoupon().equalsIgnoreCase(req.coupon))
//			return true;
//		
//		return false;
//	}
	
	private String getErrorMessage(int errorCode)
	{
		String message = "";
		
		switch(errorCode)
		{
		case -100002:
		case -4000205:
			message = "Invalid coupon code";
			break;
			
		case -100004:
			message = "Coupon code already manually expired at the request";
			break;
			
		case -100005:
			message = "Coupon already used";
			break;
			
		case -100007:
		case -100008:
			message = "Coupon not in service period";
			
		case -100011:
			message = "Exceeded available coupon limits";
			break;
			
		case -999999:
			message = "Internal error in the coupon system. If error continues, contact Customer Center for inquiries.";
			break;
			
		default:
			message = "Invalid coupon code";
			break;
		}
		
		return message;
	}

	// private String getStoreCode(int market)
	// {
	// 	String storeCode = "ALL";
	// 	switch (market) {
	// 		case ConstantVal.MARKET_TYPE_AOS:
	// 			storeCode = "GG";
	// 			break;
	// 		case ConstantVal.MARKET_TYPE_IOS:
	// 			storeCode = "AS";
	// 			break;
	// 	}
	// 	return storeCode;
	// }
}
