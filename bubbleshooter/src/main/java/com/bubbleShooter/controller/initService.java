package com.bubbleShooter.controller;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bubbleShooter.common.ConstantVal;
import com.bubbleShooter.common.GameResource;
import com.bubbleShooter.common.ServerConfig;
import com.bubbleShooter.common.StorageContext;

@Service
public class initService {
	@Autowired
	private StorageContext storageContext;

	@Autowired
	private GameResource gameResource;

	private int ColCount = 15;
	private int RowCount = 5;

	public void initServerMode(String serverMode) {
		StringBuilder strBuilder = new StringBuilder();

		System.out.println("========== SERVER MODE ==================================================================");

		for (int index = 0; index < ColCount; ++index) {
			strBuilder.append(serverMode);
			strBuilder.append(" ");
		}

		for (int row = 0; row < RowCount; ++row)
			System.out.println(strBuilder.toString());

		System.out.println("==========================================================================================");
		System.out.println("");
	}

	public boolean serverStatusClose(boolean server_status_close, String server_status_notice) {
		boolean flag = false;
		if (server_status_close) {
			System.out.println("======== SERVER STATUS CLOSE =============================================================");
			System.out.println("CLOSE NOTICE MESSAGE : " + server_status_notice);
			System.out.println("==========================================================================================");
			System.out.println("");

			flag = true;
		}

		return flag;
	}

	public void serverInfoCheck() throws Exception {
		String ipAddress = getLocalServerIp();
		ServerConfig.SERVER_IP = ipAddress;

		System.out.println("======== SERVER INFO =====================================================================");
		System.out.println("SERVER IP ADDRESS : " + ipAddress);
		System.out.println("==========================================================================================");
		System.out.println("");
	}

	// Windows, Linux 기반 Server IP를 얻기 위한 함수 (Private IP만 얻어온다)
	public String getLocalServerIp() {
		try {
			Enumeration<NetworkInterface> nienum = NetworkInterface.getNetworkInterfaces();

			while (nienum.hasMoreElements()) {
				NetworkInterface ni = nienum.nextElement();
				Enumeration<InetAddress> ia = ni.getInetAddresses();

				while (ia.hasMoreElements()) {
					InetAddress inetAddress = ia.nextElement();
					if (!inetAddress.isLoopbackAddress() && !inetAddress.isLinkLocalAddress() && inetAddress.isSiteLocalAddress()) {
						// Public IP를 얻고 싶은 경우, String 변수에 받아서 최종 값을 리턴하면 된다.
						return inetAddress.getHostAddress().toString();
					}
				}
			}
		} catch (SocketException e) {
			System.out.println(e);
		}

		return "127.0.0.1";
	}

	public void initResource() throws Exception {
		 System.out.println("======== SERVER LOAD RESOURCE ============================================================");
		 storageContext.getDataOne(ConstantVal.STATIC_DB, "initQry");
		 gameResource.LoadResource();
		 System.out.println("==========================================================================================");
		 System.out.println("");
		 System.out.println("======== SERVER VERIFY RESOURCE ==========================================================");
		 gameResource.verify();
		 System.out.println("==========================================================================================");
		 System.out.println("");
		 System.out.println(ServerConfig.USE_ENCRYPTION);
	}
}
