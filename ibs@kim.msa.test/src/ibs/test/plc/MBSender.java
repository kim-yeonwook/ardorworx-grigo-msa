package ibs.test.plc;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

import com.fasterxml.jackson.databind.ObjectMapper;

public class MBSender {
	
	public Socket _so;
	public DataInputStream  _in;
	public DataOutputStream _out;
	
	public void set() throws Exception {
		try { _so = new Socket("192.168.0.80", 502);} catch (Exception e) {}
		try { _in = new DataInputStream(_so.getInputStream());} catch (Exception e) {}
		try { _out = new DataOutputStream(_so.getOutputStream());} catch (Exception e) {}
	}
	
	public void send(MBPLC plc) throws Exception {
		try {
			plc.write(_out);
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			plc.read(_in);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static final class test {
		public static void main(String[] args) {
			try {
				
				MBPLC plc = SignalMap.get("").newInstance();
				
				MBSender con = new MBSender();
				con.set();
				
				con.send(plc);
				
				// 시그널업
				
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
