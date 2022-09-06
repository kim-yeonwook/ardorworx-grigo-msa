package ibs.test.plc;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

import ibs.test.util.BytesTest;

public class _test_PLCTEST {
	
	String add = "192.168.0.80";
	
	public final static int MBAP_SIZE = 7;
	
	//////////////////////////////////////////////
	// MBAP
	//
	
	// 2 byte
	public int transaction_id;
	
	// 2 byte
	public int protocol_id;
	
	// 2 byte
	public int length;
	
	// 1 byte
	public int unit_id;
	
	//
	//
	/////////////////////////////////////////////
	
	// 시작 메모리는 0으로 시작하지 않음 (1 ~ 9999, 10001 ~ 19999, 30001 ~ 40001, 40001 ~ 49999)
	// 0으로 지정하면 메모리는 1로잡힘 (예 : start_address를 50으로 지정하면 메모리는 51로 잡힘)
	public int start_address;
	
	public int end_address;
	
	// 
	public int function_code;

	public Socket _so;
	public DataInputStream  _in;
	public DataOutputStream _out;
	
	public _test_PLCTEST() {
		try {
			_so = new Socket("192.168.0.80", 502);
			_in = new DataInputStream(_so.getInputStream());
			_out = new DataOutputStream(_so.getOutputStream());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public byte[] readValue() throws Exception {
		
		byte[] result = new byte[12];
		int index = 0;
		
		byte[] transaction_id = new byte[2];
		transaction_id[0] = 0x00;
//		transaction_id[1] = 0x01;
		transaction_id[1] = 0x00;
		
		System.arraycopy(transaction_id, 0, result, index, 2);
		index += 2;
		
		byte[] protocol_id = new byte[2];
//		protocol_id[0] = 0x08;
//		protocol_id[1] = 0x0c;
		protocol_id[0] = 0x00;
		protocol_id[1] = 0x00;
		
		System.arraycopy(protocol_id, 0, result, index, 2);
		index += 2;
		
		byte[] length = new byte[2];
//		length[0] = 0x03;
//		length[1] = 0x04;
		length[0] = 0x00;
		length[1] = 0x06;
		
		System.arraycopy(length, 0, result, index, 2);
		index += 2;
		
		// tcp port는 0x01고
		byte[] unit_id = new byte[1];
		unit_id[0] = 0x01;
		
		System.arraycopy(unit_id, 0, result, index, 1);
		index += 1;
		
		byte[] function_code = new byte[1];
//		function_code[0] = 0x04;
		function_code[0] = 0x03;
		
		System.arraycopy(function_code, 0, result, index, 1);
		index += 1;
		
		byte[] start_address = new byte[2];
		start_address[0] = 0x00;
//		start_address[1] = 0x00;
//		start_address[1] = 0x1e;
		start_address[1] = 0x13;
		
		System.arraycopy(start_address, 0, result, index, 2);
		index += 2;
		
		byte[] end_address = new byte[2];
		end_address[0] = 0x00;
//		end_address[1] = 0x14;
		end_address[1] = 0x02;
		
		System.arraycopy(end_address, 0, result, index, 2);
		index += 2;
		
		return result;
	}
	
	public byte[] writeValue() throws Exception {
		
		byte[] result = new byte[12];
		int index = 0;
		
		byte[] transaction_id = new byte[2];
		transaction_id[0] = 0x00;
		transaction_id[1] = 0x00;
		
		System.arraycopy(transaction_id, 0, result, index, 2);
		index += 2;
		
		byte[] protocol_id = new byte[2];
		protocol_id[0] = 0x00;
		protocol_id[1] = 0x00;
		
		System.arraycopy(protocol_id, 0, result, index, 2);
		index += 2;
		
		byte[] length = new byte[2];
		length[0] = 0x00;
		length[1] = 0x06;
		
		System.arraycopy(length, 0, result, index, 2);
		index += 2;
		
		// tcp port는 0x01고
		byte[] unit_id = new byte[1];
		unit_id[0] = 0x01;
		
		System.arraycopy(unit_id, 0, result, index, 1);
		index += 1;
		
		byte[] function_code = new byte[1];
		function_code[0] = 0x06;
		
		System.arraycopy(function_code, 0, result, index, 1);
		index += 1;
		
		byte[] start_address = new byte[2];
		start_address[0] = 0x00;
		start_address[1] = 0x13;
		
		System.arraycopy(start_address, 0, result, index, 2);
		index += 2;
		
		byte[] write_data = new byte[2];
		write_data[0] = 0x05;
		write_data[1] = 0x2b;
		
		System.arraycopy(write_data, 0, result, index, 2);
		index += 2;
		
		return result;
	}
	
	public void outPLC(byte[] payload) throws Exception {
		
		_out.write(payload);	_out.flush();
		
	}
	
	public void inPLC() throws Exception {
		
		byte[] _res = new byte[50];
			_in.read(_res);
			
			System.out.println(BytesTest.byte2HexPad(_res));
		
	}
	
	
	private final static class test {
		public static void main(String[] args) throws Exception {
			try {
				_test_PLCTEST test = new _test_PLCTEST();
				
//				System.out.println(BytesTest.byte2HexPad(test.writeValue()));
				System.out.println(BytesTest.byte2HexPad(test.readValue()));
				
				test.outPLC(test.readValue());
//				test.outPLC(test.writeValue());
				test.inPLC();
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
	}
	
}
