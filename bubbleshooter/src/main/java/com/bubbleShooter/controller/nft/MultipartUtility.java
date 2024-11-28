package com.bubbleShooter.controller.nft;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import com.bubbleShooter.common.BubbleException;

public class MultipartUtility {
	private String charset;
	private PrintWriter writer;

	private static final String CRLF = "\r\n";
	private OutputStream output = null;
	
	public MultipartUtility()
	{
		this.charset = "UTF-8";
	}

	public List<String> send_file_execute(String uuid, String wallet, String uri, int itemId, String type, File metaData, String secret) throws IOException, BubbleException {

		List<String> response = new ArrayList<String>();

		String boundary = this.setBoundary(); // boundary정의
		URL url = new URL(uri);

		HttpURLConnection connection;
		if (uri.startsWith("https")) {
			connection = (HttpsURLConnection) url.openConnection();
		} else {
			connection = (HttpURLConnection) url.openConnection();
		}

		try {
			connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
			connection.setRequestMethod("POST");
			connection.setDoInput(true);
			connection.setDoOutput(true);
			connection.setUseCaches(false);
			connection.setConnectTimeout(10000);

			this.output = connection.getOutputStream();
			this.writer = new PrintWriter(new OutputStreamWriter(this.output, charset), true);

			// 전송
			addString(boundary, "address", wallet);
			addString(boundary, "uid", uuid);
			addString(boundary, "ino", String.valueOf(itemId));
			addString(boundary, "type", type);
			addFile(boundary, "metadata", metaData);
			addString(boundary, "security", secret);
			addEnd(boundary);

			// Request is lazily fired whenever ou need to obtain information about
			// response.
			int status = connection.getResponseCode();
			if (status == HttpURLConnection.HTTP_OK) {
				BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), charset));
				String line = "";
				while ((line = br.readLine()) != null) {
					response.add(line);
				}
			} else {
				throw new IOException("Server returned non-OK status: " + status);
			}

		} catch (Exception e) {
			System.out.println(e);

		} finally {
			this.writer.close();
			this.output.close();
			connection.disconnect();
		}
		return response;
	}

	/**********************************************************************
	 * 통신 관련
	 **********************************************************************/
	// 바운더리 셋팅
	private String setBoundary() {
		String boundaryTime = Long.toHexString(System.currentTimeMillis()); // Just generate some unique random value.
		String boundary = "aaboo" + boundaryTime;
		return boundary;
	}

	// 스트링 추가
	private void addString(String boundary, String _key, String _value) {// Send normal String
		StringBuilder sb = new StringBuilder();
		sb.append("--" + boundary).append(CRLF);
		sb.append("Content-Disposition: form-data; name=\"" + _key + "\"").append(CRLF);
		sb.append("Content-Type: text/plain; charset=" + charset).append(CRLF);
		sb.append(CRLF).append(_value).append(CRLF);

		this.writer.append(sb).flush();
	}

	// 파일 추가
	private void addFile(String boundary, String _key, File _file) throws IOException {// Send File
		StringBuilder sb = new StringBuilder();
		sb.append("--" + boundary).append(CRLF);
		sb.append("Content-Disposition: form-data; name=\"" + _key + "\"; filename=\"" + _file.getName() + "\"")
				.append(CRLF);
		sb.append("Content-Type: " + URLConnection.guessContentTypeFromName(_file.getName())).append(CRLF); // Text file
																											// itself
																											// must be
																											// saved in
																											// this
																											// charset!
		sb.append("Content-Transfer-Encoding: binary").append(CRLF);
		sb.append(CRLF);
		this.writer.append(sb).flush();

		FileInputStream inputStream = new FileInputStream(_file);
		byte[] buffer = new byte[(int) _file.length()];
		int bytesRead = -1;
		while ((bytesRead = inputStream.read(buffer)) != -1) {
			this.output.write(buffer, 0, bytesRead);
		}
		this.output.flush();
		inputStream.close();

		this.writer.append(CRLF).flush();
	}

	// 전송처리 끝
	private void addEnd(String boundary) {// End of multipart/form-data.
		StringBuilder sb = new StringBuilder();
		sb.append("--").append(boundary).append("--").append(CRLF);
		this.writer.append(sb).flush();
	}
}
