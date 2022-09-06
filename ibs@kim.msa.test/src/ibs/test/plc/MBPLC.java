package ibs.test.plc;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.util.HashMap;

import ibs.test.signal.SignalIF;
import ibs.test.util.BytesTest;

public abstract class MBPLC {

	public String serial_no;
	public String comm_code;
	
	public final static int MBAP_SIZE = 7;
	public static final int MEM_LENGTH = 2;
	
	// 2 byte
	public static final byte[] TRANSACTION = {0x00, 0x00};
	// MODBUS-TCP는 0x0000
	public static final byte[] PROTOCOL = {0x00, 0x00};
	// 2 byte
	public byte[] length;

	// TCP 통신 0x01로 고정
	public byte unit_id = 0x01;
	public byte function_code;
	

	// 시작 메모리는 0으로 시작하지 않음 (1 ~ 9999, 10001 ~ 19999, 30001 ~ 40001, 40001 ~ 49999)
	// 0으로 지정하면 메모리는 1로잡힘 (예 : start_address를 50으로 지정하면 메모리는 51로 잡힘)
	public int start_address;
	
	public abstract void setSignal(HashMap<String, Object> params);
	
	public abstract byte[] setReq();
	
	public void write(DataOutputStream _out) throws Exception {
		byte[] _req = setReq();
		_out.write(_req);	_out.flush();
	}
	
	
	public void read(DataInputStream _in) throws Exception {
		byte[] size = new byte[MBAP_SIZE];
		
		
		byte[] _res = new byte[MBAP_SIZE];
		
		_in.read(_res);
		
		System.out.println(BytesTest.byte2HexPad(_res));
	}
	
}
