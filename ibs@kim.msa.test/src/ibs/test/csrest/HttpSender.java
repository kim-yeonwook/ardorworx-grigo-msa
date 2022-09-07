package ibs.test.csrest;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.HashMap;

import com.fasterxml.jackson.databind.ObjectMapper;

public class HttpSender {
	
	HttpURLConnection con;
	
	public void set(HttpURLConnection con) throws Exception {
		this.con = con;
	}
	
	public void send(byte[] payload) throws Exception {
		DataOutputStream _out = new DataOutputStream(con.getOutputStream());
		_out.write(payload); _out.flush();
	}
	
	public HashMap<String, Object> receive() throws Exception {
		InputStream stream = con.getInputStream();
		int size = stream.available();
		
		DataInputStream _in = new DataInputStream(stream);
		byte[] result = new byte[size];
		_in.read(result);
		
		return new ObjectMapper().readValue(result, HashMap.class);
	}
	
}
