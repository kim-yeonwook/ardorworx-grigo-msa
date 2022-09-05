package ibs.test.csrest.cs;

import java.net.HttpURLConnection;
import java.net.URL;

import ibs.test.csrest.HttpHandler;

public class JWTTOKEN extends HttpHandler {
	
	private static final String API = "/api/internal/login";
	
	@Override
	public HttpURLConnection getHttpUrlConnection(String api) throws Exception {
		URL url = new URL("http://" + HOST + API);
		
		HttpURLConnection con = (HttpURLConnection)url.openConnection();
		con.setUseCaches(false);
		con.setDoOutput(true);
		con.setRequestMethod(POST);
		con.setRequestProperty("Accept", "*/*");
		con.setRequestProperty("Content-Type", "application/json; utf-8");
		con.setRequestProperty("Connection", "keep-alive");
		con.setRequestProperty("Accept-Encoding", "gzip, deflate, br");
		
		return con;
	}
	
}
