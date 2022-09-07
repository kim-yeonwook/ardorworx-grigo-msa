package ibs.test.plc;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.HashMap;

import com.fasterxml.jackson.databind.ObjectMapper;

import ibs.test.plc.lora.RS485MEM;
import ibs.test.signal.Signal;
import ibs.test.signal.loras.RS485;
import mecury.io.LocalProperties;

public class MBSender {
	
	public static String HOST = LocalProperties.get("plc.host", "192.168.0.80");
	public static int PORT = LocalProperties.getInt("plc.port", 502);
	
	public static Socket _so;
	public static DataInputStream  _in;
	public static DataOutputStream _out;
	
	public static synchronized void open() throws Exception {
		try {
			try { 
				_so = new Socket(HOST, PORT);
			} catch (Exception e) {
				
			}
			_in = new DataInputStream(_so.getInputStream());
			_out = new DataOutputStream(_so.getOutputStream());
		} catch (Exception e) {
			if(_so!=null) {try {_so.close();}catch (Exception e1) {}}
			if(_in!=null) {try { _in.close();}catch (Exception e1) {}}
			if(_out!=null) {try { _out.close();}catch (Exception e1) {}}
			System.out.println("SOCKET CONNETED FAILED");
		}
	}
	
	public static synchronized void send(MBPLC plc) throws Exception {
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
	
	public static void close() {
		if(_so!=null) {try {_so.close();}catch (Exception e1) {}}
		if(_in!=null) {try { _in.close();}catch (Exception e1) {}}
		if(_out!=null) {try { _out.close();}catch (Exception e1) {}}
		System.out.println("CLOSE OK");
	}
	
	private static final class test {
		public static void main(String[] args) {
			try {
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("serial_no", "BA-K53-220704-005");
				map.put("comm_code", "LORA.CS.RS485");
				map.put("val1", 1);
				map.put("val2", 2);
				map.put("val3", 3);
				map.put("val4", 4);
				map.put("val5", 5);
				map.put("val6", 6);
				map.put("val7", 7);
				map.put("val8", 8);
				
				Signal signal = new ObjectMapper().readValue(new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsBytes(map), RS485.class);
				
				MBPLC plc = new RS485MEM();
				plc.setSignal(signal);
				plc.setSAddress(100);
				
				MBSender con = new MBSender();
				con.open();
				
				// 쓰기
				con.send(plc);
				
				// 종료
				con.close();
				
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
