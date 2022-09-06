package ibs.test.plc;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

import com.fasterxml.jackson.databind.ObjectMapper;

import ibs.test.signal.Signal;
import mecury.io.LocalProperties;

public class MBSender {
	
	public String HOST = LocalProperties.get("plc.host", "192.168.0.80");
	public int PORT = LocalProperties.getInt("plc.port", 502);
	
	public Socket _so;
	public DataInputStream  _in;
	public DataOutputStream _out;
	
	public void open() throws Exception {
		try { _so = new Socket(HOST, PORT);} catch (Exception e) {}
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
	
	public void close() throws Exception {
		_so.close();
		_in.close();
		_out.close();
	}
	
	private static final class test {
		public static void main(String[] args) {
			try {
				Signal signal = new Signal();
				
				MBPLC plc = PMap.get("RS485").newInstance();
				plc.setSignal(signal);
				plc.start_address = MEMMap.get(signal.serial_no);
				
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
