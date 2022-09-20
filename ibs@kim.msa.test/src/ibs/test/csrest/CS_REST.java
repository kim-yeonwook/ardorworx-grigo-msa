package ibs.test.csrest;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

import com.fasterxml.jackson.databind.ObjectMapper;

import mecury.io.LocalProperties;

public abstract class CS_REST {
	
	public String HOST = LocalProperties.get("lora.cs.server", "http://dev.ardorworx.co.kr:7005");

	public String jwt;
	
	public String eui;
	
	public HttpURLConnection con;
	
	public HashMap<String, Object> rtn;
	
	public static final String GET = "GET";
	public static final String PUT = "PUT";
	public static final String POST = "POST";
	public static final String DELETE = "DELETE";
	
	public abstract HttpURLConnection getConnection() throws Exception;
	
	public void send(byte[] payload) throws Exception {
		HttpURLConnection con = null;
		OutputStream _out = null;
		InputStream _in = null;
		try {
			con = getConnection();
			con.connect();
			
			_out = con.getOutputStream();
			
			System.out.println(new ObjectMapper().readValue(payload, HashMap.class));
			
			_out.write(payload); _out.flush(); 
			
			_in = con.getInputStream();
			int size = _in.available();
			
			byte[] result = new byte[size];
			_in.read(result);
		
			rtn = new ObjectMapper().readValue(result, HashMap.class);
			
		} catch (Exception e) {
			e.printStackTrace();
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
