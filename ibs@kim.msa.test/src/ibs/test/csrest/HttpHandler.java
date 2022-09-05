package ibs.test.csrest;

import java.net.HttpURLConnection;
import java.net.URL;

import mecury.io.LocalProperties;

public abstract class HttpHandler {
	
	public String HOST = LocalProperties.get("lora.cs.server", "dev.ardorworx.co.kr:7005");

	public String JWT = LocalProperties.get("lora.cs.jwt", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOiJhcyIsImV4cCI6MTY2MjAyMDcxNCwiaWQiOjEsImlzcyI6ImFzIiwibmJmIjoxNjYxOTM0MzE0LCJzdWIiOiJ1c2VyIiwidXNlcm5hbWUiOiJhZG1pbiJ9.vkNBRVTyyDoaVy84pCr_enepAZdM2a54Ytg0R2afzFk");
	
	public static final String GET = "GET";
	public static final String PUT = "PUT";
	public static final String POST = "POST";
	public static final String DELETE = "DELETE";
	
	public abstract HttpURLConnection getHttpUrlConnection(String url) throws Exception;

//	public abstract HttpURLConnection getHttpUrlConnection(String key) throws Exception;
}
