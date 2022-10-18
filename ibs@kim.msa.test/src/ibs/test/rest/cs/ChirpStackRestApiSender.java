package ibs.test.rest.cs;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

import com.fasterxml.jackson.databind.ObjectMapper;

import mecury.io.LocalProperties;
import mecury.log.Log;
import v3.venus.mod.Modular;

public class ChirpStackRestApiSender {
	
	public String HOST = LocalProperties.get("lora.cs.server", "192.168.0.170:8080");

	public String url;
	
	public String jwt;
	
	public String eui;
	
	public String method;
	
	public HttpURLConnection con;
	
	public HashMap<String, Object> rtn;
	
	public static final String GET = "GET";
	public static final String PUT = "PUT";
	public static final String POST = "POST";
	public static final String DELETE = "DELETE";
	
//	public abstract HttpURLConnection getConnection() throws Exception;
	
	public HttpURLConnection getConnection() throws Exception {
		URL url = new URL(HOST + this.url);
		
		HttpURLConnection con = (HttpURLConnection)url.openConnection();
		con.setUseCaches(false);
		con.setDoOutput(true);
		con.setRequestMethod(this.method);
		con.setRequestProperty("Accept", "*/*");
		con.setRequestProperty("Content-Type", "application/json; utf-8");
		con.setRequestProperty("Connection", "keep-alive");
		con.setRequestProperty("Accept-Encoding", "gzip, deflate, br");
		if (this.jwt != null) con.setRequestProperty("Grpc-Metadata-Authorization", this.jwt);
		
		return con;
	}
	
	@SuppressWarnings("finally")
	public void send(byte[] payload) throws Exception {
		HttpURLConnection con = null;
		OutputStream _out = null;
		InputStream _in = null;
		try {
			Log.info(Modular.ID, new ObjectMapper().readValue(payload, HashMap.class));
			
			con = getConnection();
			con.connect();
			
			_out = con.getOutputStream();
			
			_out.write(payload); _out.flush(); 
			
			_in = con.getInputStream();
			int size = _in.available();
			
			byte[] result = new byte[size];
			_in.read(result);
		
			rtn = new ObjectMapper().readValue(result, HashMap.class);
			
			Log.info(Modular.ID, rtn);
		} catch (Exception e) {
			Log.info(Modular.ID, e.getMessage());
			throw e;
		} finally {
			if (_in!=null) try {_in.close();} catch (Exception e) {}
			if (_out!=null) try {_out.close();} catch (Exception e) {}
			if (con!=null) try {con.disconnect();} catch (Exception e) {}
		}
	}
	
	public HashMap<String, Object> getRtn() throws Exception {
		return this.rtn;
	}

}
