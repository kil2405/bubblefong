package com.bubbleShooter.controller.error;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import com.bubbleShooter.common.BaseSessionClass;
import com.bubbleShooter.common.ConstantVal;
import com.bubbleShooter.common.ErrorCodeInfo;
import com.bubbleShooter.common.GameResource;
import com.bubbleShooter.common.RepositoryService;
import com.bubbleShooter.domain.User;
import com.bubbleShooter.resource.ErrorCodeResource;
import com.bubbleShooter.util.TimeCalculation;

@RestController
@RequestMapping("/error")
public class ErrorController implements org.springframework.boot.web.servlet.error.ErrorController
{
	private final static Logger LOGGER = LoggerFactory.getLogger(ErrorController.class);
	
	private final ErrorAttributes errorAttributes;
	
	@Value("${server.status.close}")
	private boolean server_status_close;
	
	@Value("${server.status.notice}")
	private String server_status_notice;
	
	@Value("${spring.server.mode}")
	private String serverMode;
	
	@Autowired
	private RepositoryService repositoryService;
	
	@Autowired
	private GameResource gameResource;
	
	public ErrorController(ErrorAttributes errorAttributes)
	{
		Assert.notNull(errorAttributes, "ErrorAttributes must not be null");
		this.errorAttributes = errorAttributes;
	}
	
	@Override
	public String getErrorPath() {
		// TODO Auto-generated method stub
		return "/error";
	}
	
	@Autowired
	private BaseSessionClass bSC;
	
	@RequestMapping
	public ResponseEntity<Map<String, Object>> error(HttpServletRequest aRequest, HttpServletResponse response)
	{
		int customUserId = -1;
		int userId = bSC.getUserId();
		
		ServletWebRequest servletWebRequest = new ServletWebRequest(aRequest);
		Map<String, Object> result = this.errorAttributes.getErrorAttributes(servletWebRequest, true);
		
		LOGGER.error("userId:" + userId + " : " + result.toString());
		
		int customStatusCode = ((Integer) result.get("status")).intValue();
		
		String requestUrl = (String) result.get("path");
		String errorText[] = ((String) result.get("message")).split(ConstantVal.ERROR_MES_DT);
		
		try
		{
			customUserId = Integer.parseInt(errorText[0]);
		}
		catch(Exception e)
		{
		}
		
		if(customUserId != -1)
			userId = customUserId;
		
		result.put("code", customStatusCode);

		String language = "English";
		if(userId > 0)
		{
			try {
				User user = repositoryService.getUser(userId, false);
				language = user.getLanguage();
			}
			catch(Exception e) {}
		}
		
		ErrorCodeResource errorCodeRS = gameResource.getErrorCode().get(customStatusCode);
		if(errorCodeRS == null)
		{
			errorCodeRS = gameResource.getErrorCode().get(20022);
		}
		
		result.put("action", errorCodeRS.getActionCode());
		
		if(errorCodeRS.getMessageEn().isBlank())
		{
			if(!errorCodeRS.getMessageKo().isBlank())
				result.put("message", errorCodeRS.getMessageKo());
			else if (errorText == null || errorText.length < 3 || errorText[2] == null || errorText[2].isEmpty())
				result.put("message", "unknown error message");
			else
				result.put("message", errorText[2]);
		}
		else
		{
			if(language.equals("Korean"))
				result.put("message", errorCodeRS.getMessageKo());
			else
				result.put("message", errorCodeRS.getMessageEn());
		}
		
		result.put("userId", userId);
		
		try
		{
			if (customStatusCode != 404 && customStatusCode != ErrorCodeInfo.ERROR_CDOE_FILTER_20013)
			{
				HashMap<String, Object> param = new HashMap<>();
				param.put("in_month", TimeCalculation.getMonth());
				param.put("in_log_time", TimeCalculation.getCurrentUnixTime());
				param.put("in_user_id", userId);
				param.put("in_request_url", requestUrl);
				param.put("in_error_code", customStatusCode);
	
				if (errorText == null || errorText.length < 2)
					param.put("in_request_body", "");
				else
					param.put("in_request_body", errorText[1].substring(0,Math.min(errorText[1].length(), 4000)).trim());
	
				if (errorText == null || errorText.length < 3)
					param.put("in_error_text", "");
				else
					param.put("in_error_text", errorCodeRS.getMessageEn());
	
				repositoryService.setErrorLog(userId, param);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		result.remove("timestamp");
		result.remove("error");
		result.remove("exception");
		result.remove("path");
		result.remove("userId");
		result.remove("status");
		if(!serverMode.contains("local"))
			result.remove("trace");
		
		return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
