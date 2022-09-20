package ibs.test.csrest.cs;

import java.net.HttpURLConnection;
import java.net.URL;

import ibs.test.csrest.CS_REST;

public class INDEVICE extends CS_REST {
	
	@Override
	public HttpURLConnection getConnection() throws Exception {
		URL url = new URL(HOST + "/api/devices");
		
		HttpURLConnection con = (HttpURLConnection)url.openConnection();
		con.setUseCaches(false);
		con.setDoOutput(true);
		con.setRequestMethod(POST);
		con.setRequestProperty("Accept", "*/*");
		con.setRequestProperty("Content-Type", "application/json; utf-8");
		con.setRequestProperty("Connection", "keep-alive");
		con.setRequestProperty("Accept-Encoding", "gzip, deflate, br");
		if (jwt != null) con.setRequestProperty("Grpc-Metadata-Authorization", jwt);
		
		return con;
	}
	
}
