package ibs.test.bus;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

import ibs.test.util.BytesTest;

public class _test_PLCTEST {
	String add = "192.168.1.80";
	
	public final static int MBAP_SIZE = 7;
	
	public int transaction_id;
	
	public int protocol_id;
	
	public int length;
	
	public int unit_id;
	
	public int function_code;

	public Socket _so;
	public DataInputStream  _in;
	public DataOutputStream _out;
	
	public _test_PLCTEST() {
		try {
			_so = new Socket("192.168.1.80", 502);
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
		start_address[1] = 0x1e;
		
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
		
//		byte[] header = new byte[8];
//		
//			int i = 0;
//			do {
//				i += _in.read(header,i,8-i);
//			}while(i<8);
//		
//		System.out.println(BytesTest.byte2HexPad(header));
//		
//		for (int index=0; index< 10; index++) {
		byte[] _res = new byte[50];
			_in.read(_res);
			
			System.out.println(BytesTest.byte2HexPad(_res));
			
//		}
		
	}
	
	public byte[] isEndian(byte[] payload) throws Exception {
		byte[] result = new byte[payload.length];
		
		for (int index = 0; index < payload.length; index += 2) {
			result[index] = payload[payload.length - index - 2];
			result[index + 1] = payload[payload.length- index - 1];
		}
		
		return result;
	}
	
	
	private final static class test {
		public static void main(String[] args) throws Exception {
			try {
				_test_PLCTEST test = new _test_PLCTEST();
				
//				System.out.println(BytesTest.byte2HexPad(test.readValue()));
				
				System.out.println(BytesTest.byte2HexPad(test.writeValue()));
//				System.out.println(BytesTest.byte2HexPad(test.readValue()));
//				System.out.println(BytesTest.byte2HexPad(BytesTest.chgEndian(test.writeValue())));
				
//				test.outPLC(BytesTest.chgEndian(test.writeValue()));
//				test.outPLC(test.isEndian(test.readValue()));
//				test.outPLC(test.readValue());
				test.outPLC(test.writeValue());
				test.inPLC();
				
				
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
	}
	
}
