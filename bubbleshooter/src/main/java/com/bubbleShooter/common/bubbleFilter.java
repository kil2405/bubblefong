package com.bubbleShooter.common;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ReadListener;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.WriteListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.bubbleShooter.domain.User;
import com.bubbleShooter.domain.UserVersion;
import com.bubbleShooter.domain.VersionCheckResult;
import com.bubbleShooter.resource.ErrorCodeResource;
import com.bubbleShooter.util.TimeCalculation;
import com.google.gson.Gson;

public class bubbleFilter implements Filter {

	private final static Logger LOGGER = LoggerFactory.getLogger(bubbleFilter.class);

	@Value("${server.status.close}")
	private boolean server_status_close;

	@Value("${server.status.notice}")
	private String server_status_notice;
	
	@Value("${spring.server.mode}")
	private String serverMode;

	@Autowired
	private StorageContext storageContext;
	
	@Autowired
	private RepositoryService repositoryService;
	
	@Autowired
	private GameResource gameResource;

	@Override
	public void init(javax.servlet.FilterConfig arg0) throws ServletException {
		SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, arg0.getServletContext());
	}

	@Override
	public void destroy() {
	}

	// 레디스 세션 정보를 확인하여 리턴해준다. 암화키를 넘겨준다.
	// CDN check
	// appVersion check
	// return
	// OK : 0
	// NOSERVERINFO : 1
	// NOSESSION : 2
	// CDNCHECK :3
	// VERSIONCHECK :4
	public VersionCheckResult checkRedisSession(int userId) throws Exception {
		VersionCheckResult result = new VersionCheckResult();
		result.result = ConstantVal.VERSION_CHECK_OK;

		Gson gson = new Gson();
		User user = repositoryService.getRedisUser(userId, gson);
		if (user == null) {
			result.result = ConstantVal.VERSION_CHECK_NO_SESSION;
			return result;
		}
		
		result.encryption = user.getEncryption();
		// 서버 점검 상태 - 개발자 bypass

		UserVersion userVersion = repositoryService.getUserVersion(userId, gson);
		if (userVersion == null) {
			result.result = ConstantVal.VERSION_CHECK_NO_SESSION;
			return result;
		}
		
		//버전값이 존재하면, 새션 유지를 위해 갱신해준다.
		repositoryService.setUserVersion(userId, user, userVersion, gson);
		result.result = ConstantVal.VERSION_CHECK_OK;
		result.encryption = user.getEncryption();
		
		repositoryService.setUser(userId, user);

		return result;
	}

	private boolean excludeUrl(String uri, String contextPath) {
		if (uri.startsWith(contextPath + "/api/client-secure/")) {
			return false;
		} else {
			return true;
		}
	}

	private void checkServerTime() {
		try {
			if (System.currentTimeMillis() - TimeCalculation.SERVER_TIME_REFRESH_TIME > ConstantVal.HOUR_OF_SECOND * 1000) {

				TimeCalculation.SERVER_TIME_REFRESH_TIME = System.currentTimeMillis();

				Long serverTime = storageContext.getDataOne(ConstantVal.GAME_DB, "get_server_time_LST_SP");

				TimeCalculation.SERVER_TIME_VALUE = System.currentTimeMillis() - serverTime * 1000;
			}
		} catch (Exception e) {
		}
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		byte[] encryptBytes;

		HttpServletRequest hsr = (HttpServletRequest) request;
		String requestUrl = hsr.getRequestURI().toString().trim();
		String contextPath = hsr.getContextPath();
		String encryption = null;

		if (storageContext == null) {
			// 빈 생성
			System.out.println("GameDataProcess is null");
			ServletContext servletContext = request.getServletContext();
			WebApplicationContext webApplicationContext = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			storageContext = webApplicationContext.getBean(StorageContext.class);
		}

		if (!requestUrl.startsWith(contextPath + "/api/client-secure") && !requestUrl.startsWith(contextPath + "/api/client") && !requestUrl.startsWith(contextPath + "/api/pvp-server") && !requestUrl.startsWith(contextPath + "/api/op")) {
			System.out.println("invalid URL : " + requestUrl);
			return;
		}

		if (server_status_close) {
			// 서버 종료시.
			if (!requestUrl.startsWith(contextPath + "/api/no-enc/alive/ping")) {
				((HttpServletResponse) response).sendError(10000, server_status_notice);
				return;
			}
		}

		checkServerTime();
		XSSRequestWrapper cloneRequest = null;
		ResponseWrapper responseWrapper = new ResponseWrapper((HttpServletResponse) response);
		if (excludeUrl(requestUrl, contextPath))// 여기서는 암호화 안하는 패킷
		{
			cloneRequest = new XSSRequestWrapper(hsr, null);
			
			try {			
				chain.doFilter(cloneRequest, responseWrapper);
				encryptBytes = responseWrapper.getDataStream();
				response.setContentLength(encryptBytes.length);
				response.getOutputStream().write(encryptBytes);
			
			} catch (BubbleException be) {
				LOGGER.error("POS A error:[" + requestUrl + "]" + cloneRequest.getRequestBody());
				((HttpServletResponse) response).sendError(be.errorCode, be.userId + ConstantVal.ERROR_MES_DT + cloneRequest.getRequestBody() + ConstantVal.ERROR_MES_DT + " ");
			} catch (Exception e) {
				LOGGER.error("POS B error:[" + requestUrl + "]" + cloneRequest.getRequestBody());
				e.printStackTrace();
				((HttpServletResponse) response).sendError(ErrorCodeInfo.ERROR_CDOE_FILTER_20021, "-1" + ConstantVal.ERROR_MES_DT + cloneRequest.getRequestBody() + ConstantVal.ERROR_MES_DT + " ");
			}

			return;
		}

		String clientUserId = hsr.getHeader("userId");
		VersionCheckResult versionCheckResult = null;

		// redis session check
		if (clientUserId != null) {
			try {
				versionCheckResult = checkRedisSession(Integer.parseInt(clientUserId));
			} catch (Exception e) {
				LOGGER.error("checkRedisSession error:[" + requestUrl + "]");
				((HttpServletResponse) response).sendError(ErrorCodeInfo.ERROR_CDOE_FILTER_20011, "checkRedisSession error:[" + requestUrl + "]");
				return;
			}
		} else {
			LOGGER.error("no header value( userId) [" + requestUrl + "]");
			((HttpServletResponse) response).sendError(ErrorCodeInfo.ERROR_CDOE_FILTER_20019, "no header value( userId) [" + requestUrl + "]");
			return;
		}

		// NOSERVERINFO
		// NOSESSION
		// CDNCHECK
		// VERSIONCHECK
		// encryption key
		
		if (versionCheckResult.result == ConstantVal.VERSION_CHECK_NO_SERVER_INFO) {
			ErrorCodeResource error = gameResource.getErrorCode().get(ErrorCodeInfo.ERROR_CDOE_FILTER_20010);
			if(error != null)
			{
				((HttpServletResponse) response).sendError(ErrorCodeInfo.ERROR_CDOE_FILTER_20010, error.getMessageEn());
			}
			return;
		}

		if (versionCheckResult.result == ConstantVal.VERSION_CHECK_NO_SESSION) {
			ErrorCodeResource error = gameResource.getErrorCode().get(ErrorCodeInfo.ERROR_CDOE_FILTER_20016);
			if(error != null)
			{
				((HttpServletResponse) response).sendError(ErrorCodeInfo.ERROR_CDOE_FILTER_20016, error.getMessageEn());
			}
			return;
		}

		if (versionCheckResult.result == ConstantVal.VERSION_CHECK_CDN) {
			((HttpServletResponse) response).sendError(ErrorCodeInfo.ERROR_CDOE_FILTER_20012, "-1" + ConstantVal.ERROR_MES_DT + " " + ConstantVal.ERROR_MES_DT + " ");
			return;
		}

		if (versionCheckResult.result == ConstantVal.VERSION_CHECK_APPVERSION) {
			((HttpServletResponse) response).sendError(ErrorCodeInfo.ERROR_CDOE_FILTER_20013, "-1" + ConstantVal.ERROR_MES_DT + " " + ConstantVal.ERROR_MES_DT + " ");
			return;
		}

		if (versionCheckResult.result == ConstantVal.VERSION_CHECK_SERVER_INSPECTION) {
			((HttpServletResponse) response).sendError(ErrorCodeInfo.ERROR_CDOE_FILTER_20014, "-1" + ConstantVal.ERROR_MES_DT + " " + ConstantVal.ERROR_MES_DT + " ");
			return;
		}

		if (!ServerConfig.USE_ENCRYPTION) {

			cloneRequest = new XSSRequestWrapper(hsr, null);

			try {
				chain.doFilter(cloneRequest, responseWrapper);
				encryptBytes = responseWrapper.getDataStream();

				response.setContentLength(encryptBytes.length);
				response.getOutputStream().write(encryptBytes);

			} catch (BubbleException e) {
				LOGGER.error("POS C error:[" + requestUrl + "]" + cloneRequest.getRequestBody());
				((HttpServletResponse) response).sendError(e.errorCode, e.userId + ConstantVal.ERROR_MES_DT + cloneRequest.getRequestBody() + ConstantVal.ERROR_MES_DT + " ");
			} catch (Exception e) {
				LOGGER.error("POS D error:[" + requestUrl + "]" + cloneRequest.getRequestBody());
				LOGGER.error("POS D error:", e);
				e.printStackTrace();
				((HttpServletResponse) response).sendError(ErrorCodeInfo.ERROR_CDOE_FILTER_20020, "-1" + ConstantVal.ERROR_MES_DT + cloneRequest.getRequestBody() + ConstantVal.ERROR_MES_DT + " ");
			}

			return;
		}

		encryption = versionCheckResult.encryption;

		cloneRequest = new XSSRequestWrapper(hsr, encryption);

		if (cloneRequest.isDecodeError) {
			LOGGER.error("Encryption data error !! [" + requestUrl + "]");
			((HttpServletResponse) response).sendError(ErrorCodeInfo.ERROR_CDOE_FILTER_20015, "-1" + ConstantVal.ERROR_MES_DT + " " + ConstantVal.ERROR_MES_DT + " ");
			return;
		}

		try {
			chain.doFilter(cloneRequest, responseWrapper);

			if (responseWrapper.getStatus() == 200) {

				try {
					encryptBytes = AESUtil.Aes128Encode(responseWrapper.getDataStream(), encryption);
				} catch (Exception e) {
					encryptBytes = new byte[1];
				}

				response.setContentLength(encryptBytes.length);
				response.getOutputStream().write(encryptBytes);

			} else {
				encryptBytes = responseWrapper.getDataStream();
				response.setContentLength(encryptBytes.length);
				response.getOutputStream().write(encryptBytes);

			}

		} catch (BubbleException be) {
			LOGGER.error("POS D error:[" + requestUrl + "]" + cloneRequest.getRequestBody());
			((HttpServletResponse) response).sendError(be.errorCode, be.userId + ConstantVal.ERROR_MES_DT + cloneRequest.getRequestBody() + ConstantVal.ERROR_MES_DT + " ");
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("POS E error:[" + requestUrl + "]" + cloneRequest.getRequestBody());
			LOGGER.error("POS E error:", e);
			((HttpServletResponse) response).sendError(ErrorCodeInfo.ERROR_CDOE_FILTER_20022, "-1" + ConstantVal.ERROR_MES_DT + cloneRequest.getRequestBody() + ConstantVal.ERROR_MES_DT + " ");
		}
	}
}

class ResponseWrapper extends HttpServletResponseWrapper {
	ByteArrayOutputStream output;
	FilterServletOutputStream filterOutput;

	public ResponseWrapper(HttpServletResponse response) {
		super(response);
		output = new ByteArrayOutputStream();
	}

	@Override
	public ServletOutputStream getOutputStream() throws IOException {
		if (filterOutput == null) {
			filterOutput = new FilterServletOutputStream(output);
		}
		return filterOutput;
	}

	public byte[] getDataStream() {
		return output.toByteArray();
	}
}

class FilterServletOutputStream extends ServletOutputStream {

	DataOutputStream output;

	public FilterServletOutputStream(OutputStream output) {
		this.output = new DataOutputStream(output);
	}

	@Override
	public void write(int arg0) throws IOException {
		output.write(arg0);
	}

	@Override
	public void write(byte[] arg0, int arg1, int arg2) throws IOException {
		output.write(arg0, arg1, arg2);
	}

	@Override
	public void write(byte[] arg0) throws IOException {
		output.write(arg0);
	}

	public boolean isReady() {
		return true;
	}

	public void setWriteListener(WriteListener writeListener) {
	}
}

class XSSRequestWrapper extends HttpServletRequestWrapper {

	public boolean isDecodeError = false;

	private byte[] rawData;

	private HttpServletRequest request;

	private ResettableServletInputStream servletStream;

	String secretKey;

	public XSSRequestWrapper(HttpServletRequest request, String secretKey) {
		super(request);
		this.secretKey = secretKey;
		this.request = request;

		this.servletStream = new ResettableServletInputStream();

		rawData = getBody(this.request);

		if (rawData == null) {
			isDecodeError = true;
		} else {
			servletStream.stream = new ByteArrayInputStream(rawData);
		}
	}

	public String getRequestBody() {
		return "request body:" + new String(rawData);
	}

	public void resetInputStream(byte[] newRawData) {
		servletStream.stream = new ByteArrayInputStream(newRawData);
	}

	public byte[] getBody(HttpServletRequest request) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte buffer[] = new byte[128];
		try {
			InputStream inputStream = request.getInputStream();

			while (true) {
				int readCount = inputStream.read(buffer, 0, 128);

				if (readCount < 0) {
					break;
				}
				baos.write(buffer, 0, readCount);
			}
		} catch (IOException ex) {
			ex.printStackTrace();
			return null;
		} finally {
		}
		if (secretKey == null) {
			return baos.toByteArray();
		}
		return AESUtil.Aes128Decode(baos.toByteArray(), secretKey);
	}

	@Override
	public ServletInputStream getInputStream() throws IOException {
		return servletStream;
	}

	@Override
	public BufferedReader getReader() throws IOException {
		return new BufferedReader(new InputStreamReader(servletStream));
	}

	private class ResettableServletInputStream extends ServletInputStream {
		private InputStream stream;

		@Override
		public int read() throws IOException {

			return stream.read();
		}

		public boolean isFinished() {
			return false;
		}

		public boolean isReady() {
			return false;
		}

		public void setReadListener(ReadListener readListener) {
		}
	}
}

class AESUtil {
	private final static Logger LOGGER = LoggerFactory.getLogger(bubbleFilter.class);
	
	public static byte[] Aes128Encode(byte[] ori, String key) throws Exception {

		SecretKeySpec newKey = new SecretKeySpec(key.getBytes(), "AES");

		byte[] ivBytes = new byte[16];

		System.arraycopy(key.getBytes(), 0, ivBytes, 0, 16);

		AlgorithmParameterSpec ivSpec = new IvParameterSpec(ivBytes);

		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");

		cipher.init(Cipher.ENCRYPT_MODE, newKey, ivSpec);

		return cipher.doFinal(ori);

	}

	public static byte[] Aes128Decode(byte[] ori, String key) {
		byte[] encrypted = null;
		try {

			SecretKeySpec newKey = new SecretKeySpec(key.getBytes(), "AES");

			byte[] ivBytes = new byte[16];

			System.arraycopy(key.getBytes(), 0, ivBytes, 0, 16);

			AlgorithmParameterSpec ivSpec = new IvParameterSpec(ivBytes);

			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");

			cipher.init(Cipher.DECRYPT_MODE, newKey, ivSpec);

			encrypted = cipher.doFinal(ori);

		} catch (Exception e) {

			LOGGER.error("=================================================================================");
			LOGGER.error("encryption error:" + key);

			int dataSize = ori.length;

			LOGGER.error("encryption data size:" + dataSize);
			LOGGER.error("encryption data");

			String oriDataString = "";

			for (int i = 0; i < dataSize; i++) {
				oriDataString += ori[i] + ",";
			}

			LOGGER.error(oriDataString);

			LOGGER.error("=================================================================================");

			e.printStackTrace();
			return null;
		}
		return encrypted;

	}
}
